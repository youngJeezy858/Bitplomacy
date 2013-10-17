package driver;
import java.io.File;
import java.util.zip.ZipEntry;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;
import gui.Canvas;

import com.erebos.engine.core.*;


public class Driver extends EGame
{

	
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
		System.setProperty("org.lwjgl.librarypath", homeFolder.getAbsolutePath());
		
		Driver d = new Driver();
		d.setTargetFPS(30);
		d.start(d, 1380, 831, false);
	}
	
	public static void copyNatives(File destDir)
	{
	
		Driver.class.getClassLoader();
		InputStream stream=ClassLoader.getSystemResourceAsStream("natives/natives.zip");
		byte[] buffer = new byte[1024];

		     try
		     {
		    	ZipInputStream zis = new ZipInputStream(stream);
		    	ZipEntry ze = zis.getNextEntry();
		    	while(ze!=null)
		    	{
		    	   String fileName = ze.getName();
		           File newFile = new File(destDir + File.separator + fileName);
		           if(ze.isDirectory())  //Make sure we make a directory if we run into one in case it exist or we'll get exceptions
		           {
		        	   new File(newFile.getParent()).mkdirs();
		           }
		           else
		           {
		        	FileOutputStream fos = new FileOutputStream(newFile);    
		            new File(newFile.getParent()).mkdirs();
		            fos = new FileOutputStream(newFile);             
		            int len;
		            
		            while ((len = zis.read(buffer)) > 0) 
		            {
		            	fos.write(buffer, 0, len);
		            }
		            fos.close();   
		           }
		           ze = zis.getNextEntry();
		    	}
		        zis.closeEntry();
		    	zis.close();
		 
		     }
		     catch(IOException ex)
		     {
		    	 System.err.println("Error: Couldn't extract natives :"+ex.getMessage()); 
		     }
	 }    
	
	
	
}
