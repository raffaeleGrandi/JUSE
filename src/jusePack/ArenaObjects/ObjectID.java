package jusePack.ArenaObjects;
	
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;

import jusePack.utils.Position;

public class ObjectID {
	
	private int objIDnum;
	private int objType;
	private Position objPos;
	private Dimension objDim; //only for obstacles
	private Color objColor;
	
	
	public ObjectID(int objectType, Color objectColor, Position objectPosition, Dimension objectDimension){
		objType = objectType;
		objColor = objectColor;
		objPos = objectPosition;
		objDim =objectDimension;		
	}
	
	public synchronized int getIDnum(){return objIDnum;}
	
	public void setIDnum(int objIDnumber){objIDnum = objIDnumber;}
	
	public synchronized Position getPos(){return objPos;} //unziona anche come getLoc in quanto objPos è un'estensione di Point2D
	
	public void setPos(Position newPos){objPos = newPos;}
	
	public void setLoc(Point2D newLoc){objPos.x = newLoc.getX(); objPos.y = newLoc.getY(); }
	
	public synchronized double getAngle(){return objPos.theta;} //radians
	
	public synchronized void setAngle(double newAngle){ objPos.theta = newAngle%(2*Math.PI); }
	
	public int getType(){return objType;}
	
	public Color getColor(){return objColor;}
	
	public Dimension getDim(){ return objDim;}	
	
}//end class
