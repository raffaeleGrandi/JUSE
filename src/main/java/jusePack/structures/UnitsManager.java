package jusePack.structures;

import java.util.List;

import jusePack.units.*;
import jusePack.units.collisions.CollisionDetector;
import jusePack.utils.Const;

/**
 * Gestisce il loop fisico della simulazione su un virtual thread dedicato.
 *
 * Con il rilevamento collisioni geometrico, il loop è completamente
 * disaccoppiato dalla GUI: non è più necessario catturare immagini
 * dal pannello né sincronizzarsi con l'EDT di Swing via invokeAndWait.
 * Il collision detector viene chiamato ogni ciclo per ogni robot attivo —
 * essendo puro calcolo geometrico, il costo è trascurabile rispetto al
 * timeSample (~100ms).
 */
public class UnitsManager implements Runnable {

	private Thread thread;
	private List<RobotDummy> unitsVector;
	private CollisionDetector cd;
	private volatile boolean activeStatus = false;
	private double timeSample = Const.timeSample;

	public UnitsManager(CollisionDetector collDetect) {
		cd = collDetect;
	}

	public void setUnitsVector(List<RobotDummy> dummyVec) {
		unitsVector = dummyVec;
	}

	public void start() {
		thread = Thread.ofVirtual().name("units-manager").start(this);
	}

	public void stop() {
		activeStatus = false;
		if (thread != null) thread.interrupt();
	}

	public void run() {
		activeStatus = true;
		while (activeStatus) {
			for (int i = 0; i < unitsVector.size(); i++) {
				RobotUnit tempUnit = unitsVector.get(i);
				if (tempUnit.isActive()) {
					cd.testUnitForCollisions(tempUnit.getUnitObject());
					tempUnit.evaluateMovement(timeSample);
				}
			}
			try {
				Thread.sleep((long)(timeSample * 1000));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}

}
