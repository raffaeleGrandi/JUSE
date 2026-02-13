package testRobots4;

import java.awt.Color;
import java.awt.Point;
import jusePack.*;

public class MainClassTest04 {
	
	static ScenarioTest04 scenario04;	
	
	public static void main(String[] args) {		
		scenario04 = new ScenarioTest04();
		JuseManager simManager = new JuseManager(new Point(30,30),"logArea",scenario04);
		simManager.addExcludedColor(Color.YELLOW);
		simManager.startSim(2);	// inizia la simulazione 2 sec dopo			
	}//end main
}//end class
