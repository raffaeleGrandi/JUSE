package jusePack.units.collisions;

/**
 * Ellissi asse-allineata per il rilevamento collisioni geometrico.
 * La posizione è il centro dell'ellissi (in pixel).
 *
 * L'intersezione raggio-ellissi viene calcolata sostituendo
 * l'equazione parametrica del raggio nell'equazione dell'ellissi
 * e risolvendo la quadratica risultante.
 *
 * Con origine (ox, oy) e direzione unitaria (dx, dy):
 *   A·t² + B·t + C = 0
 * dove:
 *   A = dx²/a² + dy²/b²
 *   B = 2·(rx·dx/a² + ry·dy/b²)    rx = ox - cx
 *   C = rx²/a² + ry²/b² - 1        ry = oy - cy
 *
 * Il parametro t corrisponde alla distanza euclidea reale
 * poiché (dx, dy) è un vettore unitario.
 */
public class OvalShape implements CollidableShape {

    private final double cx, cy;  // centro in pixel
    private final double a, b;    // semi-assi in pixel

    /**
     * @param cx     centro X in pixel
     * @param cy     centro Y in pixel
     * @param width  larghezza dell'ellissi (asse orizzontale) in pixel
     * @param height altezza dell'ellissi (asse verticale) in pixel
     */
    public OvalShape(double cx, double cy, double width, double height) {
        this.cx = cx;
        this.cy = cy;
        this.a = width / 2.0;
        this.b = height / 2.0;
    }

    @Override
    public double rayIntersect(double ox, double oy, double dx, double dy, double maxDist) {
        double rx = ox - cx;
        double ry = oy - cy;

        double A = (dx * dx) / (a * a) + (dy * dy) / (b * b);
        double B = 2.0 * (rx * dx / (a * a) + ry * dy / (b * b));
        double C = (rx * rx) / (a * a) + (ry * ry) / (b * b) - 1.0;

        double disc = B * B - 4.0 * A * C;
        if (disc < 0) return -1;  // nessuna intersezione

        double sqrtDisc = Math.sqrt(disc);
        double t1 = (-B - sqrtDisc) / (2.0 * A);
        double t2 = (-B + sqrtDisc) / (2.0 * A);

        // Entrambe le intersezioni sono dietro all'origine
        if (t2 < 0) return -1;

        // Origine dentro l'ellissi (t1 < 0 < t2): intersezione immediata
        if (t1 < 0) return 0;

        return (t1 <= maxDist) ? t1 : -1;
    }

}
