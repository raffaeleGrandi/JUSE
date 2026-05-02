package testRobots4;

import java.awt.Color;
import java.awt.Dimension;

import jusePack.ArenaObjects.ArenaObjectType;
import jusePack.ArenaObjects.Obstacle;
import jusePack.structures.ScenarioManager;
import jusePack.utils.Position;

public class ScenarioTest04 extends ScenarioManager {

	public ScenarioTest04(){
		buildRobotsVector();
		buildObstaclesVector();
	}//ScenarioBuilder

	private void buildRobotsVector(){
		addRobot(new TestDummy04(1,new Position(7,17,Math.toRadians(90)),2));
		//addRobot(new TestDummy04(2,new Position(10,20,0),2));
		//addRobot(new TestDummy04(3,new Position(11,12,0),2));
		//addRobot(new TestDummy04(4,new Position(5,15,0),2));
		//addRobot(new TestDummy04(5,new Position(17,19,0),2));
		//addRobot(new TestDummy04(6,new Position(21,30,0),2));
		//addRobot(new TestDummy04(7,new Position(30,18,0),2));
		//addRobot(new TestDummy04(8,new Position(31,12,0),2));
		//addRobot(new TestDummy04(9,new Position(33,12,0),2));
	}//buildRobotsVector

	private void buildObstaclesVector(){
		// Rettangoli: la posizione era già il centro, invariati
		addObstacle(new Obstacle(ArenaObjectType.OBSTACLE_RECT,Color.YELLOW,new Position(10,5,30),new Dimension(10,10)));

		// Ovali: posizione aggiornata a centro (vecchio top-left + dim/2)
		// ex (30,5) dim(3,3)  → centro (31.5, 6.5)
		addObstacle(new Obstacle(ArenaObjectType.OBSTACLE_OVAL,Color.WHITE,new Position(31.5,6.5,0),new Dimension(3,3)));
		// ex (20,5) dim(3,3)  → centro (21.5, 6.5)
		addObstacle(new Obstacle(ArenaObjectType.OBSTACLE_OVAL,Color.WHITE,new Position(21.5,6.5,0),new Dimension(3,3)));
		// ex (20,15) dim(4,4) → centro (22.0, 17.0)
		addObstacle(new Obstacle(ArenaObjectType.OBSTACLE_OVAL,Color.WHITE,new Position(22.0,17.0,0),new Dimension(4,4)));
		// ex (11,15) dim(3,3) → centro (12.5, 16.5)
		addObstacle(new Obstacle(ArenaObjectType.OBSTACLE_OVAL,Color.WHITE,new Position(12.5,16.5,0),new Dimension(3,3)));
		// ex (30,5) dim(3,3)  → centro (31.5, 6.5)  [duplicato del primo oval]
		addObstacle(new Obstacle(ArenaObjectType.OBSTACLE_OVAL,Color.WHITE,new Position(31.5,6.5,0),new Dimension(3,3)));

		// Rettangoli: invariati
		addObstacle(new Obstacle(ArenaObjectType.OBSTACLE_RECT,Color.WHITE,new Position(20,35,20),new Dimension(5,8)));
		addObstacle(new Obstacle(ArenaObjectType.OBSTACLE_RECT,Color.RED,new Position(30,37,30),new Dimension(2,15)));
		addObstacle(new Obstacle(ArenaObjectType.OBSTACLE_RECT,Color.BLUE,new Position(30,25,10),new Dimension(7,7)));

		// ex (8,28) dim(6,3)  → centro (11.0, 29.5)
		addObstacle(new Obstacle(ArenaObjectType.OBSTACLE_OVAL,Color.WHITE,new Position(11.0,29.5,0),new Dimension(6,3)));
	}//buildObstaclesVector

}//end ScenarioTest04
