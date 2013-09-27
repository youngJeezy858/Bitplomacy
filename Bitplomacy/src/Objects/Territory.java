package Objects;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.SpriteSheet;

import Driver.Canvas;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

public class Territory extends ImageEntity {

	private EAnimation terr;
	private String name;
	private Color colorKey;
	Canvas canvas;
	int owner;
	private final int NETURAL = 0;
	private final int ENGLAND = 1;
	private final int AUSTRIA_HUNGARY = 2;
	private final int ITALY = 3;
	private final int TURKEY = 4;
	private final int FRANCE = 5;
	private final int RUSSIA = 6;
	private final int GERMANY = 7;

	
	public Territory(SpriteSheet terr, String name, Canvas c, boolean land, Color color){
		super(terr);
		owner = TURKEY;
		this.terr = new EAnimation(terr.getSprite(owner, 0));
		this.name = name;
		colorKey = color;
		canvas = c;
	}
	
	public void update(int delta){
		Color c;
		try{
			c = canvas.MasterMap.getColor(Mouse.getX(), Math.abs(Mouse.getY()-831));
			if (c.getRed()==colorKey.getRed() && c.getBlue()==colorKey.getBlue() && c.getGreen()==colorKey.getGreen()){
				System.out.println(name + " hey I work!!!!");
				canvas.tName = name;
				canvas.state = 1;
			}
			else
				System.out.println(name + "\n" + c.getRed() + " " + c.getBlue() + " " + c.getGreen() + "\n" + colorKey.getRed() + " " + colorKey.getBlue() + " " + colorKey.getGreen());
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.out.println(e.getMessage());
			System.out.println("nope " + name);
		}

	}
	
	public void eDraw(){
		terr.draw(this.getX(), this.getY());
	}

	public void setOwner(int owner){
		this.owner = owner;
	}
}
