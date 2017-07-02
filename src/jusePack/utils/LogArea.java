package jusePack.utils;

import javax.swing.*;

@SuppressWarnings("serial")
public class LogArea extends JTextArea{
	private boolean clear = true;
	
	public LogArea(String initString){
		super(initString);
		setEditable(false);			
	}
	
	public LogArea(String initString, int row, int col){
		super(initString,row,col);
		setEditable(false);		
	}
	
	public void addMSG(String msg){ 
		if (clear){ setText(""); clear = false; }
		append("\n"+msg);
		this.setCaretPosition(this.getDocument().getLength());		
	}
	
	public void setClear(boolean choice){ clear = choice; }

}//end class
