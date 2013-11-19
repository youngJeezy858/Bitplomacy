package gameObjects;

import java.util.ArrayList;

import gui.Canvas;

// TODO: Auto-generated Javadoc
/**
 * The Class Order.
 */
public class Order {

	/** The terr1. */
	private Territory terr1;
	
	/** The terr2. */
	private Territory terr2;
	
	/** The convoy destination. */
	private Territory convoyDestination;
	
	/** The unit. */
	private Unit unit;
	
	/** The supported unit. */
	private Unit supportedUnit;
	
	/** The convoy units. */
	private ArrayList<Unit> convoyUnits;
	
	/** The command. */
	private String command;
	
	/** The strength. */
	private int strength;
	
	/** The state. */
	private int state;
	
	/** The Constant NOT_CHECKED. */
	public static final int NOT_CHECKED = 0;
	
	/** The Constant CHECKED_WAITING. */
	public static final int CHECKED_WAITING = 1;
	
	/** The Constant FAILED. */
	public static final int FAILED = 2;
	
	/** The Constant PASSED. */
	public static final int PASSED = 3;

	public static final int FOLLOWING = 4;

	public static final int DONE = 5;
		
	/**
	 * Instantiates a new order.
	 *
	 * @param t1 the t1
	 */
	public Order(Territory t1){
		terr1 = t1;
		unit = terr1.getUnit();
		strength = 1;
		command = "idle";
		state = NOT_CHECKED;
		convoyUnits = new ArrayList<Unit>();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = terr1.getOwnerName() + " unit in \n" + terr1.getName() + "\n";
		if (command != "idle")
			s += command + "\n";
		if (supportedUnit != null)
			s += "for " + Territory.getOwnerName(supportedUnit.getOwner()) + "\nat ";
		if (terr2 != null)
			s += terr2.getName() + "\n";
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
		return terr1;
	}

	/**
	 * Gets the terr2.
	 *
	 * @return the terr2
	 */
	public Territory getTerr2() {
		return terr2;
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
		
		if (command.equals("attack") && terr2 != null && !terr2.equals(terr1))
			return terr1.isValidAttack(terr2, convoyUnits);
		else if (command.equals("move") && terr2 != null && !terr2.equals(terr1))
			return true;
		else if (command.equals("support") && supportedUnit != null && terr2 != null && !terr2.equals(terr1))
			return true;
		else if (command.equals("defend"))
			return true;
		else if (command.equals("convoy") && terr2 != null && convoyDestination != null && !terr2.equals(terr1)
				&& !unit.isLand() && !terr1.isLand() && terr2.getUnit() != null && 
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
		
		if (!unit.isLand() && terr2.isLand() && !terr2.hasCoast())
			return false;
		if (unit.isLand() && !terr2.isLand())
			return false;
		
		String supportedCommand = supportedUnit.getOrder().command;
		if (!(supportedCommand.equals("attack") || supportedCommand.equals("move") || supportedCommand.equals("defend"))) 
			return false;
		
		return terr1.isAdjacent(terr2);
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
		terr1 = t;
	}

	/**
	 * Sets the terr2.
	 *
	 * @param t2 the new terr2
	 */
	public void setTerr2(Territory t2){
		terr2 = t2;
	}

	/**
	 * Expecting convoy.
	 *
	 * @return true, if successful
	 */
	public boolean expectingConvoy() {
		if (!terr1.isAdjacent(terr2) && unit.isLand() && terr2.isLand())
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
		if (!o.getCommand().equals("convoy") || !o.getTerr2().equals(terr1))
			return false;
		o = convoyUnits.get(convoyUnits.size()).getOrder();
		if (!o.getCommand().equals("convoy") || !o.getConvoyDestination().equals(terr2))
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
				temp.getOrder().adjudicate(Order.PASSED);
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
