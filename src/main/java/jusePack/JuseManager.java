package jusePack;

import java.awt.Point;

import javax.swing.JFrame;

import jusePack.gui.JuseMF;
import jusePack.structures.ScenarioManager;
import jusePack.structures.UnitsManager;
import jusePack.units.collisions.CollisionDetector;
import jusePack.utils.Const;

public class JuseManager {

	UnitsManager unitsManager;
	JuseMF mainFrame;
	ScenarioManager scenarioMngRef;

	public JuseManager(Point frameLoc, String logInitStr, ScenarioManager scenarioMng){
		scenarioMngRef = scenarioMng;
		JFrame.setDefaultLookAndFeelDecorated(true);
		mainFrame = new JuseMF(Const.guiFrameTitle, frameLoc, logInitStr, scenarioMngRef);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CollisionDetector collDetect = new CollisionDetector(scenarioMngRef.getObstacles());
		unitsManager = new UnitsManager(collDetect);
		unitsManager.setUnitsVector(scenarioMngRef.getRobots());
		scenarioMngRef.setUnitsManagerSet(true);
	}

	public void setNewScenario(ScenarioManager newScenario){
		mainFrame.simPanel.configureGUI(newScenario.getRobots(), newScenario.getObstacles());
	}

	public void startSim(int delay_sec){
		printLogMsg(true, "Simulation starting in " + delay_sec + " s");
		try { Thread.sleep(delay_sec * 1000L); }
		catch (InterruptedException e) {
			System.err.println("Start sim sleep error");
			e.printStackTrace();
		}
		mainFrame.startRefresh();
		unitsManager.start();
		scenarioMngRef.enableRobots();
		printLogMsg(false, "Robots enabled");
	}

	public void stopSim(){
		scenarioMngRef.disableRobots();
		unitsManager.stop();
		printLogMsg(false, "Robots disabled");
	}

	public void printLogMsg(boolean setClear, String msg){ mainFrame.showMSG(setClear, msg); }

}
