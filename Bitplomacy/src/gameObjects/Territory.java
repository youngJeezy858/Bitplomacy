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

	/** Used for drawing the Territory image */
	private EAnimation territoryImage;
	
	/** The sprite sheet to display each country's ownership  */
	private SpriteSheet ss;
	
	/** The name of the Territory */
	private String name;
	
	//TODO: Color keys have to go. Explore alpha more
	/** The color key. */
	
	/** Does this Territory have a supply center? */
	private boolean hasSupplyCenter;
	
	/** Is this a Land Territory (water otherwise) */
	private boolean isLand;
	
	/** Does this Territory have a coast? */
	private boolean hasCoast;
	
	/** The unit currently occupying this Territory */
	private Unit unit;
	
	/** The list of Territories that are adjacent to this one. */
	private ArrayList<String> adjacentTerritories;
	
	/** The current owner (Player) . */
	private int owner;

	/** The owner value of this Territory. Player's can only build Units in their home cities. */
	private int homeCity;
	
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
	 * @param color the color key
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
		for (String s : adjacentTerritories){
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
	 * Checks if the input color matches the colorkey. Used to see if the mouse is over this Territory. 
	 * @param my 
	 * @param mx 
	 *
	 * @param c the color to be checked
	 * @return true, if mouse is over this Territory
	 */
	public boolean isMouseOver(int mx, int my){
		mx = (int) (mx - getX());
		my = (int) (my - getY());
		if (ss.getColor(mx, my).a == 0)
			return false;
		else
			return true;
	}

	/**
	 * Checks is this Territory has a supply center
	 *
	 * @return true, if successful
	 */
	public boolean hasSC(){
		return hasSupplyCenter;
	}
	
	/**
	 * Sets the owner
	 *
	 * @param owner the new owner
	 */
	public void setOwner(int owner){
		this.owner = owner;
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
	}

	/**
	 * Draws the occupying Unit if there is one.
	 */
	public void uDraw(){
		if (unit != null)
			unit.draw(this.getX()+(this.getWidth()/16)-16, this.getY()+(this.getHeight()/2)-24);
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
	 * Checks if this Territory is a home city of the input owner's 
	 * @param owner the owner
	 * @return true, if successful 
	 */
	public boolean isHomeCity(int owner) {
		return owner == homeCity;
	}

}
