package orders;

import org.newdawn.slick.Graphics;

import gameObjects.Territory;

/**
 * The Class BlankOrder.
 */
public class BlankOrder extends Order{

	/**
	 * Instantiates a new blank order.
	 *
	 * @param t the starting Territory
	 */
	public BlankOrder(Territory t) {
		super(t);
		command = "blank";
	}

	/* (non-Javadoc)
	 * @see orders.Order#isValidOrder()
	 */
	@Override
	public boolean isValidOrder() {
		return true;
	}

	/* (non-Javadoc)
	 * @see orders.Order#addAdditionalTerritory(gameObjects.Territory)
	 */
	@Override
	public void addAdditionalTerritory(Territory t) {
		//do nothing
	}

	/* (non-Javadoc)
	 * @see orders.Order#toShortString()
	 */
	@Override
	public String toShortString() {
		return "idle";
	}

	/* (non-Javadoc)
	 * @see orders.Order#draw(org.newdawn.slick.Graphics, int, int)
	 */
	@Override
	public void draw(Graphics g, int x, int y) {
		// do nothing
		
	}

}
