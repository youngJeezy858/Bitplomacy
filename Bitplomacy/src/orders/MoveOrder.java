package orders;

import java.util.ArrayList;

import gameObjects.Territory;
import gameObjects.Unit;

public class MoveOrder extends Order {

	private ArrayList<Territory> convoyPath;
	
	public MoveOrder(Territory t) {
		super(t);
		command = "move";
	}

	@Override
	public boolean isValidOrder() {
		
		if (destinationTerritory == null)
			return false;
		
		else if (unit.isArmy() && !destinationTerritory.isLand())
			return false;
	
		else if (!unit.isArmy() && destinationTerritory.isLand() && !destinationTerritory.hasCoast())
			return false;
		
		else if (!isAdjacent() && convoyPath.size() == 0)
			return false;
	
		else
			return true;
	}	

	@Override
	public void addAdditionalTerritory(Territory t) {
		convoyPath.add(t);
	}

	public int resolveMove() {
		
		if (state == CHECKING)
			return FOLLOWING;
		
		else if (!findConvoyPath(unit, destinationTerritory, convoyPath))
			return FAILED;
		
		else if (destinationTerritory.getUnit() != null){
			Order occupyingUnitOrder = destinationTerritory.getUnit().getOrder();
			
			if (occupyingUnitOrder.getState() == FAILED)
				return FAILED;
			
			else if (!occupyingUnitOrder.equals("attack")
					&& !occupyingUnitOrder.equals("move"))
				return FAILED;

			else if (occupyingUnitOrder.equals("attack")) {
				if (occupyingUnitOrder.getDestinationTerritory().equals(
						currentTerritory))
					return FAILED;

				else if (((AttackOrder)occupyingUnitOrder).resolveAttack() == CHECKED_WAITING)
					return FOLLOWING;
			} 
			
			else if (occupyingUnitOrder.equals("move")) {
				if (occupyingUnitOrder.getDestinationTerritory().equals(
						currentTerritory)) {
					if (occupyingUnitOrder.getUnit().getOwner() == unit.getOwner())
						return PASSED;
					else
						return FAILED;
				}
				state = CHECKING;
				int i = ((MoveOrder) occupyingUnitOrder).resolveMove();
				if (i == CHECKED_WAITING || i == FOLLOWING)
					return FOLLOWING;
			}
			return FAILED;
		}
		return CHECKED_WAITING;
	}

	private boolean findConvoyPath(Unit currUnit, Territory t,
			ArrayList<Territory> convoyPath) {
		if (convoyPath.size() == 0){
			if (currUnit.getTerritory().isAdjacent(t))
				return true;
			else
				return false;
		}
		
		Order temp = null;
		int i;
		
		for (i = 0; i < convoyPath.size(); i++){
			if (currUnit.getTerritory().isAdjacent(convoyPath.get(i))){
				temp = convoyPath.get(i).getUnit().getOrder();
				if (temp.getState() != FAILED && temp.equals("convoy"))
					temp.setState(PASSED);
				else 
					temp = null;
				break;
			}
		}
		
		if (temp == null)
			return false;
		else{
			convoyPath.remove(i);
			return findConvoyPath(temp.getUnit(), t, convoyPath);
		}
	}
	
	public void moveUnit() {
		destinationTerritory.setUnit(unit);
		if (!destinationTerritory.hasSC())
			destinationTerritory.setOwner(unit.getOwner());
		unit.setTerritory(destinationTerritory);
		state = Order.DONE;
	}

}