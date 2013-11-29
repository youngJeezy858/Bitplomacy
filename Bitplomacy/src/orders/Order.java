package orders;

import canvases.GameCanvas;
import gameObjects.Territory;
import gameObjects.Unit;

/**
 * Contains objects that are involved for an order of a unit.  Orders include: 
 * move, attack, defend, support, convoy, build army, build navy, retreat, and disband.
 */
public abstract class Order {

	/** The initial territory of the Order. Selected first in the process of creating an Order. */
	protected Territory currentTerritory;
	
	/** The destination of the command. Selected second in the process of creating an Order.
	 * Can be null for some orders. */
	protected Territory destinationTerritory;
	
	/** Commands include: move, attack, defend, support, convoy, build army, build navy, 
	 * retreat, and disband. */
	protected String command;
	
	/** The strength an attack, move, or defend order. Initialized at 1. Used during adjudication 
	 * to see if one of those 3 moves failed or passed. If the order is supported it will be 
	 * incremented by one for each supporting unit. */
	protected int strength;
	
	/** The current state of the Order. Primarily used during adjudication. Check the static int 
	 * fields for descriptions of each. */
	protected int state;
	
	protected Unit unit;
	
	/** Initial state of all Orders. */
	public static final int NOT_CHECKED = 0;
	
	/** After the syntax of the Order is checked during adjudication, it will set to this state. */
	public static final int CHECKED_WAITING = 1;
	
	/** Order is set to this state if it fails any of the checks during adjudication. */
	public static final int FAILED = 2;
	
	/** Once adjudication is complete, all orders that do not have a state of FAILED are set to
	 * this state. An Order is only executed if it has a state of PASSED at the end of adjudication.*/
	public static final int PASSED = 3;

	/** Used if an attack/move order is following another attack/move Order. */
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
	 * @param t 
	 */
	public Order(Territory t){
		currentTerritory = t;
		unit = t.getUnit();
		strength = 1;
		state = NOT_CHECKED;
	}

	/**
	 * Sets the state of the Order. Ideally one will use the static int fields
	 * of this class as input.
	 *
	 * @param i the state of the Order
	 */
	public void setState(int i){
		state = i;
	}

	/**
	 * Adds the order to the game's current Turn so it may be adjudicated there.
	 */
	public void execute(){
		GameCanvas.getC().addOrder(this);	
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
	 * Gets the strength of an attack, move, or defend Order.
	 *
	 * @return the strength
	 */
	public int getStrength(){
		return strength;
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

	/**
	 * Sets the command.
	 *
	 * @param c the new command
	 */
	public void setCommand(String c){
		command = c;
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
	 * Checks if the starting Territory is adjacent to the destination Territory.
	 * 
	 * @return true, if territories are adjacent
	 */
	public boolean isAdjacent() {
		if (!unit.isArmy() && destinationTerritory.hasCoasts()){
			return destinationTerritory.isAdjacentNC(currentTerritory) || destinationTerritory.isAdjacentSC(currentTerritory);
		}
		return currentTerritory.isAdjacent(destinationTerritory);
	}
	
	public boolean equals(String command){
		return this.command.equals(command);
	}
	
	public abstract boolean isValidOrder();
	
	public abstract void addAdditionalTerritory(Territory t);

	public void setUnitOrder() {
		currentTerritory.getUnit().setOrder(this);
	}

	public Unit getUnit() {
		return unit;
	}
	
}
