package jusePack.units;

import java.awt.geom.Point2D;
import java.util.Random;


import jusePack.ArenaObjects.ArenaObjectABS;
import jusePack.ArenaObjects.ObjectID;
import jusePack.structures.UnitsManager;
import jusePack.units.collisions.*;
import jusePack.utils.Const;
import jusePack.utils.Position;

public class UnitObject extends ArenaObjectABS {
	
	public CollisionHandler collHand;	
	public UnitsManager collMng = null; // viene settato da RobotUnit
	public ArraySensori bumpSensors; //vengono usati dal CollisionDetector e dal CollisionHandler
	public ArraySensori irSensors; //vengono settati dal CollisionDetector e usati da RobotUnit
	private Random errore;	
	public double sensorRange;
	private Position oldPos;
		
	double wheelRadius = 0.25; //casella
	double wheelsDistance = 2; //caselle	
					
	public UnitObject(ObjectID unitIDref, double sensRange){
		super(unitIDref);
		bumpSensors = new ArraySensori();
		irSensors = new ArraySensori(255);
		collHand = new CollisionHandler(this);
		sensorRange = sensRange < Const.sensorsCheckingRangeMin  ? Const.sensorsCheckingRangeMin : sensRange; //caselle 
		errore = new Random();	
		oldPos = this.getExactPos();
	}//end constructor	
	
	public ObjectID getObjID(){ return this.objectID;}
	
	public int getIDnum(){return objectID.getIDnum();}
	
	public void setPos(Position newPos){ objectID.setPos(newPos);}
	
	public void setLoc(Point2D newLoc){ objectID.setLoc(new Point2D.Double(newLoc.getX(),newLoc.getY())); }
	
	public Position getExactPos(){return objectID.getPos();} // serve per il TargetPanel
	
	public Position getOldPos(){ return oldPos;}
	
	public double getExactAngle(){return objectID.getAngle();}
	
	/***********************************************************************************************************/
	
	public Point2D getErrorPos(){//punto di introduzione dell'EG
		double locGausErr = errore.nextGaussian(); // errore normale medio 0 dev 1
		Point2D unitLoc = objectID.getPos();
		Point2D errLoc = new Point2D.Double(locGausErr*Const.devStdPos+unitLoc.getX(),locGausErr*Const.devStdPos+unitLoc.getY());
		return errLoc ;
	}//getErrorPos
		
	public double getErrAngle(){//punto di introduzione dell'EG
		double angGausErr = errore.nextGaussian(); // errore normale medio 0 dev 1
		double unitAngle = objectID.getAngle();
		double errAng = angGausErr*Const.devStdAng+unitAngle;
		return errAng; //domanda: quanto incide l'arrotondamento sull'errore? Serve?
	}//getAngle 	
	
	/***********************************************************************************************************/

	/*
	 * Il calcolo del displacement deve essere fatto in pixel non in caselle e 
	 * quindi seleziono
	 */
	
	public boolean valid_displacement(){ 
		Position currPos = this.getExactPos();		
		if ((currPos.distance(oldPos)*15)>=1) {
			oldPos = currPos;	
			return true;
		}
		else return false;		
	}//valid_displacement
	
	public void resetToOldPosition(){setPos(oldPos);}
	
	/***********************************************************************************************************/
	
	public boolean detectCollisions(int dir){		
		boolean collisionDetect = false;
		if (dir != 0){
			collisionDetect = collHand.detectCollisions(dir);
		}
		return collisionDetect;
	}//detectCollisions
	
	//public PointsList getFCWlist(){return collHand.getFCWlist();}	
	
} // end class
