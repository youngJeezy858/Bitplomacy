package buttons;

import org.newdawn.slick.Image;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

/**
 * Used for choosing a specific ally after winter adjudication for a combo win.  
 */
public class AllyChoice extends ImageEntity{

	/** The image of the team. */
	private Image flag;
	
	/** To draw the image to the screen. */
	private EAnimation ea;
	
	/**
	 * Instantiates a new ally choice.
	 *
	 * @param i the Image to use for this team
	 */
	public AllyChoice(Image i){
		super(i);
		flag = i;
		ea = new EAnimation(flag);
	}
	
	/**
	 * Checks if the mouse cursor is over this choice.
	 *
	 * @param mx the Mouse's x coordinate
	 * @param my the Mouse's y coordinate
	 * @return true, if is mouse over this choice
	 */
	public boolean isMouseOver(int mx, int my){
		mx = (int) (mx - this.getX());
		my = (int) (my - this.getY());
		if (flag.getColor(mx, my).a == 0)
			return false;
		else
			return true;
	}
	
	/* (non-Javadoc)
	 * @see com.erebos.engine.entity.ImageEntity#draw()
	 */
	public void draw(){
		ea.draw(this.getX(), this.getY());
	}
}
