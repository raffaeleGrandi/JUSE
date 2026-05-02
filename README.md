# JUSE — Java UniBot Simulation Environment

JUSE is a lightweight, self-contained 2D simulator for **differential-drive mobile robots**, written entirely in Java. It was originally built as the software counterpart of UniBot, a real differential-drive robot co-developed at the University of Bologna, and has been used in robotics and AI courses and student theses since 2008.

The goal of this public release is to give students, researchers, and developers a **simple, hackable Java simulator** to prototype and test robot navigation algorithms — because very few maintained options of this kind exist today.

---

## Features

- **2D grid arena** (40×40 cells, configurable) with Swing-based visualization
- **Differential-drive kinematics** — exact and Runge-Kutta integration models
- **18 IR sensors + 18 bump sensors** per robot, based on geometric ray casting
- **Rectangular and oval obstacles**, with support for rotation
- **Multi-robot scenarios** — add as many robots as needed
- **Custom robot behavior** — just extend one class and implement your control loop
- **Custom scenarios** — define obstacles and initial robot positions in plain Java
- **Fault simulation** — robots can be set to auto-disable after a configurable timeout
- No external dependencies beyond the JDK — pure Java + Gradle

---

## Requirements

- Java 21 or later
- Gradle (wrapper included — no installation needed)

---

## Quick Start

```bash
git clone https://github.com/raffaeleGrandi/JUSE.git
cd JUSE
./gradlew run
```

This launches the bundled demo scenario (`testRobots4`), which shows a single robot navigating an arena with rectangular and oval obstacles using a simple reactive avoidance algorithm.

---

## How to Create Your Own Robot

Extend `RobotDummy` and implement `executeLoop()`. This method runs in its own virtual thread and contains your entire control logic:

```java
import jusePack.units.RobotDummy;
import jusePack.utils.Position;

public class MyRobot extends RobotDummy {

    public MyRobot(int id, Position startPos, double sensorRange) {
        super(id, startPos, sensorRange);
    }

    @Override
    public void executeLoop() {
        forward(500);           // set both motors forward at speed 500
        while (true) {
            int[] sensors = getAllSens();   // read all 18 IR sensors (0-255)
            if (sensors[0] < 30) {         // obstacle ahead (sensor 0 = front)
                rotateBy(45, 50);          // rotate 45° in 50ms steps
            }
            Thread.sleep(200);
        }
    }
}
```

Key methods available in your robot class:

| Method | Description |
|--------|-------------|
| `forward(speed)` / `backward(speed)` | Set linear motion |
| `setMotorSpeed(left, right)` | Direct motor control (differential drive) |
| `rotateBy(angleDeg, stepMs)` | Blocking rotation |
| `getAllSens()` | Returns all 18 IR sensor values (0 = obstacle contact, 255 = clear) |
| `getOneSen(index)` | Single sensor value |
| `getPosition()` | Current robot position (x, y, theta) |

---

## How to Create a Scenario

Extend `ScenarioManager` and define your robots and obstacles:

```java
import jusePack.ArenaObjects.*;
import jusePack.structures.ScenarioManager;
import jusePack.utils.Position;
import java.awt.*;

public class MyScenario extends ScenarioManager {

    public MyScenario() {
        // Add robots: (id, position, sensorRange)
        addRobot(new MyRobot(1, new Position(10, 10, 0), 2));
        addRobot(new MyRobot(2, new Position(20, 20, Math.toRadians(90)), 2));

        // Add obstacles: (type, color, position, dimension)
        addObstacle(new Obstacle(ArenaObjectType.OBSTACLE_RECT,
                Color.GRAY, new Position(15, 15, 45), new Dimension(6, 3)));
        addObstacle(new Obstacle(ArenaObjectType.OBSTACLE_OVAL,
                Color.WHITE, new Position(25, 10, 0), new Dimension(4, 4)));
    }
}
```

Then wire it up in your main class:

```java
public class Main {
    public static void main(String[] args) {
        MyScenario scenario = new MyScenario();
        JuseManager sim = new JuseManager(new Point(50, 50), "My Simulation", scenario);
        sim.startSim(2);   // start after 2-second delay
    }
}
```

---

## Project Structure

```
src/
├── main/java/
│   ├── jusePack/               # Core simulator
│   │   ├── JuseManager.java    # Main entry point — wires scenario, GUI, physics
│   │   ├── gui/                # Swing-based visualization
│   │   ├── structures/         # ScenarioManager, UnitsManager (physics loop)
│   │   ├── units/              # RobotUnit, RobotDummy, UnitObject
│   │   │   └── collisions/     # Ray casting: CollidableShape, RectShape, OvalShape
│   │   ├── ArenaObjects/       # Obstacle, ObjectID, ArenaObjectType
│   │   └── utils/              # Const, Position, LogArea
│   └── testRobots4/            # Example scenario (bundled demo)
└── test/java/                  # Unit tests (JUnit 5)
```

---

## Sensor Layout

Sensors are numbered 0–17, evenly spaced at 20° intervals starting from the robot's heading direction (sensor 0 = front):

```
        [17][0][1]
      [16]       [2]
    [15]           [3]
    [14]    (R)    [4]
    [13]           [5]
      [12]       [6]
        [11][10][7]
            [9][8]
```

IR sensors return values from 0 (obstacle at contact distance) to 255 (no obstacle within range). Bump sensors activate when an obstacle is within physical contact range.

---

## Background

JUSE was born in April 2008 as a simulation tool developed alongside Raffaele Grandi's M.Sc. thesis in Computer Science & Software Engineering at the **University of Bologna**. The thesis focused on coordinating mobile robot swarms using Particle Swarm Optimization — JUSE was the environment in which those algorithms were designed and tested before running on real hardware.

The key design decision — modeling each simulated robot as an independent agent in the Multi-Agent Systems sense, with separate layers of computation from logical to physical — made JUSE flexible enough to be adopted in university courses on Robotics and Artificial Intelligence, and as a platform for several student theses.

Development continued during Raffaele's PhD (2010–2013) in Automation & Operational Research, where JUSE supported research on multi-robot coordination, swarm intelligence, and consensus-based control. During this period, **UniBot** — a physical differential-drive robot co-developed in the lab — became JUSE's real-world counterpart, and the two platforms were used together for teaching and research.

This public release brings the codebase up to modern Java (21), replaces the original pixel-based collision detection with a clean geometric ray casting model, and opens the project to external contributors.

If you use JUSE in academic work, you may reference the following:

> R. Grandi — *"Coordination of a mobile robots swarm by using Particle Swarm Optimization algorithms"* — M.Sc. Thesis, University of Bologna, School of Engineering and Architecture, 2009.

> R. Grandi, R. Falconi, C. Melchiorri — *"UniBot Remote Laboratory: A Scalable Web-Based Set-up for Education and Experimental Activities in Robotics"* — IFAC World Congress 2011, Milan, Italy. DOI: [10.3182/20110828-6-IT-1002.03103](https://doi.org/10.3182/20110828-6-IT-1002.03103)

---

## Contributing

Contributions are welcome. If you find a bug, have a feature idea, or want to improve the documentation, open an issue or submit a pull request.

Some known areas for future work:
- Robot-to-robot detection in the geometric collision model
- Configurable sensor count and layout
- Scenario loading from file (JSON/XML)
- Headless mode for batch experiments

---

## License

This project is licensed under the terms of the [LICENSE](LICENSE) file included in the repository.

---

*Originally developed at the University of Bologna, Department of Computer Science and Engineering.*
