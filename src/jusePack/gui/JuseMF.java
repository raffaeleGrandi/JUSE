package jusePack.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Point;
import javax.swing.JFrame;

import jusePack.structures.ScenarioManager;
import jusePack.utils.Const;
import jusePack.utils.LogArea;

@SuppressWarnings("serial")
public class JuseMF extends JFrame{
	
	LogArea logArea;
	int deltaW = 20;
	int deltaH = 40;	
	public SimPanel simPanel;
	
	public JuseMF(String frameTitle, Point frameLoc, String logInitStr, ScenarioManager scenario){
		super(frameTitle);		
		Container c = getContentPane();			
		c.setLayout(new FlowLayout());
		simPanel = new SimPanel(Const.guiFrameDim,logInitStr,scenario.getRobotsVector(),scenario.getObstaclesVector());
		c.add(simPanel);		
		setLocation(frameLoc);
        setSize(simPanel.getPreferredSize().width+deltaW,simPanel.getPreferredSize().height+deltaH); 
        setVisible(true);
        setResizable(true); 
        
	}//end constructor
	
	public void showMSG(boolean setClear, String msg){ simPanel.showMSG(setClear, msg); }
	
	public void startRefresh(){simPanel.startRefresh();}
	

}//end JUnibot
