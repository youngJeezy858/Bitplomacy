package buttons;

import org.newdawn.slick.Image;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

public class AllyChoice extends ImageEntity{

	private Image flag;
	private EAnimation ea;
	
	public AllyChoice(Image i){
		super(i);
		flag = i;
		ea = new EAnimation(flag);
	}
	
	public boolean isMouseOver(int mx, int my){
		mx = (int) (mx - this.getX());
		my = (int) (my - this.getY());
		if (flag.getColor(mx, my).a == 0)
			return false;
		else
			return true;
	}
	
	public void draw(){
		ea.draw(this.getX(), this.getY());
	}
}
