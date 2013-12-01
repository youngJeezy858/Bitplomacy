package buttons;

import org.newdawn.slick.Color;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

/**
 * The Class Button.
 */
public abstract class Button extends ImageEntity {

	/** Draws the button to the screen. */
	private EAnimation ea;
	
	/**
	 * Instantiates a new button.
	 *
	 * @param x the x coordinate for the button's location on the screen
	 * @param y the y coordinate for the button's location on the screen
	 * @param path the file path of the Image 
	 */
	public Button(int x, int y, String path) {
		super(EAnimation.loadImage(path));
		ea = new EAnimation(EAnimation.loadImage(path));
		this.setX(x);
		this.setY(y);
	}
	
	/* (non-Javadoc)
	 * @see com.erebos.engine.entity.ImageEntity#draw()
	 */
	public void draw(){
		ea.draw(this.getX(), this.getY());
	}

	/**
	 * Checks if the Mouse cursor is over this button.
	 *
	 * @param mx the Mouse's x coordinate
	 * @param my the Mouse's y coordinate
	 * @return true, if is mouse over this button
	 */
	public boolean isMouseOver(int mx, int my){
		int x = (int) (mx - this.getX());
		int y = (int) (my - this.getY());
		if (mx >= this.getX() && mx <= this.getWidth() + this.getX()
				&& my >= this.getY() && my <= this.getHeight() + this.getY()){
			Color c = ea.getImage(0).getColor(x, y);
			if (c.a != 0)
				return true;
		}
		return false;
	}
	
	/**
	 * Performs the button's function. Usually this method is 
	 * used in combination with isMouseOver().
	 */
	public abstract void update();
}
