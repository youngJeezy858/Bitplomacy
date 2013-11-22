package gameObjects;

import java.util.ArrayList;

import gui.Canvas;

/**
 * Contains objects that are involved for an order of a unit.  Orders include: 
 * move, attack, defend, support, convoy, build army, build navy, retreat, and disband.
 */
public class Order {

	/** The initial territory of the Order. Selected first in the process of creating an Order. */
	private Territory currentTerritory;
	
	/** The destination of the command. Selected second in the process of creating an Order.
	 * Can be null for some orders. */
	private Territory destinationTerritory;
	
	/** Used for a convoy Order. Specifies where the army being convoyed should be convoyed to 
	 * by the convoying navy. Will be null if not a Convoy Order. */
	private Territory convoyDestination;
	
	/** The unit executing the Order. */
	private Unit unit;
	
	/** If the Order is support this will be the unit that is being supported. Will conatin a 
	 * null value if the order is not a support. */
	private Unit supportedUnit;
	
	/** Used for an attack or move Order. If the attack/move is being convoyed, this will contain a
	 * list of units that are convoying. Will contain an empty list otherwise. */
	private ArrayList<Unit> convoyUnits;
	
	/** Commands include: move, attack, defend, support, convoy, build army, build navy, 
	 * retreat, and disband. */
	private String command;
	
	/** The strength an attack, move, or defend order. Initialized at 1. Used during adjudication 
	 * to see if one of those 3 moves failed or passed. If the order is supported it will be 
	 * incremented by one for each supporting unit. */
	private int strength;
	
	/** The current state of the Order. Primarily used during adjudication. Check the static int 
	 * fields for descriptions of each. */
	private int state;
	
	/** Initial state of all Orders. */
	public static final int NOT_CHECKED = 0;
	
	/** After the syntax of the Order is checked during adjudication, it will set to this state. */
	public static final int CHECKED_WAITING = 1;
	
	/** Order is set to this state if it fails any of the checks during adjudication. */
	public static final int FAILED = 2;
	
	/** Once adjudication is complete, all orders that do not have a state of FAILED are set to
	 * this state. An Order is only executed if it has a state of PASSED at the end of adjudication.*/
	public static final int PASSED = 3;

	// TODO: Decide if this FOLLOWING field is necessary in Order (Check Turn)
	/** This may not be necessary. */
	public static final int FOLLOWING = 4;

	/** Primarily used during the retreat phase. If a retreat/disband is not necessary for a unit,
	 * the Order state is set to DONE so it will be skipped. */
	public static final int DONE = 5;

	/** Primarily used for resolving move/attack/retreat conflicts. If a valid Order is determined to have
	 * a move conflict which causes it to fail, the Order state is set to CHECKER. This is so other Orders
	 * are still compared to this order when resolving conflicts. */
	public static final int CHECKER = 6;

	/** Used for solving the revolving 3+ unit move problem. If a unit is moving/attacking another unit 
	 * which is also moving, it is set to this state. So if that unit is also moving into another territory
	 * with a unit that is moving into the first there will not be an endless loop. */
	public static final int CHECKING = 7;
		
	/**
	 * Instantiates a new order.
	 *
	 * @param t1 the t1
	 */
	public Order(Territory t1){
		currentTerritory = t1;
		unit = currentTerritory.getUnit();
		strength = 1;
		command = "idle";
		state = NOT_CHECKED;
		convoyUnits = new ArrayList<Unit>();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = "";
		if (unit != null)
			s = Territory.getOwnerName(unit.getOwner()) + " unit in ";
		s = currentTerritory.getOwnerName() + " unit in \n" + currentTerritory.getName() + "\n";
		if (command != "idle")
			s += command + "\n";
		if (supportedUnit != null)
			s += "for " + Territory.getOwnerName(supportedUnit.getOwner()) + "\nat ";
		if (destinationTerritory != null)
			s += destinationTerritory.getName() + "\n";
		if (convoyUnits.size() > 0){
			s += "by way of \n";
			for (Unit u : convoyUnits)
				s += u.getTerritory().getName() + "\n";
		}
		if (convoyDestination != null)
			s += "to " + convoyDestination.getName();
		return s;
	}

	/**
	 * Adds the convoy unit.
	 *
	 * @param u the u
	 */
	public void addConvoyUnit(Unit u){
		convoyUnits.add(u);
	}

	/**
	 * Adjudicate.
	 *
	 * @param i the i
	 */
	public void adjudicate(int i){
		state = i;
	}

	/**
	 * Execute.
	 */
	public void execute(){
		Canvas.getC().addOrder(this);	
	}

	/**
	 * Gets the command.
	 *
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Gets the convoy destination.
	 *
	 * @return the convoy destination
	 */
	public Territory getConvoyDestination(){
		return convoyDestination;
	}

	/**
	 * Gets the strength.
	 *
	 * @return the strength
	 */
	public int getStrength(){
		return strength;
	}

	/**
	 * Gets the supported unit.
	 *
	 * @return the supported unit
	 */
	public Unit getSupportedUnit() {
		return supportedUnit;
	}

	/**
	 * Gets the terr1.
	 *
	 * @return the terr1
	 */
	public Territory getTerr1() {
		return currentTerritory;
	}

	/**
	 * Gets the terr2.
	 *
	 * @return the terr2
	 */
	public Territory getTerr2() {
		return destinationTerritory;
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Increment strength.
	 */
	public void incrementStrength(){
		strength++;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * Checks if is valid order.
	 *
	 * @return true, if is valid order
	 */
	public boolean isValidOrder(){
		
		if (command.equals("attack") && destinationTerritory != null && !destinationTerritory.equals(currentTerritory))
			return currentTerritory.isValidAttack(destinationTerritory, convoyUnits);
		else if (command.equals("move") && destinationTerritory != null && !destinationTerritory.equals(currentTerritory))
			return true;
		else if (command.equals("support") && supportedUnit != null && destinationTerritory != null && !destinationTerritory.equals(currentTerritory))
			return true;
		else if (command.equals("defend"))
			return true;
		else if (command.equals("convoy") && destinationTerritory != null && convoyDestination != null && !destinationTerritory.equals(currentTerritory)
				&& !unit.isLand() && !currentTerritory.isLand() && destinationTerritory.getUnit() != null && 
				(convoyDestination.isLand() || (!convoyDestination.isLand() && convoyDestination.getUnit() != null)))
			return true;
		else
			return false;
		
	}

	/**
	 * Checks if is valid support.
	 *
	 * @return true, if is valid support
	 */
	public boolean isValidSupport() {
		
		if (!unit.isLand() && destinationTerritory.isLand() && !destinationTerritory.hasCoast())
			return false;
		if (unit.isLand() && !destinationTerritory.isLand())
			return false;
		if (supportedUnit.getOrder().equals("attack") && destinationTerritory.getUnit() != null && destinationTerritory.getUnit().getOwner() == unit.getOwner())
			return false;
		
		String supportedCommand = supportedUnit.getOrder().command;
		if (!(supportedCommand.equals("attack") || supportedCommand.equals("move") || supportedCommand.equals("defend"))) 
			return false;
		
		return currentTerritory.isAdjacent(destinationTerritory);
	}

	/**
	 * Sets the command.
	 *
	 * @param c the new command
	 */
	public void setCommand(String c){
		command = c;
	}

	/**
	 * Sets the convoy destination.
	 *
	 * @param t the new convoy destination
	 */
	public void setConvoyDestination(Territory t) {
		convoyDestination = t;
	}

	/**
	 * Sets the support.
	 *
	 * @param u the new support
	 */
	public void setSupport(Unit u) {
		supportedUnit = u;
	}

	/**
	 * Sets the terr1.
	 *
	 * @param t the new terr1
	 */
	public void setTerr1(Territory t){
		currentTerritory = t;
	}

	/**
	 * Sets the terr2.
	 *
	 * @param t2 the new terr2
	 */
	public void setTerr2(Territory t2){
		destinationTerritory = t2;
	}

	/**
	 * Expecting convoy.
	 *
	 * @return true, if successful
	 */
	public boolean expectingConvoy() {
		if (!currentTerritory.isAdjacent(destinationTerritory) && unit.isLand() && destinationTerritory.isLand())
			return true;
		return false;
	}

	/**
	 * Push order.
	 */
	public void pushOrder() {
		unit.setOrder(this);
	}

	/**
	 * Check convoying units.
	 *
	 * @return true, if successful
	 */
	public boolean checkConvoyingUnits() {	
		Order o = convoyUnits.get(0).getOrder();
		if (!o.getCommand().equals("convoy") || !o.getTerr2().equals(currentTerritory))
			return false;
		o = convoyUnits.get(convoyUnits.size()).getOrder();
		if (!o.getCommand().equals("convoy") || !o.getConvoyDestination().equals(destinationTerritory))
			return false;
		for (int i = 1; i < convoyUnits.size(); i++) {
			o = convoyUnits.get(i).getOrder();
			if (!o.getCommand().equals("convoy")
					|| !o.getTerr2().equals(convoyUnits.get(i-1).getOrder()))
				return false;
		}
		return true;
	}
	
	/**
	 * Equals.
	 *
	 * @param command the command
	 * @return true, if successful
	 */
	public boolean equals(String command){
		return this.command.equals(command);
	}

	/**
	 * Gets the convoy units.
	 *
	 * @return the convoy units
	 */
	public ArrayList<Unit> getConvoyUnits() {
		return convoyUnits;
	}
	
	/**
	 * Find convoy path.
	 *
	 * @param currUnit the curr unit
	 * @param t the t
	 * @param convoyUnits the convoy units
	 * @return true, if successful
	 */
	public static boolean findConvoyPath(Unit currUnit, Territory t, ArrayList<Unit> convoyUnits) {
		
		if (convoyUnits.size() == 0){
			if (currUnit.getTerritory().isAdjacent(t))
				return true;
			else
				return false;
		}
		
		Unit temp = null;
		int i;
		for (i = 0; i < convoyUnits.size(); i++){
			if (currUnit.getTerritory().isAdjacent(convoyUnits.get(i).getTerritory())){
				temp = convoyUnits.get(i);
				if (temp.getOrder().getState() != Order.FAILED)
					temp.getOrder().adjudicate(Order.PASSED);
				else 
					temp = null;
				break;
			}
		}
		
		if (temp == null)
			return false;
		else{
			convoyUnits.remove(i);
			return findConvoyPath(temp, t, convoyUnits);
		}
		
	}
	
}
