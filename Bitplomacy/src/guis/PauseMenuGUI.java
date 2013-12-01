package guis;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import buttons.Button;
import buttons.ExitGameButton;
import buttons.ResumeButton;
import buttons.SaveAndExitGameButton;
import buttons.SaveGameButton;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

/**
 * GUI for when the game is paused.
 */
public class PauseMenuGUI extends ImageEntity{
	
	/** The buttons. */
	private Button[] buttons;
	
	/** To draw the background of this GUI. */
	private EAnimation ea;
	
	/**
	 * Instantiates a new pause menu gui.
	 *
	 * @param gc the gc
	 * @param image the background image
	 */
	public PauseMenuGUI(GameContainer gc, Image image){
		super(image);
		ea = new EAnimation(image);
		buttons = new Button[4];
		this.setX(gc.getWidth()/2 - image.getWidth()/2);
		this.setY(gc.getHeight()/2 - image.getHeight()/2);
		buttons[0] = new SaveGameButton((int)this.getX() + 50, (int)this.getY() + 60, "/images/Button_SaveGame.png");
		buttons[1] = new ExitGameButton((int)this.getX() + 50, (int)this.getY() + 180, "/images/Button_ExitGame.png");
		buttons[2] = new SaveAndExitGameButton((int)this.getX() + 50, (int)this.getY() + 300, "/images/Button_SaveAndExitGame.png");
		buttons[3] = new ResumeButton((int)this.getX() + 250, (int)this.getY() + 10, "/images/Button_CloseWindow.png");
	}

	/* (non-Javadoc)
	 * @see com.erebos.engine.entity.ImageEntity#draw()
	 */
	public void draw(){
		ea.draw(this.getX(), this.getY());
		for (Button b : buttons)
			b.draw();
	}
	
	/**
	 * Checks if a button was selected and updates appropriately.
	 *
	 * @param mx the x coordinate of the Mouse cursor
	 * @param my the y coordinate of the Mouse cursor
	 */
	public void update(int mx, int my){
		for (Button b : buttons){
			if (b.isMouseOver(mx, my))
				b.update();
		}
	}
	
}
