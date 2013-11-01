package gameObjects;
import java.util.ArrayList;

import gui.Canvas;

import org.newdawn.slick.Color;
import org.newdawn.slick.SpriteSheet;
import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

public class Territory extends ImageEntity {

	private EAnimation terr;
	private SpriteSheet ss;
	private String name;
	private Color colorKey;
	private boolean supplyCenter;
	private boolean land;
	private boolean hasCoast;
	private Unit unit;
	private ArrayList<String> adjacentTerritories;
	
	private int owner;
	public static final int NEUTRAL = 0;
	public static final int ENGLAND = 1;
	public static final int AUSTRIA_HUNGARY = 2;
	public static final int ITALY = 3;
	public static final int TURKEY = 4;
	public static final int FRANCE = 5;
	public static final int RUSSIA = 6;
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
	
	public void addAdjacent(String t){
		adjacentTerritories.add(t);
	}
	
	/*
	 * Draws the territory to the map
	 */
	public void eDraw(){
		terr.draw(this.getX(), this.getY());
	}
	
	public boolean equals(Territory t){
		if (t.getName().equals(name))
			return true;
		else
			return false;
	}

	public String getName(){
		return name;
	}
	
	public int getOwner(){
		return owner;
	}
	
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
	
	public Unit getUnit() {
		return unit;
	}

	public boolean isAdjacent(Territory terr){
		for (String s : adjacentTerritories){
			if (terr.getName().equals(s))
		 		return true;
		 }
		 return false;
	}

	public boolean isLand(){
		return land;
	}

	public boolean isValidAttack(Territory t, ArrayList<Unit> convoyUnits) {		
	
		if (unit.isLand() && !t.land)
			return false;
		if (t.land && !unit.isLand() && !t.hasCoast)
			return false;
		if (!isAdjacent(t) && unit.isLand() && convoyUnits.size() > 0)
			return true;
		return isAdjacent(t);
	
	}

	public boolean isValidConvoy(Territory convoyStart, Territory convoyDestination) {
		//case one: water unit wants to convoy
		if (!unit.isLand()){
			if (convoyStart.getUnit() == null)
				return false;
			else if (!convoyDestination.isLand() && convoyDestination.getUnit() == null)
				return false;
			else if (!convoyStart.isLand() && convoyDestination.getUnit() == null)
				return false;
		}
		else
			return false;
		
		return isAdjacent(convoyDestination) && isAdjacent(convoyStart);
	}

	public boolean isValidSupport(Territory t, Unit u) {
		
		// case one: land unit wanting to support water -
		// exit immediately
		if (!t.land && unit.isLand()) 
			return false;
	
		// case two: water unit wanting to supports a port-less land territory -
		// exit immediately
		if (t.land && !unit.isLand() && !t.hasCoast)
			return false;
		
		//case three: check if the territory is adjacent to this unit
		return isAdjacent(t) && u.getTerritory().isAdjacent(t);
	
	}

	public boolean hasSC(){
		return supplyCenter;
	}
	
	/*
	 * Sets the owner of the Territory.  See class fields for int codes of each team.
	 * @param owner -> the owner of the Territory represented as an int value.
	 */
	public void setOwner(int owner){
		this.owner = owner;
		terr = new EAnimation(ss.getSprite(owner, 0));
	}

	public void setUnit(Unit u){
		unit = u;
		setOwner(u.getOwner());
	}

	public void removeUnit(){
		if (!hasSC())
			setOwner(NEUTRAL);
		unit = null;
	}

	public void uDraw(){
		if (unit != null)
			unit.draw(this.getX()+(this.getWidth()/16)-20, this.getY()+(this.getHeight()/2)-50);
	}

	/*
	 * Updates the Territory. Current state of this method is that if the mouse cursor is over this 
	 * Territory, then display the name.  See updateGame in the Canvas class for more info.
	 */
	public void update(){
		Color c = Canvas.getC().getCurrentColor();
		if (c.getRed()==colorKey.getRed() && c.getBlue()==colorKey.getBlue() && c.getGreen()==colorKey.getGreen())
			Canvas.getC().updateTerritory(this);
	}
	
}
