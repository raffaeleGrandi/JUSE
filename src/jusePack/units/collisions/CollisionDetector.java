package jusePack.units.collisions;

	import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
	import java.awt.Color;
	import java.util.Vector;
import jusePack.ArenaObjects.ObjectID;
import jusePack.gui.panels.*;
import jusePack.units.UnitObject;
import jusePack.utils.Const;
	
public class CollisionDetector {	
	
	private CollisionPanel collPanelLink;
	public BufferedImage arenaImage; // DEVE essere settata dal UnitManager
	public Vector<Color> excludedColor = new Vector<Color>(1,1); //viene usato da JuseManager per aggiungere i colori da escludere
	public int sensorsCheckingRange =  Const.sensorsCheckingRange0;
	double dFactor = Const.latoCasella;
		
	public CollisionDetector(CollisionPanel collisionPanelRef){
		collPanelLink = collisionPanelRef;
		collPanelLink.panelActive = true;
		excludedColor.add(Const.arenaFloorColor1);
		excludedColor.add(Const.arenaFloorColor2);
		excludedColor.add(Const.sensorRangeColor);
	}
	
	public synchronized void testUnitforCollisions(UnitObject testUnit){
		ObjectID unitToTestID = testUnit.getID();
		testUnit.bumpSensors.resetSensors();
		sensorsCheckingRange = (int)(dFactor*testUnit.sensorRange); //in pixel 15 = 1 casella
		if(Const.cmnt)System.out.println("\nCD>detecting for unit #"+unitToTestID.getIDnum()+" scr = "+sensorsCheckingRange);		
		
		Point2D unitLoc = unitToTestID.getPos();
		if(Const.cmnt)System.out.println("\nCD>unitPosition: "+unitLoc);		
		double unitCenterX = dFactor*unitLoc.getX(); 	//convertito in pixel
		double unitCenterY = dFactor*unitLoc.getY();	//convertito in pixel
		double unitAng = unitToTestID.getAngle();
		double alpha0 = unitAng;
	
		
		for(int i = 0; i < (Const.numSensori); i++){
			double delta = testUnit.bumpSensors.getSensorAngle(i); // già in radianti
			double alpha = alpha0 + delta;
			if(Const.cmnt)System.out.println("Angoli: alpha0="+alpha0+"; delta="+delta+";alpha="+alpha);
			boolean trovata = false;
			int sensDist = 1; //in pixel: non può essere minore di 1;
			int k = 1;//costante di aggiustamento della distanza sensore
			while ((!trovata)&&(sensDist < sensorsCheckingRange)){
				int checkpointX = (int)(unitCenterX + (Const.unitRadius+k+sensDist)*Math.cos(alpha));
				int checkpointY = (int)(unitCenterY + (Const.unitRadius+k+sensDist)*Math.sin(alpha));	
				Color checkedColor = new Color(arenaImage.getRGB(checkpointX, checkpointY));
				if (sensDist < Const.sensorsCheckingRange0){//lavoro sui bumper
					if (!excludedColor.contains(checkedColor)) {
						testUnit.bumpSensors.setSensorValue(i, sensDist);	
						testUnit.irSensors.setSensorValue(i, sensDist);
						System.out.println("CD>Active BumpSensor #"+i+", sensorAngle: "+Math.toDegrees(delta)+", sensDist: "+sensDist);
						trovata = true;						
					}	
					else {testUnit.bumpSensors.setSensorValue(i, 0);}
					testUnit.bumpSensors.setCheckingPoint(i,new Point2D.Double(checkpointX/dFactor,checkpointY/dFactor)); // convertito in "caselle"
				}//end if bumpers
				else {//lavoro sui sensori infrosso
					if (!excludedColor.contains(checkedColor)) {
						testUnit.irSensors.setSensorValue(i, sensDist);					
						if(Const.cmnt)System.out.println("CD>Active IRSensor #"+i+", sensorAngle: "+Math.toDegrees(delta)+", sensDist: "+sensDist);
						trovata = true;
					}	
					else {
						testUnit.irSensors.resetSensorValue(i);
					}
					testUnit.irSensors.setCheckingPoint(i,new Point2D.Double(checkpointX/dFactor,checkpointY/dFactor)); // convertito in "caselle"
				}//end else infrarossi
				sensDist+=1;
			}//while		
		}//for
		if(Const.cmnt)System.out.println("\nCD>end detection for unit #"+unitToTestID.getIDnum());	
	}//detectCollisions	
	
	/****************************************************************************************************************************/
	
}// end class


