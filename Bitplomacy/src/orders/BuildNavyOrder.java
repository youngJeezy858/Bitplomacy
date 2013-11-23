package orders;

import gameObjects.Territory;

public class BuildNavyOrder extends Order {

	public BuildNavyOrder(Territory t) {
		super(t);
		command = "build navy";
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
