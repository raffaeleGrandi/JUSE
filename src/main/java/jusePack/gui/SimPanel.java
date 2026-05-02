package jusePack.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import jusePack.ArenaObjects.ArenaObjectABS;
import jusePack.ArenaObjects.Obstacle;
import jusePack.gui.panels.*;
import jusePack.units.RobotDummy;
import jusePack.utils.Const;
import jusePack.utils.LogArea;

@SuppressWarnings("serial")
public class SimPanel extends JPanel implements ActionListener{

	protected Dimension dimPanel;
	protected LogArea logArea;
	private ArenaPanel arena;
	protected Timer repainter;

	public SimPanel(Dimension mainPanelDim, String logAreaInit, List<RobotDummy> dummyVector, List<Obstacle> obstVector) {
		dimPanel = mainPanelDim;
		logArea = new LogArea(logAreaInit,5,5);
		repainter = new Timer(Const.repaintTime,this);
		setLayout(new BorderLayout());
		add(new JScrollPane(logArea),BorderLayout.SOUTH);
		configureGUI(dummyVector,obstVector);
		setPreferredSize(dimPanel);
		setVisible(true);
	}

	public void configureGUI(List<RobotDummy> dummyVec, List<? extends Obstacle> obstVec){
		if (obstVec != null){
			List<ArenaObjectABS> aovObst = new ArrayList<>();
			aovObst.addAll(obstVec);
			ArenaFloorPanel arenaFloor = new ArenaFloorPanel(aovObst);
			if (dummyVec != null) {
				List<ArenaObjectABS> aovUnit = new ArrayList<>();
				for (int i = 0; i< dummyVec.size(); i++){
					aovUnit.add(dummyVec.get(i).getUnitObject());
				}
				BufferedImage arenaFloorImage = Const.getPanelImage(arenaFloor);
				arena = new ArenaPanel(arenaFloorImage,aovUnit);
				add(arena,BorderLayout.CENTER);
			}
			else showMSG(true,"Unable to initialize Arena. DummiesVector null");
		}
		else showMSG(true,"Unable to initialize Arena. ObstacleVector null");
	}

	public synchronized void showMSG(boolean setClear, String msg){
		if (setClear)logArea.setClear(true);
		logArea.addMSG(msg);
	}

	private synchronized void arenaRepaint(){
		arena.repaint();
	}

	public BufferedImage getArenaImage(){
		return Const.getPanelImage(arena);
	}

	public ArenaPanel getArenaPanelRef(){return arena;}

	public void startRefresh(){repainter.start();}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		arenaRepaint();
	}

}
