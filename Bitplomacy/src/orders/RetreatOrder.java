package orders;

import java.util.ArrayList;

import gameObjects.Territory;

public class RetreatOrder extends Order {

	public RetreatOrder(Territory t) {
		super(t);
		command = "retreat";
	}

	@Override
	public boolean isValidOrder() {
		return false;
	}

	@Override
	public void addAdditionalTerritory(Territory t) {
		//do nothing
	}

	public void resolveRetreat(ArrayList<Order> retreatingUnits) {
		int i;
		for (i = 0; i < retreatingUnits.size(); i++){
			if (currentTerritory.equals(retreatingUnits.get(i).getStartingTerritory())){
				if (unit.isArmy() && !destinationTerritory.isLand())
					state = FAILED;
				else if (!unit.isArmy() && destinationTerritory.isLand() && !destinationTerritory.hasCoast())
					state = FAILED;
				else if (destinationTerritory.getUnit() != null)
					state = FAILED;
				else if (isAdjacent())
					state = FAILED;
				else
					state = CHECKED_WAITING;
			}
		}
		if (state != CHECKED_WAITING && state != FAILED)
			state = DONE;
		else
			retreatingUnits.remove(i);
	}
	
	public String toString(){
		String s = currentTerritory.getName() + "\n";
		s += command + "\n";
		if (destinationTerritory != null)
			s += destinationTerritory.getName();
		else 
			s += "[SELECT DESTINATION\n OF RETREAT]";
		return s;
	}
		
}
