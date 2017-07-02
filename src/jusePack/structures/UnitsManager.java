package jusePack.structures;

import jusePack.gui.panels.ArenaPanel;
import jusePack.units.*;
import jusePack.units.collisions.CollisionDetector;
import jusePack.utils.Const;

public class UnitsManager extends Thread{

	private RobotDummyVector unitsVector;	
	private CollisionDetector cd;
	private ArenaPanel arenaPanelLink;	
	public boolean activeStatus = false;	
	private double timeSample = Const.timeSample;
	
	public UnitsManager(ArenaPanel arenaPanelRef, CollisionDetector collDetect){
		cd = collDetect;
		arenaPanelLink = arenaPanelRef;	
		if (arenaPanelLink == null) System.out.println("NULL");
	}//constructor
	
	/* Prima di qualunque operazione di detecting è necessario configurare il numero di robot presenti nello scenario */
	/* Viene eseguito da JuseManager */
		
	public void setUnitsVector(RobotDummyVector dummyVec){
		unitsVector = dummyVec;		
	}//setUnitVector
	
	public void run(){
		activeStatus = true;
		while (activeStatus)
			for (int i=0; i < unitsVector.size(); i++) {
				RobotUnit tempUnit = unitsVector.elementAt(i);
				if (tempUnit.isActive()){
					UnitObject tempUnitObj = tempUnit.getUnitObject();	
					if (tempUnitObj.valid_displacement()){//mi chiedo se l'unità si è mossa di un valore valido in pixel
						cd.arenaImage = Const.getPanelImage(arenaPanelLink);
						cd.testUnitforCollisions(tempUnitObj);
						tempUnit.evaluateMovement(timeSample);
					}
					
					else if (!tempUnit.collisionStatus)	tempUnit.moveUnit(timeSample);
						else { /*devi permettere la SOLA rotazione del robot*/
							if(tempUnit.evaluateRotation()){
								tempUnit.moveUnit(timeSample);
								cd.arenaImage = Const.getPanelImage(arenaPanelLink);
								cd.testUnitforCollisions(tempUnitObj);
								tempUnit.detectCollisions();
							}
						}					
				}				
			}//for
	}//run

}//CollisionsManager
