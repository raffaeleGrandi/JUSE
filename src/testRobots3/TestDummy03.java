package testRobots3;

import jusePack.units.RobotDummy;
import jusePack.units.UnitObject;
import jusePack.utils.Position;


public class TestDummy03 extends RobotDummy {

	/* 
	 * Robot physical parameter
	 */

	double wheelRadius = 0.25; //casella
	double wheelsDistance = 2; //caselle - diametro del robot

	UnitObject tempUObj = this.getUnitObject();
	double wlk_init, wrk_init;
	boolean active = true;

	public TestDummy03(int unitIDnum, Position unitPos, double sensRange) {
		super(unitIDnum, unitPos, sensRange);
	}

	@Override

	public void executeLoop() {
		System.out.println("TestDummy #"+this.getIDnum()+" started");
		setMotorSpeed(1.5,1.5);		
		sleepSimTime(2);
		setMotorSpeed(1.5,6);
		sleepSimTime(2);
		setMotorSpeed(2,6);
		sleepSimTime(1);
		setMotorSpeed(2,2);
		sleepSimTime(4);
		setActive(false);		
		System.out.println("EndLoop - Disable Unit");
	}//end executeLoop	
	
	private void sleepSimTime(double st_val){		
		//while(this.tsCounter)
		try { Thread.sleep((int) st_val*1000); } 
		catch (InterruptedException e) { e.printStackTrace(); }		
	}//sleepTime	
	
}//end TestDummy
