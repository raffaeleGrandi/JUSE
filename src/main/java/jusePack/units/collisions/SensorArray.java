package jusePack.units.collisions;

import java.awt.geom.Point2D;

import jusePack.utils.Const;

public class SensorArray {

	private double sensorStepAngle = 360.0 / Const.sensorCount; // 20 degrees
	private double[] sensorAngleArray;
	private int[] sensorValue;
	private Point2D[] checkPoint;
	private int arrayLength = Const.sensorCount;
	private int counter;
	private int initSensValue;

	public SensorArray(int initVal){
		initSensValue = initVal;
		sensorAngleArray = new double[arrayLength];
		sensorValue = new int[arrayLength];
		checkPoint = new Point2D[arrayLength];
		initializeSensors();
	}

	public SensorArray(){ this(0); }

	private void initializeSensors(){
		for (int index = 0; index < sensorAngleArray.length; index++){
			sensorAngleArray[index] = Math.toRadians(sensorStepAngle*index);
			sensorValue[index]=initSensValue;
			checkPoint[index] = null;
			counter = 0;
		}
	}

	public void resetSensors(){
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

	public Point2D getCheckPoint(int sensNum){return checkPoint[sensNum];}

	public int length(){return arrayLength;}

	public int collisionNum(){return counter;}

	public boolean isFull(){
		return collisionNum() == length();
	}

	public boolean isEmpty(){
		return collisionNum() == 0;
	}

	public String toString(){
		String arrayString = "\n";
		for (int index = 0; index < sensorAngleArray.length; index++){
			arrayString += "sensor# "+index+", value: "+sensorValue[index]+", relativeCP: "+checkPoint[index]+"\n";
		}
		return arrayString;
	}

}
