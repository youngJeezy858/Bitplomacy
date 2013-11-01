package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import gameObjects.Order;
import gameObjects.Player;
import gameObjects.Territory;
import gameObjects.Turn;
import gameObjects.Unit;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
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
	private Commands[] commands = { new AttackCommand(1152, 500, 100),
			new DefendCommand(1152, 606, 150),
			new SupportCommand(1269, 500, 50),
			new ConvoyCommand(1269, 606, 250),
			new SubmitCommand(1151, 717, 200) };
	private Territory disTerr;
	private int state;
	private Order currOrder;
	private Turn currTurn;
	
	public static final int START = 0;
	public static final int NORM = 1;
	public static final int TERR_SELECTED = 2;
	public static final int COMM_SELECTED = 3;
	public static final int SELECT_SUPPORT = 4;
	public static final int SELECT_CONVOY_DESTINATION = 5;
	public static final int SELECT_CONVOY_UNITS = 6;
	
	/* MasterMap contains the color keys for the individual territories.  It is 
	 * referenced in the Territory */
	private Image MasterMap;
	private Image Borders;
		
	/* SpriteSheets for Units */
	private SpriteSheet landUnit;
	private SpriteSheet waterUnit;
	
	private Canvas(){
		super(1);
		state = 1;
	}
	
	@Override
	public void eInit(GameContainer gc, EGame eg) {		
		
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
		
		commands[0].setEA(new EAnimation(EAnimation.loadImage("/images/AttackIcon.png")));
		commands[1].setEA(new EAnimation(EAnimation.loadImage("/images/DefendIcon.png")));
		commands[2].setEA(new EAnimation(EAnimation.loadImage("/images/SupportIcon.png")));
		commands[3].setEA(new EAnimation(EAnimation.loadImage("/images/ConvoyIcon.png")));
		commands[4].setEA(new EAnimation(EAnimation.loadImage("/images/SubmitIcon.png")));
		
		Image temp = EAnimation.loadImage("/images/LandUnit.png");
		landUnit = new SpriteSheet(temp, temp.getWidth()/7, temp.getHeight());
		temp = EAnimation.loadImage("/images/WaterUnit.png");
		waterUnit = new SpriteSheet(temp, temp.getWidth()/7, temp.getHeight());
		
		currTurn = new Turn("Spring", 1900);
		
		try {
			//define territories
			File f = new File("src/docs/terr.csv");
			Scanner sc = new Scanner(f);
			sc.nextLine();
			int lines = 0;
			while (sc.hasNextLine()){
				lines++;
				sc.nextLine();
			}
			sc.close();
			sc = new Scanner(f);
			sc.nextLine();
			int i = 0;
			territories = new Territory[lines];
			while (sc.hasNextLine()){
				String s = sc.nextLine();
				String s2[] = s.split("\t");
				SpriteSheet ss = SSFactory(s2[9]);
				territories[i] = new Territory(ss, s2[0], new Boolean(s2[1]), 
						new Boolean(s2[2]), new Boolean(s2[3]), 
						new Color(new Integer(s2[4]), new Integer(s2[5]), new Integer(s2[6])));
				territories[i].setX(new Integer(s2[7]));
				territories[i].setY(new Integer(s2[8]));
				i++;
			}
			sc.close();
			
			//define adjacent territories
			f = new File("src/docs/adjacentTerr.csv");
			sc = new Scanner(f);
			sc.nextLine();
			while (sc.hasNextLine()){
				String s[] = sc.nextLine().split(",");
				Territory t = getT(s[0]);
				for (i = 1; i < s.length; i++){
					System.out.println(s[i]);
					t.addAdjacent(s[i]);
				}
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	    setBoard();
	    adjustNumSC();

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
		createUnit(getT("Munich"), Territory.GERMANY, waterUnit, false, players[Territory.GERMANY-1]);

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

	@Override
	public void eRender(GameContainer gc, EGame eg, Graphics g) {
			
		//draw masterMap for color keys
		g.drawImage(MasterMap, 0, 0);	
		
		g.setColor(Color.green);
		g.fillRect(1106, 0, 1400-1126, gc.getHeight());
		
		g.setColor(Color.black);
		g.drawString("Country:", 1150, 50);
		g.drawString("Owner:", 1150, 120);
		
		g.drawString("SUPPLY CENTER TOTALS", 1145, 200);
		int i = 220;
		for (Player p : players){
			g.drawString(p.getName() + ": " + p.getSupplyCount(), 1145, i);
			i = i + 20;
		}
		
		if (currOrder != null){
			g.drawString(currOrder.toString(), 1130, 380);
			if (currOrder.isReady()){
				state = NORM;
				if (currOrder.isValidOrder()){
					g.drawString("Accepted...", 1130, 470);
					currOrder.getUnit().setOrder(currOrder);
				}
				else
					g.drawString("Not a valid order...", 1130, 470);
			}
		}
		
		for (Commands c : commands)
			c.draw();
		for (Territory t: territories)
			t.eDraw();
		g.drawImage(Borders, 0, 0);
		for (Territory t: territories)
			t.uDraw();
		
		Font f = g.getFont();
		g.setFont(new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 20), true));
		g.drawString(currTurn.toString(), 10, 10);
		
		g.setFont(f);
		if (state == TERR_SELECTED || state == COMM_SELECTED) {
			g.drawString(disTerr.getName(), 1145, 70);
			g.drawString(disTerr.getOwnerName(), 1145, 140);
		}

	}

	@Override
	public void eUpdate(GameContainer gc, EGame eg, int delta) {		
		updateGame(gc);
	}

	/*
	 * Updates the game on for the current frame. Game is set to run at 60 frames per second so this fires 60 times a second.
	 */
	private void updateGame(GameContainer gc) {
		int mx = Mouse.getX();
		int my = Math.abs(Mouse.getY() - 831);
		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			for (Territory t : territories) {
				if (mx >= t.getX() && mx <= t.getWidth() + t.getX()
						&& my >= t.getY() && my <= t.getHeight() + t.getY())
					t.update();
			}
			for (Commands c : commands) {
				if (mx >= c.getX() && mx <= c.getWidth() + c.getX()
						&& my >= c.getY() && my <= c.getHeight() + c.getY())
					c.update();
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
		if (s.equals("Spring"))
			currTurn = new Turn("Summer", i);
		else if (s.equals("Summer"))
			currTurn = new Turn("Fall", i);
		else if (s.equals("Fall"))
			currTurn = new Turn("Winter", i);
		else if (s.equals("Winter"))
			currTurn = new Turn("Build/Remove", i);
		else{
			i++;
			currTurn = new Turn("Spring", i);
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

	/*
	 * Sets the current state of the game.  Used for updating the game.
	 * 
	 * @param s -> the state of the game as an int value.  See class fields for state descriptions
	 */
	public void setState(int s){
		state = s;
	}
	
	public void attack() {
		if (state == Canvas.TERR_SELECTED && currOrder != null){
			currOrder.setCommand("attack");
			state = Canvas.COMM_SELECTED;
		}
		else
			System.out.println("Attack command wus good");
	}

	public void support() {
		if (state == Canvas.TERR_SELECTED && currOrder != null){
			currOrder.setCommand("support");
			state = Canvas.COMM_SELECTED;
		}
		else
			System.out.println("support command wus good");	
	}

	public void defend() {
		if (state == Canvas.TERR_SELECTED && currOrder != null){
			currOrder.setCommand("defend");
			currOrder.setTerr2(currOrder.getTerr1());
			currOrder.getUnit().setOrder(currOrder);
			state = Canvas.NORM;
		}	
		else
			System.out.println("defend command wus good");
	}

	public void convoy() {
		if (state == Canvas.TERR_SELECTED && currOrder != null){
			currOrder.setCommand("convoy");
			state = Canvas.COMM_SELECTED;
		}
		else
			System.out.println("Convoy command wus good");	
		
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

	public void updateTerritory(Territory t) {

		if (state == NORM || state == TERR_SELECTED){
			disTerr = t;
			state = TERR_SELECTED;
			if (t.getUnit() != null)
				currOrder = new Order(t);
		}
		else if (state == COMM_SELECTED){
			if (currOrder.getCommand().equals("attack")){
				currOrder.setTerr2(t);
				if (currOrder.expectingConvoy())
					state = SELECT_CONVOY_UNITS;
			}
			else if (currOrder.getCommand().equals("support")){
				currOrder.setTerr2(t);
				state = SELECT_CONVOY_DESTINATION;
			}
			else if (currOrder.getCommand().equals("convoy")){
				currOrder.setTerr2(t);
				state = Canvas.SELECT_CONVOY_DESTINATION;
			}
		}
		else if (state == SELECT_SUPPORT){
			if (t.getUnit() != null){
				currOrder.setSupport(t.getUnit());
				state = NORM;
			}
		}
		else if (state == Canvas.SELECT_CONVOY_DESTINATION){
			currOrder.setConvoyDestination(t);
			state = NORM;
		}
		else if (state == Canvas.SELECT_CONVOY_UNITS){
			if (currOrder.isReady())
				state = NORM;
		}
			

	}

}
