package gameObjects;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import orders.DisbandOrder;
import orders.Order;
import phases.BuildPhase;
import phases.RetreatPhase;
import canvases.GameCanvas;

/**
 * The Class Player represents one of the 7 countries on the map.
 */
public class Player {

	/** The list of Units a Player controls. */
	private ArrayList<Unit> units;
	
	/** The name of the Player's country. */
	private String name;
	
	/** The supply center count. */
	private int supplyCenterCount;
	
	/** The owner key. */
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
	 * Adds a Unit to the Player's Unit list.
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
	 * Gets the name of the Player's country.
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
	 * Gets the total count of Units the Player has currently.
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

	/**
	 * Gets the number of armies owned by this Player.
	 *
	 * @return the number of armies
	 */
	public int getNumArmies() {
		int i = 0;
		for (Unit u : units){
			if (u.isArmy())
				i++;
		}
		return i;
	}
	
	/**
	 * Gets the number of navies owned by this Player.
	 *
	 * @return the number of navies
	 */
	public int getNumNavies() {
		int i = 0;
		for (Unit u : units){
			if (!u.isArmy())
				i++;
		}
		return i;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String s = name + ":\n";
		
		if (GameCanvas.getC().getPhase().getSeason().contains("Retreats")){
			RetreatPhase rp = (RetreatPhase) GameCanvas.getC().getPhase();
			ArrayList<Order> retreats = rp.getRetreatingUnits();
			for (Order o : retreats){
				for (Unit u : units){
					if (o.getStartingTerritory().equals(u.getTerritory()))
						s += u.toString();
				}
			}
		}
		
		else {
			for (Unit u : units)
				s += u.toString() + "\n";
		}
		return s;
	}

	/**
	 * Draw all of this Player's Units' Orders to the sidebar.
	 *
	 * @param g the graphics to draw Strings with
	 * @param x the x coordinate to draw the order at
	 * @param y the y coordinate to draw the order at
	 * @return the x and y coordinates as an int[]
	 */
	public int[] draw(Graphics g, int x, int y) {
		if (y > 380){
			x += 165;
			y = 110;
		}
		g.drawString(name + ":", x, y);
		y += 10;
		
		if (GameCanvas.getC().getPhase().getSeason().contains("Retreats")){
			RetreatPhase rp = (RetreatPhase) GameCanvas.getC().getPhase();
			ArrayList<Order> retreats = rp.getRetreatingUnits();
			for (Order o : retreats){
				for (Unit u : units){
					if (o.getStartingTerritory().equals(u.getTerritory())){
						if (y > 390){
							x += 165;
							y = 110;
						} 
						g.drawString(u.toString(), x, y);
						y += 10;
					}
				}
			}
		}
		
		else if (GameCanvas.getC().getPhase().getSeason().equals("Build/Remove")){
			int i = supplyCenterCount - units.size();
			
			if (y > 390){
				x += 165;
				y = 110;
			} 
			
			if (i > 0)
				g.drawString("Can build " + i + " units!", x, y);
			else if (i < 0)
				g.drawString("Must disband " + Math.abs(i) + " units!", x, y);
			else
				g.drawString("Can't build or remove anything!", x, y); 
			y += 10;
		}
		
		else {
			for (Unit u : units){
				if (y > 390){
					x += 165;
					y = 110;
				} 
				g.drawString(u.toString(), x, y);
				y += 10;
			}
		}
		int[] out = {x, y};
		return out;
	}

	/**
	 * Checks if all of this Player's Units have orders.
	 *
	 * @return true, if successful
	 */
	public boolean allHaveOrders() {
		if (GameCanvas.getC().getPhase().getSeason().contains("Retreats")){
			RetreatPhase rp = (RetreatPhase) GameCanvas.getC().getPhase();
			ArrayList<Order> retreats = rp.getRetreatingUnits();
			for (Order o : retreats){
				for (Unit u : units){
					if (o.getStartingTerritory().equals(u.getTerritory()) && u.getOrder() == null){
						return false;
					}
				}
			}
		}
		
		else if (GameCanvas.getC().getPhase().getSeason().equals("Build/Remove")){
			int i = supplyCenterCount - units.size();
			BuildPhase bp = (BuildPhase) GameCanvas.getC().getPhase();
			if (i > 0){
				int count = 0;
				ArrayList<Order> orders = bp.getBuildOrders();
				for (Order o : orders){
					if (o.getStartingTerritory().isHomeCity(ownerKey))
						count++;
				}
				if (count < i || (ownerKey == Territory.RUSSIA && count == 4) || (ownerKey != Territory.RUSSIA && count == 3))
					return false;
			}
			else if (i < 0){
				int count = 0;
				ArrayList<DisbandOrder> orders = bp.getDisbandOrders();
				for (DisbandOrder o : orders){
					for (Unit u : units){
						if (o.getStartingTerritory().equals(u.getTerritory()))
							count++;
					}
				}
				if (count < Math.abs(i))
					return false;
			}
		}
		
		else {
			for (Unit u : units) {
				if (u.getOrder() == null)
					return false;
			}
		}
		
		return true;
	}

	/**
	 * Save this Player's Units' locations and if it is an Army or Fleet.
	 * Used for saving the game.
	 *
	 * @return the Players Units represented as Strings
	 */
	public String saveUnits() {
		String s = "";
		for (Unit u : units)
			s += u.getTerritory().getName() + "\t" + u.isArmy() + "\t";
		return s;
	}
	
}
