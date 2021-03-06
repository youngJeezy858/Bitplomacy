package orders;

import org.newdawn.slick.Graphics;

import gameObjects.Territory;

/**
 * The Class BuildNavyOrder.
 */
public class BuildNavyOrder extends Order {

	/**
	 * Instantiates a new builds the navy order.
	 *
	 * @param t the starting Territory
	 */
	public BuildNavyOrder(Territory t) {
		super(t);
		command = "build navy";
	}

	/* (non-Javadoc)
	 * @see orders.Order#isValidOrder()
	 */
	@Override
	public boolean isValidOrder() {
		return false;
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
	public String toString(){
		String s = currentTerritory.getName() + "\n";
		s += command + "\n";
		s += "for " + currentTerritory.getOwnerName();
		return s;
	}

	/* (non-Javadoc)
	 * @see orders.Order#toShortString()
	 */
	@Override
	public String toShortString() {
		return currentTerritory.getName().substring(0, 4) + "  owned by " + currentTerritory.getOwnerName().substring(0, 4) + " " + command;
	}
	
	/* (non-Javadoc)
	 * @see orders.Order#draw(org.newdawn.slick.Graphics, int, int)
	 */
	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawString(currentTerritory.getName() + " to", x, y);
		g.drawString("build navy", x, y+10);
		
	}
}
