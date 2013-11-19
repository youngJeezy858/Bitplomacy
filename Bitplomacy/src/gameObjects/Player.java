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
	 * Adjust num sc.
	 *
	 * @param num the num
	 */
	public void adjustNumSC(int num){
		supplyCenterCount = num;
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
	
}
