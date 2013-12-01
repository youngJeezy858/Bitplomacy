package phases;

import java.util.ArrayList;

import orders.AttackOrder;
import orders.BlankOrder;
import orders.ConvoyOrder;
import orders.DefendOrder;
import orders.MoveOrder;
import orders.Order;
import orders.SupportOrder;

/**
 * The Class PlanningPhase.
 */
public class PlanningPhase extends Phase{

	/** The support orders. */
	private ArrayList<SupportOrder> supportOrders;
	
	/** The defend orders. */
	private ArrayList<DefendOrder> defendOrders;
	
	/** The attack orders. */
	private ArrayList<AttackOrder> attackOrders;
	
	/** The move orders. */
	private ArrayList<MoveOrder> moveOrders;	
	
	/** The convoy orders. */
	private ArrayList<ConvoyOrder> convoyOrders;
	
	/** The blank orders. */
	private ArrayList<BlankOrder> blankOrders;
	
	/** The retreating territories. */
	private ArrayList<Order> retreatingUnits;
	
	
	/**
	 * Instantiates a new planning phase.
	 *
	 * @param season the season
	 * @param year the year
	 */
	public PlanningPhase(String season, int year) {
		super(season, year);	
		supportOrders = new ArrayList<SupportOrder>();
		defendOrders = new ArrayList<DefendOrder>();
		attackOrders = new ArrayList<AttackOrder>();
		moveOrders = new ArrayList<MoveOrder>();
		convoyOrders = new ArrayList<ConvoyOrder>();
		blankOrders = new ArrayList<BlankOrder>();
		retreatingUnits = new ArrayList<Order>();		
	}

	/* (non-Javadoc)
	 * @see phases.Phase#addOrder(orders.Order)
	 */
	@Override
	public void addOrder(Order o) {
		if (o.getCommand().equals("support"))
			supportOrders.add((SupportOrder) o);
		else if (o.getCommand().equals("defend"))
			defendOrders.add((DefendOrder) o);
		else if (o.getCommand().equals("attack"))
			attackOrders.add((AttackOrder) o);
		else if (o.getCommand().equals("convoy"))
			convoyOrders.add((ConvoyOrder) o);
		else if (o.getCommand().equals("move"))
			moveOrders.add((MoveOrder) o);		
		else
			blankOrders.add((BlankOrder) o);
	}
	
	/**
	 * Adds an Order containing a Unit that needs to retreat.
	 *
	 * @param o the Order
	 */
	public void addRetreat(Order o){
		retreatingUnits.add(o);
	}
	
	/**
	 * Gets the retreating units.
	 *
	 * @return the retreating units
	 */
	public ArrayList<Order> getRetreatingUnits(){
		return retreatingUnits;
	}

	/**
	 * Gets the attack orders.
	 *
	 * @return the attack orders
	 */
	public ArrayList<AttackOrder> getAttackOrders(){
		return attackOrders;
	}
	
	/* (non-Javadoc)
	 * @see phases.Phase#adjudicate()
	 */
	@Override
	public void adjudicate() {
		checkSyntax();
		resolveSupport();

		for (AttackOrder ao : attackOrders){
			if (ao.getState() == Order.CHECKED_WAITING)
				ao.resolveWaterAttack(ao.getDestinationTerritory().getUnit());
		}
			
		for (MoveOrder mo : moveOrders){
			if (mo.getState() != Order.FAILED)
				mo.setState(mo.resolveMove());
		}
		
		for (AttackOrder ao : attackOrders){
			if (ao.getState() != Order.FAILED)
				ao.setState(ao.resolveAttack());
		}
		
		for (AttackOrder ao : attackOrders){
			if (ao.getState() == Order.CHECKED_WAITING){
				if (hasConflict(ao))
					ao.setState(Order.CHECKER);
				else if (ao.getDestinationTerritory().getUnit() == null)
					ao.setState(Order.PASSED);
			}
		}
		
		for (MoveOrder mo : moveOrders){
			if (mo.getState() == Order.CHECKED_WAITING){
				if (hasConflict(mo))
					mo.setState(Order.CHECKER);
				else if (mo.getDestinationTerritory().getUnit() == null)
					mo.setState(Order.PASSED);
			}
		}
		
		for (AttackOrder ao : attackOrders){
			if (ao.getState() == Order.PASSED || ao.getState() == Order.FOLLOWING || ao.getState() == Order.CHECKING)
				ao.getStartingTerritory().removeUnit();
		}
		
		for (MoveOrder mo : moveOrders){
			if (mo.getState() == Order.PASSED || mo.getState() == Order.FOLLOWING || mo.getState() == Order.CHECKING)
				mo.getStartingTerritory().removeUnit();
		}
		
		for (AttackOrder ao : attackOrders){
			if (ao.getState() == Order.PASSED || ao.getState() == Order.FOLLOWING || ao.getState() == Order.CHECKING)
				ao.moveUnit();
		}
		
		for (MoveOrder mo : moveOrders){
			if (mo.getState() == Order.PASSED || mo.getState() == Order.FOLLOWING || mo.getState() == Order.CHECKING)
				mo.moveUnit();
		}

	}
	
	/**
	 * Checks the syntax of each Order. Attack Orders are checked more thoroughly
	 * to resolve fleet on fleet attacks.
	 */
	private void checkSyntax() {
		
		for (SupportOrder o : supportOrders){
			if (!o.isValidOrder())
				o.setState(Order.FAILED);
		}
		
		for (MoveOrder o : moveOrders){
			if (!o.isValidOrder())
				o.setState(Order.FAILED);
		}
		
		for (ConvoyOrder o : convoyOrders){
			if (!o.isValidOrder())
				o.setState(Order.FAILED);
		}
		
		for (AttackOrder o : attackOrders){
			if (!o.isValidOrder())
				o.setState(Order.FAILED);
			else if (o.getConvoyPath().size() == 0)
				o.setState(Order.CHECKED_WAITING);
		}
	}
	
	/**
	 * Resolves the Support Orders.
	 */
	private void resolveSupport() {
		for (SupportOrder so : supportOrders){
			
			if (so.getState() == Order.FAILED)
				continue;
			
			else if (isSupportCut(so))
				so.setState(Order.FAILED);
			
			else{
				so.setState(Order.PASSED);
				so.support();
			}	
		}
	}
	
	/**
	 * Checks if a Support order was cut off by an attack.
	 *
	 * @param so the Support Order
	 * @return true, if support is cut
	 */
	private boolean isSupportCut(Order so) {
		for (AttackOrder ao : attackOrders) {
			if (ao.getState() != Order.CHECKED_WAITING)
				continue;
			else if (ao.getDestinationTerritory().equals(
					so.getStartingTerritory()))
				return true;
		}
		return false;
	}

	/**
	 * Checks for conflicting moves/attacks.
	 *
	 * @param o the Order
	 * @return true, if successful
	 */
	private boolean hasConflict(Order o) {
		for (AttackOrder ao : attackOrders){
			if (ao.getState() != Order.CHECKED_WAITING && ao.getState() != Order.FOLLOWING 
					&& ao.getState() != Order.CHECKER && ao.getState() != Order.PASSED)
				continue;
			else if (ao.getStartingTerritory().equals(o.getStartingTerritory()))
				continue;
			
			else if (ao.getDestinationTerritory().equals(o.getDestinationTerritory())){
				if (ao.getStrength() >= o.getStrength())
					return true;
			}
		}
		for (MoveOrder mo : moveOrders){
			if (mo.getState() != Order.CHECKED_WAITING && mo.getState() != Order.FOLLOWING 
					&& mo.getState() != Order.CHECKER  && mo.getState() != Order.PASSED)
				continue;
			else if (mo.getStartingTerritory().equals(o.getStartingTerritory()))
				continue;
			
			else if (mo.getDestinationTerritory().equals(o.getDestinationTerritory())){
				if (mo.getStrength() >= o.getStrength())
					return true;
			}
		}
		return false;
	}

}
