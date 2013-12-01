package orders;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import phases.PlanningPhase;
import canvases.GameCanvas;
import gameObjects.Territory;
import gameObjects.Unit;

/**
 * The Class AttackOrder.
 */
public class AttackOrder extends Order {

	/** The convoy path. */
	private ArrayList<Territory> convoyPath;
	
	/**
	 * Instantiates a new attack order.
	 *
	 * @param t the starting Territory
	 */
	public AttackOrder(Territory t) {
		super(t);
		command = "attack";
		convoyPath = new ArrayList<Territory>();
	}

	/* (non-Javadoc)
	 * @see orders.Order#isValidOrder()
	 */
	@Override
	public boolean isValidOrder() {
		
		if (destinationTerritory == null)
			return false;
		
		else if (unit.isArmy() && !destinationTerritory.isLand())
			return false;
		
		else if (!unit.isArmy() && destinationTerritory.isLand() && !destinationTerritory.hasCoast())
			return false;
		
		else if (!isAdjacent() && convoyPath.size() == 0)
			return false;
		
		else
			return true;
	}

	/* (non-Javadoc)
	 * @see orders.Order#addAdditionalTerritory(gameObjects.Territory)
	 */
	@Override
	public void addAdditionalTerritory(Territory t) {
		convoyPath.add(t);
	}
	
	/**
	 * Gets the convoy path.
	 *
	 * @return the convoy path
	 */
	public ArrayList<Territory> getConvoyPath(){
		return convoyPath;
	}

	
	/**
	 * Resolves this Order if it is a Fleet attacking another Fleet.
	 *
	 * @param attackedUnit the Unit being attacked
	 */
	public void resolveWaterAttack(Unit attackedUnit) {  
		
		if (attackedUnit != null && !destinationTerritory.isLand()
				&& attackedUnit.getOrder().equals("convoy")) {
			if (strength > 1)
				attackedUnit.getOrder().setState(Order.FAILED);
			else
				state = FAILED;
		}		
	}

	/**
	 * Resolve this attack Order
	 *
	 * @return the state of the Order
	 */
	public int resolveAttack() {
		
		if (state == CHECKING)
			return FOLLOWING;
		
		else if (!findConvoyPath(unit, destinationTerritory, convoyPath))
			return FAILED;
		
		else if (destinationTerritory.getUnit() != null){
			Order occupyingUnitOrder = destinationTerritory.getUnit().getOrder();
			
			if (occupyingUnitOrder.getState() == CHECKING)
				return CHECKED_WAITING;
			
			else if (occupyingUnitOrder.equals("attack") && occupyingUnitOrder.getState() != FAILED){
				if (occupyingUnitOrder.getDestinationTerritory().equals(currentTerritory)){
					if (occupyingUnitOrder.getStrength() >= strength)
						return FAILED;
					else {
						PlanningPhase pp = (PlanningPhase) GameCanvas.getC().getPhase();
						pp.addRetreat(occupyingUnitOrder);
						return CHECKED_WAITING;
					}
				}
				state = CHECKING;
				int i = ((AttackOrder) occupyingUnitOrder).resolveAttack();
				if (i == FAILED){
					if (strength > 1)
						return CHECKED_WAITING;
					else
						return FAILED;
				}
				else
					return FOLLOWING;
			}
			
			else if (occupyingUnitOrder.equals("move") && occupyingUnitOrder.getState() != FAILED){
				int i = ((MoveOrder) occupyingUnitOrder).resolveMove();
				if (i != CHECKED_WAITING){
					if (strength > 1){
						occupyingUnitOrder.setState(FAILED);
						PlanningPhase pp = (PlanningPhase) GameCanvas.getC().getPhase();
						pp.addRetreat(occupyingUnitOrder);
						if (i == PASSED)
							occupyingUnitOrder.getDestinationTerritory().getUnit().getOrder().setState(FAILED);
						return CHECKED_WAITING;
					}
					else 
						return FAILED;
				}
				else 
					return FOLLOWING;
			}
			
			else if (occupyingUnitOrder.getUnit().getOwner() == unit.getOwner())
				return FAILED;
			
			else if (occupyingUnitOrder.equals("defend") && occupyingUnitOrder.getState() != FAILED){
				if (strength > occupyingUnitOrder.getStrength()){
					PlanningPhase pp = (PlanningPhase) GameCanvas.getC().getPhase();
					pp.addRetreat(occupyingUnitOrder);
					return CHECKED_WAITING;
				}
				else
					return FAILED;
			}
			
			else {
				if (strength > 1) {
					PlanningPhase pp = (PlanningPhase) GameCanvas.getC().getPhase();
					pp.addRetreat(occupyingUnitOrder);
					return CHECKED_WAITING;
				} else
					return FAILED;
			}
		}
		return CHECKED_WAITING;
	}
	
	/**
	 * Recursive method to find the path of this attack Order by using the convoy path.
	 *
	 * @param currUnit the current unit
	 * @param t the destination Territory of this attack
	 * @param convoyPath the convoy path
	 * @return true, if successful
	 */
	private boolean findConvoyPath(Unit currUnit, Territory t,
			ArrayList<Territory> convoyPath) {
		if (convoyPath.size() == 0){
			if (currUnit.getTerritory().isAdjacent(t))
				return true;
			else
				return false;
		}
		
		Order temp = null;
		int i;
		
		for (i = 0; i < convoyPath.size(); i++){
			if (currUnit.getTerritory().isAdjacent(convoyPath.get(i))){
				temp = convoyPath.get(i).getUnit().getOrder();
				if (temp.getState() != FAILED && temp.equals("convoy"))
					temp.setState(PASSED);
				else 
					temp = null;
				break;
			}
		}
		
		if (temp == null)
			return false;
		else{
			convoyPath.remove(i);
			return findConvoyPath(temp.getUnit(), t, convoyPath);
		}
	}
	
	/**
	 * Moves the Unit to its destination Territory.
	 */
	public void moveUnit() {
		destinationTerritory.setUnit(unit);
		if (!unit.isArmy()) {
			if (destinationTerritory.isAdjacentNC(currentTerritory))
				destinationTerritory.setNC(true);
			else if (destinationTerritory.isAdjacentSC(currentTerritory))
				destinationTerritory.setSC(true);
		}
		if (!destinationTerritory.hasSC())
			destinationTerritory.setOwner(unit.getOwner());
		unit.setTerritory(destinationTerritory);
		state = Order.DONE;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String s = currentTerritory.getName() + "\n";
		s += command + "\n";
		if (destinationTerritory != null)
			s += destinationTerritory.getName() + "\n";
		else 
			s += "[SELECT TERRITORY\n TO ATTACK]";
		if (convoyPath.size() != 0){
			for (Territory t : convoyPath)
				s += "by way of " + t.getName() + "\n"; 
		}
		return s;
	}

	/* (non-Javadoc)
	 * @see orders.Order#toShortString()
	 */
	@Override
	public String toShortString() {
		String s = "Att ";
		if (destinationTerritory != null)
			s += destinationTerritory.getName().substring(0, 4) + " ";
		if (convoyPath.size() != 0)
			s += "C ";
		for (Territory t : convoyPath)
			s += t.getName().substring(0, 2) + " ";
		return s;
	}

	/* (non-Javadoc)
	 * @see orders.Order#draw(org.newdawn.slick.Graphics, int, int)
	 */
	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawString(currentTerritory.getName() + " attacking", x, y);
		y += 10;
		if (destinationTerritory != null){
			if (convoyPath.size() != 0)
				g.drawString(destinationTerritory.getName() + " by way of:", x, y);
			else
				g.drawString(destinationTerritory.getName(), x, y);
		}
		else{
			g.drawString("[SELECT TERRITORY TO ATTACK]", x, y);
			return;
		}
		y += 10;
		if (convoyPath.size() != 0) {
			for (Territory t : convoyPath){
				g.drawString(t.getName(), x, y);
				y += 10;
			}
		}		
	}

}
