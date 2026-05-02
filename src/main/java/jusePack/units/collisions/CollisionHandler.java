package jusePack.units.collisions;

import jusePack.utils.Const;

public class CollisionHandler {

	private SensorArray unitSensors;

	public CollisionHandler(SensorArray bumpSensors){
		unitSensors = bumpSensors;
	}

	public boolean detectCollisions(int dir){
		boolean collisionFound;
		if(Const.debugEnabled)System.out.println(unitSensors.toString());
		if (unitSensors.isEmpty()) collisionFound = false;
		else
			if ((dir > 0) && checkFWDgroup() || (dir < 0) && checkBWDgroup()){ collisionFound = true; }
			else collisionFound = false;
		return collisionFound;
	}

	private boolean checkFWDgroup(){
		int collisionSum = 0;
		for(int j=0; j<3; j++){collisionSum += unitSensors.getSensorValue(j);}
		for(int j=16; j<Const.sensorCount; j++){collisionSum += unitSensors.getSensorValue(j);}
		return collisionSum != 0;
	}

	private boolean checkBWDgroup(){
		int collisionSum = 0;
		for(int j=7; j<12; j++){collisionSum += unitSensors.getSensorValue(j);}
		return collisionSum != 0;
	}

}
