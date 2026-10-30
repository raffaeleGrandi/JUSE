package testRobots3;

import jusePack.structures.ScenarioManager;
import jusePack.utils.Position;

public class ScenarioTest03 extends ScenarioManager {

	public ScenarioTest03(){
		buildRobotsVector();
		buildObstaclesVector();
	}//ScenarioBuilder
	
	private void buildRobotsVector(){		
		addRobot(new TestDummy03(1,new Position(7,7,0),2));
		addRobot(new TestDummy03(2,new Position(10,10,0),2));
	}//buildRobotsVector
	
	private void buildObstaclesVector(){
		
	}//buildObstaclesVector
	
}//end ScenarioTest01
