package jusePack.units;

import java.awt.geom.Point2D;

import jusePack.ArenaObjects.ArenaObjectABS;
import jusePack.ArenaObjects.ObjectID;
import jusePack.units.collisions.*;
import jusePack.utils.Const;
import jusePack.utils.Position;

public class UnitObject extends ArenaObjectABS {

	private CollisionHandler collHand;
	private SensorArray bumpSensors;
	private SensorArray irSensors;
	private double sensorRange;
	private Position oldPos;

	double wheelRadius = 0.25; // cells
	double wheelsDistance = 2; // cells

	public UnitObject(ObjectID unitIDref, double sensRange){
		super(unitIDref);
		bumpSensors = new SensorArray();
		irSensors = new SensorArray(255);
		collHand = new CollisionHandler(bumpSensors);
		sensorRange = sensRange < Const.sensorsCheckingRangeMin ? Const.sensorsCheckingRangeMin : sensRange;
		oldPos = this.getExactPos();
	}

	public ObjectID getObjID(){ return this.objectID;}

	public int getIDnum(){return objectID.getIDnum();}

	public SensorArray getBumpSensors(){ return bumpSensors; }

	public SensorArray getIrSensors(){ return irSensors; }

	public double getSensorRange(){ return sensorRange; }

	public void setPos(Position newPos){ objectID.setPos(newPos);}

	public void setLoc(Point2D newLoc){ objectID.setLoc(new Point2D.Double(newLoc.getX(),newLoc.getY())); }

	public Position getExactPos(){return objectID.getPos();}

	public Position getOldPos(){ return oldPos;}

	public double getExactAngle(){return objectID.getAngle();}

	/***********************************************************************************************************/

	public boolean hasValidDisplacement(){
		Position currPos = this.getExactPos();
		if ((currPos.distance(oldPos) * Const.cellSize) >= 1) {
			oldPos = currPos;
			return true;
		}
		else return false;
	}

	public void resetToOldPosition(){setPos(oldPos);}

	/***********************************************************************************************************/

	public boolean detectCollisions(int dir){
		boolean collisionDetect = false;
		if (dir != 0){
			collisionDetect = collHand.detectCollisions(dir);
		}
		return collisionDetect;
	}

}
