package gameObjects;

import java.util.ArrayList;

/**
 * The Class Player represents on of the 7 countries on the map.
 */
public class Player {

	/** The list of Units a Player controls */
	private ArrayList<Unit> units;
	
	/** The name of the Player's country */
	private String name;
	
	/** The supply center count. */
	private int supplyCenterCount;
	
	/** */
	private int ownerKey;
	
	/**
	 * Instantiates a new player. Sets the key value of the player automatically.
	 *
	 * @param name the name of the country
	 */
	public Player(String name){
		this.name = name;
		ownerKey = Territory.NEUTRAL;
		 if (name.equals("England"))
			 ownerKey = Territory.ENGLAND;
		else if (name.equals("Austria-Hungary"))
			 ownerKey = Territory.AUSTRIA_HUNGARY;
		else if (name.equals("Italy"))
			 ownerKey = Territory.ITALY;
		else if (name.equals("Turkey"))
			 ownerKey = Territory.TURKEY;
		else if (name.equals("France"))
			 ownerKey = Territory.FRANCE;
		else if (name.equals("Russia"))
			 ownerKey = Territory.RUSSIA;
		else if (name.equals("Germany"))
			 ownerKey = Territory.GERMANY;
		units = new ArrayList<Unit>();
	}
	
	/**
	 * Adds a Unit to the Player's Unit list
	 *
	 * @param u the Unit to be added
	 */
	public void addUnit(Unit u){
		units.add(u);
	}
	
	/**
	 * Gets the number of supply centers currently controlled by the Player.
	 *
	 * @return the supply center count
	 */
	public int getSupplyCenterCount(){
		return supplyCenterCount;
	}
	
	/**
	 * Gets the name of the Player's country
	 *
	 * @return the country name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Gets the owner as an int value. Check the static int fields found
	 * in the Territory class for specific values of each country.
	 *
	 * @return the owner's int value
	 */
	public int getOwnerKey(){
		return ownerKey;
	}
	
	/**
	 * Sets the number of supply centers a Player controls.
	 *
	 * @param sc the number of supply centers
	 */
	public void setSupplyCenterCount(int sc){
		supplyCenterCount = sc;
	}

	/**
	 * Executes every Units' order that are owned by the Player.
	 */
	public void executeOrders() {
		for (Unit u : units)
			u.executeOrder();
	}

	/**
	 * Sets every Units' order to null.
	 */
	public void resetOrders() {
		for (Unit u : units)
			u.setOrder(null);
	}
	
	/**
	 * Removes a Unit from the Player's Unit list. 
	 *
	 * @param t the current Territory of the Unit
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
	 * Gets the total count of Units the Player has currently 
	 *
	 * @return the unit count
	 */
	public int getNumUnits() {
		return units.size();
	}

	/**
	 * Gets the first Unit from the Unit list.  Returns null if the Player has none.
	 *
	 * @return the unit
	 */
	public Unit getAUnit() {
		if (units.size() == 0)
			return null;
		return units.get(0);
	}

	public int getNumArmies() {
		int i = 0;
		for (Unit u : units){
			if (u.isArmy())
				i++;
		}
		return i;
	}
	
	public int getNumNavies() {
		int i = 0;
		for (Unit u : units){
			if (!u.isArmy())
				i++;
		}
		return i;
	}
	
}
