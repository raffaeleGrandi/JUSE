package jusePack.gui.panels;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import jusePack.utils.Const;

@SuppressWarnings("serial")
public class CollisionPanel extends JPanel{

	private Graphics cgc; //collision graphic component
	private List<Point2D> cpv;
	private int cpvIndex = 0;
	private static final int MAX_SIZE = 100;
	private double scaleFactor;
	public boolean panelActive = false; //attivato dal CollisionDetector

	public CollisionPanel(){
		setDoubleBuffered(true);
		setSize(Const.auxGPanelDim);
		scaleFactor = Const.auxGPanelScaleFactor;
		cpv = new ArrayList<>();
	}

	public void paint(Graphics g){
		super.paintComponent(g);
		cgc = g;
		drawBackground();
		if (panelActive){ drawCollisions();	}
	}

	public void addCollisionPoint(Point2D cp){
		if (cpvIndex < cpv.size()) {
			cpv.set(cpvIndex, cp);
		} else {
			cpv.add(cp);
		}
		cpvIndex++;
		cpvIndex %= MAX_SIZE;
	}

	public void clearCollisionsVector(){cpv.clear(); cpvIndex = 0;}

	private void drawBackground(){ setBackground(Const.collisionPanelColor); }

	private void drawCollisions(){
		for (Point2D cPoint : cpv){
			int cPointX = (int)(cPoint.getX()*scaleFactor);
			int cPointY = (int)(cPoint.getY()*scaleFactor);
			cgc.setColor(Const.collisionColor);
			cgc.drawLine(cPointX, cPointY, cPointX, cPointY);
		}
	}

}// end collisionPanel
