package jusePack.ArenaObjects;

public abstract class ArenaObjectABS {
	
	protected ObjectID objectID;
	
	public ArenaObjectABS(ObjectID objID){
		objectID = objID;
	}
	
	public ObjectID getID(){ return objectID;}

}//end class
