package guis;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import canvases.GameCanvas;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

/**
 * GUI for the 'are you sure?' window when you adjudicate without giving
 * all Units Orders.
 */
public class PauseMenuAdjudicateGUI extends ImageEntity {

	/** The yes button. */
	private ImageEntity yesButton;
	
	/** The no button. */
	private ImageEntity noButton;
	
	/** to draw the background of this GUI. */
	private EAnimation background;
	
	/** To draw the yes button. */
	private EAnimation yes;
	
	/** To draw the no button. */
	private EAnimation no;

	
	/**
	 * Instantiates a new pause menu adjudicate gui.
	 *
	 * @param gc the gc
	 */
	public PauseMenuAdjudicateGUI(GameContainer gc){
		super(EAnimation.loadImage("/images/Paused_Adjudicate.png"));
		yesButton = new ImageEntity(EAnimation.loadImage("/images/YesButton.png"));
		yes = new EAnimation(EAnimation.loadImage("/images/YesButton.png"));
		noButton = new ImageEntity(EAnimation.loadImage("/images/NoButton.png"));
		no = new EAnimation(EAnimation.loadImage("/images/NoButton.png"));
		background = new EAnimation(EAnimation.loadImage("/images/Paused_Adjudicate.png"));
		this.setX(gc.getWidth()/2 - this.getWidth()/2);
		this.setY(gc.getHeight()/2 - this.getHeight()/2);
		yesButton.setX(gc.getWidth()/2 - this.getWidth()/2 + 40);
		yesButton.setY(gc.getHeight()/2 + this.getHeight()/16);
		noButton.setX(gc.getWidth()/2 + this.getWidth()/2 - yes.getWidth() - 40);
		noButton.setY(gc.getHeight()/2 + this.getHeight()/16);
	}

	/* (non-Javadoc)
	 * @see com.erebos.engine.entity.ImageEntity#draw()
	 */
	public void draw(){
		background.draw(this.getX(), this.getY());
		yes.draw(yesButton.getX(), yesButton.getY());
		no.draw(noButton.getX(), noButton.getY());
	}
	
	/**
	 * Checks if yes or no was selected and updates appropriately.
	 *
	 * @param mx the x coordinate of the Mouse cursor
	 * @param my the y coordinate of the Mouse cursor
	 */
	public void update(int mx, int my) {
		int x = (int) (mx - yesButton.getX());
		int y = (int) (my - yesButton.getY());
		if (mx >= yesButton.getX() && mx <= yesButton.getWidth() + yesButton.getX()
				&& my >= yesButton.getY() && my <= yesButton.getHeight() + yesButton.getY()){
			Color c = yes.getImage(0).getColor(x, y);
			if (c.a != 0){
				GameCanvas.getC().adjudicate();
				return;
			}
		}
		x = (int) (mx - noButton.getX());
		y = (int) (my - noButton.getY());
		if (mx >= noButton.getX() && mx <= noButton.getWidth() + noButton.getX()
				&& my >= noButton.getY() && my <= noButton.getHeight() + noButton.getY()){
			Color c = no.getImage(0).getColor(x, y);
			if (c.a != 0){
				GameCanvas.getC().setState(GameCanvas.NORM);
				return;
			}
		}
	}
	
	
}
