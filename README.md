# JUSE

> **This is the historical version of JUSE (2008–2011), kept as it was.**
> Development continues in **[Pianura](https://github.com/raffaeleGrandi/Pianura)**, its modern evolution.

author: Raffaele Grandi
period: April 2008 - July 2011
description: 
Java UniBot Simulation Environment (J.U.S.E.) was designed as a stand-alone simulator for differential wheeled mobile robots, self-made and completely based on Java technologies. The basic idea of J.U.S.E. was to create the simulation counterpart of the real robot Unibot, where users had the possibility to test algorithms on their computers before testing them on real robots. From the earliest versions of J.U.S.E., each simulated robot was designed as an agent, considered in the meaning of Multi Agents System terminology, with different layers of computation, from the logical to the physical one. At the beginning J.U.S.E. was developed as a support to my master thesis but the simplicity of the environment and the java-based architecture was a good choice in order to adopt J.U.S.E. as an instrument in the courses of Robotics and Artificial
Intelligence and for many other thesis.

## Running it today

The code is unchanged since 2011 and still compiles and runs on a current JDK
(tested with 21 and 25). From the repository root:

```
javac -d bin -sourcepath src src/testRobots4/MainClassTest04.java
java -cp bin testRobots4.MainClassTest04
```

The second example works the same way with `testRobots3/MainClassTest03`.

Notes:

- the compiler warns that `new Double(...)` in `ArenaPanel.java` is deprecated; the
  warning is harmless;
- in `testRobots3` the robots can reach the edge of the arena: the original pixel-based
  collision detection then reads outside its image and the simulation thread stops with
  an `ArrayIndexOutOfBoundsException`. This is how JUSE behaved in 2011 and is left as is;
  Pianura replaced that collision model with geometric ray casting.
