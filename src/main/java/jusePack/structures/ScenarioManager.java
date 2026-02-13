package jusePack.structures;

import java.util.ArrayList;
import java.util.List;

import jusePack.ArenaObjects.Obstacle;
import jusePack.units.RobotDummy;

public class ScenarioManager {

	List<Obstacle> obstVector;
	List<RobotDummy> dummyVector;
	public boolean unitsMNGset = false;

	public ScenarioManager(){
		dummyVector = new ArrayList<>();
		obstVector = new ArrayList<>();
	}//ScenarioBuilder

	public void addRobot(RobotDummy robot){	dummyVector.add(robot); }

	public void addObstacle(Obstacle obstacle){ obstVector.add(obstacle);}

	public List<RobotDummy> getRobotsVector(){ return dummyVector; }

	public List<Obstacle> getObstaclesVector(){ return obstVector; }

	public int getNumRobots(){ return dummyVector.size();}

	public int getNumObstacles(){ return obstVector.size();}

	public void removeRobot(int index){ dummyVector.remove(index);}

	/*
	public void removeObstacle(int index){
		obstVector.remove(index);
		---> Arena Refresh <----
	}

	public void moveObstacle(int index, Position newPos){
		obstVector.get(index).setPos(newPos);
	}
	*/

	public void enableRobots(){
		if (unitsMNGset){
			for(int i = 0; i < dummyVector.size(); i++){
				RobotDummy dummyTemp = dummyVector.get(i);
				if (dummyTemp.faultTimer!=null) dummyTemp.faultTimer.start();
				dummyTemp.start();
			}
		}
		else { System.err.println("ERROR: unable to start robots - unitsManager not set");}
	}//end enableRobots

	public void disableRobots(){
		for(int i = 0; i < dummyVector.size(); i++){
			dummyVector.get(i).setActive(false);
		}
	}//end disableRobots

	public void disableRobot(int index){
		dummyVector.get(index).setActive(false);
	}//disableRobot

}//end ScenarioManager
