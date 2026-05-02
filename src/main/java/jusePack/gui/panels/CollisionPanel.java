package jusePack.gui.panels;

/**
 * RIMOSSO DAL SIMULATORE
 *
 * Questa classe era originariamente usata come pannello di debug per visualizzare
 * i punti di collisione rilevati dai sensori durante la simulazione.
 *
 * L'idea di design originale (non implementata) era di usare la raccolta dei punti
 * di collisione per costruire una memoria condivisa dello sciame: ogni robot avrebbe
 * contribuito i propri punti di contatto a una mappa di densità globale, con un
 * fattore di "evaporazione" variabile nel tempo (ispirato alla stigmergia delle colonie
 * di formiche). Questa mappa avrebbe potuto essere usata per identificare zone ad alta
 * densità di ostacoli e calcolare percorsi liberi, come supporto agli algoritmi di
 * ottimizzazione basati su sciame (PSO).
 *
 * Tale funzionalità appartiene allo strato degli algoritmi dei robot,
 * non al simulatore. Se si vorrà implementarla in futuro, andrà realizzata
 * come componente separata a livello di scenario/robot e non dipenderà
 * dalla rappresentazione visiva delle collisioni.
 *
 * Il rilevamento collisioni è ora puramente geometrico (CollisionDetector)
 * e non richiede alcun pannello ausiliario.
 */
@Deprecated
public class CollisionPanel {
    private CollisionPanel() {}
}
