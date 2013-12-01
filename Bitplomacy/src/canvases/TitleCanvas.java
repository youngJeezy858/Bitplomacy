package canvases;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import buttons.Button;
import buttons.LoadAutosaveButton;
import buttons.LoadGameButton;
import buttons.NewGameButton;

import com.erebos.engine.core.ECanvas;
import com.erebos.engine.core.EGame;
import com.erebos.engine.graphics.EAnimation;

public class TitleCanvas extends ECanvas{

	public static final int NORM = 0;
	public static final int START_NEWGAME = 1;
	private EAnimation titleScreen;
	private Button[] buttons;
	private static TitleCanvas tc = null;
	private int state;
	

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
	}

	@Override
	public void eRender(GameContainer arg0, EGame arg1, Graphics arg2) {
		titleScreen.draw(0, 0);
		for (Button b : buttons)
			b.draw();
	}

	@Override
	public void eUpdate(GameContainer gc, EGame eg, int arg2) {
		if (state == START_NEWGAME)
			eg.enterState(1);
		
		for (Button b : buttons){
			if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
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

}
