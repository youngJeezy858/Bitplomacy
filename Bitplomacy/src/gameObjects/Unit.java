package gameObjects;

import org.newdawn.slick.SpriteSheet;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

public class Unit extends ImageEntity{

	private boolean land;
	private EAnimation image;
	private SpriteSheet ss;
	private Territory curTerr;
	
	public Unit(SpriteSheet ss, int owner, boolean land, Territory t){
		super(ss);
		this.land = land;
		this.ss = ss;
		image = new EAnimation(ss.getSprite(owner, 0));
		curTerr = t;
	}
	
	public void draw(float x, float y){
		image.draw(x, y);
	}
	
	public boolean isLand(){
		return land;
	}
	
	public void setOwner(int owner){
		image = new EAnimation(ss.getSprite(owner, 0));
	}
	
	public Territory getT(){
		return curTerr;
	}
	
}
