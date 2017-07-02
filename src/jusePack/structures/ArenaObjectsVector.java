package jusePack.structures;

	import java.util.Vector;

import jusePack.ArenaObjects.ArenaObjectABS;

@SuppressWarnings("serial")
public class ArenaObjectsVector extends Vector<ArenaObjectABS>{
	
	public ArenaObjectsVector(int startingCapacity, int increment){
		super(startingCapacity,increment);
	}
	
	public ArenaObjectsVector(){this(1,1);}

}
