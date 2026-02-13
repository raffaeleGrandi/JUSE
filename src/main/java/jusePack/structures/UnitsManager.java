package jusePack.structures;

import java.util.List;

import javax.swing.SwingUtilities;

import jusePack.gui.panels.ArenaPanel;
import jusePack.units.*;
import jusePack.units.collisions.CollisionDetector;
import jusePack.utils.Const;

public class UnitsManager implements Runnable{

	private Thread thread;
	private List<RobotDummy> unitsVector;
	private CollisionDetector cd;
	private ArenaPanel arenaPanelLink;
	public volatile boolean activeStatus = false;
	private double timeSample = Const.timeSample;

	public UnitsManager(ArenaPanel arenaPanelRef, CollisionDetector collDetect){
		cd = collDetect;
		arenaPanelLink = arenaPanelRef;
		if (arenaPanelLink == null) System.out.println("NULL");
	}//constructor

	/* Prima di qualunque operazione di detecting è necessario configurare il numero di robot presenti nello scenario */
	/* Viene eseguito da JuseManager */

	public void setUnitsVector(List<RobotDummy> dummyVec){
		unitsVector = dummyVec;
	}//setUnitVector

	private void updateArenaImage() {
		try {
			SwingUtilities.invokeAndWait(() -> cd.arenaImage = Const.getPanelImage(arenaPanelLink));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		thread = Thread.ofVirtual().name("units-manager").start(this);
	}

	public void run(){
		activeStatus = true;
		while (activeStatus) {
			for (int i=0; i < unitsVector.size(); i++) {
				RobotUnit tempUnit = unitsVector.get(i);
				if (tempUnit.isActive()){
					UnitObject tempUnitObj = tempUnit.getUnitObject();
					if (tempUnitObj.valid_displacement()){
						updateArenaImage();
						cd.testUnitforCollisions(tempUnitObj);
						tempUnit.evaluateMovement(timeSample);
					}

					else if (!tempUnit.collisionStatus)	tempUnit.moveUnit(timeSample);
						else {
							if(tempUnit.evaluateRotation()){
								tempUnit.moveUnit(timeSample);
								updateArenaImage();
								cd.testUnitforCollisions(tempUnitObj);
								tempUnit.detectCollisions();
							}
						}
				}
			}//for
			try {
				Thread.sleep((long)(timeSample * 1000));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}//run

}//CollisionsManager
