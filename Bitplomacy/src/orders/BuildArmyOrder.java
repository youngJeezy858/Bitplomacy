package orders;

import org.newdawn.slick.Graphics;

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
	
	public String toString(){
		String s = currentTerritory.getName() + "\n";
		s += command + "\n";
		s += "for " + currentTerritory.getOwnerName();
		return s;
	}

	@Override
	public String toShortString() {
		return currentTerritory.getName().substring(0, 4) + "  owned by " + currentTerritory.getOwnerName().substring(0, 4) + " " + command;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawString(currentTerritory.getName() + " to", x, y);
		g.drawString("build army", x, y+10);	
	}
	
}
