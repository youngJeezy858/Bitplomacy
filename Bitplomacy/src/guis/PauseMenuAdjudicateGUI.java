package guis;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import canvases.GameCanvas;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

public class PauseMenuAdjudicateGUI extends ImageEntity {

	private ImageEntity yesButton;
	private ImageEntity noButton;
	private EAnimation background;
	private EAnimation yes;
	private EAnimation no;

	
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

	public void draw(){
		background.draw(this.getX(), this.getY());
		yes.draw(yesButton.getX(), yesButton.getY());
		no.draw(noButton.getX(), noButton.getY());
	}
	
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
