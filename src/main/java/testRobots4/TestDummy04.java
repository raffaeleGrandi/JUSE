package testRobots4;

import jusePack.units.RobotDummy;
import jusePack.units.UnitObject;
import jusePack.utils.Const;
import jusePack.utils.Position;

public class TestDummy04 extends RobotDummy {

	UnitObject tempUObj = this.getUnitObject();
	double wlk_init = 500;
	double wrk_init = 500;
	boolean active = true;
	int lastRotDir = -1;
	boolean avoiding = false;

	public TestDummy04(int unitIDnum, Position unitPos, double sensRange) {
		super(unitIDnum, unitPos, sensRange);
	}

	@Override
	public void executeLoop() {
		System.out.println("TestDummy #"+this.getIDnum()+" started");
		setMotorSpeed(wlk_init,wrk_init);
		navigationManager();
		setActive(false);
		System.out.println("EndLoop - Disable Unit");
	}

	private void navigationManager(){
		System.out.println("NavMan");
		double wlk = wlk_init;
		double wrk = wrk_init;
		while(active){
			boolean bumpContact = !tempUObj.getBumpSensors().isEmpty();
			int rotDir = evalSensors(readSensors());

			if (bumpContact && rotDir == 0) {
				rotDir = lastRotDir;
			}

			switch(rotDir){
				case -1 :{
					if(Const.debugEnabled)System.out.println("TD #"+this.getIDnum()+">Increasing CCW rotation");
					wrk += 0.5;
					wlk = 0;
				} break;
				case 0 :{
					if(Const.debugEnabled)System.out.println("TD #"+this.getIDnum()+">Speed reset");
					wrk = wrk_init;
					wlk = wlk_init;
				} break;
				case 1 :{
					if(Const.debugEnabled)System.out.println("TD #"+this.getIDnum()+">Increasing CW rotation");
					wrk = 0;
					wlk += 0.5;
				} break;
			}
			setMotorSpeed(wlk,wrk);
			sleepSimTime(0.2);
		}
	}

	private int[] readSensors(){
		int[] sensVal = tempUObj.getIrSensors().getSensors();
		String sensString = "";
		for(int i=0; i<sensVal.length;i++) sensString += sensVal[i]+",";
		if(Const.debugEnabled)System.out.println("TD #"+this.getIDnum()+" sensArr:"+sensString);
		return sensVal;
	}

	private int evalSensors(int[] sensVal){
		int delta = 25;
		boolean rightObstacle = (sensVal[0] < delta) || (sensVal[1] < delta) || (sensVal[2] < delta) || (sensVal[3] < delta) || (sensVal[4] < delta);
		boolean leftObstacle = (sensVal[17] < delta) || (sensVal[16] < delta) || (sensVal[15] < delta) || (sensVal[14] < delta);

		if (!rightObstacle && !leftObstacle) {
			avoiding = false;
			return 0;
		}

		if (!avoiding) {
			avoiding = true;
			if (rightObstacle && !leftObstacle) {
				lastRotDir = -1;
			} else if (leftObstacle && !rightObstacle) {
				lastRotDir = 1;
			}
		}
		return lastRotDir;
	}

	private void sleepSimTime(double st_val){
		try { Thread.sleep((long)(st_val * 1000)); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}

}
