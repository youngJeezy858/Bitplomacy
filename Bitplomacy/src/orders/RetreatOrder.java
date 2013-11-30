package orders;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

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

	public void resolveRetreat(ArrayList<Order> retreatingUnits) {
		int i;
		for (i = 0; i < retreatingUnits.size(); i++){
			if (currentTerritory.equals(retreatingUnits.get(i).getStartingTerritory())){
				if (unit.isArmy() && !destinationTerritory.isLand()){
					state = FAILED;
					break;
				}
				else if (!unit.isArmy() && destinationTerritory.isLand() && !destinationTerritory.hasCoast()){
					state = FAILED;
					break;
				}
				else if (destinationTerritory.getUnit() != null){
					state = FAILED;
					break;
				}
				else if (currentTerritory.isInSC() && !currentTerritory.isAdjacentSC(destinationTerritory)){
					state = FAILED;
					break;
				}
				else if (currentTerritory.isInNC() && !currentTerritory.isAdjacentNC(destinationTerritory)){
					state = FAILED;
					break;
				}
				else if (!isAdjacent()){
					state = FAILED;
					break;
				}
				else{
					state = CHECKED_WAITING;
					break;
				}
			}
		}
		if (state != CHECKED_WAITING && state != FAILED)
			state = DONE;
		else
			retreatingUnits.remove(i);
	}
	
	public String toString(){
		String s = currentTerritory.getName() + "\n";
		s += command + "\n";
		if (destinationTerritory != null)
			s += destinationTerritory.getName();
		else 
			s += "[SELECT DESTINATION\n OF RETREAT]";
		return s;
	}

	@Override
	public String toShortString() {
		String s = " Ret ";
		if (destinationTerritory != null)
			s += destinationTerritory.getName().substring(0, 4);
		return s;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawString(currentTerritory.getName() + " retreating", x, y);
		y += 10;
		if (destinationTerritory != null)
			g.drawString("to " + destinationTerritory.getName(), x, y);
		else
			g.drawString("[SELECT DESTINATION OF RETREAT]", x, y);
	}
		
}
