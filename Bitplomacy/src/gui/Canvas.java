package gui;

import java.util.Scanner;

import gameObjects.Order;
import gameObjects.Player;
import gameObjects.Territory;
import gameObjects.Turn;
import gameObjects.Unit;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;

import com.erebos.engine.core.*;
import com.erebos.engine.graphics.EAnimation;


public class Canvas extends ECanvas{

	/* Singleton variable for the Canvas */
	private static Canvas c = null;
	
	private Territory[] territories;
	private Player[] players;
	private Commands[] commands;
	private Commands[] regularCommands = { new AttackCommand(1166, 635, 100),
			new DefendCommand(1126, 729, 150),
			new SupportCommand(1212, 729, 50),
			new ConvoyCommand(1296, 729, 250),
			new SubmitCommand(1152, 546, 200),
			new MoveCommand(1258, 635, 25),
			new SetOrderCommand(1152, 450, 75),
			new DiscardOrderCommand(1269, 450, 125)};
	private Commands[] buildRemoveCommands = { new BuildArmyCommand(1166, 635, 100),
			new BuildNavyCommand(1258, 635, 25),
			new RemoveUnitCommand(1212, 729, 50),
			new SubmitCommand(1152, 546, 200),
			new SetOrderCommand(1152, 450, 75),
			new DiscardOrderCommand(1269, 450, 125)};
	
	private String displayTerritoryName;
	private String displayTerritoryOwner;
	private Order currOrder;
	private Turn currTurn;
	private TrueTypeFont font;
	
	private int state;
	public static final int NORM = 0;
	public static final int COMM_SELECTED = 1;
	public static final int SELECT_SUPPORT = 2;
	public static final int SELECT_CONVOY_DESTINATION = 3;
	public static final int SELECT_CONVOY_UNITS = 4;
	
	/* MasterMap contains the color keys for the individual territories.  It is 
	 * referenced in the Territory */
	private Image MasterMap;
	private Image Borders;
		
	/* SpriteSheets for Units */
	private SpriteSheet landUnit;
	private SpriteSheet waterUnit;
	
	private Canvas(){
		super(1);
		state = NORM;
	}
	
	@Override
	public void eInit(GameContainer gc, EGame eg) {		
		
		displayTerritoryName = "";
		displayTerritoryOwner = "";
		
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
		
		regularCommands[0].setEA(new EAnimation(EAnimation.loadImage("/images/AttackIcon.png")));
		regularCommands[1].setEA(new EAnimation(EAnimation.loadImage("/images/DefendIcon.png")));
		regularCommands[2].setEA(new EAnimation(EAnimation.loadImage("/images/SupportIcon.png")));
		regularCommands[3].setEA(new EAnimation(EAnimation.loadImage("/images/ConvoyIcon.png")));
		regularCommands[4].setEA(new EAnimation(EAnimation.loadImage("/images/SubmitIcon.png")));
		regularCommands[5].setEA(new EAnimation(EAnimation.loadImage("/images/MoveIcon.png")));
		regularCommands[6].setEA(new EAnimation(EAnimation.loadImage("/images/SetOrderIcon.png")));
		regularCommands[7].setEA(new EAnimation(EAnimation.loadImage("/images/DiscardOrderIcon.png")));

		buildRemoveCommands[0].setEA(new EAnimation(EAnimation.loadImage("/images/BuildArmyIcon.png")));
		buildRemoveCommands[1].setEA(new EAnimation(EAnimation.loadImage("/images/BuildNavyIcon.png")));
		buildRemoveCommands[2].setEA(new EAnimation(EAnimation.loadImage("/images/RemoveUnitIcon.png")));
		buildRemoveCommands[3].setEA(new EAnimation(EAnimation.loadImage("/images/SubmitIcon.png")));
		buildRemoveCommands[4].setEA(new EAnimation(EAnimation.loadImage("/images/SetOrderIcon.png")));
		buildRemoveCommands[5].setEA(new EAnimation(EAnimation.loadImage("/images/DiscardOrderIcon.png")));

		Image temp = EAnimation.loadImage("/images/LandUnitUpdated.png");
		landUnit = new SpriteSheet(temp, temp.getWidth()/7, temp.getHeight());
		temp = EAnimation.loadImage("/images/WaterUnitUpdated.png");
		waterUnit = new SpriteSheet(temp, temp.getWidth()/7, temp.getHeight());
		
		currTurn = new Turn("Spring/Summer", 1900);
		commands = regularCommands;
		
		//define territories
		Scanner sc = new Scanner(Canvas.class.getResourceAsStream("/docs/terr.csv"));
		sc.nextLine();
		int lines = 0;
		while (sc.hasNextLine()){
			lines++;
			sc.nextLine();
		}
		sc.close();
		sc = new Scanner(Canvas.class.getResourceAsStream("/docs/terr.csv"));
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
		sc = new Scanner(Canvas.class.getResourceAsStream("/docs/adjacentTerr.csv"));
		sc.nextLine();
		while (sc.hasNextLine()){
			String s[] = sc.nextLine().split("\t");
			Territory t = getT(s[0]);
			for (i = 1; i < s.length; i++)
				t.addAdjacent(s[i]);
		}	
		sc.close();
		
	    setBoard();
	    adjustNumSC();

	    font = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 20), true);
	}

	/*
	 * Sets up the play area for a 7 teams.  Specifically it
	 * sets ownership for territories and generate units for each 
	 * team. 
	 */
	private void setBoard() {
		createUnit(getT("Edinburgh"), Territory.ENGLAND, waterUnit, false, players[Territory.ENGLAND-1]);
		createUnit(getT("Liverpool"), Territory.ENGLAND, landUnit, true, players[Territory.ENGLAND-1]);
		createUnit(getT("London"), Territory.ENGLAND, waterUnit, false, players[Territory.ENGLAND-1]);

		createUnit(getT("Vienna"), Territory.AUSTRIA_HUNGARY, landUnit, true, players[Territory.AUSTRIA_HUNGARY-1]);
		createUnit(getT("Budapest"), Territory.AUSTRIA_HUNGARY, landUnit, true, players[Territory.AUSTRIA_HUNGARY-1]);
		createUnit(getT("Trieste"), Territory.AUSTRIA_HUNGARY, waterUnit, false, players[Territory.AUSTRIA_HUNGARY-1]);

		createUnit(getT("Paris"), Territory.FRANCE, landUnit, true, players[Territory.FRANCE-1]);
		createUnit(getT("Brest"), Territory.FRANCE, waterUnit, false, players[Territory.FRANCE-1]);
		createUnit(getT("Marseilles"), Territory.FRANCE, landUnit, true, players[Territory.FRANCE-1]);

		createUnit(getT("Berlin"), Territory.GERMANY, landUnit, true, players[Territory.GERMANY-1]);
		createUnit(getT("Kiel"), Territory.GERMANY, waterUnit, false, players[Territory.GERMANY-1]);
		createUnit(getT("Munich"), Territory.GERMANY, landUnit, true, players[Territory.GERMANY-1]);

		createUnit(getT("Venice"), Territory.ITALY, landUnit, true, players[Territory.ITALY-1]);
		createUnit(getT("Rome"), Territory.ITALY, landUnit, true, players[Territory.ITALY-1]);
		createUnit(getT("Naples"), Territory.ITALY, waterUnit, false, players[Territory.ITALY-1]);

		createUnit(getT("St. Petersburgh"), Territory.RUSSIA, waterUnit, false, players[Territory.RUSSIA-1]);
		createUnit(getT("Moscow"), Territory.RUSSIA, landUnit, true, players[Territory.RUSSIA-1]);
		createUnit(getT("Sevastopal"), Territory.RUSSIA, waterUnit, false, players[Territory.RUSSIA-1]);
		createUnit(getT("Warsaw"), Territory.RUSSIA, landUnit, true, players[Territory.RUSSIA-1]);

		createUnit(getT("Ankara"), Territory.TURKEY, waterUnit, false, players[Territory.TURKEY-1]);
		createUnit(getT("Constantinople"), Territory.TURKEY, landUnit, true, players[Territory.TURKEY-1]);
		createUnit(getT("Smyrna"), Territory.TURKEY, landUnit, true, players[Territory.TURKEY-1]);
	}
	
	private void createUnit(Territory t, int owner, SpriteSheet ss, boolean isLand, Player p){
		t.setOwner(owner);
		Unit temp = new Unit(ss, owner - 1, isLand, t);
		t.setUnit(temp);
		p.addUnit(t.getUnit());
	}

	/*
	 * Used to generate a SpriteSheet of a territory.  Assumes that your SpriteSheet has 8 sprites.
	 * @param String location -> the file path of the image
	 * @returns -> a Spritesheet of the image
	 */
	private SpriteSheet SSFactory(String location){
		Image temp = EAnimation.loadImage(location);
		SpriteSheet ss = new SpriteSheet(temp, temp.getWidth()/8, temp.getHeight());
		return ss;
	}

	public void eRender(GameContainer gc, EGame eg, Graphics g) {
			
		//draw masterMap for color keys
		g.drawImage(MasterMap, 0, 0);	
		
		g.setColor(Color.gray);
		g.fillRect(1106, 0, 1400-1126, gc.getHeight());
		
		g.setColor(Color.black);
		g.drawString("Country:", 1150, 20);
		g.drawString("Owner:", 1150, 70);
		
		g.drawString("SUPPLY CENTER TOTALS", 1145, 120);
		int i = 140;
		for (Player p : players){
			g.drawString(p.getName() + ": " + p.getSupplyCount(), 1145, i);
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
		for (Territory t: territories)
			t.eDraw();
		g.drawImage(Borders, 0, 0);
		for (Territory t: territories)
			t.uDraw();
		
		g.setFont(font);
		g.drawString(currTurn.toString(), 10, 10);

	}

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

	public void addOrder(Order o){
		currTurn.addOrder(o);
	}

	public void adjustNumSC() {
		int i;
		for (Player p : players){
			i = 0;
	    	for (Territory t : territories){
	    		if (t.hasSC() && t.getOwnerName().equals(p.getName()))
	    			i++;
	    	}
			p.adjustNumSC(i);
		}
	}

	private void adjustTurn() {
		
		String s = currTurn.getSeason();
		int i = currTurn.getYear();
		if (s.equals("Spring/Summer"))
			currTurn = new Turn("Fall/Winter", i);
		else if (s.equals("Fall/Winter")){
			currTurn = new Turn("Build/Remove", i);
			commands = buildRemoveCommands;
		}
		else{
			i++;
			currTurn = new Turn("Spring/Summer", i);
			commands = regularCommands;
		}
	}

	public static Canvas getC() {
		if (c == null)
			c = new Canvas();
		return c;
	}

	/*
	 * Gets the Color from the current mouse location.  Used to access color keys.
	 * 
	 * @returns Color -> the Color at the current mouse position
	 */
	public Color getCurrentColor(){
		return new Color(MasterMap.getColor(Mouse.getX(), Math.abs(Mouse.getY()-831)));
	}
	
	
	public Order getOrder() {
		return currOrder;
	}

	public int getState() {
		return state;
	}

	public Territory getT(String name){
		for (Territory t : territories){
			if (t.getName().equals(name))
				return t;
		}
		return null;
	}

	public void setCommand(String s){		
		if (currOrder != null){
			currOrder.setCommand(s);
			if (s.equals("defend"))
				state = NORM;
			else
				state = COMM_SELECTED;
		}
		System.out.println(s + " command wus good");
	}

	public void submit() {
		currOrder = null;
		for (Player p : players)
			p.executeOrders();
		currTurn.resolveOrders();
		for (Player p : players)
			p.resetOrders();
		adjustNumSC();
		adjustTurn();
		state = NORM;
	}

	/*
	 * Sets the current state of the game.  Used for updating the game.
	 * 
	 * @param s -> the state of the game as an int value.  See class fields for state descriptions
	 */
	public void setState(int s){
		state = s;
	}
	
	public void updateTerritory(Territory t) {

		displayTerritoryName = t.getName();
		displayTerritoryOwner = t.getOwnerName();
		
		if (state == NORM){
			if (t.getUnit() != null)
				currOrder = new Order(t);
		}
		
		else if (state == COMM_SELECTED){			
			currOrder.setTerr2(t);
			if (currOrder.getCommand().equals("attack") || currOrder.getCommand().equals("move"))
				state = SELECT_CONVOY_UNITS;
			else if (currOrder.getCommand().equals("support"))
				state = SELECT_SUPPORT;
			else if (currOrder.getCommand().equals("convoy"))
				state = SELECT_CONVOY_DESTINATION;
		}
		
		else if (state == SELECT_SUPPORT){
			if (t.getUnit() != null)
				currOrder.setSupport(t.getUnit()); 
		}
		else if (state == SELECT_CONVOY_DESTINATION)
			currOrder.setConvoyDestination(t);
		else if (state == SELECT_CONVOY_UNITS){
			if (t.getUnit() != null)
				currOrder.addConvoyUnit(t.getUnit());
		}
	}

	public void finalizeOrder() {
		System.out.println("order set wus good");
		if (currOrder != null)
			currOrder.pushOrder();
		currOrder = null;
		state = NORM;
	}

	public void discardOrder() {
		System.out.println("discardin that muthasuckin order");
		currOrder = null;
		state = NORM;
	}

}
