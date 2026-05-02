package jusePack.units.collisions;

/**
 * Interfaccia per le forme geometriche usate nel rilevamento collisioni.
 * Il ray casting sostituisce il precedente campionamento pixel-based,
 * eliminando la dipendenza dalla GUI nel loop fisico.
 */
public interface CollidableShape {

    /**
     * Calcola l'intersezione di un raggio con questa forma.
     *
     * @param ox    coordinata X dell'origine del raggio (pixel)
     * @param oy    coordinata Y dell'origine del raggio (pixel)
     * @param dx    componente X della direzione (vettore unitario)
     * @param dy    componente Y della direzione (vettore unitario)
     * @param maxDist distanza massima di rilevamento (pixel)
     * @return distanza in pixel dall'origine al punto di intersezione,
     *         0 se l'origine è già dentro la forma,
     *         -1 se nessuna intersezione entro maxDist
     */
    double rayIntersect(double ox, double oy, double dx, double dy, double maxDist);

}
