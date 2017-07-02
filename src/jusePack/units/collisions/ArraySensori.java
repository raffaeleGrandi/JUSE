package jusePack.units.collisions;

	import java.awt.geom.Point2D;

import jusePack.utils.Const;

public class ArraySensori {

	private double sensorStepAngle = 360/Const.numSensori; // 20 gradi
	private double[] sensorAngleArray;
	private int[] sensorValue;
	private Point2D checkPoint[];
	private int arrayLenght = Const.numSensori;
	private int counter;
	private int initSensValue;
	
	
	public ArraySensori(int initVal){
		initSensValue = initVal;
		sensorAngleArray = new double[arrayLenght];
		sensorValue = new int[arrayLenght];
		checkPoint = new Point2D[arrayLenght];
		inizilizzaSensori();		
	}
	
	public ArraySensori(){ this(0); }
	
	private void inizilizzaSensori(){		
		for (int index = 0; index < sensorAngleArray.length; index++){
			sensorAngleArray[index] = Math.toRadians(sensorStepAngle*index);
			sensorValue[index]=initSensValue;
			checkPoint[index] = null;
			counter = 0;
		}				
	} 
	
	public void resetSensors(){ // chiamato dal CD
		for (int index = 0; index < sensorAngleArray.length; index++){
			sensorValue[index]=initSensValue;
			checkPoint[index] = null;
			counter = 0;
		}	
	}
	
	public void setSensorValue(int sensNum, int sensValue){ 
		sensorValue[sensNum] = sensValue;
		if (sensValue != 0) counter++;
	}
	
	public void resetSensorValue(int sensNum){ sensorValue[sensNum] = initSensValue; }
	
	public int getSensorValue(int sensNum){return sensorValue[sensNum];}
	
	public int[] getSensors(){ return sensorValue;}
	
	public double getSensorAngle(int sensNum){ return sensorAngleArray[sensNum]; }
	
	public void setCheckingPoint(int sensNum, Point2D cPoint){checkPoint[sensNum] = cPoint;}
	
	public Point2D getCheckPoint(int sensNum){return checkPoint[sensNum];} //usato da buildFCWlist() di CH
	
	public int length(){return arrayLenght;}
	
	public int collisionNum(){return counter;} // chiamato dalla rua
	
	public boolean isFull(){ 
		if (collisionNum() == length())	return true;
		else return false;
	}
	
	public boolean isEmpty(){
		if (collisionNum() == 0) return true;
		else return false;
	}
	
	public String toString(){
		String arrayString = "\n";	
		for (int index = 0; index < sensorAngleArray.length; index++){
			arrayString += "sensor# "+index+", value: "+sensorValue[index]+", relativeCP: "+checkPoint[index]+"\n";
		}
		return arrayString;
	}
	
}//end class

/*
 * Angolo - sensore:
 * 0 - 0
 * 20 - 1
 * 40 - 2
 * 60 - 3
 * 
 * 
Angoli di collisione:

	x muro:

	frontali: 0/28 -> 332/359 [0/30 -> 330/360]
	
	laterali sinistri:62/118 [60/120]

	posteriori: 152/208 [150/210]

	laterali destri: 242/298 [240/300]	

	x unità: minore
	
* */
