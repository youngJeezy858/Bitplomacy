package orders;

import org.newdawn.slick.Graphics;

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

	@Override
	public String toShortString() {
		return "defends";
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawString(currentTerritory.getName() + " defending", x, y);
	}

}
