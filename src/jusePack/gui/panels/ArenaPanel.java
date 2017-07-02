package jusePack.gui.panels;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.util.*;

import jusePack.ArenaObjects.ArenaObjectABS;
import jusePack.ArenaObjects.ObjectID;
import jusePack.structures.ArenaObjectsVector;
import jusePack.units.UnitObject;
import jusePack.utils.Const;

@SuppressWarnings("serial")
public class ArenaPanel extends JPanel {
	
	private Graphics agc; //arena graphic component
	private BufferedImage floorImage;
	public ArenaObjectsVector dummiesvec;	
	private double dFactor = Const.latoCasella;
	
	public ArenaPanel(BufferedImage floorImg, ArenaObjectsVector aovDummies){	
		floorImage = floorImg;
		dummiesvec = aovDummies; 				
		setDoubleBuffered(true);
		setSize(Const.arenaDimension);		
	}//end constructor
	
	public void paint(Graphics g){
		super.paintComponent(g);
		agc = g;
		drawArenaFloorImage();
		drawArenaObjects();	
	}//end paint	
	
	/******************************************************************************************************/	
	
	private void drawArenaFloorImage(){agc.drawImage(floorImage,0,0,null);}
	
	
	public void drawArenaObjects(){	
		
		for (Enumeration<ArenaObjectABS> enumDummies = dummiesvec.elements(); enumDummies.hasMoreElements();){
			UnitObject uoTemp = (UnitObject)enumDummies.nextElement();			
			drawRUnitSR(uoTemp.getID().getPos(),uoTemp.sensorRange); 
		}		
		
		for (Enumeration<ArenaObjectABS> enumDummies = dummiesvec.elements(); enumDummies.hasMoreElements();){
			UnitObject uoTemp = (UnitObject)enumDummies.nextElement();			
			drawRUnit(uoTemp.getID()); 
		}
	}//drawArenaObjects	
	
	private void drawRUnitSR(Point2D unitLocation,double sensRangeRadius){//in caselle
		int circleCenterX = new Double(dFactor*unitLocation.getX()).intValue(); // ??? il casting esplicito mi dava una ClassCastException
		int circleCenterY = new Double(dFactor*unitLocation.getY()).intValue();
		int circleRadius = Const.unitRadius+(int)(dFactor*sensRangeRadius); //
		agc.setColor(Const.sensorRangeColor);
		agc.drawOval(circleCenterX - circleRadius, circleCenterY - circleRadius, circleRadius * 2, circleRadius * 2);		
	}
	
	private void drawRUnit(ObjectID unitID){
		Point2D unitLocation = unitID.getPos(); //conversione implicita essendo il valore di ritorno una Position estensione di Point2D
		int circleCenterX = new Double(dFactor*unitLocation.getX()).intValue(); // ??? il casting esplicito mi dava una ClassCastException
		int circleCenterY = new Double(dFactor*unitLocation.getY()).intValue();
		int circleRadius = Const.unitRadius; // disegna l'estensione dell'unità
		Color unitColor = unitID.getColor();
		agc.setColor(unitColor);
		agc.fillOval(circleCenterX - circleRadius, circleCenterY - circleRadius, circleRadius * 2, circleRadius * 2);
		agc.setColor(Const.markerColor);
		circleRadius = circleRadius - 3; // raggio cerchio bianco
		agc.drawOval(circleCenterX - circleRadius, circleCenterY - circleRadius, circleRadius * 2, circleRadius * 2);
		/** disegna il marker **/
		int markerCenterX = circleCenterX + new Double(Const.markerCircleDeltaLocation*Math.cos(unitID.getAngle())).intValue();
		int markerCenterY = circleCenterY + new Double(Const.markerCircleDeltaLocation*Math.sin(unitID.getAngle())).intValue();
		int markerRadius = Const.markerCircleRadius;
		agc.fillOval(markerCenterX - markerRadius, markerCenterY - markerRadius, markerRadius * 2, markerRadius * 2);
		agc.setColor(Const.markerColor);
		agc.setFont(new Font("SansSerif",agc.getFont().getStyle(),10));
		agc.drawString(""+unitID.getIDnum(),circleCenterX-3,circleCenterY+3);
	}//end drawRUnit	
	
}// end class ArenaPanel	