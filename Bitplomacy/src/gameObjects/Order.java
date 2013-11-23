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
	 * Constructor for a new Order. Sets the unit to the input Territory's unit so potentially the unit could
	 * be null. 
	 *
	 * @param t the Territory where the Order initiated from
	 */
	public Order(Territory t){
		currentTerritory = t;
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
	 * Adds a unit that is convoying to the convoyUnits list.
	 *
	 * @param u the convoying navy Unit to be added
	 */
	public void addConvoyUnit(Unit u){
		convoyUnits.add(u);
	}

	/**
	 * Sets the state of the Order. Ideally one will use the static int fields
	 * of this class as input.
	 *
	 * @param i the state of the Order
	 */
	public void adjudicate(int i){
		state = i;
	}

	/**
	 * Adds the order to the game's current Turn so it may be adjudicated there.
	 */
	public void execute(){
		Canvas.getC().addOrder(this);	
	}

	/**
	 * Gets the String value of the command. Commands include: move, attack, defend, 
	 * support, convoy, build army, build navy, retreat, and disband.
	 *
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Gets the convoy destination of an attack or move Order.
	 *
	 * @return the convoy destination
	 */
	public Territory getConvoyDestination(){
		return convoyDestination;
	}

	/**
	 * Gets the strength of an attack, move, or defend Order.
	 *
	 * @return the strength
	 */
	public int getStrength(){
		return strength;
	}

	/**
	 * Gets the unit being supported of a support Order.
	 *
	 * @return the supported unit
	 */
	public Unit getSupportedUnit() {
		return supportedUnit;
	}

	/**
	 * Gets the Territory where the Order was initialized.
	 *
	 * @return the starting Territory
	 */
	public Territory getStartingTerritory() {
		return currentTerritory;
	}

	/**
	 * Gets the Territory where the command will be acted upon.
	 *
	 * @return the destination Territory
	 */
	public Territory getDestinationTerritory() {
		return destinationTerritory;
	}

	/**
	 * Gets the unit that will execute the Order.
	 *
	 * @return the unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Increments the int value of strength by one.
	 */
	public void incrementStrength(){
		strength++;
	}

	/**
	 * Gets the state of the Order as an int
	 *
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	//TODO: Do I want to abstract this class??? 
	/**
	 * Checks if the syntax of the Order is order.  Used during adjudication.  Will
	 * Check attack and convoy Orders more thoroughly to resolve dislodged convoys
	 * early during adjudication.  Otherwise it will simply check if an Order
	 *
	 * @return true, if syntax is valid
	 */
	public boolean isValidOrder(){
		
		if (command.equals("attack") && destinationTerritory != null && !destinationTerritory.equals(currentTerritory))
			return true;
		else if (command.equals("move") && destinationTerritory != null && !destinationTerritory.equals(currentTerritory))
			return true;
		else if (command.equals("support") && supportedUnit != null && destinationTerritory != null && !destinationTerritory.equals(currentTerritory))
			return true;
		else if (command.equals("defend"))
			return true;
		else if (command.equals("convoy") && destinationTerritory != null && convoyDestination != null && !destinationTerritory.equals(currentTerritory)
				&& !unit.isArmy() && !currentTerritory.isLand() && destinationTerritory.getUnit() != null && 
				(convoyDestination.isLand() || (!convoyDestination.isLand() && convoyDestination.getUnit() != null)))
			return true;
		else
			return false;
		
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
	 * Sets the convoy destination of a convoy Command.
	 *
	 * @param t the new convoy destination
	 */
	public void setConvoyDestination(Territory t) {
		convoyDestination = t;
	}

	/**
	 * Sets the supported unit.
	 *
	 * @param u the unit being supported
	 */
	public void setSupport(Unit u) {
		supportedUnit = u;
	}

	/**
	 * Sets the Territory where the order was initialized.
	 *
	 * @param t the Territory
	 */
	public void setStartingTerritory(Territory t){
		currentTerritory = t;
	}

	/**
	 * Sets the Territory where the order will be acted upon.
	 *
	 * @param t the Territory
	 */
	public void setDestinationTerritory(Territory t){
		destinationTerritory = t;
	}
	
	/**
	 * Checks if the input String is equal to the command field.
	 *
	 * @param command the command
	 * @return true, if input is equal to the command.
	 */
	public boolean isCommandEqual(String command){
		return this.command.equals(command);
	}

	/**
	 * Gets the list of units that are convoying this attack or move Order.
	 *
	 * @return the convoying units list
	 */
	public ArrayList<Unit> getConvoyUnits() {
		return convoyUnits;
	}

	/**
	 * Checks if the starting Territory is adjacent to the destination Territory.
	 * 
	 * @return true, if territories are adjacent
	 */
	public boolean isAdjacent() {
		return currentTerritory.isAdjacent(destinationTerritory);
	}
	
}
