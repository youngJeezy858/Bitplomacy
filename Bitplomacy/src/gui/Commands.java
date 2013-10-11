package gui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

public abstract class Commands extends ImageEntity{

	private EAnimation ea;
	private int colorKey;
	
	public Commands(float x, float y, int color) {
		setX(x);
		setY(y);
		colorKey = color;
	}
	
	public void draw(){
		ea.draw(this.getX(), this.getY());
	}
	
	public void setEA(EAnimation ea){
		setAnimation(ea);
		this.ea = ea;
	}
	
	public void update(){
		Color c = Canvas.getC().getCurrentColor();
		if (c.getRed()==0 && c.getBlue()==colorKey && c.getGreen()==0 && Mouse.isButtonDown(0))
			execute();
	}
	
	public abstract void execute();
}
