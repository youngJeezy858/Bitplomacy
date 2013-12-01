package canvases;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

import buttons.Button;
import buttons.LoadAutosaveButton;
import buttons.LoadGameButton;
import buttons.NewGameButton;

import com.erebos.engine.core.ECanvas;
import com.erebos.engine.core.EGame;
import com.erebos.engine.graphics.EAnimation;

/**
 * Used for display and updating the Title screen.
 */
public class TitleCanvas extends ECanvas{

	/** The normal state of this Canvas. */
	public static final int NORM = 0;
	
	/** The state to let the game know it needs to start. */
	public static final int START_GAME = 1;
	
	/** used for drawing the title screen. */
	private EAnimation titleScreen;
	
	/** The buttons for the title screen. */
	private Button[] buttons;
	
	/** Singleton variable to get this TitleCanvas. */
	private static TitleCanvas tc = null;
	
	/** The current state of this Canvas. */
	private int state;
	
	/** The output for displaying error messages. */
	private String output;
	
	/** The medium font. */
	private TrueTypeFont mediumFont;
	

	/**
	 * Instantiates a new title canvas.
	 */
	private TitleCanvas() {
		super(0);
	}
	
	/**
	 * Singleton method for getting this TitleCanvas.
	 *
	 * @return this TitleCanvas
	 */
	public static TitleCanvas getTC(){
		if (tc == null)
			tc = new TitleCanvas();
		return tc;
	}

	/* (non-Javadoc)
	 * @see com.erebos.engine.core.ECanvas#eInit(org.newdawn.slick.GameContainer, com.erebos.engine.core.EGame)
	 */
	@Override
	public void eInit(GameContainer arg0, EGame arg1) {
		titleScreen = new EAnimation(EAnimation.loadImage("/images/TitleScreen.png"));
		buttons = new Button[3];
		buttons[0] = new NewGameButton(135, 500, "/images/Button_NewGame.png");
		buttons[1] = new LoadGameButton(435, 500, "/images/Button_LoadGame.png");
		buttons[2] = new LoadAutosaveButton(735, 500, "/images/Button_LoadAutosave.png");
		state = NORM;
	    mediumFont = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 20), true);
	}

	/* (non-Javadoc)
	 * @see com.erebos.engine.core.ECanvas#eRender(org.newdawn.slick.GameContainer, com.erebos.engine.core.EGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void eRender(GameContainer gc, EGame eg, Graphics g) {
		titleScreen.draw(0, 0);
		for (Button b : buttons)
			b.draw();
		if (output != null){
			g.setColor(Color.black);
			g.setFont(mediumFont);
			g.drawString(output, 735, 400);
		}
	}

	/* (non-Javadoc)
	 * @see com.erebos.engine.core.ECanvas#eUpdate(org.newdawn.slick.GameContainer, com.erebos.engine.core.EGame, int)
	 */
	@Override
	public void eUpdate(GameContainer gc, EGame eg, int arg2) {
		if (state == START_GAME){
			state = NORM;
			eg.enterState(1);
		}
		
		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			for (Button b : buttons) {
				int mx = Mouse.getX();
				int my = Math.abs(Mouse.getY() - gc.getHeight());
				if (b.isMouseOver(mx, my))
					b.update();
			}
		}
	}
	
	/**
	 * Sets the current state of this Canvas.
	 *
	 * @param i the new state
	 */
	public void setState(int i){
		state = i;
	}

	/**
	 * Sets the output String.
	 *
	 * @param s the new output
	 */
	public void output(String s) {
		output = s;
	}

}
