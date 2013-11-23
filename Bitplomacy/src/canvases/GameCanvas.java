package canvases;

import java.util.ArrayList;
import java.util.Scanner;

import gameObjects.Player;
import gameObjects.Territory;
import gameObjects.Unit;

import orders.Order;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;

import phases.BuildPhase;
import phases.Phase;
import phases.PlanningPhase;
import phases.RetreatPhase;


import com.erebos.engine.core.*;
import com.erebos.engine.graphics.EAnimation;

import commands.Commands;


/**
 * The Class Canvas.
 */
public class GameCanvas extends ECanvas{

	/** Singleton variable for the Canvas. */
	private static GameCanvas c = null;
	
	/** Used to draw images for territories and methods to manipulate a Territory. */
	private Territory[] territories;
	
	/** The teams as  - 7 total. */
	private Player[] players;
	
	/** Used to draw the individual commands and has abstract methods to execute commands. */
	private Commands[] commands;
	
	/** The display territory name. */
	private String displayTerritoryName;
	
	/** The display territory owner. */
	private String displayTerritoryOwner;

	/** The Territory currently selected */
	private Territory currTerritory;
	
	/** The curr order. */
	private Order currOrder;
	
	/** The curr turn. */
	private Phase currPhase;
	
	/** The font. */
	private TrueTypeFont font;
	
	/** The state. */
	private int state;
	
	/** The Constant NORM. */
	public static final int NORM = 0;
	
	/** The Constant COMM_SELECTED. */
	public static final int COMM_SELECTED = 1;
	
	/** The Constant SELECT_SUPPORT. */
	public static final int SELECT_UNIT = 2;
	
	/** The Constant SELECT_CONVOY_DESTINATION. */
	public static final int SELECT_CONVOY_DESTINATION = 3;
	
	/** The Constant FINISH_ADJUDICATION. */
	public static final int FINISH_ADJUDICATION = 4;

	/** The Constant WINNER. */
	public static final int WINNER = 5;

	
	/** MasterMap contains the color keys for the individual territories.  It is 
	 * referenced in the Territory 
	 * */
	private Image MasterMap;
	
	/** The Borders. */
	private Image Borders;
		
	/** SpriteSheets for Units. */
	private SpriteSheet landUnit;
	
	/** The water unit. */
	private SpriteSheet waterUnit;

	/** The winning player. */
	private Player winningPlayer;
	
	/**
	 * Instantiates a new canvas.
	 */
	private GameCanvas(){
		super(1);
		state = NORM;
	}
	
	/* (non-Javadoc)
	 * @see com.erebos.engine.core.ECanvas#eInit(org.newdawn.slick.GameContainer, com.erebos.engine.core.EGame)
	 */
	@Override
	public void eInit(GameContainer gc, EGame eg) {		
		
		displayTerritoryName = "";
		displayTerritoryOwner = "";
	    font = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 20), true);
		
		players = new Player[7];
		players[0] = new Player("England");
		players[1] = new Player("Austria-Hungary");
		players[2] = new Player("Italy");
		players[3] = new Player("Turkey");
		players[4] = new Player("France");
		players[5] = new Player("Russia");
		players[6] = new Player("Germany");
		
		MasterMap=EAnimation.loadImage("/images/MasterMap.png");
		Borders=EAnimation.loadImage("/images/Borders.png");

		Image temp = EAnimation.loadImage("/images/LandUnitUpdated.png");
		landUnit = new SpriteSheet(temp, temp.getWidth()/7, temp.getHeight());
		temp = EAnimation.loadImage("/images/WaterUnitUpdated.png");
		waterUnit = new SpriteSheet(temp, temp.getWidth()/7, temp.getHeight());
		
		currPhase = new PlanningPhase("Spring/Summer", 1900);
		
		//define territories
		Scanner sc = new Scanner(GameCanvas.class.getResourceAsStream("/docs/terr.csv"));
		sc.nextLine();
		int lines = 0;
		while (sc.hasNextLine()){
			lines++;
			sc.nextLine();
		}
		sc.close();
		sc = new Scanner(GameCanvas.class.getResourceAsStream("/docs/terr.csv"));
		sc.nextLine();
		int i = 0;
		territories = new Territory[lines];
		while (sc.hasNextLine()){
			String s = sc.nextLine();
			String s2[] = s.split("\t");
			SpriteSheet ss = SSFactory(s2[9]);
			territories[i] = new Territory(ss, s2[0], new Boolean(s2[1].trim()), 
					new Boolean(s2[2].trim()), new Boolean(s2[3].trim()), 
					new Color(new Integer(s2[4]), new Integer(s2[5]), new Integer(s2[6])));
			territories[i].setX(new Integer(s2[7]));
			territories[i].setY(new Integer(s2[8]));
			i++;
		}
		sc.close();
		
		//define adjacent territories
		sc = new Scanner(GameCanvas.class.getResourceAsStream("/docs/adjacentTerr.csv"));
		sc.nextLine();
		while (sc.hasNextLine()){
			String s[] = sc.nextLine().split("\t");
			Territory t = getTerritory(s[0]);
			for (i = 1; i < s.length; i++)
				t.addAdjacent(s[i]);
		}	
		sc.close();
		
	    setBoard();
	    adjustNumSC();
	}

	/**
	 * Sets up the play area for a 7 teams.  Specifically it
	 * sets ownership for territories and generate units for each 
	 * team. 
	 */
	private void setBoard() {
		createStartingUnit(getTerritory("Edinburgh"), waterUnit, false, players[Territory.ENGLAND-1]);
		createStartingUnit(getTerritory("Liverpool"), landUnit, true, players[Territory.ENGLAND-1]);
		createStartingUnit(getTerritory("London"), waterUnit, false, players[Territory.ENGLAND-1]);

		createStartingUnit(getTerritory("Vienna"), landUnit, true, players[Territory.AUSTRIA_HUNGARY-1]);
		createStartingUnit(getTerritory("Budapest"), landUnit, true, players[Territory.AUSTRIA_HUNGARY-1]);
		createStartingUnit(getTerritory("Trieste"), waterUnit, false, players[Territory.AUSTRIA_HUNGARY-1]);

		createStartingUnit(getTerritory("Paris"), landUnit, true, players[Territory.FRANCE-1]);
		createStartingUnit(getTerritory("Brest"), waterUnit, false, players[Territory.FRANCE-1]);
		createStartingUnit(getTerritory("Marseilles"), landUnit, true, players[Territory.FRANCE-1]);

		createStartingUnit(getTerritory("Berlin"), landUnit, true, players[Territory.GERMANY-1]);
		createStartingUnit(getTerritory("Kiel"), waterUnit, false, players[Territory.GERMANY-1]);
		createStartingUnit(getTerritory("Munich"), landUnit, true, players[Territory.GERMANY-1]);

		createStartingUnit(getTerritory("Venice"), landUnit, true, players[Territory.ITALY-1]);
		createStartingUnit(getTerritory("Rome"), landUnit, true, players[Territory.ITALY-1]);
		createStartingUnit(getTerritory("Naples"), waterUnit, false, players[Territory.ITALY-1]);

		createStartingUnit(getTerritory("St. Petersburgh"), waterUnit, false, players[Territory.RUSSIA-1]);
		createStartingUnit(getTerritory("Moscow"), landUnit, true, players[Territory.RUSSIA-1]);
		createStartingUnit(getTerritory("Sevastopal"), waterUnit, false, players[Territory.RUSSIA-1]);
		createStartingUnit(getTerritory("Warsaw"), landUnit, true, players[Territory.RUSSIA-1]);

		createStartingUnit(getTerritory("Ankara"), waterUnit, false, players[Territory.TURKEY-1]);
		createStartingUnit(getTerritory("Constantinople"), landUnit, true, players[Territory.TURKEY-1]);
		createStartingUnit(getTerritory("Smyrna"),landUnit, true, players[Territory.TURKEY-1]);
	}
	
	/**
	 * Creates the unit.
	 *
	 * @param t the t
	 * @param ss the ss
	 * @param isLand the is land
	 * @param p the p
	 */
	private void createStartingUnit(Territory t, SpriteSheet ss, boolean isLand, Player p){
		t.setOwner(p.getOwnerKey());
		t.setHomeCity(p.getOwnerKey());
		Unit temp = new Unit(ss, p.getOwnerKey() - 1, isLand, t);
		t.setUnit(temp);
		p.addUnit(t.getUnit());
	}
	
	/**
	 * Creates the unit.
	 *
	 * @param t the t
	 * @param isLand the is land
	 * @param p the p
	 */
	public void createUnit(Territory t, boolean isLand, Player p){
		Unit temp = null;
		if (isLand)
			temp = new Unit(landUnit, p.getOwnerKey() - 1, isLand, t);
		else
			temp = new Unit(waterUnit, p.getOwnerKey() - 1, isLand, t);
		t.setUnit(temp);
		p.addUnit(t.getUnit());
	}

	/**
	 * Used to generate a SpriteSheet of a territory.  Assumes that your SpriteSheet has 8 sprites.
	 * 
	 * @param location the location
	 * @return the sprite sheet
	 */
	private SpriteSheet SSFactory(String location){
		Image temp = EAnimation.loadImage(location);
		SpriteSheet ss = new SpriteSheet(temp, temp.getWidth()/8, temp.getHeight());
		return ss;
	}

	/* (non-Javadoc)
	 * @see com.erebos.engine.core.ECanvas#eRender(org.newdawn.slick.GameContainer, com.erebos.engine.core.EGame, org.newdawn.slick.Graphics)
	 */
	public void eRender(GameContainer gc, EGame eg, Graphics g) {
		
		if (state != WINNER) {
			// draw masterMap for color keys
			g.drawImage(MasterMap, 0, 0);

			g.setColor(Color.gray);
			g.fillRect(1106, 0, 1400 - 1126, gc.getHeight());

			g.setColor(Color.black);
			g.drawString("Country:", 1150, 20);
			g.drawString("Owner:", 1150, 70);

			g.drawString("SUPPLY CENTER TOTALS", 1145, 120);
			int i = 140;
			for (Player p : players) {
				g.drawString(p.getName() + ": " + p.getSupplyCenterCount(), 1145, i);
				i = i + 20;
			}

			g.setColor(Color.blue);
			if (currOrder != null)
				g.drawString(currOrder.toString(), 1130, 290);
			g.setColor(Color.black);

			g.drawString(displayTerritoryName, 1145, 40);
			g.drawString(displayTerritoryOwner, 1145, 90);

			for (Commands c : commands)
				c.draw();
			for (Territory t : territories)
				t.eDraw();
			g.drawImage(Borders, 0, 0);
			for (Territory t : territories)
				t.uDraw();

			g.setFont(font);
			g.drawString(currPhase.toString(), 10, 10);
		}
		else{
			g.setColor(Color.green);
			g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
			g.setColor(Color.black);
			g.setFont(font);
			g.drawString(winningPlayer.getName(), 30, 30);
			g.drawString("WINS!!!", 100, 130);
		}

	}

	/* (non-Javadoc)
	 * @see com.erebos.engine.core.ECanvas#eUpdate(org.newdawn.slick.GameContainer, com.erebos.engine.core.EGame, int)
	 */
	@Override
	public void eUpdate(GameContainer gc, EGame eg, int delta) {			
		
		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			
			int mx = Mouse.getX();
			int my = Math.abs(Mouse.getY() - 831);
			
			for (Territory t : territories) {
				if (mx >= t.getX() && mx <= t.getWidth() + t.getX()
						&& my >= t.getY() && my <= t.getHeight() + t.getY() &&
						t.isMouseOver(getCurrentColor())){
					updateTerritory(t);
					return;
				}
			}
			
			for (Commands c : commands) {
				if (mx >= c.getX() && mx <= c.getWidth() + c.getX()
						&& my >= c.getY() && my <= c.getHeight() + c.getY()){
					c.update();
					return;
				}
			}
		}
	}

	/**
	 * Adds the order.
	 *
	 * @param o the o
	 */
	public void addOrder(Order o){
		currPhase.addOrder(o);
	}

	/**
	 * Adjust num sc.
	 */
	public void adjustNumSC() {
		int i;
		for (Territory t : territories){
			if (t.getUnit() != null)
				t.setOwner(t.getUnit().getOwner());
		}
		for (Player p : players){
			i = 0;
	    	for (Territory t : territories){
	    		if (t.getOwner() == p.getOwnerKey() && t.hasSC())
	    			i++;
	    	}
			p.setSupplyCenterCount(i);
		}
	}

	/**
	 * Adjust turn.
	 */
	private void adjustTurn() {
		
		String s = currPhase.getSeason();
		int i = currPhase.getYear();
		
		if (s.equals("Spring/Summer"))
			currPhase = new RetreatPhase("Summer Retreats", i, ((PlanningPhase)currPhase).getRetreatingUnits());
		
		else if (s.equals("Summer Retreats"))
			currPhase = new PlanningPhase("Fall/Winter", i);
		
		else if (s.equals("Fall/Winter"))
			currPhase = new RetreatPhase("Winter Retreats", i, ((PlanningPhase)currPhase).getRetreatingUnits());

		else if (s.equals("Winter Retreats")){
			currPhase = new BuildPhase("Build/Remove", i);
			adjustNumSC();
			for (Player p : players){
				if (p.getSupplyCenterCount() >= 24){
					state = WINNER;
					winningPlayer = p;
					font = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 40), true);
				}
			}
		}
		
		else{
			i++;
			currPhase = new PlanningPhase("Spring/Summer", i);
		}
	}

	/**
	 * Gets the c.
	 *
	 * @return the c
	 */
	public static GameCanvas getC() {
		if (c == null)
			c = new GameCanvas();
		return c;
	}

	/**
	 * Gets the Color from the current mouse location.  Used to access color keys.
	 * 
	 * @return the current color
	 */
	public Color getCurrentColor(){
		return new Color(MasterMap.getColor(Mouse.getX(), Math.abs(Mouse.getY()-831)));
	}
	
	
	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
	public Order getOrder() {
		return currOrder;
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
	 * Gets the t.
	 *
	 * @param name the name
	 * @return the t
	 */
	private Territory getTerritory(String name){
		for (Territory t : territories){
			if (t.getName().equals(name))
				return t;
		}
		return null;
	}

	/**
	 * Sets the command.
	 *
	 * @param s the new command
	 */
	public void setCommand(String s){		
		if (currOrder != null){
			currOrder.setCommand(s);
			if (s.equals("defend") || s.equals("disband") || s.contains("build"))
				state = NORM;
			else
				state = COMM_SELECTED;
		}
	}

	/**
	 * Submit.
	 */
	public void submit() {
		currOrder = null;
		for (Player p : players)
			p.executeOrders();
		currPhase.adjudicate();
		for (Player p : players)
			p.resetOrders();
		adjustTurn();
		if (winningPlayer == null)
			state = NORM;
	}

	/**
	 * Sets the state.
	 *
	 * @param s the new state
	 */
	public void setState(int s){
		state = s;
	}
	
	/**
	 * Update territory.
	 *
	 * @param t the t
	 */
	public void updateTerritory(Territory t) {

		displayTerritoryName = t.getName();
		displayTerritoryOwner = t.getOwnerName();
		
		if (state == NORM){
			if (t.getUnit() != null || currPhase.getSeason().equals("Build/Remove"))
				currTerritory = t;
		}
		
		else if (state == COMM_SELECTED){			
			currOrder.setDestinationTerritory(t);
			if (currOrder.getCommand().equals("attack") || currOrder.getCommand().equals("move") 
					|| currOrder.getCommand().equals("support"))
				state = SELECT_UNIT;
			else if (currOrder.getCommand().equals("convoy"))
				state = SELECT_CONVOY_DESTINATION;
		}
		
		else if (state == SELECT_UNIT){
			if (t.getUnit() != null)
				currOrder.addAdditionalTerritory(t); 
		}
		
		else if (state == SELECT_CONVOY_DESTINATION)
			currOrder.addAdditionalTerritory(t);
	}

	/**
	 * Finalize order.
	 */
	public void finalizeOrder() {
		if (currOrder != null){
			if (!currPhase.getSeason().equals("Build/Remove") || currOrder.getCommand().equals("disband"))
				currOrder.setUnitOrder();
			else 
				currPhase.addBuildOrder(currOrder);
		}
		currOrder = null;
		state = NORM;
	}

	/**
	 * Discard order.
	 */
	public void discardOrder() {
		currOrder = null;
		state = NORM;
	}

	/**
	 * Removes the unit.
	 *
	 * @param unit the unit
	 */
	public void removeUnit(Unit unit) {
		players[unit.getOwner()-1].removeUnit(unit.getTerritory());
		if (!unit.getTerritory().hasSC())
			unit.getTerritory().setOwner(Territory.NEUTRAL);
		unit.getTerritory().removeUnit();
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public Player[] getPlayers() {
		return players;
	}

	public void setOrder(Order o) {
		currOrder = o;
	}

	public Territory getCurrentTerritory() {
		return currTerritory;
	}

	public void setCommands(Commands[] planningCommands) {
		commands = planningCommands;
	}

	public PlanningPhase getPhase() {
		return (PlanningPhase) currPhase;
	}

}