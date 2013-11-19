package gameObjects;
import gui.Canvas;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.SpriteSheet;
import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

// TODO: Auto-generated Javadoc
/**
 * The Class Territory.
 */
public class Territory extends ImageEntity {

	/** The terr. */
	private EAnimation terr;
	
	/** The ss. */
	private SpriteSheet ss;
	
	/** The name. */
	private String name;
	
	/** The color key. */
	private Color colorKey;
	
	/** The supply center. */
	private boolean supplyCenter;
	
	/** The land. */
	private boolean land;
	
	/** The has coast. */
	private boolean hasCoast;
	
	/** The unit. */
	private Unit unit;
	
	/** The adjacent territories. */
	private ArrayList<String> adjacentTerritories;
	
	/** The owner. */
	private int owner;
	
	/** The Constant NEUTRAL. */
	public static final int NEUTRAL = 0;
	
	/** The Constant ENGLAND. */
	public static final int ENGLAND = 1;
	
	/** The Constant AUSTRIA_HUNGARY. */
	public static final int AUSTRIA_HUNGARY = 2;
	
	/** The Constant ITALY. */
	public static final int ITALY = 3;
	
	/** The Constant TURKEY. */
	public static final int TURKEY = 4;
	
	/** The Constant FRANCE. */
	public static final int FRANCE = 5;
	
	/** The Constant RUSSIA. */
	public static final int RUSSIA = 6;
	
	/** The Constant GERMANY. */
	public static final int GERMANY = 7;
	

	/*
	 * Constructor method for the Territory class.
	 * 
	 * @param terr -> SpriteSheet image of the Territory
	 * @param name -> the name of a Territory as a String
	 * @param isLand -> boolean value to determine if a Territory is land or water
	 * @param hasSC -> boolean value to determine if a Territory has a supply center or not
	 * @param color -> the color that acts as the Territory's key when referencing the master map
	 */
	/**
	 * Instantiates a new territory.
	 *
	 * @param terr the terr
	 * @param name the name
	 * @param isLand the is land
	 * @param hasSC the has sc
	 * @param hasCoast the has coast
	 * @param color the color
	 */
	public Territory(SpriteSheet terr, String name, boolean isLand, boolean hasSC, boolean hasCoast, Color color){
		super(terr);
		ss = terr;
		owner = NEUTRAL;
		this.terr = new EAnimation(terr.getSprite(owner, 0));
		this.name = name;
		this.hasCoast = hasCoast;
		colorKey = color;
		supplyCenter = hasSC;
		land = isLand;
		unit = null;
		adjacentTerritories = new ArrayList<String>();
	}
	
	/**
	 * Adds the adjacent.
	 *
	 * @param t the t
	 */
	public void addAdjacent(String t){
		adjacentTerritories.add(t);
	}
	
	/*
	 * Draws the territory to the map
	 */
	/**
	 * E draw.
	 */
	public void eDraw(){
		terr.draw(this.getX(), this.getY());
	}
	
	/**
	 * Equals.
	 *
	 * @param t the t
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
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public int getOwner(){
		return owner;
	}
	
	/**
	 * Gets the owner name.
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
	 * Gets the owner name.
	 *
	 * @param owner the owner
	 * @return the owner name
	 */
	public static String getOwnerName(int owner){
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
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Checks if is adjacent.
	 *
	 * @param terr the terr
	 * @return true, if is adjacent
	 */
	public boolean isAdjacent(Territory terr){
		for (String s : adjacentTerritories){
			if (terr.getName().equals(s)){
		 		return true;
			}
		 }
		 return false;
	}

	/**
	 * Checks if is land.
	 *
	 * @return true, if is land
	 */
	public boolean isLand(){
		return land;
	}

	/**
	 * Checks if is mouse over.
	 *
	 * @param c the c
	 * @return true, if is mouse over
	 */
	public boolean isMouseOver(Color c){
		if (c.getRed()==colorKey.getRed() && c.getBlue()==colorKey.getBlue() && c.getGreen()==colorKey.getGreen())
			return true;
		return false;
	}

	/**
	 * Checks if is valid attack.
	 *
	 * @param t the t
	 * @param convoyUnits the convoy units
	 * @return true, if is valid attack
	 */
	public boolean isValidAttack(Territory t, ArrayList<Unit> convoyUnits) {		
		if (unit.isLand() && !t.land)
			return false;
		else if (t.land && !unit.isLand() && !t.hasCoast)
			return false;
		else if (t.getUnit() != null && t.getUnit().getOwner() == unit.getOwner())
			return false;
		else if (isAdjacent(t) && convoyUnits.size() == 0)
			return true;
		else if (convoyUnits.size() > 0)
			return true;
		return false;
	}

	/**
	 * Checks for sc.
	 *
	 * @return true, if successful
	 */
	public boolean hasSC(){
		return supplyCenter;
	}
	
	/*
	 * Sets the owner of the Territory.  See class fields for int codes of each team.
	 * @param owner -> the owner of the Territory represented as an int value.
	 */
	/**
	 * Sets the owner.
	 *
	 * @param owner the new owner
	 */
	public void setOwner(int owner){
		this.owner = owner;
		terr = new EAnimation(ss.getSprite(owner, 0));
	}

	/**
	 * Sets the unit.
	 *
	 * @param u the new unit
	 */
	public void setUnit(Unit u){
		unit = u;
		setOwner(u.getOwner());
	}

	/**
	 * Removes the unit.
	 */
	public void removeUnit(){
		if (!hasSC())
			setOwner(NEUTRAL);
		unit = null;
	}

	/**
	 * U draw.
	 */
	public void uDraw(){
		if (unit != null)
			unit.draw(this.getX()+(this.getWidth()/16)-16, this.getY()+(this.getHeight()/2)-24);
	}

	/**
	 * Find vacant.
	 *
	 * @return the territory
	 */
	public Territory findVacant() {
		for (String s : adjacentTerritories){
			Territory t = Canvas.getC().getT(s);
			if (t.getUnit() == null){
				if (unit.isLand() && t.land)
					return t;
				else if (!unit.isLand() && (t.hasCoast || !t.land))
					return t;
			}
		}
		return null;
	}

	/**
	 * Checks for coast.
	 *
	 * @return true, if successful
	 */
	public boolean hasCoast() {
		return hasCoast;
	}
	
}
