package orders;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import canvases.GameCanvas;
import gameObjects.Territory;

/**
 * The Class DisbandOrder.
 */
public class DisbandOrder extends Order {

	/**
	 * Instantiates a new disband order.
	 *
	 * @param t the starting Territory
	 */
	public DisbandOrder(Territory t) {
		super(t);
		command = "disband";
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

	/**
	 * Checks if this Unit needs to be disbanded and disbands it if it does.
	 *
	 * @param retreatingUnits the retreating units
	 */
	public void resolveDisband(ArrayList<Order> retreatingUnits) {
		int i;
		for (i = 0; i < retreatingUnits.size(); i++){
			if (currentTerritory.equals(retreatingUnits.get(i).getStartingTerritory())){
				GameCanvas.getC().removeUnit(unit);
				break;
			}
		}
		if (i != retreatingUnits.size())
			retreatingUnits.remove(i);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return currentTerritory.getName() + " owned by " + currentTerritory.getOwnerName() + " " + command;
	}

	/* (non-Javadoc)
	 * @see orders.Order#toShortString()
	 */
	@Override
	public String toShortString() {
		return " to disband";
	}

	/* (non-Javadoc)
	 * @see orders.Order#draw(org.newdawn.slick.Graphics, int, int)
	 */
	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawString(currentTerritory.getName() + " disbanding", x, y);
	}

}
