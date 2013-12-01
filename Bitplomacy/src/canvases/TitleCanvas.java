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

public class TitleCanvas extends ECanvas{

	public static final int NORM = 0;
	public static final int START_GAME = 1;
	private EAnimation titleScreen;
	private Button[] buttons;
	private static TitleCanvas tc = null;
	private int state;
	private String output;
	private TrueTypeFont mediumFont;
	

	private TitleCanvas() {
		super(0);
	}
	
	public static TitleCanvas getTC(){
		if (tc == null)
			tc = new TitleCanvas();
		return tc;
	}

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
	
	public void setState(int i){
		state = i;
	}

	public void output(String s) {
		output = s;
	}

}
