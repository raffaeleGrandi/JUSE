package jusePack.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Const {

	public static final int arenaWidth = 600;
	public static final int arenaHeight = arenaWidth;
	public static final Color arenaFloorColor1 = new Color(188,238,104);
	public static final Color arenaFloorColor2 = new Color(202,255,112);
	public static final Color sensorRangeColor = new Color(119,136,153);

	public static final Color arenaBorderColor = Color.RED;
	public static final int gridCellCount = 40;
	public static final int cellSize = arenaWidth / gridCellCount; // 15 pixels

	public static final Dimension arenaDimension = new Dimension(arenaWidth, arenaHeight);
	public static final Point guiFrameLoc = new Point(50,50);
	public static final Dimension guiFrameDim = new Dimension(600,680);
	public static final String guiFrameTitle = "Test - Juse Main Frame";
	public static final int repaintTime = 40; // arena refresh time in ms
	public static final double auxGPanelScaleFactor = 0.5;
	public static final Dimension auxGPanelDim = new Dimension((int)(arenaWidth*auxGPanelScaleFactor),(int)(arenaHeight*auxGPanelScaleFactor));
	public static final Color collisionPanelColor = Color.LIGHT_GRAY;
	public static final Color collisionColor = Color.BLACK;

	public static final Color unitColor = Color.BLACK;
	public static final Dimension unitDim = new Dimension(cellSize, cellSize);
	public static final Color markerColor = Color.WHITE;
	public static final int unitRadius = cellSize; // pixels
	public static final int markerCircleRadius = 3;
	public static final int markerCircleDeltaLocation = unitRadius*3/5;

	public static final int angleStep = 2;
	public static final int sensorCount = 18;
	public static final int sensorsCheckingRange0 = 3; // in pixels
	public static final double sensorsCheckingRangeMin = 0.5; // in cells

	public static boolean debugEnabled = false;
	public static double timeSample = 0.1;

	/**************************utility method*****************************************************************/

	public static BufferedImage getPanelImage(JPanel tempPanel){
        BufferedImage panelImage = new BufferedImage(tempPanel.getWidth(), tempPanel.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D graphicBuffer = panelImage.createGraphics();
        tempPanel.paint(graphicBuffer);
        return panelImage;
	}

}// end Const
