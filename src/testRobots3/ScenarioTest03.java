package testRobots3;

import java.awt.Color;
import java.awt.Dimension;

import jusePack.ArenaObjects.Obstacle;
import jusePack.structures.ScenarioManager;
import jusePack.utils.Const;
import jusePack.utils.Position;

public class ScenarioTest03 extends ScenarioManager {

	public ScenarioTest03(){
		buildRobotsVector();
		buildObstaclesVector();
	}//ScenarioBuilder
	
	private void buildRobotsVector(){		
		addRobot(new TestDummy03(1,new Position(7,7,0),2));
		addRobot(new TestDummy03(2,new Position(10,10,0),2));
		/*	addRobot(new TestDummy02(3,new Position(11,12,0),2));		
		addRobot(new TestDummy02(4,new Position(5,15,0),2));		
		addRobot(new TestDummy02(5,new Position(17,19,0),2));		
		addRobot(new TestDummy02(6,new Position(21,30,0),2));		
		addRobot(new TestDummy02(7,new Position(30,18,0),2));		
		addRobot(new TestDummy02(8,new Position(31,12,0),2));		
		addRobot(new TestDummy02(9,new Position(33,12,0),2));
		*/		
	}//buildRobotsVector
	
	private void buildObstaclesVector(){
		/*
		addObstacle(new Obstacle(Const.typeObstRect,Color.YELLOW,new Position(10,5,30),new Dimension(10,10)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(30,5,0),new Dimension(3,3)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(20,5,0),new Dimension(3,3)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(20,15,0),new Dimension(3,3)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(11,15,0),new Dimension(3,3)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(30,5,0),new Dimension(3,3)));
		*/
		
	}//buildObstaclesVector
	
}//end ScenarioTest01
