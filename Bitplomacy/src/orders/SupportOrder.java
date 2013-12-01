package orders;

import org.newdawn.slick.Graphics;

import gameObjects.Territory;

/**
 * The Class SupportOrder.
 */
public class SupportOrder extends Order {

	/** The Territory containing the Unit being supported. */
	private Territory supported;
	
	/**
	 * Instantiates a new support order.
	 *
	 * @param t the starting Territory
	 */
	public SupportOrder(Territory t) {
		super(t);
		command = "support";
	}

	/* (non-Javadoc)
	 * @see orders.Order#isValidOrder()
	 */
	@Override
	public boolean isValidOrder() {
		
		if (supported == null || destinationTerritory == null)
			return false;
		
		Order supportedOrder = supported.getUnit().getOrder();
		
		if (!unit.isArmy() && destinationTerritory.isLand() && !destinationTerritory.hasCoast())
			return false;
		
		else if (unit.isArmy() && !destinationTerritory.isLand())
			return false;
		
		else if (supportedOrder.equals("attack") && destinationTerritory.getUnit() != null &&
					destinationTerritory.getUnit().getOwner() == unit.getOwner())
			return false;
		
		else if (supportedOrder.equals("attack") && supportedOrder.equals("move") 
					&& supportedOrder.equals("defend"))
			return false;
				
		else
			return isAdjacent();
	}	

	/* (non-Javadoc)
	 * @see orders.Order#addAdditionalTerritory(gameObjects.Territory)
	 */
	@Override
	public void addAdditionalTerritory(Territory t) {
		supported = t;
	}

	/**
	 * Increments the supported Unit's strength.
	 */
	public void support() {
		supported.getUnit().getOrder().incrementStrength();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String s = currentTerritory.getName() + "\n";
		s += command + "\n";
		if (supported != null && supported.equals(destinationTerritory))
			s += "unit defending " + supported.getName();
		else if (supported != null){
			s += "unit at " + supported.getName() + "\n";
			s += "to " + destinationTerritory.getName();
		}
		else if (destinationTerritory != null){
			s += "to " + destinationTerritory.getName() + "\n";
			s += "[SELECT UNIT\n TO SUPPORT]";
		}
		else
			s += "[SELECT DESTINATION\n OF SUPPORT]";
		return s;
	}

	/* (non-Javadoc)
	 * @see orders.Order#toShortString()
	 */
	@Override
	public String toShortString() {
		String s = " Sup ";
		if (destinationTerritory != null)
			s += destinationTerritory.getName().substring(0, 4);
		if (supported != null){
			if (supported.getUnit() != null){
				if (supported.getUnit().isArmy())
					s += " A";
				else
					s += " N";
			}
			s += " in "+ supported.getName().substring(0, 4);
		}
		return s;
	}

	
	/* (non-Javadoc)
	 * @see orders.Order#draw(org.newdawn.slick.Graphics, int, int)
	 */
	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawString(currentTerritory.getName() + " supporting", x, y);
		y += 10;
		if (supported != null) 
			g.drawString(supported.getName() + " to", x, y);
		else if (destinationTerritory != null){
			g.drawString("[SELECT UNIT", x, y);	
			y += 10;
			g.drawString(" TO SUPPORT]", x, y);	
		}
		y += 10;
		if (destinationTerritory != null)
			g.drawString(destinationTerritory.getName(), x, y);
		else{
			g.drawString("[SELECT DESTINATION", x, y);
			y += 10;
			g.drawString(" OF SUPPORT]", x, y);
		}
	}
}
