package gameObjects;
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
	}
	
	/*
	 * Updates the Territory. Current state of this method is that if the mouse cursor is over this 
	 * Territory, then display the name.  See updateGame in the Canvas class for more info.
	 */
	public void update(){
		Canvas canvas = Canvas.getC();
		Color c = canvas.getCurrentColor();
		if (c.getRed()==colorKey.getRed() && c.getBlue()==colorKey.getBlue() && c.getGreen()==colorKey.getGreen()){
			if (canvas.getState() == Canvas.NORM || canvas.getState() == Canvas.TERR_SELECTED){
				canvas.setDisTerr(this);
				canvas.setState(Canvas.TERR_SELECTED);
				if (unit != null)
					canvas.resetOrder(this);
			}
			else if (canvas.getState() == Canvas.COMM_SELECTED){
				canvas.setOrder(this);
				if (canvas.getOrder().getCommand().equals("support"))
					canvas.setState(Canvas.SELECT_SUPPORT);
				else
					canvas.setState(Canvas.NORM);
			}
			else if (canvas.getState() == Canvas.SELECT_SUPPORT){
				if (unit != null){
					canvas.setSupport(unit);
					canvas.setState(Canvas.NORM);
				}
			}
		}
	}
	
	/*
	 * Draws the territory to the map
	 */
	public void eDraw(){
		terr.draw(this.getX(), this.getY());
	}
	
	public void uDraw(){
		if (unit != null)
			unit.draw(this.getX()+(this.getWidth()/16)-20, this.getY()+(this.getHeight()/2)-50);
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
	
	public boolean hasSC(){
		return supplyCenter;
	}
	
	public boolean isLand(){
		return land;
	}

	public Unit getUnit() {
		return unit;
	}
	
	public void removeUnit(){
		if (!hasSC())
			setOwner(NEUTRAL);
		unit = null;
	}

	public boolean isValidAttack(Territory t) {		
		//case one: land unit wanting to go to water. Check if there's a ship to convoy.
		if (unit.isLand() && !t.land) {
			if (t.getUnit() == null)
				return false;
		}
		 
		//case two: water unit wanting to go to a port-less land territory - exit immediately
		if (t.land && !unit.isLand() && !t.hasCoast)
			return false;
		
		return isAdjacent(t);
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

	public boolean isValidConvoy(Territory t, Unit convoyedUnit) {
		//need to think more bout this one
		return isAdjacent(t);
	}
	
	public boolean Rando(Territory t, Unit u){
		
		//From attack
		if (t.land && !unit.isLand()) {
			Order o = t.getUnit().getOrder();
			if (o == null || !o.getCommand().equals("convoy") || !t.isValidConvoy(this, unit))
				return false;
			else
				return true;
		}
		
		
		//From support
		Order o = u.getOrder();
		if (o == null || !(o.getCommand().equals("defend") || o.getCommand().equals("attack")))
			return false;
		else if (o.getCommand().equals("defend")
				&& !u.getTerritory().getName().equals(t.getName()))
			return false;
		else if (o.getCommand().equals("attack")
				&& !o.getTerr2().getName().equals(t.getName()))
			return false;
		
		return true;
	}
	
	public boolean isAdjacent(Territory terr){
		/*
		* private Arraylist<Territory> adjacentTerritories;
		* for (Territory t : adjacentTerritories){
		* 		if (t.getName().equals(terr.geName()))
		* 			return true;
		* }
		* return false;
		* 
		*/
		return true;
	}
	
}
