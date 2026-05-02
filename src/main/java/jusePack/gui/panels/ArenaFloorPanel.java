package jusePack.gui.panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import jusePack.ArenaObjects.ArenaObjectABS;
import jusePack.ArenaObjects.ArenaObjectType;
import jusePack.ArenaObjects.ObjectID;
import jusePack.ArenaObjects.Obstacle;
import jusePack.utils.Const;
import jusePack.utils.Position;

@SuppressWarnings("serial")
public class ArenaFloorPanel extends JPanel{

	private int colorSwitchFlag = 0;
	private Graphics pgc;
	private List<ArenaObjectABS> obstVect;
	private int cellSizePx = Const.cellSize;

	public ArenaFloorPanel(List<ArenaObjectABS> obstacles){
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
	}

	/*****************************************************************************************************/

	private void drawArenaFloor(){
		Color startingSquareFloorColor = Const.arenaFloorColor1;
		pgc.setColor(startingSquareFloorColor);
		for (int i=0; i < Const.gridCellCount; i++){
			switchSquareFloorColor();
			for (int j=0; j < Const.gridCellCount; j++){
				pgc.fillRect(j*Const.cellSize, i*Const.cellSize, Const.cellSize, Const.cellSize);
				switchSquareFloorColor();
			}
		}
	}

	private void switchSquareFloorColor(){
		if (colorSwitchFlag == 0) {	pgc.setColor(Const.arenaFloorColor2);	colorSwitchFlag = 1;}
		else { pgc.setColor(Const.arenaFloorColor1); colorSwitchFlag = 0;	}
	}

	/*****************************************************************************************************/

	private void drawArenaBorder(){
		drawRectObst(new Obstacle(ArenaObjectType.OBSTACLE_RECT,Const.arenaBorderColor, new Position(0,20,0), new Dimension(1,40))); //left
		drawRectObst(new Obstacle(ArenaObjectType.OBSTACLE_RECT,Const.arenaBorderColor, new Position(40,20,0), new Dimension(1,40))); //right
		drawRectObst(new Obstacle(ArenaObjectType.OBSTACLE_RECT,Const.arenaBorderColor, new Position(20,0,Math.toRadians(90)), new Dimension(1,40))); //top
		drawRectObst(new Obstacle(ArenaObjectType.OBSTACLE_RECT,Const.arenaBorderColor, new Position(20,40,Math.toRadians(90)), new Dimension(1,40))); //bottom
	}

	private void drawBanner(){
		pgc.setFont(new Font("SansSerif",pgc.getFont().getStyle(),12));
		switchSquareFloorColor();
		pgc.drawString("J",cellSizePx*35+7,cellSizePx*39-3);
		switchSquareFloorColor();
		pgc.drawString("U",cellSizePx*36+3,cellSizePx*39-3);
		switchSquareFloorColor();
		pgc.drawString("S",cellSizePx*37+3,cellSizePx*39-3);
		switchSquareFloorColor();
		pgc.drawString("E",cellSizePx*38+3,cellSizePx*39-3);
	}

	/*****************************************************************************************************/

	private void drawArenaObst(){
		for (ArenaObjectABS arenaObj : obstVect){
			Obstacle obstTemp = (Obstacle)arenaObj;
			switch(obstTemp.getID().getType()){
				case OBSTACLE_RECT: drawRectObst(obstTemp); break;
				case OBSTACLE_OVAL: drawOvalObst(obstTemp); break;
				default: break;
			}
		}
	}

	private void drawRectObst(Obstacle obst){
		ObjectID tempID = obst.getID();
		pgc.setColor(tempID.getColor());
		double cellPx = Const.cellSize;
		double pX = cellPx*tempID.getPos().x;
		double pY = cellPx*tempID.getPos().y;
		double wth = cellPx*tempID.getDim().width;
		double hgt = cellPx*tempID.getDim().height;
		double ang = tempID.getAngle();
		fillRotatedRect(pX,pY,wth,hgt,ang);
	}

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

		int[] xPointsT1 = new int[3]; int[] yPointsT1 = new int[3];
		int[] xPointsT2 = new int[3]; int[] yPointsT2 = new int[3];

		xPointsT1[0] = x0;	xPointsT1[1] = x3;	xPointsT1[2] = x2;
		yPointsT1[0] = y0; yPointsT1[1] = y3; yPointsT1[2] = y2;

		xPointsT2[0] = x0;	xPointsT2[1] = x1;	xPointsT2[2] = x2;
		yPointsT2[0] = y0; yPointsT2[1] = y1; yPointsT2[2] = y2;

		pgc.fillPolygon(xPointsT1,yPointsT1,3);
		pgc.fillPolygon(xPointsT2,yPointsT2,3);
	}

	public void drawOvalObst(Obstacle obst){
		ObjectID tempID = obst.getID();
		pgc.setColor(tempID.getColor());
		double cellPx = Const.cellSize;
		// La posizione è il CENTRO dell'ellissi (convenzione allineata ai rettangoli)
		double cx = cellPx * tempID.getPos().x;
		double cy = cellPx * tempID.getPos().y;
		double w  = cellPx * tempID.getDim().width;
		double h  = cellPx * tempID.getDim().height;
		pgc.fillOval((int)(cx - w / 2), (int)(cy - h / 2), (int)w, (int)h);
	}

}
