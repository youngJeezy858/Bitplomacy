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

}
