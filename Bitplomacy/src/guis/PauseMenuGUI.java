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

public class PauseMenuGUI extends ImageEntity{
	
	private Button[] buttons;
	private EAnimation ea;
	
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

	public void draw(){
		ea.draw(this.getX(), this.getY());
		for (Button b : buttons)
			b.draw();
	}
	
	public void update(int mx, int my){
		for (Button b : buttons){
			if (b.isMouseOver(mx, my))
				b.update();
		}
	}
	
}
