# Examples

Each example is a scenario (arena, obstacles, robots) plus a `MainClassTestNN` that starts it.
The robot controllers are in [`../algorithms`](../algorithms/README.md).

| Scenario | Robots | Controller | Origin |
|---|---|---|---|
| [`scenario03`](scenario03) | 2, open arena | `TestDummy03` (timed motion) | original, 2011 (was `src/testRobots3`) |
| [`scenario04`](scenario04) | 1, with obstacles | `TestDummy04` (IR avoidance) | original, 2011 (was `src/testRobots4`) |
| [`scenario05`](scenario05) | 9, with obstacles | `TestDummy04` | **added 2026-10-30** |
| [`scenario06`](scenario06) | 5, with obstacles | `BraitenbergAvoider` | **added 2026-10-30** |

Scenarios marked *added* carry a dated header explaining what they are.

## Running

From the repository root (replace `04` with the scenario number):

```
javac -d bin -sourcepath src:. examples/scenario04/MainClassTest04.java
java -cp bin examples.scenario04.MainClassTest04
```

On Windows use `;` instead of `:` in the source path.

## Things worth knowing

- **Speed.** The simulator is a busy loop bound by rendering, so how fast the robots move
  depends on the computer, not on the commands they send.
- **Scenario 03:** the robots reach the edge of the arena and the pixel-based collision detection
  reads outside its image (`ArrayIndexOutOfBoundsException` in the simulation thread). Original
  behaviour, left as is.
- **Scenario 05:** with the original controller, robots that meet head-on may stay jammed.
- **Scenario 06:** the vehicles use a longer sensing range (4 cells) and touch obstacles only
  occasionally.
- Occasionally a Java2D exception may appear in the console: the physics paints the arena from a
  non-UI thread. Original behaviour as well.
