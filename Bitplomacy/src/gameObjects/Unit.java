package gameObjects;

import gui.Canvas;

import org.newdawn.slick.SpriteSheet;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

// TODO: Auto-generated Javadoc
/**
 * The Class Unit.
 */
public class Unit extends ImageEntity{

	/** The land. */
	private boolean land;
	
	/** The image. */
	private EAnimation image;
	
	/** The ss. */
	private SpriteSheet ss;
	
	/** The cur terr. */
	private Territory curTerr;
	
	/** The order. */
	private Order order;
	
	/** The owner. */
	private int owner;
	
	/**
	 * Instantiates a new unit.
	 *
	 * @param ss the ss
	 * @param owner the owner
	 * @param land the land
	 * @param t the t
	 */
	public Unit(SpriteSheet ss, int owner, boolean land, Territory t){
		super(ss);
		this.land = land;
		this.ss = ss;
		image = new EAnimation(ss.getSprite(owner, 0));
		curTerr = t;
		this.owner = owner + 1;
	}
	
	/* (non-Javadoc)
	 * @see com.erebos.engine.entity.ImageEntity#draw(float, float)
	 */
	public void draw(float x, float y){
		image.draw(x, y);
	}
	
	/**
	 * Checks if is land.
	 *
	 * @return true, if is land
	 */
	public boolean isLand(){
		return land;
	}
	
	/**
	 * Sets the owner.
	 *
	 * @param owner the new owner
	 */
	public void setOwner(int owner){
		image = new EAnimation(ss.getSprite(owner, 0));
	}
	
	/**
	 * Gets the territory.
	 *
	 * @return the territory
	 */
	public Territory getTerritory(){
		return curTerr;
	}
	
	/**
	 * Sets the territory.
	 *
	 * @param t the new territory
	 */
	public void setTerritory(Territory t){
		curTerr = t;
	}

	/**
	 * Execute order.
	 */
	public void executeOrder() {
		if (order != null)
			order.execute();
		else{
			Order o = new Order(curTerr);
			o.setTerr2(curTerr);
			order = o;
			Canvas.getC().addOrder(o);
		}
	}

	/**
	 * Sets the order.
	 *
	 * @param o the new order
	 */
	public void setOrder(Order o) {
		order = o;
	}

	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public int getOwner() {
		return owner;
	}

	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}
	
	/**
	 * Reset order.
	 */
	public void resetOrder(){
		order = null;
	}

	/**
	 * Retreat.
	 */
	public void retreat() {
		Territory t = curTerr.findVacant();
		if (t == null)
			curTerr.setUnit(null);
		else{
			curTerr = t;
			t.setUnit(this);
		}
		
	}
}
