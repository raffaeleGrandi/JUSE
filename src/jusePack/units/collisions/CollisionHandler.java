package jusePack.units.collisions;

import jusePack.ArenaObjects.ObjectID;
import jusePack.units.UnitObject;
import jusePack.utils.Const;

/*
 * Il CollisionHandler gestisce i bumperSensors mentre i sensori "infrarossi" sono letti direttamente dall'agente.
 * 
 * */

public class CollisionHandler {
	
	private ObjectID unitID;
	private ArraySensori unitSensors;	
	private boolean fwdCollisionDetect,bwdCollisionDetect;	
	
	public CollisionHandler(UnitObject unitOwn){		
		unitID = unitOwn.getID();
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
	
	/**********************************************************************************************************/
	
	//la fcwl (free collision windows list) viene richiesta che vi sia presenza o no di collisione: sfruttando il fatto che i sensori sono "sporchi" 
	//dall'ultima rilevazione effettuata
	
	public PointsList getFCWlist(){ return buildFCWlist(); }
	
	/**********************************************************************************************************/	
	
	/**
	 * Un'unità puà passare quando è presente una finestra di 5/18 sensori priva di collisioni
	 * La FCWL ritorna il checkpoint di mezzo della finestra libera da collisioni
	 * In questo modo ho un'indicazione dell'eventuale direzione da seguire
	 */	
	
	private PointsList buildFCWlist(){ 
		System.out.println("CollisionHandler.buildFCWlist>Active for unit "+unitID.getIDnum());
		System.out.println("CollisionHandler.buildFCWlist>Array sensors:"+unitSensors.toString());
		PointsList freeCollWindList = new PointsList();
		for(int c = 0; c < unitSensors.length(); c++){
			int i = c+2;
			if ((unitSensors.getSensorValue(i-2) == 0)&& 
				(unitSensors.getSensorValue((i-1)%18) == 0)&&
				(unitSensors.getSensorValue(i%18) == 0)&&
				(unitSensors.getSensorValue((i+1)%18) == 0)&& 
				(unitSensors.getSensorValue((i+2)%18) == 0))
				freeCollWindList.add(unitSensors.getCheckPoint(i%18));
		}	
		System.out.println("CollisionHandler.buildFCWlist>fcwList: "+printFCWL(freeCollWindList));
		return freeCollWindList;
	}//buildFCWlist			
	
	private String printFCWL(PointsList fcwlRef){
		String fcwlString = "\n";	
		for (int index = 0; index < fcwlRef.size(); index++){
			fcwlString += "fcw relativeCP: "+fcwlRef.get(index)+"\n";
		}
		return fcwlString;
	}	
	
}// end class
