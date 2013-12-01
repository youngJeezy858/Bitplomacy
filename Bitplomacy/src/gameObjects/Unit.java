package gameObjects;


import orders.BlankOrder;
import orders.Order;

import org.newdawn.slick.SpriteSheet;

import canvases.GameCanvas;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

/**
 * Represents either an army or fleet.
 */
public class Unit extends ImageEntity{

	/** Is this Unit an army? */
	private boolean isArmy;
	
	/** What actually draws the Unit to the screen. */
	private EAnimation image;
	
	/** The current Territory of the Unit. */
	private Territory territory;
	
	/** The current Order of the Unit. */
	private Order order;
	
	/** The owner of the Unit. */
	private int owner;
	
	/**
	 * Instantiates a new unit.
	 *
	 * @param ss the SpriteSheet for grabbing an image of the Unit
	 * @param owner the owner
	 * @param isArmy true, if the Unit is an army
	 * @param t the current location of this Unit
	 */
	public Unit(SpriteSheet ss, int owner, boolean isArmy, Territory t){
		super(ss);
		this.isArmy = isArmy;
		image = new EAnimation(ss.getSprite(owner, 0));
		territory = t;
		this.owner = owner + 1;
	}
	
	/* (non-Javadoc)
	 * @see com.erebos.engine.entity.ImageEntity#draw(float, float)
	 */
	public void draw(float x, float y){
		image.draw(x, y);
	}
	
	/**
	 * Checks if Unit is an army.
	 *
	 * @return true, if army, false if navy
	 */
	public boolean isArmy(){
		return isArmy;
	}
	
	/**
	 * Gets the current Territory of this Unit.
	 *
	 * @return the territory
	 */
	public Territory getTerritory(){
		return territory;
	}
	
	/**
	 * Sets the current Territory.
	 *
	 * @param t the new territory
	 */
	public void setTerritory(Territory t){
		territory = t;
	}

	/**
	 * Execute the current Order for this Unit. Will submit a blank Order
	 * if none was given.
	 */
	public void executeOrder() {
		if (order != null)
			order.execute();
		else {
			BlankOrder o = new BlankOrder(territory);
			o.setDestinationTerritory(territory);
			order = o;
			GameCanvas.getC().addOrder(o);
		}
	}

	/**
	 * Sets the current Order.
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
	 * Gets the current Order.
	 *
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String s = "";
		if (isArmy)
			s += "A in ";
		else
			s += "F in ";
		s += territory.getName().substring(0, 4);
		if (order == null)
			s += "    --    --";
		else {
			s += " " + order.toShortString();
		}
		return s;
	}
	
}
