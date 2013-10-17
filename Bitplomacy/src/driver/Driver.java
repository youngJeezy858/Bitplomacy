package driver;
import java.io.File;
import java.io.ObjectInputStream.GetField;

import gui.Canvas;

import com.erebos.engine.core.*;


public class Driver extends EGame{

	public Driver() 
	{
		super("Bitplomacy");
		setShowFPS(true);
		addCanvas(Canvas.getC());
	}

	public static void main(String[] args)
	{
		File homeFolder=new File(System.getProperty("user.home")+File.separator+"Bitplomacy");
		if(!homeFolder.exists())
			homeFolder.mkdir();
	
		copyNatives(homeFolder);
		System.setProperty("natives", homeFolder.getAbsolutePath());
		
		Driver d = new Driver();
		d.setTargetFPS(30);
		d.start(d, 1380, 831, false);
	}
	
	public static void copyNatives(File destDir)
	{
		
		//Extract native.zip to destDir
	
	}
	
}
