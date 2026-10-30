package testRobots4;

import java.awt.Color;
import java.awt.Dimension;

import jusePack.ArenaObjects.Obstacle;
import jusePack.structures.ScenarioManager;
import jusePack.utils.Const;
import jusePack.utils.Position;

public class ScenarioTest04 extends ScenarioManager {

	public ScenarioTest04(){
		buildRobotsVector();
		buildObstaclesVector();
	}//ScenarioBuilder
	
	private void buildRobotsVector(){		
		addRobot(new TestDummy04(1,new Position(7,17,Math.toRadians(90)),2));
				
	}//buildRobotsVector
	
	private void buildObstaclesVector(){		
		
		addObstacle(new Obstacle(Const.typeObstRect,Color.YELLOW,new Position(10,5,30),new Dimension(10,10)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(30,5,0),new Dimension(3,3)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(20,5,0),new Dimension(3,3)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(20,15,0),new Dimension(4,4)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(11,15,0),new Dimension(3,3)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(30,5,0),new Dimension(3,3)));
		addObstacle(new Obstacle(Const.typeObstRect,Color.WHITE,new Position(20,35,20),new Dimension(5,8)));
		addObstacle(new Obstacle(Const.typeObstRect,Color.RED,new Position(30,37,30),new Dimension(2,15)));
		addObstacle(new Obstacle(Const.typeObstRect,Color.BLUE,new Position(30,25,10),new Dimension(7,7)));
		addObstacle(new Obstacle(Const.typeObstOval,Color.WHITE,new Position(8,28,0),new Dimension(6,3)));
	}//buildObstaclesVector
	
}//end ScenarioTest01
