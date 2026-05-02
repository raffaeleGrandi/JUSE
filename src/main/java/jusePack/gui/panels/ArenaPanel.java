package jusePack.gui.panels;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;

import jusePack.ArenaObjects.ArenaObjectABS;
import jusePack.ArenaObjects.ObjectID;
import jusePack.units.UnitObject;
import jusePack.utils.Const;

@SuppressWarnings("serial")
public class ArenaPanel extends JPanel {

	private Graphics agc;
	private BufferedImage floorImage;
	private List<ArenaObjectABS> unitObjects;
	private double cellSizePx = Const.cellSize;

	public ArenaPanel(BufferedImage floorImg, List<ArenaObjectABS> aovDummies){
		floorImage = floorImg;
		unitObjects = aovDummies;
		setDoubleBuffered(true);
		setSize(Const.arenaDimension);
	}

	public void paint(Graphics g){
		super.paintComponent(g);
		agc = g;
		drawArenaFloorImage();
		drawArenaObjects();
	}

	/******************************************************************************************************/

	private void drawArenaFloorImage(){agc.drawImage(floorImage,0,0,null);}

	private void drawArenaObjects(){

		for (ArenaObjectABS arenaObj : unitObjects){
			UnitObject uoTemp = (UnitObject)arenaObj;
			drawUnitSensorRange(uoTemp.getID().getPos(), uoTemp.getSensorRange());
		}

		for (ArenaObjectABS arenaObj : unitObjects){
			UnitObject uoTemp = (UnitObject)arenaObj;
			drawUnit(uoTemp.getID());
		}
	}

	private void drawUnitSensorRange(Point2D unitLocation, double sensRangeRadius){
		int circleCenterX = (int)(cellSizePx * unitLocation.getX());
		int circleCenterY = (int)(cellSizePx * unitLocation.getY());
		int circleRadius = Const.unitRadius+(int)(cellSizePx*sensRangeRadius);
		agc.setColor(Const.sensorRangeColor);
		agc.drawOval(circleCenterX - circleRadius, circleCenterY - circleRadius, circleRadius * 2, circleRadius * 2);
	}

	private void drawUnit(ObjectID unitID){
		Point2D unitLocation = unitID.getPos();
		int circleCenterX = (int)(cellSizePx * unitLocation.getX());
		int circleCenterY = (int)(cellSizePx * unitLocation.getY());
		int circleRadius = Const.unitRadius;
		Color unitColor = unitID.getColor();
		agc.setColor(unitColor);
		agc.fillOval(circleCenterX - circleRadius, circleCenterY - circleRadius, circleRadius * 2, circleRadius * 2);
		agc.setColor(Const.markerColor);
		circleRadius = circleRadius - 3;
		agc.drawOval(circleCenterX - circleRadius, circleCenterY - circleRadius, circleRadius * 2, circleRadius * 2);
		int markerCenterX = circleCenterX + (int)(Const.markerCircleDeltaLocation * Math.cos(unitID.getAngle()));
		int markerCenterY = circleCenterY + (int)(Const.markerCircleDeltaLocation * Math.sin(unitID.getAngle()));
		int markerRadius = Const.markerCircleRadius;
		agc.fillOval(markerCenterX - markerRadius, markerCenterY - markerRadius, markerRadius * 2, markerRadius * 2);
		agc.setColor(Const.markerColor);
		agc.setFont(new Font("SansSerif",agc.getFont().getStyle(),10));
		agc.drawString(""+unitID.getIDnum(),circleCenterX-3,circleCenterY+3);
	}

}
