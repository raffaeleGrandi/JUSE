package jusePack.ArenaObjects;

import java.awt.Color;
import java.awt.Dimension;

import jusePack.utils.Position;

public class Obstacle extends ArenaObjectABS {

	
	public Obstacle(int obstType, Color color, Position obstPos, Dimension obstDim ){
		super(new ObjectID(obstType, color, obstPos, obstDim));
	}
	
}//end class
