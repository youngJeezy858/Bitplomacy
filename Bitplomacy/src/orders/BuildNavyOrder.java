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

	public String toString(){
		String s = currentTerritory.getName() + "\n";
		s += command + "\n";
		s += "for " + currentTerritory.getOwnerName();
		return s;
	}
	
}
