package jusePack.units;

import java.awt.Color;
import jusePack.ArenaObjects.ObjectID;
import jusePack.structures.UnitsManager;
import jusePack.utils.Const;
import jusePack.utils.Position;

public abstract class RobotUnit extends Thread{

	private ObjectID unitID;	
	private UnitObject unitObj;	
	protected boolean activeStatus = true;	
	double vK = 0;
	double wK = 0;	
	public boolean collisionStatus = false;

	public RobotUnit(int unitIDnum,Position unitPos,double sensRange, Color unitColor){
		unitObj = new UnitObject(new ObjectID(Const.typeUnit, unitColor, unitPos, Const.unitDim),sensRange);
		unitID = unitObj.getID();
		unitID.setIDnum(unitIDnum);	
	}//end RobotUnit

	/***********************************************************************************************************/

	public void setCollisionsManager(UnitsManager collManagRef){ unitObj.collMng = collManagRef; }

	public void setActive(boolean status){
		System.out.println("RU"+getIDnum()+">setActive to "+status);
		activeStatus = status;
	}//setActive

	/***********************************************************************************************************/

	//public ObjectID getUID(){ return unitID;}

	/*	Used by SimPanel*/
	public UnitObject getUnitObject(){ return unitObj; }

	/* Used by RU */
	public int getIDnum(){ return unitID.getIDnum(); }

	public Position getPosition(){ return unitObj.getExactPos(); }

	/***********************************************************************************************************/

	public int[] getAllSens(){ return unitObj.irSensors.getSensors();}

	public int getOneSen(int index){ return unitObj.irSensors.getSensorValue(index);}

	/**********************************************************************************************************/

	public void setMotorSpeed(double wlk,double wrk){
		double lftSpeed = wlk/100;
		double rgtSpeed = wrk/100;
		vK= eq_Vk(lftSpeed,rgtSpeed);	//robot linear speed
		wK = eq_Wk(lftSpeed,rgtSpeed); //robot angular speed		
	}//setWheelSpeed

	public void setRobotSpeed(double linSpeed, double angSpeed){
		vK = linSpeed;
		wK = angSpeed; 
	}//setRobotSpeed

	public boolean isActive(){ return activeStatus; }

	/***********************************************************************************************************/

	public void run(){
		activeStatus = true;		
		while(activeStatus)	executeLoop();		
	}//run

	// executeLoop viene implementato dalla classe RobotDummy ed esegue il programma di controllo del robot

	public abstract void executeLoop(); 

	/**********************************	Moving Methods **********************************************************/

	/*Old Style*/

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
		collisionStatus = false; //ipotizzo che il collisionStatus sia false (ricorda che il collision status è leggibile dall'esterno)
	}//rotateBy

	public void forward(double speed){ this.setMotorSpeed(Math.abs(speed), Math.abs(speed)); }//forward
	
	public void backward(double speed){ this.setMotorSpeed(-Math.abs(speed), -Math.abs(speed)); }//backward

	/***********************************************************************************************************/

	/* Metodo utilizzato da UnitManager */
	public void evaluateMovement(double timeSample){		
		collisionStatus = detectCollisions();
		if(Const.cmnt)System.out.println("Collisions detected for unit# "+this.getIDnum()+" = "+collisionStatus);					
		if (!collisionStatus) this.moveUnit(timeSample);
		else{
			stopUnit();
			//Position currPos = getPosition();
			//Position oldPos = unitObj.getOldPos();
			//if((currPos.x == oldPos.x)&&(currPos.y == oldPos.y)){this.moveUnit(timeSample);}
			//else{ unitObj.resetToOldPosition(); }
			//unitObj.resetToOldPosition();
		}		
	}//evaluateMovement

	/***********************************************************************************************************/
	
	/*
	 * In caso di collisione il sistema deve permettere la sola rotazione del robot.
	 * Viene chiamato da UnitManager
	 * */

	public boolean evaluateRotation(){	//se la velocità di avanzamento == 0 e quella di rotazione !=0 torna true 
		System.out.println("Eseguo evalRot");
		if ((vK==0) && (wK!=0)) return true;
		else return false;
	}

	/***********************************************************************************************************/

	public void stopUnit(){ // viene utilizzato anche da UnitManager per fermare l'unità in caso di collisioni
		System.out.println("Viene eseguito stopUnit");
		setMotorSpeed(0,0);			
	}//stopUnit

	public void moveUnit(double timeSample){ //Viene chiamato da UnitsManager. Crea così l'autonomia del robot
		Position currPos = unitObj.getExactPos();
		//System.out.println(currPos.toString());		 
		Position newPos = null;
		if(Math.abs(wK) < 0.001) newPos = metodo_RungeKutta(currPos,vK,0,timeSample);
		else newPos = metodo_Esatto(currPos,vK,wK,timeSample);
		unitObj.setPos(newPos);		
	}//moveUnit

	/***********************************************************************************************************/

	public int getDirection(){		
		int dir = (int) Math.signum(vK); //per decidere in quale direzione sta andando vede il segno della velocità del robot
		return dir;
	}

	public boolean detectCollisions(){//viene utilizzato da UnitManager
		int dir = this.getDirection();
		if(Const.cmnt)System.out.println("Direction of unit #"+getIDnum()+" = "+dir);
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

	private Position metodo_RungeKutta(Position currPos, double vK, double wK, double timeSample){
		//System.out.println("Utilizzo metodo_RK");
		double nextTheta = (currPos.theta + wK*timeSample)%(2*Math.PI);
		double nextPosX = currPos.x + vK*timeSample*Math.cos(currPos.theta + wK*timeSample/2);
		double nextPosY = currPos.y + vK*timeSample*Math.sin(currPos.theta + wK*timeSample/2);
		Position newPos = new Position(nextPosX,nextPosY,nextTheta);
		return newPos;
	}

	/************************************************************************************************/

	private Position metodo_Esatto(Position currPos, double vK, double wK, double timeSample){
		//System.out.println("Utilizzo metodo_Esatto");
		double nextTheta = (currPos.theta + wK*timeSample)%(2*Math.PI);
		double nextPosX = currPos.x + (vK/wK)*(Math.sin(nextTheta) - Math.sin(currPos.theta));
		double nextPosY = currPos.y - (vK/wK)*(Math.cos(nextTheta) - Math.cos(currPos.theta));		
		Position newPos = new Position(nextPosX,nextPosY,nextTheta);
		return newPos;
	}//metodoEsatto

	/*********************************************************************************************************/

	/******************************************************************************************************

		private boolean goToLocation(Point2D newLocation){		
			Point2D unitLoc =  unitID.getPos();
			double unitAngle = unitID.getAngle();
			System.out.println("RU.goToLocation>UnitLocation: "+unitLoc+"; destination: "+newLocation);
			System.out.println("RU.goToLocation>UnitAngle: "+unitAngle);	
			int alpha = findAngle(unitAngle,unitLoc,newLocation);						
			unitObj.ruotaDi(alpha);			
			double distance = unitLoc.distance(newLocation);
			boolean collided = unitObj.muoviDi(distance);		
			return !collided;
		}//end goToLocation

	/******************************************************************************************************

		private int findAngle(double unitAngle, Point2D unitLoc, Point2D destPoint){
			double alphaR = Math.atan2(unitLoc.getY() - destPoint.getY(), destPoint.getX() - unitLoc.getX());			
			if (alphaR < 0) alphaR += 2*Math.PI;
			if (unitAngle < 0) unitAngle += 2*Math.PI;
			double deltaR = alphaR - unitAngle;
			if (deltaR < - Math.PI) deltaR +=  2*Math.PI; //servono per ruotare verso la direzione più corta.
			if (deltaR >  Math.PI) deltaR -=  2*Math.PI;
			return (int)Math.toDegrees(deltaR);					
		}//findAngle
	 */

}//end RobotUnit
