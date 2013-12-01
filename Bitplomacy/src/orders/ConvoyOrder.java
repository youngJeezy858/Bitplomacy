package orders;

import org.newdawn.slick.Graphics;

import gameObjects.Territory;

/**
 * The Class ConvoyOrder.
 */
public class ConvoyOrder extends Order{

	/** The convoy destination. */
	private Territory convoyDestination;
	
	/**
	 * Instantiates a new convoy order.
	 *
	 * @param t the starting Territory
	 */
	public ConvoyOrder(Territory t) {
		super(t);
		command = "convoy";
	}

	/* (non-Javadoc)
	 * @see orders.Order#isValidOrder()
	 */
	@Override
	public boolean isValidOrder() {
		
		if (destinationTerritory == null || convoyDestination == null)
			return false;
		
		else if (!unit.isArmy() && currentTerritory.isLand())
			return false;
		
		else if (destinationTerritory.getUnit() == null)
			return false;
		
		else if (!convoyDestination.isLand() && convoyDestination.getUnit() == null)
			return false;
		
		else
			return destinationTerritory.isAdjacent(currentTerritory) 
					&& convoyDestination.isAdjacent(currentTerritory);
	}

	/* (non-Javadoc)
	 * @see orders.Order#addAdditionalTerritory(gameObjects.Territory)
	 */
	@Override
	public void addAdditionalTerritory(Territory t) {
		convoyDestination = t;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = currentTerritory.getName() + "\n";
		s += command + "\n";
		if (destinationTerritory != null)
			s += destinationTerritory.getName() + "\n";
		else{
			s += "[SELECT TERRITORY\n TO CONVOY]";
			return s;
		}
		if (convoyDestination != null) 
			s += "to " + convoyDestination.getName();
		else
			s += "[SELECT DESTINATION\n OF CONVOY]";
		return s;
	}

	/* (non-Javadoc)
	 * @see orders.Order#toShortString()
	 */
	@Override
	public String toShortString() {
		String s = "Con ";
		if (destinationTerritory != null)
			s += destinationTerritory.getName().substring(0, 4);
		if (convoyDestination != null)
			s += " to " + convoyDestination.getName().substring(0, 4);
		return s;
	}

	/* (non-Javadoc)
	 * @see orders.Order#draw(org.newdawn.slick.Graphics, int, int)
	 */
	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawString(currentTerritory.getName() + " convoying", x, y);
		y += 10;
		if (destinationTerritory != null)
			g.drawString(destinationTerritory.getName() + " to", x, y);
		else{
			g.drawString("[SELECT UNIT TO CONVOY]", x, y);
			return;
		}
		y += 10;
		if (convoyDestination != null) 
			g.drawString(convoyDestination.getName(), x, y);
		else{
			g.drawString("[SELECT DESTINATION", x, y);
			g.drawString(" OF CONVOY]", x, y+10);
		}
	}

	
	
}
