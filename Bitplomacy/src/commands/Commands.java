package commands;


import org.newdawn.slick.Color;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;


/**
 * The Class Commands.
 */
public abstract class Commands extends ImageEntity{

	/** Used to draw this Command. */
	private EAnimation ea;

	/**
	 * Instantiates a new commands.
	 *
	 * @param x the x coordinate to draw this command
	 * @param y the y coordinate to draw this command
	 */
	public Commands(float x, float y) {
		setX(x);
		setY(y);
	}
	
	/* (non-Javadoc)
	 * @see com.erebos.engine.entity.ImageEntity#draw()
	 */
	public void draw(){
		ea.draw(this.getX(), this.getY());
	}
	
	/**
	 * Sets the EAnimation to draw this command.
	 *
	 * @param ea the new EAnimation
	 */
	public void setEA(EAnimation ea){
		setAnimation(ea);
		this.ea = ea;
	}
	
	/**
	 * Checks if this command was clicked on and executes it's command
	 * if it was.
	 *
	 * @param mx the x coordinate of the Mouse cursor
	 * @param my the y coordinate of the Mouse cursor
	 */
	public void update(int mx, int my){
		mx = (int) (mx - getX());
		my = (int) (my - getY());
		Color c = ea.getImage(0).getColor(mx, my);
		if (c.a != 0)
			execute();
	}
	
	/**
	 * Executes this command. Typically it will simply set the 
	 * command for the current Order of the GameCanvas but there
	 * are some special cases.
	 */
	public abstract void execute();
}
