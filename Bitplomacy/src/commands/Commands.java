package commands;


import org.newdawn.slick.Color;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;


//TODO: Need a better command GUI. One that pops up right at your button click.
/**
 * The Class Commands.
 */
public abstract class Commands extends ImageEntity{

	/** The ea. */
	private EAnimation ea;
	
	
	/**
	 * Instantiates a new commands.
	 *
	 * @param x the x
	 * @param y the y
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
	public void update(int mx, int my){
		mx = (int) (mx - getX());
		my = (int) (my - getY());
		Color c = ea.getImage(0).getColor(mx, my);
		if (c.a != 0)
			execute();
	}
	
	/**
	 * Execute.
	 */
	public abstract void execute();
}
