package jusePack.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.Vector;

import jusePack.ArenaObjects.Obstacle;
import jusePack.gui.panels.*;
import jusePack.structures.*;
import jusePack.units.RobotDummy;
import jusePack.utils.Const;
import jusePack.utils.LogArea;


@SuppressWarnings("serial")
public class SimPanel extends JPanel implements ActionListener{
	
	protected Dimension dimPanel;
	protected LogArea logArea;
	private ArenaPanel arena;
	protected Timer repainter;

	public SimPanel(Dimension mainPanelDim, String logAreaInit, RobotDummyVector dummyVector, ObstaclesVector obstVector) {
		dimPanel = mainPanelDim; //righe x colonne
		logArea = new LogArea(logAreaInit,5,5);
		repainter = new Timer(50,this);
		setLayout(new BorderLayout());
		add(new JScrollPane(logArea),BorderLayout.SOUTH);
		configureGUI(dummyVector,obstVector);
		setPreferredSize(dimPanel);			
		setVisible(true);		
	}//end constructor
	
	public void configureGUI(Vector<RobotDummy> dummyVec,Vector<Obstacle> obstVec){				
		if (obstVec != null){
			ArenaObjectsVector aovObst = new ArenaObjectsVector();
			aovObst.addAll(obstVec);
			ArenaFloorPanel arenaFloor = new ArenaFloorPanel(aovObst);
			if (dummyVec != null) {
				ArenaObjectsVector aovUnit = new ArenaObjectsVector();
				for (int i = 0; i< dummyVec.size(); i++){ 
					aovUnit.add(dummyVec.elementAt(i).getUnitObject());
				}
				BufferedImage arenaFloorImage = Const.getPanelImage(arenaFloor);
				arena = new ArenaPanel(arenaFloorImage,aovUnit);
				add(arena,BorderLayout.CENTER);
			}
			else showMSG(true,"Unable to initialize Arena. DummiesVector null");			
		}
		else showMSG(true,"Unable to initialize Arena. ObstacleVector null");
	}//end configureGUI
		
	/*
	private BufferedImage getPanelImage(JPanel tempPanel){		
        BufferedImage panelImage = new BufferedImage(tempPanel.getWidth(), tempPanel.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D graphicBuffer = panelImage.createGraphics();
        tempPanel.paint(graphicBuffer);
        return panelImage;		
	}
	*/	
	
	public synchronized void showMSG(boolean setClear, String msg){
		if (setClear)logArea.setClear(true);
		logArea.addMSG(msg); 
	}//showMSG

	private synchronized void arenaRepaint(){
		arena.repaint();
	}//repaint
	
	public BufferedImage getArenaImage(){
		return Const.getPanelImage(arena);
	}//getArenaImage

	public ArenaPanel getArenaPanelRef(){return arena;}
	
	public void startRefresh(){repainter.start();}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		arenaRepaint();		
		//showMSG(false,"repaint");
	}
	
}//SimPanel
