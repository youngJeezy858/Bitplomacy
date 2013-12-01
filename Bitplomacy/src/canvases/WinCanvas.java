package canvases;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

import buttons.ReturnToTitleButton;

import com.erebos.engine.core.ECanvas;
import com.erebos.engine.core.EGame;
import com.erebos.engine.graphics.EAnimation;

/**
 * Used for displaying and updating the screen when a Player wins the game.
 */
public class WinCanvas extends ECanvas{

	/** The normal state of this Canvas. */
	private static final int NORM = 0;
	
	/** The state for returning to the TitleCanvas. */
	public static final int RETURN_TO_START = 1;
	
	/** Singleton variable for getting this Canvas. */
	private static WinCanvas wc;
	
	/** Used for drawing the background. */
	private EAnimation background;
	
	/** The button to return to the Title Screen. */
	private ReturnToTitleButton exitButton;
	
	/** The current state of the game. */
	private int state;
	
	/** The name(s) of the winner(s). */
	private String winner;
	
	/** The big font. */
	private TrueTypeFont bigFont;
	
	/**
	 * Instantiates a new win canvas.
	 */
	private WinCanvas(){
		super(2);
	}

	/**
	 * Singleton method for getting this Canvas.
	 *
	 * @return this Canvas
	 */
	public static WinCanvas getWC(){
		if (wc == null)
			wc = new WinCanvas();
		return wc;
	}
	
	/* (non-Javadoc)
	 * @see com.erebos.engine.core.ECanvas#eInit(org.newdawn.slick.GameContainer, com.erebos.engine.core.EGame)
	 */
	@Override
	public void eInit(GameContainer gc, EGame eg) {
		background = new EAnimation(EAnimation.loadImage("/images/WinCanvasBackground.png"));
		exitButton = new ReturnToTitleButton(5, 5, "/images/Button_ExitGame.png");
		state = NORM;
		winner = "hai muthfucka";
		bigFont = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 40), true);
	}

	/* (non-Javadoc)
	 * @see com.erebos.engine.core.ECanvas#eRender(org.newdawn.slick.GameContainer, com.erebos.engine.core.EGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void eRender(GameContainer gc, EGame eg, Graphics g) {
		background.draw();
		exitButton.draw();
		g.setFont(bigFont);
		g.setColor(Color.black);
		g.drawString(winner, 400, 650);
	}

	/* (non-Javadoc)
	 * @see com.erebos.engine.core.ECanvas#eUpdate(org.newdawn.slick.GameContainer, com.erebos.engine.core.EGame, int)
	 */
	@Override
	public void eUpdate(GameContainer gc, EGame eg, int arg2) {
		if (state == RETURN_TO_START){
			state = NORM;
			eg.enterState(0);
		}	
		int mx = Mouse.getX();
		int my = Math.abs(Mouse.getY() - gc.getHeight());
		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON) && exitButton.isMouseOver(mx, my))
			exitButton.update();
	}

	/**
	 * Sets the current state of this game.
	 *
	 * @param i the new state
	 */
	public void setState(int i) {
		state = i;
	}

	/**
	 * Sets the winner(s) of the game.
	 *
	 * @param s the new winner(s)
	 */
	public void setWinner(String s) {
		winner = s;
	}
}
