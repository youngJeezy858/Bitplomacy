package orders;

import gameObjects.Territory;

public class BuildArmyOrder extends Order {

	public BuildArmyOrder(Territory t) {
		super(t);
		command = "build army";
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
