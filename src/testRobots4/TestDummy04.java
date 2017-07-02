package testRobots4;

import jusePack.units.RobotDummy;
import jusePack.units.UnitObject;
import jusePack.utils.Const;
import jusePack.utils.Position;

public class TestDummy04 extends RobotDummy {

	/* 
	 * Robot physical parameter
	 */

	double wheelRadius = 0.25; //casella
	double wheelsDistance = 2; //caselle - diametro del robot

	UnitObject tempUObj = this.getUnitObject();
	double wlk_init = 0.1;
	double wrk_init = 0.1;
	boolean active = true;

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
	}//end executeLoop	

	private void navigationManager(){
		System.out.println("NavMan");
		double wlk = wlk_init;
		double wrk = wrk_init;
		while(active){						
			int rotDir = evalSensors(readSensors());
			switch(rotDir){
				case -1 :{
					if(Const.cmnt)System.out.println("TD #"+this.getIDnum()+">Incremento rotazione antioraria");
					wrk += 0.001;
					wlk = 0;
				} break;
				case 0 :{
					if(Const.cmnt)System.out.println("TD #"+this.getIDnum()+">Reset della velocità");
					wrk = wrk_init;
					wlk = wlk_init;
				} break;
				case 1 :{
					if(Const.cmnt)System.out.println("TD #"+this.getIDnum()+">Incremento rotazione oraria");
					wrk = 0;
					wlk += 0.001;
				} break;
			}//end switch
			setMotorSpeed(wlk,wrk);
			//sleepSimTime(0.2);			
		}//end while
	}//navigationManager
	
	private int[] readSensors(){
		int[] sensVal = tempUObj.irSensors.getSensors();
		String sensString = "";
		for(int i=0; i<sensVal.length;i++) sensString += sensVal[i]+",";
		if(Const.cmnt)System.out.println("TD #"+this.getIDnum()+" sensArr:"+sensString);
		return sensVal;
	}//readSensors

	private int evalSensors(int[] sensVal){
		int res = 0;
		int delta = 25;
		if ((sensVal[0] < delta) || (sensVal[1] < delta) || (sensVal[2] < delta) || (sensVal[3] < delta) || (sensVal[4] < delta)){
			res = -1;//gira antiorario
		}
		else if ((sensVal[17] < delta) || (sensVal[16] < delta) || (sensVal[15] < delta) || (sensVal[14] < delta)){			
			res = 1;//gira orario
		}
		return res;
	}//evalSensors

	private void sleepSimTime(double st_val){		
		//while(this.tsCounter)
		try { Thread.sleep((int) st_val*1000); } 
		catch (InterruptedException e) { e.printStackTrace(); }		
	}//sleepTime	

}//end TestDummy
