package jusePack.gui.panels;

	import java.awt.*;
	import java.awt.geom.Point2D;
	import java.util.Enumeration;
	import javax.swing.JPanel;	

import jusePack.units.collisions.PointsList;
import jusePack.utils.Const;

@SuppressWarnings("serial")
public class CollisionPanel extends JPanel{
	
	private Graphics cgc; //collision graphic component
	private PointsList cpv;
	private double scaleFactor;
	public boolean panelActive = false; //attivato dal CollisionDetector
	
	public CollisionPanel(){
		setDoubleBuffered(true);
		setSize(Const.auxGPanelDim);
		scaleFactor = Const.auxGPanelScaleFactor;
		cpv = new PointsList(); 		
	}
	
	public void paint(Graphics g){
		super.paintComponent(g);
		cgc = g;	
		drawBackground();
		if (panelActive){ drawCollisions();	}			
	}
		
	public void addCollisionPoint(Point2D cp){cpv.addCollisionPoint(cp);}
	
	public void clearCollisionsVector(){cpv.clearCollisionsList();}
	
	private void drawBackground(){ setBackground(Const.collisionPanelColor); }				
	
	private void drawCollisions(){		
		for (Enumeration<Point2D> enumCollPoints = cpv.elements(); enumCollPoints.hasMoreElements();){
			Point2D cPoint = enumCollPoints.nextElement();
			int cPointX = (int)(cPoint.getX()*scaleFactor);
			int cPointY = (int)(cPoint.getY()*scaleFactor);
			cgc.setColor(Const.collisionColor);
			cgc.drawLine(cPointX, cPointY, cPointX, cPointY);
		}		
	}
	
}// end collisionPanel