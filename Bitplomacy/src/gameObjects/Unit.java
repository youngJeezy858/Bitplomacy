package gameObjects;

import org.newdawn.slick.SpriteSheet;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

public class Unit extends ImageEntity{

	private boolean land;
	private EAnimation image;
	private SpriteSheet ss;
	private Territory curTerr;
	private Order order;
	private int owner;
	
	public Unit(SpriteSheet ss, int owner, boolean land, Territory t){
		super(ss);
		this.land = land;
		this.ss = ss;
		image = new EAnimation(ss.getSprite(owner, 0));
		curTerr = t;
		this.owner = owner + 1;
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
	
	public Territory getTerritory(){
		return curTerr;
	}
	
	public void setTerritory(Territory t){
		curTerr = t;
	}

	public void executeOrder() {
		if (order != null){
			System.out.println("hiya");
			order.execute();
		}
		else
			System.out.println(curTerr.getName());
		order = null;
	}

	public void setOrder(Order o) {
		order = o;
	}

	public int getOwner() {
		return owner;
	}
}
