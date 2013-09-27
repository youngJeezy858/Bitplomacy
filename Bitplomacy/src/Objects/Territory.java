package Objects;
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
	private boolean supplyCenter;
	private boolean land;
	
	private int owner;
	private final int NETURAL = 0;
	private final int ENGLAND = 1;
	private final int AUSTRIA_HUNGARY = 2;
	private final int ITALY = 3;
	private final int TURKEY = 4;
	private final int FRANCE = 5;
	private final int RUSSIA = 6;
	private final int GERMANY = 7;
	

	/*
	 * Constructor method for the Territory class.
	 * 
	 * @param terr -> SpriteSheet image of the Territory
	 * @param name -> the name of a Territory as a String
	 * @param isLand -> boolean value to determine if a Territory is land or water
	 * @param hasSC -> boolean value to determine if a Territory has a supply center or not
	 * @param color -> the color that acts as the Territory's key when referencing the master map
	 */
	public Territory(SpriteSheet terr, String name, boolean isLand, boolean hasSC, Color color){
		super(terr);
		owner = TURKEY;
		this.terr = new EAnimation(terr.getSprite(owner, 0));
		this.name = name;
		colorKey = color;
		supplyCenter = hasSC;
		land = isLand;
	}
	
	/*
	 * Updates the Territory. Current state of this method is that if the mouse cursor is over this 
	 * Territory, then display the name.  See updateGame in the Canvas class for more info.
	 */
	public void update(){
		Color c;
		try{
			c = Canvas.getC().getCurrentColor();
			if (c.getRed()==colorKey.getRed() && c.getBlue()==colorKey.getBlue() && c.getGreen()==colorKey.getGreen()){
				System.out.println(name + " hey I work!!!!");
				Canvas.getC().setTName(name);
				Canvas.getC().setState(1);
			}
			else
				System.out.println(name + "\n" + c.getRed() + " " + c.getBlue() + " " + c.getGreen() + "\n" + colorKey.getRed() + " " + colorKey.getBlue() + " " + colorKey.getGreen());
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.out.println(e.getMessage());
			System.out.println("nope " + name);
		}

	}
	
	/*
	 * Draws the territory to the map
	 */
	public void eDraw(){
		terr.draw(this.getX(), this.getY());
	}

	/*
	 * Sets the owner of the Territory.  See class fields for int codes of each team.
	 * @param owner -> the owner of the Territory represented as an int value.
	 */
	public void setOwner(int owner){
		this.owner = owner;
	}
}
