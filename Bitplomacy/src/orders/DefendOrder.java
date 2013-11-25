package orders;

import gameObjects.Territory;

public class DefendOrder extends Order {

	public DefendOrder(Territory t) {
		super(t);
		command = "defend";
	}

	@Override
	public boolean isValidOrder() {
		return true;
	}

	@Override
	public void addAdditionalTerritory(Territory t) {
		//do nothing
	}
	
	public String toString() {
		String s = currentTerritory.getName() + "\n";
		s += command;
		return s;
	}

}
