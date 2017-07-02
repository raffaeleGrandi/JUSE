package jusePack.structures;

import java.util.Vector;
import jusePack.units.RobotDummy;

@SuppressWarnings("serial")
public class RobotDummyVector extends Vector<RobotDummy> {
	
	public RobotDummyVector(int startingCapacity, int increment){
		super(startingCapacity,increment);
	}

	public RobotDummyVector(){ this(1,1); }

}//end class
