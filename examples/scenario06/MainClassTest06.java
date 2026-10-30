package examples.scenario06;

import java.awt.Point;
import jusePack.*;

/* Added on 2026-10-30, see ScenarioTest06. Not part of the original JUSE. */
public class MainClassTest06 {

	static ScenarioTest06 scenario06;

	public static void main(String[] args) {
		scenario06 = new ScenarioTest06();
		JuseManager simManager = new JuseManager(new Point(30,30),"logArea",scenario06);
		simManager.startSim(2);	// inizia la simulazione 2 sec dopo
	}//end main
}//end class
