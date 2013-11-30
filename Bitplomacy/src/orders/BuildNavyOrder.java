package orders;

import org.newdawn.slick.Graphics;

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

	@Override
	public String toShortString() {
		return currentTerritory.getName().substring(0, 4) + "  owned by " + currentTerritory.getOwnerName().substring(0, 4) + " " + command;
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawString(currentTerritory.getName() + " to", x, y);
		g.drawString("build navy", x, y+10);
		
	}
}
