package Objects;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

import Driver.Canvas;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

public class Territory extends ImageEntity {

	GameContainer gc;
	EAnimation terr;
	String name;
	Color color;
	Canvas canvas;
	
	
	final int MOUSEOVER = 1;
	int state;
	
	public Territory(GameContainer gc, Image terr, String name, Canvas c, boolean land, int r, int g, int b){
		super(terr);
		this.terr = new EAnimation(terr);
		this.name = name;
		color = new Color(r, g, b);
		state = 0;
		canvas = c;
	}
	
	public void update(int delta){
		Color c;
		try{
			c = canvas.MasterMap.getColor(Mouse.getX(), Math.abs(Mouse.getY()-831));
			if (c.getRed()==color.getRed() && c.getBlue()==color.getBlue() && c.getGreen()==color.getGreen()){
				System.out.println(name + " hey I work!!!!");
				canvas.tName = name;
				canvas.state = 1;
			}
			else
				System.out.println(name + "\n" + c.getRed() + " " + c.getBlue() + " " + c.getGreen() + "\n" + color.getRed() + " " + color.getBlue() + " " + color.getGreen());
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.out.println(e.getMessage());
			System.out.println("nope " + name);
		}
		
		switch (state){
		case MOUSEOVER: {
			
		}
		}
	}

	
}
