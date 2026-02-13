package jusePack.units.collisions;

import jusePack.units.UnitObject;
import jusePack.utils.Const;

/*
 * Il CollisionHandler gestisce i bumperSensors mentre i sensori "infrarossi" sono letti direttamente dall'agente.
 * 
 * */

public class CollisionHandler {
	
	private ArraySensori unitSensors;
	private boolean fwdCollisionDetect,bwdCollisionDetect;

	public CollisionHandler(UnitObject unitOwn){
		unitSensors = unitOwn.bumpSensors;
	}
	
	public boolean detectCollisions(int dir){ // metodo chiamato dalla ruo
		boolean collisionFound;	
		if(Const.cmnt)System.out.println(unitSensors.toString());
		if (unitSensors.isEmpty()) collisionFound = false; 
		else 
			if ((dir > 0) && checkFWDgroup() || (dir < 0) && checkBWDgroup()){ collisionFound = true; }
			else collisionFound = false; // le collisioni lateriali non sono trattate				
		return collisionFound;		
	}
	
	private boolean checkFWDgroup(){
		int collisionSum = 0;
		for(int j=0; j<3; j++){collisionSum += unitSensors.getSensorValue(j);} // attenzione se cambia il numero di sensori!!!!
		for(int j=16; j<18; j++){collisionSum += unitSensors.getSensorValue(j);} // attenzione se cambia il numero di sensori!!!!
		if (collisionSum!=0) fwdCollisionDetect = true;		
		else fwdCollisionDetect = false;
		return fwdCollisionDetect;
	}
	
	private boolean checkBWDgroup(){
		int collisionSum = 0;
		for(int j=7; j<12; j++){collisionSum += unitSensors.getSensorValue(j);}
		if (collisionSum!=0) bwdCollisionDetect = true;		
		else bwdCollisionDetect = false;
		return bwdCollisionDetect;
	}	
	
}// end class
