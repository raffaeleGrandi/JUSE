package jusePack.units.collisions;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import jusePack.ArenaObjects.Obstacle;
import jusePack.ArenaObjects.ObjectID;
import jusePack.units.UnitObject;
import jusePack.utils.Const;
import jusePack.utils.Position;

/**
 * Rilevamento collisioni basato su ray casting geometrico.
 *
 * Sostituisce il precedente approccio pixel-based (cattura BufferedImage
 * dalla GUI + campionamento colori) con test geometrici puri sulle forme
 * degli ostacoli. Questo elimina la dipendenza da SwingUtilities.invokeAndWait()
 * nel loop fisico, disaccoppiando completamente la simulazione dalla GUI.
 *
 * Per ogni robot vengono lanciati 18 raggi (uno per sensore) dall'origine
 * del robot nella direzione assoluta del sensore. Per ogni raggio si cerca
 * l'intersezione più vicina con gli ostacoli della scena e con i bordi
 * dell'arena. Il valore del sensore è la distanza in pixel dalla superficie
 * del robot all'ostacolo rilevato.
 *
 * Nota: nel sistema originale i robot potevano rilevare altri robot tramite
 * i pixel colorati. In questo modello geometrico i robot testano solo gli
 * ostacoli statici. Il rilevamento robot-robot può essere aggiunto in futuro
 * passando le posizioni degli altri robot come forme circolari temporanee.
 */
public class CollisionDetector {

    private final List<CollidableShape> shapes;
    private final double robotRadiusPx;
    private final double bumpRangePx;
    private final double cellSizePx;

    /**
     * @param obstacles lista degli ostacoli dello scenario corrente
     */
    public CollisionDetector(List<Obstacle> obstacles) {
        this.robotRadiusPx = Const.unitRadius;
        this.bumpRangePx   = Const.sensorsCheckingRange0;
        this.cellSizePx    = Const.cellSize;
        this.shapes        = buildShapes(obstacles);
    }

    // ---------------------------------------------------------
    //  Costruzione delle forme geometriche dalla lista ostacoli
    // ---------------------------------------------------------

    private List<CollidableShape> buildShapes(List<Obstacle> obstacles) {
        List<CollidableShape> result = new ArrayList<>();

        // Bordi dell'arena come rettangoli sottili axis-aligned (spessore 2px)
        double aw = Const.arenaWidth;
        double ah = Const.arenaHeight;
        result.add(new RectShape(0,      ah / 2, 2, ah, 0));  // bordo sinistro
        result.add(new RectShape(aw,     ah / 2, 2, ah, 0));  // bordo destro
        result.add(new RectShape(aw / 2, 0,      aw, 2, 0));  // bordo superiore
        result.add(new RectShape(aw / 2, ah,     aw, 2, 0));  // bordo inferiore

        for (Obstacle obs : obstacles) {
            ObjectID id = obs.getID();
            // Posizione centro in pixel (tutte le forme usano il centro come origine)
            double px    = id.getPos().x * cellSizePx;
            double py    = id.getPos().y * cellSizePx;
            double w     = id.getDim().width  * cellSizePx;
            double h     = id.getDim().height * cellSizePx;
            double angle = id.getAngle();

            switch (id.getType()) {
                case OBSTACLE_RECT -> result.add(new RectShape(px, py, w, h, angle));
                case OBSTACLE_OVAL -> result.add(new OvalShape(px, py, w, h));
                default -> {}
            }
        }
        return result;
    }

    // ---------------------------------------------------------
    //  Aggiornamento sensori per un robot
    // ---------------------------------------------------------

    /**
     * Calcola i valori di tutti i sensori del robot tramite ray casting.
     * Aggiorna bump sensors (contatto fisico) e IR sensors (campo di rilevamento).
     */
    public void testUnitForCollisions(UnitObject testUnit) {
        SensorArray bumpSensors = testUnit.getBumpSensors();
        SensorArray irSensors   = testUnit.getIrSensors();
        bumpSensors.resetSensors();
        irSensors.resetSensors();

        double sensorRangePx = cellSizePx * testUnit.getSensorRange();
        double maxRayDist    = robotRadiusPx + sensorRangePx;

        Position pos   = testUnit.getExactPos();
        double cx      = pos.x * cellSizePx;
        double cy      = pos.y * cellSizePx;
        double heading = pos.theta;

        if (Const.debugEnabled)
            System.out.println("\nCD>rilevamento unità #" + testUnit.getIDnum()
                + " pos:(" + pos.x + "," + pos.y + ") range:" + sensorRangePx + "px");

        for (int i = 0; i < Const.sensorCount; i++) {
            double sensorAngle = heading + irSensors.getSensorAngle(i);
            double dx = Math.cos(sensorAngle);
            double dy = Math.sin(sensorAngle);

            double nearestDist = findNearestHit(cx, cy, dx, dy, maxRayDist);

            if (nearestDist >= 0) {
                // Distanza dalla superficie del robot (clamped a 0 se il robot sovrappone l'ostacolo)
                int surfaceDist = (int) Math.max(0, nearestDist - robotRadiusPx);

                // IR sensor: distanza in pixel dalla superficie del robot
                irSensors.setSensorValue(i, surfaceDist);
                irSensors.setCheckingPoint(i, new Point2D.Double(
                    (cx + nearestDist * dx) / cellSizePx,
                    (cy + nearestDist * dy) / cellSizePx
                ));

                // Bump sensor: attivo solo se l'ostacolo è entro il range di contatto fisico
                if (surfaceDist < bumpRangePx) {
                    // Garantiamo valore >= 1 affinché il counter di SensorArray.isEmpty() funzioni
                    bumpSensors.setSensorValue(i, Math.max(1, surfaceDist));
                    bumpSensors.setCheckingPoint(i, irSensors.getCheckPoint(i));
                    if (Const.debugEnabled)
                        System.out.println("CD>BumpSensor #" + i
                            + " angolo:" + String.format("%.1f", Math.toDegrees(irSensors.getSensorAngle(i)))
                            + "° dist:" + surfaceDist + "px");
                }
            }
            // Se nessun ostacolo entro maxRayDist, i sensori mantengono il valore di reset:
            // 0 per bump (nessun contatto), 255 per IR (nessun ostacolo nel campo)
        }

        if (Const.debugEnabled)
            System.out.println("CD>fine rilevamento unità #" + testUnit.getIDnum());
    }

    // ---------------------------------------------------------
    //  Ray casting contro tutte le forme della scena
    // ---------------------------------------------------------

    private double findNearestHit(double ox, double oy,
                                   double dx, double dy,
                                   double maxDist) {
        double nearest = -1;
        for (CollidableShape shape : shapes) {
            double d = shape.rayIntersect(ox, oy, dx, dy, maxDist);
            if (d >= 0 && (nearest < 0 || d < nearest)) {
                nearest = d;
            }
        }
        return nearest;
    }

}
