import acm.graphics.*;
import acm.program.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;

import javax.swing.*;

public class MyFirstTimer extends GraphicsProgram implements ActionListener {
	public static final int PROGRAM_HEIGHT = 600;
	public static final int PROGRAM_WIDTH = 800;
	public static final int MAX_STEPS = 20;
	private GLabel myLabel;
	private Timer time;
	private int numTimes = 0;
	

	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
	}
	
	public void run() {
		myLabel = new GLabel("# of times called?", 0, 100);
		add(myLabel);
		
		this.time = new Timer (1000, this);
		this.time.setInitialDelay(3000);
		this.time.start();		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		numTimes++;
		myLabel.move(5,0);
		myLabel.setLabel("times called? " + numTimes);
		
		if (numTimes == 10) {
			this.time.stop();
		}
	}
	
	public static void main(String[] args) {
		new MyFirstTimer().start();
	}
}