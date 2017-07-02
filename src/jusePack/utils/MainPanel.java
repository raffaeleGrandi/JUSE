package jusePack.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public abstract class MainPanel extends JPanel implements ActionListener{
	
	protected Dimension dimPanel;
	protected LogArea logArea;
			
	public MainPanel(Dimension mainPanelDim, String logAreaInit){
		dimPanel = mainPanelDim; //righe x colonne
		logArea = new LogArea(logAreaInit);
		setLayout(new BorderLayout());
		add(new JScrollPane(logArea),BorderLayout.WEST);
		configureGUI();
		setPreferredSize(dimPanel);			
		setVisible(true);
	}//end constructor
	
	protected abstract void configureGUI();
	
	public void showMSG(boolean setClear, String msg){
		if (setClear)logArea.setClear(true);
		logArea.addMSG(msg); 
	}//showMSG
	
}//end class
