package orders;

import org.newdawn.slick.Graphics;

import gameObjects.Territory;

public class ConvoyOrder extends Order{

	private Territory convoyDestination;
	
	public ConvoyOrder(Territory t) {
		super(t);
		command = "convoy";
	}

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

	@Override
	public void addAdditionalTerritory(Territory t) {
		convoyDestination = t;
	}

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

	@Override
	public String toShortString() {
		String s = "Con ";
		if (destinationTerritory != null)
			s += destinationTerritory.getName().substring(0, 4);
		if (convoyDestination != null)
			s += " to " + convoyDestination.getName().substring(0, 4);
		return s;
	}

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
