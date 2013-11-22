package gameObjects;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player {

	/** The units. */
	private ArrayList<Unit> units;
	
	/** The name. */
	private String name;
	
	/** The supply center count. */
	private int supplyCenterCount;
	
	/**
	 * Instantiates a new player.
	 *
	 * @param name the name
	 */
	public Player(String name){
		this.name = name;
		units = new ArrayList<Unit>();
	}
	
	/**
	 * Adds the unit.
	 *
	 * @param u the u
	 */
	public void addUnit(Unit u){
		units.add(u);
	}
	
	/**
	 * Gets the supply count.
	 *
	 * @return the supply count
	 */
	public int getSupplyCount(){
		return supplyCenterCount;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Gets the owner num.
	 *
	 * @return the owner num
	 */
	public int getOwnerNum(){
		int i = 8;
		 if (name.equals("England"))
			 i = 1;
		else if (name.equals("Austria-Hungary"))
			 i = 2;
		else if (name.equals("Italy"))
			 i = 3;
		else if (name.equals("Turkey"))
			 i = 4;
		else if (name.equals("France"))
			 i = 5;
		else if (name.equals("Russia"))
			 i = 6;
		else if (name.equals("Germany"))
			 i = 7;
		return i;
	}
	
	/**
	 * Adjust num sc.
	 *
	 * @param i the i
	 */
	public void adjustNumSC(int i){
		supplyCenterCount = i;
	}

	/**
	 * Execute orders.
	 */
	public void executeOrders() {
		for (Unit u : units)
			u.executeOrder();
	}

	/**
	 * Reset orders.
	 */
	public void resetOrders() {
		for (Unit u : units)
			u.resetOrder();
	}
	
	/**
	 * Removes the unit.
	 *
	 * @param t the t
	 */
	public void removeUnit(Territory t){
		int i;
		boolean blah = false;
		for (i = 0; i < units.size(); i++){
			if (units.get(i).getTerritory().equals(t)){
				blah = true;
				break;
			}
		}
		if (blah)
			units.remove(i);
	}

	/**
	 * Gets the num units.
	 *
	 * @return the num units
	 */
	public int getNumUnits() {
		return units.size();
	}

	/**
	 * Gets the a unit.
	 *
	 * @return the a unit
	 */
	public Unit getAUnit() {
		if (units.size() == 0)
			return null;
		return units.get(0);
	}
	
}
