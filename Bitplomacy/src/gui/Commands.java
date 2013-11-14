package gui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

// TODO: Auto-generated Javadoc
/**
 * The Class Commands.
 */
public abstract class Commands extends ImageEntity{

	/** The ea. */
	private EAnimation ea;
	
	/** The color key. */
	private int colorKey;
	
	/**
	 * Instantiates a new commands.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	public Commands(float x, float y, int color) {
		setX(x);
		setY(y);
		colorKey = color;
	}
	
	/* (non-Javadoc)
	 * @see com.erebos.engine.entity.ImageEntity#draw()
	 */
	public void draw(){
		ea.draw(this.getX(), this.getY());
	}
	
	/**
	 * Sets the ea.
	 *
	 * @param ea the new ea
	 */
	public void setEA(EAnimation ea){
		setAnimation(ea);
		this.ea = ea;
	}
	
	/**
	 * Update.
	 */
	public void update(){
		Color c = Canvas.getC().getCurrentColor();
		if (c.getRed()==0 && c.getBlue()==colorKey && c.getGreen()==0 && Mouse.isButtonDown(0))
			execute();
	}
	
	/**
	 * Execute.
	 */
	public abstract void execute();
}
