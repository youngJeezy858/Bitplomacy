package buttons;

import org.newdawn.slick.Color;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

public abstract class Button extends ImageEntity {

	private EAnimation ea;
	
	public Button(int x, int y, String s) {
		super(EAnimation.loadImage(s));
		ea = new EAnimation(EAnimation.loadImage(s));
		this.setX(x);
		this.setY(y);
	}
	
	public void draw(){
		ea.draw(this.getX(), this.getY());
	}

	public boolean isMouseOver(int mx, int my){
		int x = (int) (mx - this.getX());
		int y = (int) (my - this.getY());
		if (mx >= this.getX() && mx <= this.getWidth() + this.getX()
				&& my >= this.getY() && my <= this.getHeight() + this.getY()){
			Color c = ea.getImage(0).getColor(x, y);
			if (c.a != 0)
				return true;
		}
		return false;
	}
	
	public abstract void update();
}
