package jusePack.structures;

import java.util.ArrayList;
import java.util.List;

import jusePack.ArenaObjects.Obstacle;
import jusePack.units.RobotDummy;

public class ScenarioManager {

	private List<Obstacle> obstacles;
	private List<RobotDummy> robots;
	private boolean unitsManagerSet = false;

	public ScenarioManager(){
		robots = new ArrayList<>();
		obstacles = new ArrayList<>();
	}

	public void addRobot(RobotDummy robot){ robots.add(robot); }

	public void addObstacle(Obstacle obstacle){ obstacles.add(obstacle);}

	public List<RobotDummy> getRobots(){ return robots; }

	public List<Obstacle> getObstacles(){ return obstacles; }

	public int getNumRobots(){ return robots.size();}

	public int getNumObstacles(){ return obstacles.size();}

	public void removeRobot(int index){ robots.remove(index);}

	public void setUnitsManagerSet(boolean set){ this.unitsManagerSet = set; }

	public void enableRobots(){
		if (unitsManagerSet){
			for(int i = 0; i < robots.size(); i++){
				RobotDummy dummyTemp = robots.get(i);
				if (dummyTemp.getFaultTimer()!=null) dummyTemp.getFaultTimer().start();
				dummyTemp.start();
			}
		}
		else { System.err.println("ERROR: unable to start robots - unitsManager not set");}
	}

	public void disableRobots(){
		for(int i = 0; i < robots.size(); i++){
			robots.get(i).setActive(false);
		}
	}

	public void disableRobot(int index){
		robots.get(index).setActive(false);
	}

}
