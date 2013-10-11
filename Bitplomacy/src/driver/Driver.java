package driver;
import gui.Canvas;

import com.erebos.engine.core.*;


public class Driver extends EGame{

	public Driver() {
		super("Bitplomacy");
		setShowFPS(true);
		addCanvas(Canvas.getC());
	}

	public static void main(String[] args){
		Driver d = new Driver();
		d.setTargetFPS(30);
		d.start(d, 1380, 831, false);
	}
	
}
