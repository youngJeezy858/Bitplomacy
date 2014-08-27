package canvases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import gameObjects.Player;
import gameObjects.Territory;
import gameObjects.Unit;
import guis.ChooseAllyGUI;
import guis.CommandGUI;
import guis.PauseMenuAdjudicateGUI;
import guis.PauseMenuGUI;
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
import buttons.Button;
import buttons.PauseButton;

import com.erebos.engine.core.*;
import com.erebos.engine.graphics.EAnimation;

import commands.SubmitCommand;

/**
 * Used to display and update the map and commands while playing the game. 
 */
public class GameCanvas extends ECanvas{

	/** Singleton variable for the Canvas. */
	private static GameCanvas c = null;
	
	/** Used to draw images for territories and methods to manipulate a Territory. */
	private Territory[] territories;
	
	/** The teams - 7 total. */
	private Player[] players;
	
	/** The Territory currently selected. */
	private Territory currTerritory;
	
	/** The current Order. */
	private Order currOrder;
	
	/** The current Turn. */
	private Phase currPhase;
	
	/** The medium font. */
	private TrueTypeFont mediumFont;
	
	/** The small font. */
	private TrueTypeFont smallFont;
	
	/** The current state of the Game. See static variables for descriptions of each. */
	private int state;
	
	/** The normal state of the game. */
	public static final int NORM = 0;
	
	/** The state for when a command is selected. */
	public static final int COMM_SELECTED = 1;
	
	/** The state for selecting a Unit. */
	public static final int SELECT_UNIT = 2;
	
	/** The state for selecting a Convoy Order's destination. */
	public static final int SELECT_CONVOY_DESTINATION = 3;
	
	/** The state for choosing allies. */
	public static final int CHOOSE_ALLIES = 4;

	/** The state for when a winner has been determined. */
	public static final int WINNER = 5;

	/** The state for determining a combination victory. */
	public static final int ADJUDICATE_ALLIES = 6;

	/** The state for selecting a command. */
	public static final int SELECT_COMM = 7;

	/** The state for when allies need to be adjudicated. */
	private static final int PAUSED_ADJUDICATE = 8;

	/** The state for when the game is paused. */
	public static final int PAUSED = 9;

	/** The state to return to the title screen. */
	public static final int RETURN_TO_START = 10;
			
	/** SpriteSheet for armies. */
	private SpriteSheet landUnit;
	
	/** SpriteSheet for fleets. */
	private SpriteSheet waterUnit;

	/** The name(s) of the player(s) who won. */
	private String winningPlayer;
	
	/** The GUI for choosing allies. */
	private ChooseAllyGUI allySelector;

	/** Used for drawing non-accessible territories and the bridge. */
	private EAnimation overlay;

	/** Used for drawing the side bar. */
	private EAnimation sidebar;
	
	/** The button to adjudicate Orders. */
	private SubmitCommand adjudicateButton;

	/** The GUI for selecting a command. */
	private CommandGUI commandGUI;

	/** The GUI for making sure you wish to adjudicate. */
	private PauseMenuAdjudicateGUI pauseMenuAdjudicate;
	
	/** The button for pausing the game. */
	private Button pauseButton;

	/** The pause menu GUI. */
	private PauseMenuGUI pauseMenu;

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

		smallFont = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 10), true);
	    mediumFont = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 20), true);
		
		players = new Player[7];
		players[0] = new Player("England");
		players[1] = new Player("Austria-Hungary");
		players[2] = new Player("Italy");
		players[3] = new Player("Turkey");
		players[4] = new Player("France");
		players[5] = new Player("Russia");
		players[6] = new Player("Germany");
		
		Image temp = EAnimation.loadImage("/images/ArmyUnit_updated.png");
		landUnit = new SpriteSheet(temp, temp.getWidth()/7, temp.getHeight());
		temp = EAnimation.loadImage("/images/NavyUnit_updated.png");
		waterUnit = new SpriteSheet(temp, temp.getWidth()/7, temp.getHeight());
		
		temp = EAnimation.loadImage("/images/AllyChoiceBackground.png");
		Image[] flags = new Image[8];
		flags[0] = EAnimation.loadImage("/images/EnglandChoice.png");
		flags[1] = EAnimation.loadImage("/images/AustriaHungaryChoice.png");
		flags[2] = EAnimation.loadImage("/images/ItalyChoice.png");
		flags[3] = EAnimation.loadImage("/images/TurkeyChoice.png");
		flags[4] = EAnimation.loadImage("/images/FranceChoice.png");
		flags[5] = EAnimation.loadImage("/images/RussiaChoice.png");
		flags[6] = EAnimation.loadImage("/images/GermanyChoice.png");
		flags[7] = EAnimation.loadImage("/images/NobodyChoice.png");
		allySelector = new ChooseAllyGUI(flags, temp, gc);
		allySelector.setY((gc.getHeight() - temp.getHeight()) / 2);
		allySelector.setX((gc.getWidth() - temp.getWidth()) / 2);
		
		currPhase = new PlanningPhase("Spring/Summer", 1900);
		commandGUI = new CommandGUI();
		
		overlay = new EAnimation(EAnimation.loadImage("/images/overlay.png"));
		sidebar = new EAnimation(EAnimation.loadImage("/images/sidebar.png"));
		adjudicateButton = new SubmitCommand(805, 630);
		adjudicateButton.setEA(new EAnimation(EAnimation.loadImage("/images/Icon_Adjudicate_updated.png")));
		pauseMenuAdjudicate = new PauseMenuAdjudicateGUI(gc);
		pauseMenu = new PauseMenuGUI(gc, EAnimation.loadImage("/images/pauseMenu.png"));
		pauseButton = new PauseButton(5, 5, "/images/Button_Pause.png");
		
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
			SpriteSheet ss = SSFactory(s2[6], new Boolean(s2[1].trim()));
			territories[i] = new Territory(ss, s2[0], new Boolean(s2[1].trim()), 
					new Boolean(s2[2].trim()), new Boolean(s2[3].trim()));
			territories[i].setX(new Integer(s2[4]));
			territories[i].setY(new Integer(s2[5]));
			territories[i].setUnitX(new Integer(s2[7]));
			territories[i].setUnitY(new Integer(s2[8]));
			try{
				territories[i].setSCX(new Integer(s2[9]));
				territories[i].setSCY(new Integer(s2[10]));
				territories[i].setNCX(new Integer(s2[11]));
				territories[i].setNCY(new Integer(s2[12]));
			}
			catch (ArrayIndexOutOfBoundsException e){
				System.out.println("no Coasts for " + s2[0]);
			}
			i++;
		}
		sc.close();
		
		//define adjacent territories
		sc = new Scanner(GameCanvas.class.getResourceAsStream("/docs/adjacentTerr.csv"));
		sc.nextLine();
		while (sc.hasNextLine()){
			String s[] = sc.nextLine().split(",");
			Territory t = getTerritory(s[0]);
			for (i = 1; i < s.length; i++){
				if (s[i].equals("southCoast"))
					break;
				t.addAdjacent(s[i]);
			}
			for (i = i + 1; i < s.length; i++){
				if (s[i].equals("northCoast"))
					break;
				t.addSCAdjacent(s[i]);
			}
			for (i = i + 1; i < s.length; i++){
				t.addNCAdjacent(s[i]);
			}
		}	
		sc.close();

		setBoard();
	    adjustNumSC();	
	}

	/**
	 * Places units for each team in their starting locations.
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
		createStartingUnit(getTerritory("Napoli"), waterUnit, false, players[Territory.ITALY-1]);

		createStartingUnit(getTerritory("St. Petersburg"), waterUnit, false, players[Territory.RUSSIA-1]);
		getTerritory("St. Petersburg").setSC(true);
		createStartingUnit(getTerritory("Moscow"), landUnit, true, players[Territory.RUSSIA-1]);
		createStartingUnit(getTerritory("Sevastopol"), waterUnit, false, players[Territory.RUSSIA-1]);
		createStartingUnit(getTerritory("Warsaw"), landUnit, true, players[Territory.RUSSIA-1]);

		createStartingUnit(getTerritory("Ankara"), waterUnit, false, players[Territory.TURKEY-1]);
		createStartingUnit(getTerritory("Constantinople"), landUnit, true, players[Territory.TURKEY-1]);
		createStartingUnit(getTerritory("Smyrna"),landUnit, true, players[Territory.TURKEY-1]);
	}
	
	/**
	 * Creates a Unit at the beginning of the game. Sets the Territory as a home city
	 * for the Player.
	 *
	 * @param t the location of the Unit
	 * @param ss the Spritesheet of this Unit
	 * @param isArmy true, if the Unit is an army
	 * @param p the the Player who owns the Unit
	 */
	private void createStartingUnit(Territory t, SpriteSheet ss, boolean isArmy, Player p){
		t.setOwner(p.getOwnerKey());
		t.setHomeCity(p.getOwnerKey());
		Unit temp = new Unit(ss, p.getOwnerKey() - 1, isArmy, t);
		t.setUnit(temp);
		p.addUnit(t.getUnit());
	}
	
	/**
	 * Creates a Unit.
	 *
	 * @param t the location of the Unit
	 * @param isArmy true, if the Unit is an army
	 * @param p the the Player who owns this Unit
	 */
	public void createUnit(Territory t, boolean isArmy, Player p){
		Unit temp = null;
		if (isArmy)
			temp = new Unit(landUnit, p.getOwnerKey() - 1, isArmy, t);
		else if (t.hasCoasts()){
			t.setSC(true);
			temp = new Unit(waterUnit, p.getOwnerKey() - 1, isArmy, t);
		}
		else
			temp = new Unit(waterUnit, p.getOwnerKey() - 1, isArmy, t);
		t.setUnit(temp);
		p.addUnit(t.getUnit());
	}

	/**
	 * Used to generate a SpriteSheet of a territory.  Assumes that your SpriteSheet has 8 sprites.
	 *
	 * @param location the file path of the SpriteSheet
	 * @param isArmy true, if the Unit is an army
	 * @return the sprite sheet
	 */
	private SpriteSheet SSFactory(String location, Boolean isArmy){
		Image temp = EAnimation.loadImage(location);
		SpriteSheet ss;
		if (isArmy)
			ss = new SpriteSheet(temp, temp.getWidth()/8, temp.getHeight());
		else
			ss = new SpriteSheet(temp, temp.getWidth(), temp.getHeight());		
		return ss;
	}

	/* (non-Javadoc)
	 * @see com.erebos.engine.core.ECanvas#eRender(org.newdawn.slick.GameContainer, com.erebos.engine.core.EGame, org.newdawn.slick.Graphics)
	 */
	public void eRender(GameContainer gc, EGame eg, Graphics g) {
		
		for (Territory t : territories)
			t.eDraw();
		overlay.draw(0, 0);
		for (Territory t : territories)
			t.uDraw();

		g.setColor(Color.black);
		g.setFont(mediumFont);
		g.drawString(currPhase.toString(), 65, 10);
		sidebar.draw(806, 0);
		int ySC = 400;
		int[] orderCoord = { 815, 110 };
		for (Player p : players) {
			g.setFont(smallFont);
			if (orderCoord[1] > 390) {
				orderCoord[0] += 165;
				orderCoord[1] = 110;
			}
			orderCoord = p.draw(g, orderCoord[0], orderCoord[1]);
			g.setFont(mediumFont);
			g.drawString(p.getNumArmies() + "", 877, ySC);
			g.drawString(p.getNumNavies() + "", 980, ySC);
			g.drawString(p.getSupplyCenterCount() + "", 1070, ySC);
			ySC += 33;
			orderCoord[1] += 5;
		}
		g.setFont(smallFont);
		if (currOrder != null) {
			currOrder.draw(g, 865, 35);
			commandGUI.drawSetDiscard();
		}
		adjudicateButton.draw();
		pauseButton.draw();

		if (state == CHOOSE_ALLIES)
			allySelector.draw();
		else if (state == SELECT_COMM)
			commandGUI.draw();
		else if (state == PAUSED_ADJUDICATE)
			pauseMenuAdjudicate.draw();
		else if (state == PAUSED)
			pauseMenu.draw();
	}

	/* (non-Javadoc)
	 * @see com.erebos.engine.core.ECanvas#eUpdate(org.newdawn.slick.GameContainer, com.erebos.engine.core.EGame, int)
	 */
	@Override
	public void eUpdate(GameContainer gc, EGame eg, int delta) {			
		
		if (state == WINNER){
			WinCanvas.getWC().setWinner(winningPlayer.toUpperCase());
			eg.enterState(2);
		}
		
		else if (state == ADJUDICATE_ALLIES){
			winningPlayer = allySelector.adjucate();
			if (winningPlayer != null){
				mediumFont = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 40), true);
				state = WINNER;
			}
			else
				state = NORM;
		}
		else if (state == RETURN_TO_START){
			state = NORM;
			eg.enterState(0);
			eInit(gc, eg);
		}
		
		else if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			
			int mx = Mouse.getX();
			int my = Math.abs(Mouse.getY() - gc.getHeight());
			
			if (state == PAUSED_ADJUDICATE)
				pauseMenuAdjudicate.update(mx, my);
			else if (state == PAUSED)
				pauseMenu.update(mx, my);
			else if (state == CHOOSE_ALLIES) 
				allySelector.update(mx, my);
			else if (state == SELECT_COMM)
				commandGUI.update(mx, my);
			else {
				if (pauseButton.isMouseOver(mx, my))
					pauseButton.update();
				
				for (Territory t : territories) {
					if (mx >= t.getX()
							&& ((t.isLand() && mx <= t.getWidth() / 8
									+ t.getX()) || (!t.isLand() && mx <= t
									.getWidth() + t.getX())) && my >= t.getY()
							&& my <= t.getHeight() + t.getY()
							&& t.isMouseOver(mx, my)) {
						updateTerritory(t, mx, my);
						return;
					}
				}

				if (mx >= adjudicateButton.getX() && mx <= adjudicateButton.getWidth() + adjudicateButton.getX()
						&& my >= adjudicateButton.getY() && my <= adjudicateButton.getHeight() + adjudicateButton.getY()) {
					adjudicateButton.update(mx, my);
					return;
				}
				
				if (currOrder != null)
					commandGUI.updateSetDiscard(mx, my);
			}					
		}
	}

	/**
	 * Adds an Order to the current Phase.
	 *
	 * @param o the Order to be added
	 */
	public void addOrder(Order o){
		currPhase.addOrder(o);
	}

	/**
	 * Adjusts the number of supply centers each team currently has.
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
	 * Moves the current Phase on to the next one.
	 */
	private void adjustTurn() {
		
		String s = currPhase.getSeason();
		int i = currPhase.getYear();
		
		if (s.equals("Spring/Summer")){
			if (((PlanningPhase) currPhase).getRetreatingUnits().size() != 0)
				currPhase = new RetreatPhase("Summer Retreats", i, 
						((PlanningPhase)currPhase).getRetreatingUnits(), ((PlanningPhase) currPhase).getAttackOrders());
			else
				currPhase = new PlanningPhase("Fall/Winter", i);
		}
		
		else if (s.equals("Summer Retreats"))
			currPhase = new PlanningPhase("Fall/Winter", i);
		
		else if (s.equals("Fall/Winter")){
			if (((PlanningPhase) currPhase).getRetreatingUnits().size() != 0)
				currPhase = new RetreatPhase("Winter Retreats", i, 
						((PlanningPhase)currPhase).getRetreatingUnits(), ((PlanningPhase) currPhase).getAttackOrders());
			else{
				currPhase = new BuildPhase("Build/Remove", i);
				adjustNumSC();
				int highest = 0;
				for (Player p : players){
					if (p.getSupplyCenterCount() >= 24){
						state = WINNER;
						winningPlayer = p.getName() + " WINS!!!";
						mediumFont = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 40), true);
						break;
					}
					if (p.getSupplyCenterCount() > highest)
						highest = p.getSupplyCenterCount();
				}
				if (state != WINNER && highest*2 >= 24){
					winningPlayer = "";
					state = CHOOSE_ALLIES;
				}
			}
		}

		else if (s.equals("Winter Retreats")){
			currPhase = new BuildPhase("Build/Remove", i);
			adjustNumSC();
			int highest = 0;
			for (Player p : players){
				if (p.getSupplyCenterCount() >= 24){
					state = WINNER;
					winningPlayer = p.getName() + " WINS!!!";
					mediumFont = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 40), true);
					break;
				}
				if (p.getSupplyCenterCount() > highest)
					highest = p.getSupplyCenterCount();
			}
			if (state != WINNER && highest*2 >= 24){
				winningPlayer = "";
				state = CHOOSE_ALLIES;
			}
		}
		
		else{
			i++;
			currPhase = new PlanningPhase("Spring/Summer", i);
		}
		
		save();
	}

	/**
	 * Singleton getter method to get this Canvas.
	 *
	 * @return this GameCanvas
	 */
	public static GameCanvas getC() {
		if (c == null)
			c = new GameCanvas();
		return c;
	}	
	
	/**
	 * Gets the current Order.
	 *
	 * @return the order
	 */
	public Order getOrder() {
		return currOrder;
	}

	/**
	 * Gets the current state of the game.
	 *
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * Gets a Territory from the territories array.
	 *
	 * @param name the name of the Territory
	 * @return the Territory, null if not found
	 */
	private Territory getTerritory(String name){
		for (Territory t : territories){
			if (t.getName().equals(name))
				return t;
		}
		return null;
	}

	/**
	 * Sets the command for the current Order
	 *
	 * @param s the command
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
	 * Submits all Orders to the current Phase. Will ask 'are you sure?' 
	 * if a unit does not have an Order.
	 */
	public void submit() {
		currOrder = null;
		for (Player p: players){
			if (!p.allHaveOrders()){
				state = PAUSED_ADJUDICATE;
				break;
			}
		}
		if (state == PAUSED_ADJUDICATE)
			return;
		else
			adjudicate();
	}

	/**
	 * Adjudicates all Orders for this Phase.
	 */
	public void adjudicate() {
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
	 * Sets the current state of this game.
	 *
	 * @param s the new state
	 */
	public void setState(int s){
		state = s;
	}
	
	/**
	 * Updates a Territory depending on the state of the game.
	 * Only fires when a Territory is clicked on and if the current
	 * state deems updating a Territory is necessary.
	 *
	 * @param t the Territory to be updated
	 * @param mx the x coordinate of the Mouse cursor to display the command GUI
	 * @param my the y coordinate of the Mouse cursor to display the command GUI
	 */
	public void updateTerritory(Territory t, int mx, int my) {
		
		if (state == NORM){
			if (t.getUnit() != null || currPhase.getSeason().equals("Build/Remove")){
				currTerritory = t;
				commandGUI.setCoord(mx, my);
				state = SELECT_COMM;				
			}
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
	 * Sets the current Order for the Unit or if it is the Build Phase
	 * the Order is sent straight to the Phase.
	 */
	public void finalizeOrder() {
		if (currOrder != null){
			if (!currPhase.getSeason().equals("Build/Remove") || currOrder.getCommand().equals("disband"))
				currOrder.setUnitOrder();
			else 
				((BuildPhase) currPhase).addBuildOrder(currOrder);
		}
		currOrder = null;
		state = NORM;
	}

	/**
	 * Sets the current Order value to null.
	 */
	public void discardOrder() {
		currOrder = null;
		state = NORM;
	}

	/**
	 * Removes a Unit from the board and from the Player's unit list.
	 *
	 * @param unit the Unit to be removed
	 */
	public void removeUnit(Unit unit) {
		players[unit.getOwner()-1].removeUnit(unit.getTerritory());
		if (!unit.getTerritory().hasSC())
			unit.getTerritory().setOwner(Territory.NEUTRAL);
		unit.getTerritory().removeUnit();
	}

	/**
	 * Gets the list of players.
	 *
	 * @return the players
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * Sets the current Order.
	 *
	 * @param o the new order
	 */
	public void setOrder(Order o) {
		currOrder = o;
	}

	/**
	 * Gets the current territory.
	 *
	 * @return the current territory
	 */
	public Territory getCurrentTerritory() {
		return currTerritory;
	}

	/**
	 * Gets the current Phase.
	 *
	 * @return the current Phase
	 */
	public Phase getPhase() {
		return currPhase;
	}
	
	/**
	 * Saves the game. Pretty static method right now. Only
	 * one save is allowed currently.
	 */
	public void save(){
		if (currPhase.getSeason().contains("Retreats"))
			return;
		
		File dir = new File("BITPLOMACY_SAVES");
		if (!dir.exists())
			dir.mkdir();
		File file = new File(dir, "Save1.dat");
		try {
			if (file.exists())
				file.createNewFile();
			PrintWriter pw = new PrintWriter(file);
			pw.print(currPhase.getSeason() + "\t" + currPhase.getYear() + "\n");
			for (Player p : players) {
				pw.write(p.getOwnerKey() + "\t");
				pw.write(p.saveUnits());
				pw.write("\n");
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Loads the game. Pretty static right now. The jar must be in the same directory
	 * as the BITPLOMACY_SAVES directory for it to work.
	 *
	 * @throws FileNotFoundException the file not found exception
	 */
	public void load() throws FileNotFoundException{
		File dir = new File ("BITPLOMACY_SAVES");
		File file = new File (dir, "Save1.dat");
		Scanner sc = new Scanner(file);

		for (Territory t : territories) {
			if (t.getUnit() != null)
				removeUnit(t.getUnit());
		}

		String temp = sc.nextLine();
		String[] currLine = temp.split("\t");

		if (currLine[0].equals("Build/Remove"))
			currPhase = new BuildPhase(currLine[0], new Integer(currLine[1]));
		else
			currPhase = new PlanningPhase(currLine[0], new Integer(currLine[1]));

		while (sc.hasNextLine()) {
			currLine = sc.nextLine().split("\t");
			Player p = players[new Integer(currLine[0]) - 1];
			for (int i = 1; i < currLine.length; i++) {
				createUnit(getTerritory(currLine[i]), new Boolean(
						currLine[i + 1]), p);
				i++;
			}
		}
		sc.close();
		adjustNumSC();
		
	}

}
