package orders;

import org.newdawn.slick.Graphics;

import gameObjects.Territory;

/**
 * The Class DefendOrder.
 */
public class DefendOrder extends Order {

	/**
	 * Instantiates a new defend order.
	 *
	 * @param t the starting Territory
	 */
	public DefendOrder(Territory t) {
		super(t);
		command = "defend";
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
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = currentTerritory.getName() + "\n";
		s += command;
		return s;
	}

	/* (non-Javadoc)
	 * @see orders.Order#toShortString()
	 */
	@Override
	public String toShortString() {
		return "defends";
	}

	/* (non-Javadoc)
	 * @see orders.Order#draw(org.newdawn.slick.Graphics, int, int)
	 */
	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawString(currentTerritory.getName() + " defending", x, y);
	}

}
