package jusePack.gui.panels;

import java.awt.*;
import java.util.Enumeration;
import javax.swing.*;

import jusePack.ArenaObjects.ArenaObjectABS;
import jusePack.ArenaObjects.ObjectID;
import jusePack.ArenaObjects.Obstacle;
import jusePack.structures.ArenaObjectsVector;
import jusePack.utils.Const;
import jusePack.utils.Position;

@SuppressWarnings("serial")
public class ArenaFloorPanel extends JPanel{

	private int colorSwitchFlag = 0;
	private Graphics pgc; //graphic panel component
	private ArenaObjectsVector obstVect;
	private int dFactor = Const.latoCasella;
	
	public ArenaFloorPanel(ArenaObjectsVector obstacles){
		obstVect = obstacles;
		setDoubleBuffered(true);
		setSize(Const.arenaDimension);
	}
	
	public void paintComponent(Graphics g){
		pgc = g;
		drawArenaFloor();		
		drawBanner();
		drawArenaObst();
		drawArenaBorder();
		
	}//paintComponent

	/*****************************************************************************************************/
	
	private  void drawArenaFloor(){
		Color startingSquareFloorColor = Const.arenaFloorColor1;		
		pgc.setColor(startingSquareFloorColor);
		for (int i=0; i < Const.numeroCaselle ; i++){// righe 
			switchSquareFloorColor();
			for (int j=0; j < Const.numeroCaselle; j++){// colonne
				pgc.fillRect(j*Const.latoCasella,i*Const.latoCasella, Const.latoCasella, Const.latoCasella);
				switchSquareFloorColor();
			}						
		}		
	}//drawArenaFloor
	
	private void switchSquareFloorColor(){
		if (colorSwitchFlag == 0) {	pgc.setColor(Const.arenaFloorColor2);	colorSwitchFlag = 1;}
		else { pgc.setColor(Const.arenaFloorColor1); colorSwitchFlag = 0;	}	
	}//switchSquareFloorColor
	
	/*****************************************************************************************************/
	
	private void drawArenaBorder(){
		drawRectObst(new Obstacle(Const.typeObstRect,Const.arenaBorederColor, new Position(0,20,0), new Dimension(1,40))); //left
		drawRectObst(new Obstacle(Const.typeObstRect,Const.arenaBorederColor, new Position(40,20,0), new Dimension(1,40))); //right
		drawRectObst(new Obstacle(Const.typeObstRect,Const.arenaBorederColor, new Position(20,0,Math.toRadians(90)), new Dimension(1,40))); //high
		drawRectObst(new Obstacle(Const.typeObstRect,Const.arenaBorederColor, new Position(20,40,Math.toRadians(90)), new Dimension(1,40))); //right
	}//end drawArenaBorder
	
	private void drawBanner(){
		pgc.setFont(new Font("SansSerif",pgc.getFont().getStyle(),12));
		switchSquareFloorColor();
		pgc.drawString("J",dFactor*35+7,dFactor*39-3);
		switchSquareFloorColor();
		pgc.drawString("U",dFactor*36+3,dFactor*39-3);
		switchSquareFloorColor();
		pgc.drawString("S",dFactor*37+3,dFactor*39-3);
		switchSquareFloorColor();
		pgc.drawString("E",dFactor*38+3,dFactor*39-3);
	}
	
	/*****************************************************************************************************/
	
	private void drawArenaObst(){		
		for (Enumeration<ArenaObjectABS> enumObst = obstVect.elements(); enumObst.hasMoreElements();){
			Obstacle obstTemp = (Obstacle)enumObst.nextElement();
			switch(obstTemp.getID().getType()){
				case Const.typeObstRect: drawRectObst(obstTemp); break;
				case Const.typeObstOval: drawOvalObst(obstTemp); break;
			}//end switch
		}//end for
	}//drawArenaObst	
	
	private void drawRectObst(Obstacle obst){		
		ObjectID tempID = obst.getID();
		pgc.setColor(tempID.getColor());
		double dFactor = Const.latoCasella;
		double pX = dFactor*tempID.getPos().x;
		double pY = dFactor*tempID.getPos().y;
		double wth = dFactor*tempID.getDim().width;
		double hgt = dFactor*tempID.getDim().height;
		double ang = tempID.getAngle(); //rad
		fillRotatedRect(pX,pY,wth,hgt,ang);
	}// draw drawWallObj
	
	
	
	private void fillRotatedRect(double cX, double cY, double width, double depth, double rotAng) {
		
		double k = width/2;
		double h = depth/2;
		double r = Math.sqrt(Math.pow(k,2)+Math.pow(h,2));
		double aStatR = Math.atan(h/k);
		double rotAngR = rotAng;
		
		int x1 = (int)(cX + r*Math.cos(aStatR + rotAngR));
		int y1 = (int)(cY - r*Math.sin(aStatR + rotAngR));
		int x2 = (int)(cX + r*Math.cos(- aStatR + rotAngR));
		int y2 = (int)(cY - r*Math.sin(- aStatR + rotAngR));
		int x0 = (int)(cX + r*Math.cos(Math.PI - aStatR + rotAngR));
		int y0 = (int)(cY - r*Math.sin(Math.PI - aStatR + rotAngR));
		int x3 = (int)(cX + r*Math.cos(Math.PI + aStatR + rotAngR));
		int y3 = (int)(cY - r*Math.sin(Math.PI + aStatR + rotAngR));		
		
		int[] arrayAscisseT1 = new int[3]; int[] arrayOrdinateT1 = new int[3];
		int[] arrayAscisseT2 = new int[3]; int[] arrayOrdinateT2 = new int[3];
		
		arrayAscisseT1[0] = x0;	arrayAscisseT1[1] = x3;	arrayAscisseT1[2] = x2;
		arrayOrdinateT1[0] = y0; arrayOrdinateT1[1] = y3; arrayOrdinateT1[2] = y2;
		
		arrayAscisseT2[0] = x0;	arrayAscisseT2[1] = x1;	arrayAscisseT2[2] = x2;
		arrayOrdinateT2[0] = y0; arrayOrdinateT2[1] = y1; arrayOrdinateT2[2] = y2;
		
		pgc.fillPolygon(arrayAscisseT1,arrayOrdinateT1,3);
		pgc.fillPolygon(arrayAscisseT2,arrayOrdinateT2,3);	
		
	}//end fillRotatedRect
	
	public void drawOvalObst(Obstacle obst){		
		ObjectID tempID = obst.getID();
		pgc.setColor(tempID.getColor());
		double dFactor = Const.latoCasella;
		pgc.fillOval((int)(dFactor*tempID.getPos().x),(int)(dFactor*tempID.getPos().y),(int)dFactor*tempID.getDim().width,(int)dFactor*tempID.getDim().height);
	}
	
}//ArenaFloorImageBuilder
	
	
