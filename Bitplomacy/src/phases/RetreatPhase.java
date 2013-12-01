package phases;

import java.util.ArrayList;

import orders.AttackOrder;
import orders.DisbandOrder;
import orders.Order;
import orders.RetreatOrder;
import canvases.GameCanvas;

/**
 * The Class RetreatPhase.
 */
public class RetreatPhase extends Phase{	
	
	/** The retreat orders. */
	private ArrayList<RetreatOrder> retreatOrders;
	
	/** The disband orders. */
	private ArrayList<DisbandOrder> disbandOrders;
	
	/** The retreating territories. */
	private ArrayList<Order> retreatingUnits;
	
	/** The retreating territories. */
	private ArrayList<AttackOrder> remainingAttacks;
	
	
	/**
	 * Instantiates a new retreat phase.
	 *
	 * @param season the season
	 * @param year the year
	 * @param retreatingUnits the list of Units needing to retreat
	 * @param remainingAttacks the list of Units waiting on a Unit to retreat
	 */
	public RetreatPhase(String season, int year, ArrayList<Order> retreatingUnits, ArrayList<AttackOrder> remainingAttacks) {
		super(season, year);
		retreatOrders = new ArrayList<RetreatOrder>();
		disbandOrders = new ArrayList<DisbandOrder>();
		this.retreatingUnits = retreatingUnits;
		this.remainingAttacks = remainingAttacks;
	}


	/* (non-Javadoc)
	 * @see phases.Phase#addOrder(orders.Order)
	 */
	@Override
	public void addOrder(Order o) {
		for (Order ro : retreatingUnits){
			if (ro.getStartingTerritory().equals(o.getStartingTerritory())){
				if (o.getCommand().equals("retreat"))
					retreatOrders.add((RetreatOrder) o);
				else
					disbandOrders.add(new DisbandOrder(o.getStartingTerritory()));
			}
		}	
	}

	/* (non-Javadoc)
	 * @see phases.Phase#adjudicate()
	 */
	@Override
	public void adjudicate() {

		for (DisbandOrder o : disbandOrders)
			o.resolveDisband(retreatingUnits);
		
		for (RetreatOrder o : retreatOrders)
			o.resolveRetreat(retreatingUnits);
		
		for (RetreatOrder ro : retreatOrders){
			if (ro.getState() == Order.DONE)
				continue;
			else if (hasRetreatConflict(ro))
				ro.setState(Order.CHECKER);
			else if (ro.getState() == Order.CHECKED_WAITING){
				ro.setState(Order.PASSED);
				ro.getStartingTerritory().removeUnit();
				moveUnit(ro);
			}
		}
		
		for (RetreatOrder o : retreatOrders){
			if (o.getState() == Order.FAILED || o.getState() == Order.CHECKER)
				GameCanvas.getC().removeUnit(o.getUnit());
		}
		
		for (Order o : retreatingUnits){
			o.setState(Order.FAILED);
			o.getStartingTerritory().removeUnit();
			moveUnit(o);
		}
		
		for (AttackOrder ao : remainingAttacks){
			if (ao.getState() == Order.CHECKED_WAITING){
				ao.setState(Order.PASSED);
				ao.getStartingTerritory().removeUnit();
				moveUnit(ao);
			}
		}
	}

	/**
	 * Checks conflicting retreat orders.
	 *
	 * @param o the Order
	 * @return true, if successful
	 */
	private boolean hasRetreatConflict(Order o){
		for (Order ro : retreatOrders){
			if (o.getStartingTerritory().equals(ro.getStartingTerritory()))
				continue;
			else if (ro.getState() != Order.CHECKED_WAITING && ro.getState() != Order.CHECKER)
				continue;
			else if (ro.getDestinationTerritory().equals(o.getDestinationTerritory()))
				return true;
		}
		return false;
	}
	
	/**
	 * Gets the retreating units.
	 *
	 * @return the retreating units
	 */
	public ArrayList<Order> getRetreatingUnits(){
		return retreatingUnits;
	}
	
}
