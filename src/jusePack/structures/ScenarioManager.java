package jusePack.structures;

import jusePack.ArenaObjects.Obstacle;
import jusePack.units.RobotDummy;

public class ScenarioManager {

	ObstaclesVector obstVector;
	RobotDummyVector dummyVector;
	public boolean unitsMNGset = false;
		
	public ScenarioManager(){
		dummyVector = new RobotDummyVector();
		obstVector = new ObstaclesVector();		
	}//ScenarioBuilder
	
	public void addRobot(RobotDummy robot){	dummyVector.add(robot); }
	
	public void addObstacle(Obstacle obstacle){ obstVector.add(obstacle);}
	
	public RobotDummyVector getRobotsVector(){ return dummyVector; }
	
	public ObstaclesVector getObstaclesVector(){ return obstVector; }	
	
	public int getNumRobots(){ return dummyVector.size();}
	
	public int getNumObstacles(){ return obstVector.size();}
	
	public void removeRobot(int index){ dummyVector.remove(index);}
	
	/*
	public void removeObstacle(int index){
		obstVector.remove(index);
		---> Arena Refresh <----
	}
	
	public void moveObstacle(int index, Position newPos){
		obstVector.elementAt(index).setPos(newPos);
	}
	*/
	
	public void enableRobots(){
		if (unitsMNGset){
			for(int i = 0; i < dummyVector.size(); i++){
				RobotDummy dummyTemp = dummyVector.elementAt(i);
				if (dummyTemp.faultTimer!=null) dummyTemp.faultTimer.start();
				dummyTemp.start();
			}		
		}
		else { System.err.println("ERROR: unable to start robots - unitsManager not set");}
	}//end enableRobots
	
	public void disableRobots(){
		for(int i = 0; i < dummyVector.size(); i++){
			dummyVector.elementAt(i).setActive(false);
		}				
	}//end disableRobots
	
	public void disableRobot(int index){
		dummyVector.elementAt(index).setActive(false);
	}//disableRobot
	
}//end ScenarioManager
