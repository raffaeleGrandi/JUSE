# Algorithms

Robot controllers that run on the JUSE simulator. Each one is a subclass of
`RobotDummy` and is used by one or more scenarios in [`../examples`](../examples/README.md).

| Folder | Controller | Origin | Used by |
|---|---|---|---|
| [`timedmotion/`](timedmotion) | `TestDummy03` | original, 2011 | scenario 03 |
| [`iravoidance/`](iravoidance) | `TestDummy04` | original, 2011 | scenarios 04, 05 |
| [`braitenberg/`](braitenberg) | `BraitenbergAvoider` | **added 2026-10-30** | scenario 06 |

Everything marked *original* is the code that shipped with JUSE (the file names are the
ones it always had). Everything marked *added* was written later and says so in its header.

## timedmotion: `TestDummy03`

*Original.* Open-loop motion: the robot runs a fixed sequence of wheel speeds, each for a fixed
time, then stops. No sensors are read.

| Step | left / right command | duration |
|---|---|---|
| 1 | 1.5 / 1.5 | 2 s |
| 2 | 1.5 / 6 | 2 s |
| 3 | 2 / 6 | 1 s |
| 4 | 2 / 2 | 4 s |

The durations are real time (`Thread.sleep`), after the last step the robot is deactivated.
Because the simulator runs as fast as the machine can draw, the distance covered in those
seconds depends on the computer.

Run it with `examples/scenario03` (see the examples README).

## iravoidance: `TestDummy04`

*Original.* Reactive obstacle avoidance on the infrared sensors.

- if any of the sensors 0–4 (front-right) reads below 25 px, the robot turns counter-clockwise;
- if any of the sensors 14–17 (front-left) reads below 25 px, it turns clockwise;
- otherwise it goes straight, and the forward speed grows by 0.001 per loop; the ramp is reset
  when the way is clear again.

The controller has no way out of a head-on contact: two robots facing each other, or a robot
pinned against a wall, can stay jammed. That is visible in scenario 05, and it is how the
original behaves.

## braitenberg: `BraitenbergAvoider`

***Added 2026-10-30, not part of the original JUSE.*** A Braitenberg "fear" vehicle
(V. Braitenberg, *Vehicles: Experiments in Synthetic Psychology*, MIT Press, 1984) on the
18 IR sensors: every sensor is wired to the wheels with a fixed weight and the behaviour
comes from the wiring alone.

- lateral wiring (skewed by 25° so that a head-on obstacle breaks the symmetry): an obstacle on
  one side drives the vehicle away from it;
- frontal wiring: an obstacle ahead slows both wheels;
- after a collision the vehicle rotates away from the side it was steering to.

It needs a sensing range of about 4 cells (the original examples use 2) to have time to react
at JUSE's speed. In a 30-second headless test with five vehicles (scenario 06) contact time
was around 1 % or less: it avoids obstacles well, not perfectly.

Constants (`CRUISE`, `STEER_GAIN`, `BRAKE_GAIN`, `SKEW`, `ESCAPE_DEG`) are at the top of the
class and can be changed to see how the behaviour follows the wiring.

## Adding an algorithm

Create a folder here, extend `RobotDummy`, put a short header in the class saying what it is, who
wrote it and when, and add a row to the table above. Algorithms recovered from older work are
welcome: mark them *original* only if the code is really the old one.
