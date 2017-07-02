package jusePack.units.collisions;

	import java.awt.geom.Point2D;
	import java.util.Vector;

@SuppressWarnings("serial")
public class PointsList extends Vector<Point2D>{
	
	private int MAX_SIZE = 100;
	private int index = 0;
	
	public PointsList(int startingCapacity, int increment){
		super(startingCapacity,increment);
	}
	
	public PointsList(){super();}
	
	public void addCollisionPoint(Point2D newCollisionPoint){ // chimato dal collisionDetector
		add(index,newCollisionPoint);
		index++;
		index %= MAX_SIZE;
	}
		
	public void clearCollisionsList(){removeAllElements(); index = 0;}
	
	public int getMAXsize(){return MAX_SIZE;}
	
	public void setMAXsize(int newMAXsize){MAX_SIZE = newMAXsize;}
	
}


