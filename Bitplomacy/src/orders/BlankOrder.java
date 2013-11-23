package orders;

import gameObjects.Territory;

public class BlankOrder extends Order{

	public BlankOrder(Territory t) {
		super(t);
		command = "blank";
	}

	@Override
	public boolean isValidOrder() {
		return true;
	}

	@Override
	public void addAdditionalTerritory(Territory t) {
		//do nothing
	}

}
