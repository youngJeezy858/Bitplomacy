package gameObjects;
import java.util.ArrayList;

import org.newdawn.slick.SpriteSheet;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

/**
 * The Class Territory contains an image for drawing the Territory to the screen and
 * contains objects that a Territory encapsulates. 
 */
public class Territory extends ImageEntity {

	/** Used for drawing the Territory image. */
	private EAnimation territoryImage;
	
	/** The sprite sheet to display each country's ownership. */
	private SpriteSheet ss;
	
	/** The name of the Territory. */
	private String name;
	
	/** Does this Territory have a supply center?. */
	private boolean hasSupplyCenter;
	
	/** Is this a Land Territory (water otherwise). */
	private boolean isLand;
	
	/** Does this Territory have a coast?. */
	private boolean hasCoast;
	
	/** The unit currently occupying this Territory. */
	private Unit unit;
	
	/** The list of Territories that are adjacent to this one. */
	private ArrayList<String> adjacentTerritories;
	
	/** The list of Territories that are adjacent the south coast of this one. */
	private ArrayList<String> adjacentSCTerritories;
	
	/** The list of Territories that are adjacent the north coast of this one. */
	private ArrayList<String> adjacentNCTerritories;
	
	/** The current owner (Player) . */
	private int owner;

	/** The owner value of this Territory. Player's can only build Units in their home cities. */
	private int homeCity;

	/** The x coordinate to draw the Unit. */
	private int unitX;

	/** The y coordinate to draw the Unit. */
	private int unitY;

	/** The x coordinate to draw the Unit in the south coast. */
	private int scUnitX;

	/** The y coordinate to draw the Unit in the south coast. */
	private int scUnitY;

	/** The x coordinate to draw the Unit in the north coast. */
	private int ncUnitX;

	/** The y coordinate to draw the Unit in the north coast. */
	private int ncUnitY;
	
	/** Is the Unit in the south coast? */
	private boolean isInSC;
	
	/** Is the Unit in the north coast? */
	private boolean isInNC;
	
	/** To set the owner as NEUTRAL. */
	public static final int NEUTRAL = 0;
	
	/** To set the owner as ENGLAND. */
	public static final int ENGLAND = 1;
	
	/** To set the owner as AUSTRIA_HUNGARY. */
	public static final int AUSTRIA_HUNGARY = 2;
	
	/** To set the owner as ITALY. */
	public static final int ITALY = 3;
	
	/** To set the owner as TURKEY. */
	public static final int TURKEY = 4;
	
	/** To set the owner as FRANCE. */
	public static final int FRANCE = 5;
	
	/** To set the owner as RUSSIA. */
	public static final int RUSSIA = 6;
	
	/** To set the owner as GERMANY. */
	public static final int GERMANY = 7;
	
	
	/**
	 * Instantiates a new territory. By default it sets the Territory as NEUTRAL.
	 *
	 * @param ss the sprite sheet
	 * @param name the name
	 * @param isLand true, if land
	 * @param hasSC true, if Territory has supply center
	 * @param hasCoast true, if Territory has coast
	 */
	public Territory(SpriteSheet ss, String name, boolean isLand, boolean hasSC, boolean hasCoast){
		super(ss);
		this.ss = ss;
		owner = NEUTRAL;
		this.territoryImage = new EAnimation(ss.getSprite(owner, 0));
		this.name = name;
		this.hasCoast = hasCoast;
		hasSupplyCenter = hasSC;
		this.isLand = isLand;
		unit = null;
		adjacentTerritories = new ArrayList<String>();
		homeCity = 0;
		isInNC = false;
		isInSC = false;
	}
	
	/**
	 * Adds an adjacent Territory to the list. Uses Strings since
	 * they are less intensive than instantiating new Territories in
	 * every Territory.
	 *
	 * @param t the Territory as a String
	 */
	public void addAdjacent(String t){
		adjacentTerritories.add(t);
	}
	
	/**
	 * Draws the Territory to the screen.
	 */
	public void eDraw(){
		territoryImage.draw(this.getX(), this.getY());
	}
	
	/**
	 * Checks if a Territory equals another. Specifically it checks
	 * the names of each Territory.
	 *
	 * @param t the Territory 
	 * @return true, if successful
	 */
	public boolean equals(Territory t){
		if (t.getName().equals(name))
			return true;
		else
			return false;
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
	 * Gets the owner as an int.
	 *
	 * @return the owner
	 */
	public int getOwner(){
		return owner;
	}
	
	/**
	 * Gets the owner as a String.
	 *
	 * @return the owner name
	 */
	public String getOwnerName(){
		String s = null;
		if (owner == 0)
			s = "Neutral";
		else if (owner == 1)
			s = "England";
		else if (owner == 2)
			s = "Austria-Hungary";
		else if (owner == 3)
			s = "Italy";
		else if (owner == 4)
			s = "Turkey";
		else if (owner == 5)
			s = "France";
		else if (owner == 6)
			s = "Russia";
		else if (owner == 7)
			s = "Germany";
		return s;
	}
	
	/**
	 * Gets the unit occupying the Territory. 
	 *
	 * @return the unit, null if none occupying
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Checks if another Territory is adjacent.
	 *
	 * @param t the Territory
	 * @return true, if it is adjacent
	 */
	public boolean isAdjacent(Territory t){
		if (isInNC)
			return isAdjacentNC(t);
		else if (isInSC)
			return isAdjacentSC(t);
		
		else {
			for (String s : adjacentTerritories) {
				if (t.getName().equals(s)) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * Checks if another Territory is adjacent to the north coast.
	 *
	 * @param t the Territory
	 * @return true, if it is adjacent to the north coast
	 */
	public boolean isAdjacentNC(Territory t) {
		if (adjacentNCTerritories == null)
			return false;
		for (String s : adjacentNCTerritories){
			if (t.getName().equals(s)){
		 		return true;
			}
		 }
		return false;
	}

	/**
	 * Checks if another Territory is adjacent to the south coast.
	 *
	 * @param t the Territory
	 * @return true, if it is adjacent to the south coast
	 */
	public boolean isAdjacentSC(Territory t) {
		if (adjacentSCTerritories == null)
			return false;
		for (String s : adjacentSCTerritories){
			if (t.getName().equals(s)){
		 		return true;
			}
		 }
		return false;
	}

	/**
	 * Checks if this is a land Territory.
	 *
	 * @return true, if land, false if water
	 */
	public boolean isLand(){
		return isLand;
	}

	/**
	 * Checks if the mouse is over this Territory.
	 *
	 * @param mx the mx
	 * @param my the my
	 * @return true, if mouse is over this Territory
	 */
	public boolean isMouseOver(int mx, int my){
		mx = (int) (mx - this.getX());
		my = (int) (my - this.getY());
		if (ss.getColor(mx, my).a == 0)
			return false;
		else
			return true;
	}

	/**
	 * Checks if this Territory has a supply center.
	 *
	 * @return true, if successful
	 */
	public boolean hasSC(){
		return hasSupplyCenter;
	}
	
	/**
	 * Sets the owner.
	 *
	 * @param owner the new owner
	 */
	public void setOwner(int owner){
		this.owner = owner;
		if (isLand)
			territoryImage = new EAnimation(ss.getSprite(owner, 0));
	}

	/**
	 * Sets the unit occupying this Territory.
	 *
	 * @param u the new unit
	 */
	public void setUnit(Unit u){
		unit = u;
		if (!hasSupplyCenter)
			setOwner(u.getOwner());
	}

	/**
	 * Removes the occupying unit. Will set the owner of the Territory to
	 * NEUTRAL if it does not have a supply center.
	 */
	public void removeUnit(){
		if (!hasSupplyCenter)
			setOwner(NEUTRAL);
		unit = null;
		isInNC = false;
		isInSC = false;
	}

	/**
	 * Draws the occupying Unit if there is one.
	 */
	public void uDraw(){
		if (unit != null){
			if (isInNC)
				unit.draw(this.getX()+ncUnitX, this.getY()+ncUnitY);
			else if (isInSC)
				unit.draw(this.getX()+scUnitX, this.getY()+scUnitY);
			else
				unit.draw(this.getX()+unitX, this.getY()+unitY);
		}
	}

	/**
	 * Checks if there is a coast on this Territory.
	 *
	 * @return true, if successful
	 */
	public boolean hasCoast() {
		return hasCoast;
	}

	/**
	 * Sets this Territory as a home city for a player.
	 *
	 * @param owner the owner
	 */
	public void setHomeCity(int owner) {
		homeCity = owner;
	}

	/**
	 * Checks if this Territory is a home city of the input owner's.
	 *
	 * @param owner the owner
	 * @return true, if successful
	 */
	public boolean isHomeCity(int owner) {
		return owner == homeCity;
	}

	/**
	 * Sets the unit's x coordinate for drawing it to the screen.
	 *
	 * @param i the new unit x coordinate
	 */
	public void setUnitX(int i) {
		unitX = i;
	}

	/**
	 * Sets the unit's y coordinate for drawing it to the screen.
	 *
	 * @param i the new unit y coordinate
	 */
	public void setUnitY(int i) {
		unitY = i;
	}

	/**
	 * Adds an adjacent south coast Territory.
	 *
	 * @param s the Territory name
	 */
	public void addSCAdjacent(String s) {
		if (adjacentSCTerritories == null)
			adjacentSCTerritories = new ArrayList<String>();
		adjacentSCTerritories.add(s);
	}
	
	/**
	 * Adds an adjacent north coast Territory.
	 *
	 * @param s the Territory name
	 */
	public void addNCAdjacent(String s) {
		if (adjacentNCTerritories == null)
			adjacentNCTerritories = new ArrayList<String>();
		adjacentNCTerritories.add(s);
	}

	/**
	 * Sets the unit's x coordinate for drawing it to the south coast.
	 *
	 * @param i the new south coast x coordinate
	 */
	public void setSCX(int i) {
		scUnitX = i;
	}

	/**
	 * Sets the unit's y coordinate for drawing it to the south coast.
	 *
	 * @param i the new south coast y coordinate
	 */
	public void setSCY(int i) {
		scUnitY = i;
	}

	/**
	 * Sets the unit's x coordinate for drawing it to the north coast.
	 *
	 * @param i the new north coast x coordinate
	 */
	public void setNCX(int i) {
		ncUnitX = i;
	}
	
	/**
	 * Sets the unit's y coordinate for drawing it to the north coast.
	 *
	 * @param i the new north coast y coordinate
	 */
	public void setNCY(int i) {
		ncUnitY = i;
	}

	/**
	 * Checks if this Territory has multiple coasts.
	 *
	 * @return true, if successful
	 */
	public boolean hasCoasts() {
		return adjacentNCTerritories != null;
	}

	/**
	 * Places/removes the Unit in the south coast.
	 *
	 * @param b true, if placing
	 */
	public void setSC(boolean b) {
		isInSC = b;
	}
	
	/**
	 * Places/removes the Unit in the north coast.
	 *
	 * @param b true, if placing
	 */
	public void setNC(boolean b) {
		isInNC = b;
	}

	/**
	 * Checks if the Unit is in the south coast.
	 *
	 * @return true, if is in the south coast
	 */
	public boolean isInSC() {
		return isInSC;
	}
	
	/**
	 * Checks if the Unit is in the north coast.
	 *
	 * @return true, if is in the north coast
	 */
	public boolean isInNC() {
		return isInNC;
	}
}
