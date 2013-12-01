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

public class WinCanvas extends ECanvas{

	private static final int NORM = 0;
	public static final int RETURN_TO_START = 2;
	private static WinCanvas wc;
	private EAnimation background;
	private ReturnToTitleButton exitButton;
	private int state;
	private String winner;
	private TrueTypeFont bigFont;
	
	private WinCanvas(){
		super(2);
	}

	public static WinCanvas getWC(){
		if (wc == null)
			wc = new WinCanvas();
		return wc;
	}
	
	@Override
	public void eInit(GameContainer gc, EGame eg) {
		background = new EAnimation(EAnimation.loadImage("/images/WinCanvasBackground.png"));
		exitButton = new ReturnToTitleButton(5, 5, "/images/Button_ExitGame.png");
		state = NORM;
		winner = "hai muthfucka";
		bigFont = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 40), true);
	}

	@Override
	public void eRender(GameContainer gc, EGame eg, Graphics g) {
		background.draw();
		exitButton.draw();
		g.setFont(bigFont);
		g.setColor(Color.black);
		g.drawString(winner, 400, 650);
	}

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

	public void setState(int i) {
		state = i;
	}

	public void setWinner(String s) {
		winner = s;
	}
}
