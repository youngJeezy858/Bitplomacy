package orders;

import org.newdawn.slick.Graphics;

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

	@Override
	public String toShortString() {
		return "idle";
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		// do nothing
		
	}

}
