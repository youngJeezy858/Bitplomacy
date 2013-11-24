package orders;

import java.util.ArrayList;

import canvases.GameCanvas;
import gameObjects.Territory;

public class DisbandOrder extends Order {

	public DisbandOrder(Territory t) {
		super(t);
		command = "disband";
	}

	@Override
	public boolean isValidOrder() {
		return false;
	}

	@Override
	public void addAdditionalTerritory(Territory t) {
		//do nothing
	}

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

}
