package Driver;
import com.erebos.engine.core.*;


public class Driver extends EGame{

	public Driver() {
		super("Bitplomacy");
		setShowFPS(true);
		addCanvas(new Canvas(1));
	}

	public static void main(String[] args){
		Driver d = new Driver();
		d.setTargetFPS(60);
		d.start(d, 1380, 831, false);
	}
	
}
