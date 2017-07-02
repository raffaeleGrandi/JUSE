package jusePack.structures;

import java.util.Vector;

import jusePack.ArenaObjects.Obstacle;

@SuppressWarnings("serial")
public class ObstaclesVector extends Vector<Obstacle> {
	
	public ObstaclesVector(int startingCapacity, int increment){
		super(startingCapacity,increment);
	}

	public ObstaclesVector(){ this(1,1); }

}
