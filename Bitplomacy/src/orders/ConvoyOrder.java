package orders;

import gameObjects.Territory;

public class ConvoyOrder extends Order{

	Territory convoyDestination;
	
	public ConvoyOrder(Territory t) {
		super(t);
		command = "convoy";
	}

	@Override
	public boolean isValidOrder() {
		
		if (destinationTerritory == null || convoyDestination == null)
			return false;
		
		else if (!unit.isArmy() || currentTerritory.isLand())
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

	
	
}
