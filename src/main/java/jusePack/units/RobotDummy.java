package jusePack.units;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import jusePack.utils.Const;
import jusePack.utils.Position;

public abstract class RobotDummy extends RobotUnit implements ActionListener {

	public Timer faultTimer = null;	
		
	public RobotDummy(int unitIDnum, Position unitPos, double sensRange, int faultTime, Color unitColor){
		super(unitIDnum,unitPos,sensRange,unitColor);	
		if ((faultTime) >= 0){
			faultTimer = new Timer(faultTime, this);			
		}		
	}//RobotDummy
	
	public RobotDummy(int unitIDnum,Position unitPos,double sensRange){
		this(unitIDnum,unitPos,sensRange,-1,Const.unitColor);
	}
		
	public void setActive(boolean status){ activeStatus = status; }
	
	public abstract void executeLoop();
	
	public void actionPerformed(ActionEvent e){		
		faultTimer.stop();
		activeStatus = false;
	}//actionPerformed	
	
}//RobotDummy
