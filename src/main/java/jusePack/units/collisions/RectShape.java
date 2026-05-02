package jusePack.units.collisions;

/**
 * Rettangolo ruotato per il rilevamento collisioni geometrico.
 * La posizione è il centro del rettangolo (in pixel).
 * L'angolo di rotazione è in radianti.
 *
 * Usa il metodo degli slab (AABB nel frame locale del rettangolo):
 * il raggio viene trasformato nel sistema di riferimento del rettangolo
 * (centrato nell'origine, asse-allineato), poi testato contro i due slab.
 */
public class RectShape implements CollidableShape {

    private final double cx, cy;  // centro in pixel
    private final double hw, hh;  // semi-larghezza e semi-altezza in pixel
    private final double cosA, sinA;  // cos(-angle), sin(-angle) per trasformazione inversa

    /**
     * @param cx     centro X in pixel
     * @param cy     centro Y in pixel
     * @param width  larghezza in pixel
     * @param height altezza in pixel
     * @param angle  angolo di rotazione in radianti
     */
    public RectShape(double cx, double cy, double width, double height, double angle) {
        this.cx = cx;
        this.cy = cy;
        this.hw = width / 2.0;
        this.hh = height / 2.0;
        this.cosA = Math.cos(-angle);
        this.sinA = Math.sin(-angle);
    }

    @Override
    public double rayIntersect(double ox, double oy, double dx, double dy, double maxDist) {
        // Trasformiamo il raggio nel frame locale del rettangolo (rotazione inversa)
        double rx = ox - cx;
        double ry = oy - cy;
        double lox = cosA * rx - sinA * ry;
        double loy = sinA * rx + cosA * ry;
        double ldx = cosA * dx - sinA * dy;
        double ldy = sinA * dx + cosA * dy;

        // Metodo degli slab contro AABB [-hw, hw] x [-hh, hh]
        double tmin = -Double.MAX_VALUE;
        double tmax =  Double.MAX_VALUE;

        // Slab X
        if (Math.abs(ldx) < 1e-10) {
            if (lox < -hw || lox > hw) return -1;
        } else {
            double t1 = (-hw - lox) / ldx;
            double t2 = ( hw - lox) / ldx;
            if (t1 > t2) { double tmp = t1; t1 = t2; t2 = tmp; }
            tmin = Math.max(tmin, t1);
            tmax = Math.min(tmax, t2);
            if (tmin > tmax) return -1;
        }

        // Slab Y
        if (Math.abs(ldy) < 1e-10) {
            if (loy < -hh || loy > hh) return -1;
        } else {
            double t1 = (-hh - loy) / ldy;
            double t2 = ( hh - loy) / ldy;
            if (t1 > t2) { double tmp = t1; t1 = t2; t2 = tmp; }
            tmin = Math.max(tmin, t1);
            tmax = Math.min(tmax, t2);
            if (tmin > tmax) return -1;
        }

        // La forma è completamente dietro al raggio
        if (tmax < 0) return -1;

        // Origine dentro il rettangolo: intersezione immediata
        if (tmin < 0) return (tmax <= maxDist) ? 0 : -1;

        return (tmin <= maxDist) ? tmin : -1;
    }

}
