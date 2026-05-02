package jusePack.units;

import java.awt.Color;
import jusePack.ArenaObjects.ObjectID;
import jusePack.ArenaObjects.ArenaObjectType;
import jusePack.utils.Const;
import jusePack.utils.Position;

public abstract class RobotUnit implements Runnable{

	private Thread thread;

	private ObjectID unitID;
	private UnitObject unitObj;
	protected volatile boolean activeStatus = true;
	private double vK = 0;
	private double wK = 0;
	private boolean collisionStatus = false;

	public RobotUnit(int unitIDnum, Position unitPos, double sensRange, Color unitColor){
		unitObj = new UnitObject(new ObjectID(ArenaObjectType.UNIT, unitColor, unitPos, Const.unitDim), sensRange);
		unitID = unitObj.getID();
		unitID.setIDnum(unitIDnum);
	}

	/***********************************************************************************************************/

	public void setActive(boolean status){
		System.out.println("RU"+getIDnum()+">setActive to "+status);
		activeStatus = status;
	}

	public boolean hasCollision(){ return collisionStatus; }

	/***********************************************************************************************************/

	public UnitObject getUnitObject(){ return unitObj; }

	public int getIDnum(){ return unitID.getIDnum(); }

	public Position getPosition(){ return unitObj.getExactPos(); }

	/***********************************************************************************************************/

	public int[] getAllSens(){ return unitObj.getIrSensors().getSensors();}

	public int getOneSen(int index){ return unitObj.getIrSensors().getSensorValue(index);}

	/**********************************************************************************************************/

	public void setMotorSpeed(double wlk, double wrk){
		double lftSpeed = wlk/100;
		double rgtSpeed = wrk/100;
		vK = eq_Vk(lftSpeed, rgtSpeed);
		wK = eq_Wk(lftSpeed, rgtSpeed);
	}

	public void setRobotSpeed(double linSpeed, double angSpeed){
		vK = linSpeed;
		wK = angSpeed;
	}

	public boolean isActive(){ return activeStatus; }

	/***********************************************************************************************************/

	public void start() {
		thread = Thread.ofVirtual().name("robot-" + getIDnum()).start(this);
	}

	public void run(){
		activeStatus = true;
		while(activeStatus)	executeLoop();
	}

	public abstract void executeLoop();

	/**********************************	Moving Methods **********************************************************/

	public void rotateBy(int angle, int tms){
		double totAngDisplacement = Math.toRadians(Math.abs(angle));
		double currAngDisplacement = 0;
		double stepAngle = Math.toRadians(Const.angleStep);
		int dir = (int) Math.signum(angle);
		while(currAngDisplacement < totAngDisplacement){
			double currAngle = unitObj.getExactAngle();
			unitObj.getObjID().setAngle(currAngle+dir*stepAngle);
			currAngDisplacement+=stepAngle;
			try {Thread.sleep(tms);}
			catch (InterruptedException e) {e.printStackTrace();}
		}
		collisionStatus = false;
	}

	public void forward(double speed){ this.setMotorSpeed(Math.abs(speed), Math.abs(speed)); }

	public void backward(double speed){ this.setMotorSpeed(-Math.abs(speed), -Math.abs(speed)); }

	/***********************************************************************************************************/

	public void evaluateMovement(double timeSample){
		collisionStatus = detectCollisions();
		if(Const.debugEnabled)System.out.println("Collisions detected for unit# "+this.getIDnum()+" = "+collisionStatus);
		if (!collisionStatus) {
			this.moveUnit(timeSample);
		} else {
			// Se il robot sta ruotando su se stesso (vK=0, wK!=0) lo lasciamo girare
			// anche in presenza di collisione: è fisicamente il comportamento corretto
			// per uscire dall'ostacolo senza traslazione.
			if (evaluateRotation()) {
				this.moveUnit(timeSample);
			} else {
				stopUnit();
			}
		}
	}

	/***********************************************************************************************************/

	public boolean evaluateRotation(){
		return (vK == 0) && (wK != 0);
	}

	/***********************************************************************************************************/

	public void stopUnit(){
		System.out.println("Executing stopUnit");
		setMotorSpeed(0,0);
	}

	public void moveUnit(double timeSample){
		Position currPos = unitObj.getExactPos();
		Position newPos = null;
		if(Math.abs(wK) < 0.001) newPos = computeRungeKutta(currPos,vK,0,timeSample);
		else newPos = computeExact(currPos,vK,wK,timeSample);
		unitObj.setPos(newPos);
	}

	/***********************************************************************************************************/

	public int getDirection(){
		int dir = (int) Math.signum(vK);
		return dir;
	}

	public boolean detectCollisions(){
		int dir = this.getDirection();
		if(Const.debugEnabled)System.out.println("Direction of unit #"+getIDnum()+" = "+dir);
		return unitObj.detectCollisions(dir);
	}

	/************************************************************************************************/

	private double eq_Vk(double wlk, double wrk){
		double res = unitObj.wheelRadius*(wlk+wrk)/2;
		return res;
	}

	private double eq_Wk(double wlk, double wrk){
		double res = (unitObj.wheelRadius*(wlk-wrk)/unitObj.wheelsDistance);
		if (res == 0) res=0.00000000001;
		return res;
	}

	/************************************************************************************************/

	private Position computeRungeKutta(Position currPos, double vK, double wK, double timeSample){
		double nextTheta = (currPos.theta + wK*timeSample)%(2*Math.PI);
		double nextPosX = currPos.x + vK*timeSample*Math.cos(currPos.theta + wK*timeSample/2);
		double nextPosY = currPos.y + vK*timeSample*Math.sin(currPos.theta + wK*timeSample/2);
		Position newPos = new Position(nextPosX,nextPosY,nextTheta);
		return newPos;
	}

	/************************************************************************************************/

	private Position computeExact(Position currPos, double vK, double wK, double timeSample){
		double nextTheta = (currPos.theta + wK*timeSample)%(2*Math.PI);
		double nextPosX = currPos.x + (vK/wK)*(Math.sin(nextTheta) - Math.sin(currPos.theta));
		double nextPosY = currPos.y - (vK/wK)*(Math.cos(nextTheta) - Math.cos(currPos.theta));
		Position newPos = new Position(nextPosX,nextPosY,nextTheta);
		return newPos;
	}

}
