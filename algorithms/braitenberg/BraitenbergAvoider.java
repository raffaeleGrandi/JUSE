package algorithms.braitenberg;

import jusePack.units.RobotDummy;
import jusePack.utils.Const;
import jusePack.utils.Position;

/*
 * Braitenberg-style obstacle avoidance.
 *
 * ADDED on 2026-10-30. This class is NOT part of the original JUSE (2008-2011);
 * it was written for the historical edition of the repository, to show the
 * classic reactive "vehicle" idea on JUSE's 18 infrared sensors.
 *
 * Idea (V. Braitenberg, "Vehicles: Experiments in Synthetic Psychology", MIT Press, 1984):
 * the controller has no state and no plan. Every sensor is wired to the wheels with a
 * fixed weight, and the behaviour comes entirely from the wiring:
 *   - lateral wiring: an obstacle on one side speeds up the wheel on that side,
 *     so the vehicle veers away from it (the "fear" vehicle);
 *   - frontal wiring: an obstacle ahead slows both wheels down.
 * The lateral wiring is skewed by a few degrees on purpose: a perfectly symmetric
 * vehicle facing an obstacle head-on has no reason to turn either way.
 *
 * Sensor values in JUSE: 255 = nothing in range, otherwise the distance in pixels.
 * Sensors are numbered clockwise from the heading, 20 degrees apart.
 */
public class BraitenbergAvoider extends RobotDummy {

	private static final double CRUISE = 1.0;       // wheel command of both wheels with nothing in range
	private static final double STEER_GAIN = 4.0;   // how strongly the lateral wiring turns the vehicle
	private static final double BRAKE_GAIN = 0.9;   // how strongly the frontal wiring slows it down
	private static final double SKEW = Math.toRadians(25);
	private static final int ESCAPE_DEG = 40;       // turn on the spot after a contact
	private static final int TICK_MS = 1;

	private final double[] steerWeight = new double[Const.numSensori];
	private final double[] brakeWeight = new double[Const.numSensori];
	private double lastSteer = 0;

	public BraitenbergAvoider(int unitIDnum, Position unitPos, double sensRange) {
		super(unitIDnum, unitPos, sensRange);
		wire();
	}

	/* The "wiring": one fixed weight per sensor, computed once from the sensor angle. */
	private void wire() {
		double step = 2 * Math.PI / Const.numSensori;
		for (int i = 0; i < Const.numSensori; i++) {
			double a = i * step;
			boolean ahead = Math.cos(a) > -0.2;     // sensors on the rear half are not wired
			steerWeight[i] = ahead ? Math.sin(a + SKEW) : 0;
			brakeWeight[i] = ahead ? Math.max(0, Math.cos(a)) : 0;
		}
	}

	@Override
	public void executeLoop() {
		double range = getUnitObject().sensorRange * Const.latoCasella;   // sensing range in pixels
		while (isActive()) {
			if (collisionStatus) {                  // touched something: turn away on the spot
				rotateBy(lastSteer >= 0 ? -ESCAPE_DEG : ESCAPE_DEG, TICK_MS);
				continue;
			}
			int[] ir = getAllSens();
			double steer = 0, brake = 0;
			for (int i = 0; i < ir.length; i++) {
				double d = ir[i];
				double activation = (d < range) ? (range - d) / range : 0;   // 0 = far, 1 = touching
				steer += steerWeight[i] * activation;
				brake += brakeWeight[i] * activation;
			}
			steer = Math.max(-1, Math.min(1, steer));       // > 0: obstacle on the right
			brake = Math.max(0, Math.min(1, brake));
			if (steer != 0) lastSteer = steer;

			double forward = CRUISE * (1 - BRAKE_GAIN * brake);
			double turn = CRUISE * STEER_GAIN * steer;
			setMotorSpeed(Math.max(0, forward - turn), Math.max(0, forward + turn));   // left, right
			pause(TICK_MS);
		}
	}//executeLoop

	private void pause(int ms) {
		try { Thread.sleep(ms); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}//pause

}//end BraitenbergAvoider
