package orders;

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

}
