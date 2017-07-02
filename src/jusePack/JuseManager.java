package jusePack;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JFrame;


import jusePack.gui.JuseMF;
import jusePack.gui.panels.CollisionPanel;
import jusePack.structures.ScenarioManager;
import jusePack.structures.UnitsManager;
import jusePack.units.collisions.CollisionDetector;
import jusePack.utils.Const;

public class JuseManager {

	CollisionPanel collPanel;
	CollisionDetector collDetect;
	UnitsManager unitsManager;
	JuseMF mainFrame;
	ScenarioManager scenarioMngRef;
	
	public JuseManager(Point frameLoc, String logInitStr, ScenarioManager scenarioMng){
		scenarioMngRef = scenarioMng;
		collPanel = new CollisionPanel();
		JFrame.setDefaultLookAndFeelDecorated(true);
		mainFrame = new JuseMF(Const.guiFrameTitle, frameLoc, logInitStr, scenarioMngRef);		
		mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		collDetect = new CollisionDetector(collPanel);
		unitsManager = new UnitsManager(mainFrame.simPanel.getArenaPanelRef(),collDetect);
		unitsManager.setUnitsVector(scenarioMngRef.getRobotsVector());
		scenarioMngRef.unitsMNGset = true;
	}
	
	public void setNewScenario(ScenarioManager newScenario){
		mainFrame.simPanel.configureGUI(newScenario.getRobotsVector(),newScenario.getObstaclesVector());
	//	mainFrame.simPanel.arenaRepaint();
	}
	
	public void addExcludedColor(Color excludeColor){collDetect.excludedColor.add(excludeColor);}
	
	public void startSim(int delay_sec){
		printLogMsg(true,"Simulation starting in "+delay_sec+" s");
		try {Thread.sleep(delay_sec*1000);}
		catch (InterruptedException e) {
			System.err.println("Start sim sleep error");
			e.printStackTrace();
		}
		mainFrame.startRefresh();
		
		unitsManager.start();
		scenarioMngRef.enableRobots();
		printLogMsg(false,"Robots enabled");
	}
	
	public void stopSim(){
		scenarioMngRef.disableRobots();
		printLogMsg(false,"Robots disabled");
	}
	
	public void printLogMsg(boolean setClear, String msg){mainFrame.showMSG(setClear, msg);}
	
}//JuseManager
