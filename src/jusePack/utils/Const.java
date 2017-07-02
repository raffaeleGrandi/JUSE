package jusePack.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Const {
	
	public static final int larghezzaArena = 600; //siccome ci sono 40 caselle nel simulatore, ho dato 10 pixel/casella in quanto uso i decimali sia per gli step che per la misura dei wall
	public static final int altezzaArena = larghezzaArena;
	public static final Color arenaFloorColor1 = new Color(188,238,104);
	public static final Color arenaFloorColor2 = new Color(202,255,112);
	public static final Color sensorRangeColor = new Color(119,136,153);
		
	public static final Color arenaBorederColor = Color.RED;
	public static final int numeroCaselle = 40;
	public static final int latoCasella = larghezzaArena/numeroCaselle;		//15 pixel
	
	public static final Dimension arenaDimension = new Dimension(larghezzaArena,altezzaArena); //Dimension(int width, int height) 
	public static final Point guiFrameLoc = new Point(50,50);
	public static final Dimension guiFrameDim = new Dimension(600,680);
	public static final String guiFrameTitle = new String("Test - Juse Main Frame");
	public static final int repaintTime = 40; // tempo di refresh dell'arena in ms
	public static final double auxGPanelScaleFactor = 0.5;
	public static final Dimension auxGPanelDim = new Dimension((int)(larghezzaArena*auxGPanelScaleFactor),(int)(altezzaArena*auxGPanelScaleFactor));
	public static final Color collisionPanelColor = Color.LIGHT_GRAY;
	public static final Color collisionColor = Color.BLACK;
		
	public static final Color unitColor = Color.BLACK;
	public static final Dimension unitDim = new Dimension(latoCasella,latoCasella);
	public static final Color markerColor = Color.WHITE;
	public static final int unitRadius = latoCasella; //pixel
	public static final int markerCircleRadius = 3;
	public static final int markerCircleDeltaLocation = unitRadius*3/5;
	
	public static final int angleStep = 2;	
	public static final double linearStep = 0.2;
	public static final int angleStepCollisionChecking = 5;
	public static final int numSensori = 18;
	public static final int sensorsCheckingRange0 = 3; //in pixel;  20 pix = 1 casella (RANGE> min: 2; max: not defined)
	public static final double sensorsCheckingRangeMin = 0.5; //in caselle
	
	public static final int typeUnit = 0;
	public static final int typeObstRect = 1;
	public static final int typeObstOval = 2;
	public static double devStdPos = 0.2; //
	public static double devStdAng = 1;
	
	public static boolean cmnt = false; //abilita o disabilita i commenti
	public static double timeSample = 0.1;
		
	/**************************utility method*****************************************************************/
	
	public static BufferedImage getPanelImage(JPanel tempPanel){		
        BufferedImage panelImage = new BufferedImage(tempPanel.getWidth(), tempPanel.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D graphicBuffer = panelImage.createGraphics();
        tempPanel.paint(graphicBuffer);
        return panelImage;		
	}	
		
}// end Const
