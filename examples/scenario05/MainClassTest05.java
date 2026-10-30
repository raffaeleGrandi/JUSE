package examples.scenario05;

import java.awt.Point;
import jusePack.*;

/* Added on 2026-10-30, see ScenarioTest05. Not part of the original JUSE. */
public class MainClassTest05 {

	static ScenarioTest05 scenario05;

	public static void main(String[] args) {
		scenario05 = new ScenarioTest05();
		JuseManager simManager = new JuseManager(new Point(30,30),"logArea",scenario05);
		simManager.startSim(2);	// inizia la simulazione 2 sec dopo
	}//end main
}//end class
