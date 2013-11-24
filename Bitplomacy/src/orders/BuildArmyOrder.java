package orders;

import canvases.GameCanvas;
import gameObjects.Player;
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

	public boolean resolveBuild(Player p){
		if (currentTerritory.getOwner() == p.getOwnerKey() && unit == null &&
				currentTerritory.isHomeCity(p.getOwnerKey())){
				GameCanvas.getC().createUnit(currentTerritory, true, p);
				return true;
		}
		return false;
	}
	
}
