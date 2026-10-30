package examples.scenario06;

import java.awt.Color;
import java.awt.Dimension;

import algorithms.braitenberg.BraitenbergAvoider;
import jusePack.ArenaObjects.Obstacle;
import jusePack.structures.ScenarioManager;
import jusePack.utils.Const;
import jusePack.utils.Position;

/*
 * Scenario 06: five Braitenberg-style vehicles (algorithms/braitenberg) in the
 * arena of scenario 05. The vehicles have a sensing range of 4 cells (the original
 * examples use 2): at JUSE's speed a longer range gives them time to react.
 *
 * ADDED on 2026-10-30. This scenario is NOT part of the original JUSE (2008-2011).
 */
public class ScenarioTest06 extends ScenarioManager {

	public ScenarioTest06(){
		buildRobotsVector();
		buildObstaclesVector();
	}//ScenarioBuilder

	private void buildRobotsVector(){
		addRobot(new BraitenbergAvoider(1,new Position(7,17,Math.toRadians(90)),4));
		addRobot(new BraitenbergAvoider(2,new Position(17,19,0),4));
		addRobot(new BraitenbergAvoider(3,new Position(21,30,0),4));
		addRobot(new BraitenbergAvoider(4,new Position(30,18,0),4));
		addRobot(new BraitenbergAvoider(5,new Position(33,12,0),4));
	}//buildRobotsVector

	private void buildObstaclesVector(){
		addObstacle(new Obstacle(Const.typeObstRect,Color.ORANGE,new Position(10,5,30),new Dimension(10,10)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(30,5,0),new Dimension(3,3)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(20,5,0),new Dimension(3,3)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(20,15,0),new Dimension(4,4)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(11,15,0),new Dimension(3,3)));
		addObstacle(new Obstacle(Const.typeObstRect,Color.WHITE,new Position(20,35,20),new Dimension(5,8)));
		addObstacle(new Obstacle(Const.typeObstRect,Color.RED,new Position(30,37,30),new Dimension(2,15)));
		addObstacle(new Obstacle(Const.typeObstRect,Color.BLUE,new Position(30,25,10),new Dimension(7,7)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(8,28,0),new Dimension(6,3)));
	}//buildObstaclesVector

}//end ScenarioTest06
