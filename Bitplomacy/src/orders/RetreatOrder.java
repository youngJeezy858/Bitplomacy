package orders;

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

}
