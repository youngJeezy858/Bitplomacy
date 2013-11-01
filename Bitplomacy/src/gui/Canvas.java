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
	public static final int SELECT_SUPPORT_DESTINATION = 4;
	public static final int SELECT_CONVOY = 5;
	public static final int SELECT_SUPPORT_UNITS = 6;
	
	/* MasterMap contains the color keys for the individual territories.  It is 
	 * referenced in the Territory */
	private Image MasterMap;
	private Image Borders;
		
	/* SpriteSheets for Units */
	private SpriteSheet landUnit;
	private SpriteSheet waterUnit;
	
	/* Handy dandy SpriteSheets for territories. */
	private SpriteSheet NorthAfrica;
	private SpriteSheet MidAtlantic;
	private SpriteSheet NorthAtlantic;
	private SpriteSheet NorwegianSea;
	private SpriteSheet BarentSea;
	private SpriteSheet StPetersburgh;
	private SpriteSheet Linova;
	private SpriteSheet IrishSea;
	private SpriteSheet EnglishChannel;
	private SpriteSheet NorthSea;
	private SpriteSheet Hel;
	private SpriteSheet Ska;
	private SpriteSheet BalticSea;
	private SpriteSheet GulfBothnia;
	private SpriteSheet WestMed;
	private SpriteSheet GulfLyon;
	private SpriteSheet TyrrhenianSea;
	private SpriteSheet IonianSea;
	private SpriteSheet EasternMed;
	private SpriteSheet BlackSea;
	private SpriteSheet Tunis;
	private SpriteSheet Warsaw;
	private SpriteSheet Moscow;
	private SpriteSheet Ukraine;
	private SpriteSheet Sevastopal;
	private SpriteSheet Marseilles;
	private SpriteSheet Brest;
	private SpriteSheet Paris;
	private SpriteSheet Bur;
	private SpriteSheet Pic;
	private SpriteSheet Gascony;
	private SpriteSheet Wa;
	private SpriteSheet London;
	private SpriteSheet Liverpool;
	private SpriteSheet Edinburgh;
	private SpriteSheet York;
	private SpriteSheet Cly;
	private SpriteSheet Rome;
	private SpriteSheet Naples;
	private SpriteSheet Pie;
	private SpriteSheet Venice;
	private SpriteSheet Tus;
	private SpriteSheet Kiel;
	private SpriteSheet Berlin;
	private SpriteSheet Prussia;
	private SpriteSheet Silesia;
	private SpriteSheet Ruhr;
	private SpriteSheet Munich;
	private SpriteSheet Tyrol;
	private SpriteSheet Vienna;
	private SpriteSheet Galicia;
	private SpriteSheet Budapest;
	private SpriteSheet Trieste;
	private SpriteSheet Bohmeia;
	private SpriteSheet Smyrna;
	private SpriteSheet Syria;
	private SpriteSheet Constantinople;
	private SpriteSheet Holand;
	private SpriteSheet Denmark;
	private SpriteSheet Rumania;
	private SpriteSheet Serbia;
	private SpriteSheet Bulgaria;
	private SpriteSheet Greece;
	private SpriteSheet Finland;
	private SpriteSheet Sweden;
	private SpriteSheet Norway;
	private SpriteSheet Portugal;
	private SpriteSheet Spain;
	private SpriteSheet Belgium;
	private SpriteSheet AdriaticSea;
	private SpriteSheet AegeanSea;
	private SpriteSheet Albania;
	private SpriteSheet Ankara;
	private SpriteSheet Apu;
	private SpriteSheet Armenia;

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
		
		File f = new File("/docs/terr.csv");
		try {
			Scanner sc = new Scanner(f);
			sc.nextLine();
			while (sc.hasNextLine()){
				System.out.println(sc.nextLine());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		defineSS();
	    createTerritories();
	    setBoard();
	    adjustNumSC();

	}

	/*
	 * Defines all the SpriteSheet variables
	 */
	private void defineSS() {
		
		AdriaticSea=SSFactory("/images/AdriaticSea.png");
	    AegeanSea=SSFactory("/images/AegeanSea.png");
	    Albania=SSFactory("/images/Albania.png");
	    Ankara=SSFactory("/images/Ankara.png");
	    Apu=SSFactory("/images/Apu.png");
	    Armenia=SSFactory("/images/Armenia.png");
	    BalticSea=SSFactory("/images/BalticSea.png");
	    BarentSea=SSFactory("/images/BarentSea.png");
	    Belgium=SSFactory("/images/Belgium.png");
	    Berlin=SSFactory("/images/Berlin.png");
	    BlackSea=SSFactory("/images/BlackSea.png");
	    Brest=SSFactory("/images/Brest.png");
	    Bur=SSFactory("/images/Bur.png");
	    Denmark=SSFactory("/images/Denmark.png");
	    EasternMed=SSFactory("/images/EasternMed.png");
	    Edinburgh=SSFactory("/images/Edinburgh.png");
	    EnglishChannel=SSFactory("/images/EnglishChannel.png");
	    GulfBothnia=SSFactory("/images/GulfBothnia.png");
	    GulfLyon=SSFactory("/images/GulfLyon.png");
	    Hel=SSFactory("/images/Hel.png");
	    IonianSea=SSFactory("/images/IonianSea.png");
	    IrishSea=SSFactory("/images/IrishSea.png");
	    Kiel=SSFactory("/images/Kiel.png");
	    Linova=SSFactory("/images/Linova.png");
	    Liverpool=SSFactory("/images/Liverpool.png");
	    London=SSFactory("/images/London.png");
	    Marseilles=SSFactory("/images/Marseilles.png");
		MidAtlantic=SSFactory("/images/MidAtlantic.png");
	    Moscow=SSFactory("/images/Moscow.png");
	    Munich=SSFactory("/images/Munich.png");
	    Naples=SSFactory("/images/Naples.png");
	    NorthAfrica=SSFactory("/images/NAfrica.png");
	    NorthAtlantic=SSFactory("/images/NorthAtlantic.png");
	    NorthSea=SSFactory("/images/NorthSea.png");
	    NorwegianSea=SSFactory("/images/NorwegianSea.png");
	    Paris=SSFactory("/images/Paris.png");
	    Pic=SSFactory("/images/Pic.png");
	    Sevastopal=SSFactory("/images/Sevastopal.png");
	    Ska=SSFactory("/images/Ska.png");
	    StPetersburgh=SSFactory("/images/StPetersburgh.png");
	    Tunis=SSFactory("/images/Tunis.png");
	    TyrrhenianSea=SSFactory("/images/TyrrhenianSea.png");
	    Wa=SSFactory("/images/Wa.png");
	    Warsaw=SSFactory("/images/Warsaw.png");
	    WestMed=SSFactory("/images/WestMed.png");
	    Ukraine=SSFactory("/images/Ukraine.png");
	    Gascony=SSFactory("/images/Gascony.png");
	    York=SSFactory("/images/York.png");
	    Cly=SSFactory("/images/Cly.png");
	    Rome=SSFactory("/images/Rome.png");
	    Pie=SSFactory("/images/Pie.png");
	    Venice=SSFactory("/images/Venice.png");
	    Tus=SSFactory("/images/Tus.png");
	    Prussia=SSFactory("/images/Prussia.png");
	    Silesia=SSFactory("/images/Silesia.png");
	    Ruhr=SSFactory("/images/Ruhr.png");
	    Tyrol=SSFactory("/images/Tyrol.png");
	    Vienna=SSFactory("/images/Vienna.png");
	    Galicia=SSFactory("/images/Galicia.png");
	    Budapest=SSFactory("/images/Budapest.png");
	    Trieste=SSFactory("/images/Trieste.png");
	    Bohmeia=SSFactory("/images/Bohmeia.png");
	    Smyrna=SSFactory("/images/Smyrna.png");
	    Syria=SSFactory("/images/Syria.png");
	    Constantinople=SSFactory("/images/Constantinople.png");
	    Holand=SSFactory("/images/Holand.png");
	    Rumania=SSFactory("/images/Rumania.png");
	    Serbia=SSFactory("/images/Serbia.png");
	    Bulgaria=SSFactory("/images/Bulgaria.png");
	    Greece=SSFactory("/images/Greece.png");
	    Finland=SSFactory("/images/Finland.png");
	    Sweden=SSFactory("/images/Sweden.png");
	    Norway=SSFactory("/images/Norway.png");
	    Portugal=SSFactory("/images/Portugal.png");
	    Spain=SSFactory("/images/Spain.png");
	}

	/*
	 * As the name says.  Used when initializing the game.
	 */
	private void createTerritories(){
		
		territories = new Territory[75];
		TFactory(AdriaticSea, "Adriatic Sea", false, false, false, 0, new Color(105, 205, 229), 569, 607);
		TFactory(AegeanSea, "Aegean Sea", false, false, false, 1, new Color(100, 205, 229), 738, 716);
		TFactory(Albania, "Albania", true, false, true, 2, new Color(182, 182, 182), 677, 674);
		TFactory(Ankara, "Ankara", true, true, true, 3, new Color(189, 189, 189), 872, 663);
		TFactory(Apu, "Apu", true, false, true, 4, new Color(85, 85, 85), 584, 683);
		TFactory(Armenia, "Armenia", true, false, false, 5, new Color(191, 191, 191), 1021, 664);
		TFactory(BalticSea, "Baltic Sea", false, false, false, 6, new Color(130, 205, 229), 574, 315);
		TFactory(BarentSea, "Barent Sea", false, false, false, 7, new Color(155, 205, 229), 716, 0);
		TFactory(Belgium, "Belgium", true, true, true, 8, new Color(145, 145, 145), 432, 450);
		TFactory(Berlin, "Berlin", true, true, true, 9, new Color(55, 55, 55), 564, 400);
		TFactory(BlackSea, "Black Sea", false, false, false, 10, new Color(90, 205, 229), 826, 542);
		TFactory(Bohmeia, "Bohmeia", true, false, false, 11, new Color(35, 35, 35), 576, 486);
		TFactory(Brest, "Brest", true, true, true, 12, new Color(170, 170, 170), 326, 475);
		TFactory(Budapest, "Budapest", true, true, true, 13, new Color(198, 198, 198), 637, 547);
		TFactory(Bulgaria, "Bulgaria", true, true, true, 14, new Color(181, 181, 181), 729, 646);
		TFactory(Bur, "Bur", true, false, false, 15, new Color(155, 155, 155), 411, 490);
		TFactory(Cly, "Cly", true, false, true, 16, new Color(110, 110, 110), 374, 268);
		TFactory(Constantinople, "Constantinople", true, true, true, 17, new Color(188, 188, 188), 793, 689);
		TFactory(Denmark, "Denmark", true, true, true, 18, new Color(40, 40, 40), 552, 323);
		TFactory(EasternMed, "Eastern Medditerian Sea", false, false, false, 19, new Color(95, 205, 229), 743, 783);
		TFactory(Edinburgh, "Edinburgh", true, true, true, 20, new Color(115, 115, 115), 398, 261);	
		TFactory(EnglishChannel, "English Channel", false, false, false, 21, new Color(150, 205, 229), 303, 435);	
		TFactory(Finland, "Finland", true, false, true, 22, new Color(178, 178, 178), 693, 58);		
		TFactory(Galicia, "Galicia", true, false, false, 74, new Color(199, 199, 199), 671, 490);		
		TFactory(Gascony, "Gascony", true, false, true, 23, new Color(175, 175, 175), 345, 557);
		TFactory(Greece, "Greece", true, true, true, 24, new Color(179, 179, 179), 691, 694);
		TFactory(GulfBothnia, "Gulf of Bothnia", false, false, false, 25, new Color(125, 205, 229), 654, 156);
		TFactory(GulfLyon, "Gulf of Lyon", false, false, false, 26, new Color(120, 205, 229), 353, 630);
		TFactory(Hel, "Hel", false, false, false, 27, new Color(140, 205, 229), 505, 353);
		TFactory(Holand, "Holand", true, true, true, 28, new Color(140, 140, 140), 472, 418);
		TFactory(IonianSea, "Ionian Sea", false, false, false, 29, new Color(110, 205, 229), 529, 719);
		TFactory(IrishSea, "Irish Sea", false, false, false, 30, new Color(160, 205, 229), 258, 329);
		TFactory(Kiel, "Kiel", true, true, true, 31, new Color(60, 60, 60), 504, 375);
		TFactory(Linova, "Linova", true, false, true, 32, new Color(190, 190, 190), 698, 320);
		TFactory(Liverpool, "Liverpool", true, true, true, 33, new Color(120, 120, 120), 378, 320);
		TFactory(London, "London", true, true, true, 34, new Color(130, 130, 130), 397, 395);
		TFactory(Marseilles, "Marseilles", true, true, true, 35, new Color(160, 160, 160), 389, 571);
		TFactory(MidAtlantic, "Mid Atlantic Ocean", false, false, false, 36, new Color(175, 205, 229), 0, 467);
		TFactory(Moscow, "Moscow", true, true, false, 37, new Color(194, 194, 194), 765, 289);
		TFactory(Munich, "Munich", true, true, false, 38, new Color(65, 65, 65), 488, 460);
		TFactory(NorthAfrica, "North Africa", true, false, true, 39, new Color(200, 200, 200), 113, 750);
		TFactory(Naples, "Naples", true, true, true, 40, new Color(80, 80, 80), 588, 716);
		TFactory(NorthAtlantic, "North Atlantic Ocean", false, false, false, 41, new Color(180, 205, 229), 0, 0);
		TFactory(NorthSea, "North Sea", false, false, false, 42, new Color(145, 205, 229), 427, 230);
		TFactory(Norway, "Norway", true, true, true, 43, new Color(176, 176, 176), 523, 36);
		TFactory(NorwegianSea, "Norwegian Sea", false, false, false, 44, new Color(165, 205, 229), 239, 0);
		TFactory(Paris, "Paris", true, true, false, 45, new Color(165, 165, 165), 392, 508);
		TFactory(Pic, "Pic", true, false, true, 46, new Color(150, 150, 150), 398, 473);
		TFactory(Pie, "Pie", true, false, true, 47, new Color(105, 105, 105), 483, 588);
		TFactory(Portugal, "Portugal", true, true, true, 48, new Color(185, 185, 185), 181, 611);
		TFactory(Prussia, "Prussia", true, false, true, 49, new Color(50, 50, 50), 619, 374);
		TFactory(Rome, "Rome", true, true, true, 50, new Color(90, 90, 90), 551, 675);
		TFactory(Ruhr, "Ruhr", true, false, false, 51, new Color(70, 70, 70), 486, 443);
		TFactory(Rumania, "Rumania", true, true, true, 52, new Color(184, 184, 184), 743, 553);
		TFactory(Serbia, "Serbia", true, true, false, 53, new Color(183, 183, 183), 679, 625);
		TFactory(Sevastopal, "Sevastopal", true, true, true, 54, new Color(192, 192, 192), 832, 424);
		TFactory(Silesia, "Silesia", true, false, false, 55, new Color(45, 45, 45), 572, 449);
		TFactory(Ska, "Ska", false, false, false, 56, new Color(135, 205, 229), 535, 303);
		TFactory(Smyrna, "Smyrna", true, true, true, 57, new Color(187, 187, 187), 803, 708);
		TFactory(Spain, "Spain", true, true, true, 58, new Color(180, 180, 180), 206, 574);
		TFactory(StPetersburgh, "St. Petersburgh", true, true, true, 59, new Color(195, 195, 195), 723, 0);
		TFactory(Sweden, "Sweden", true, true, true, 60, new Color(177, 177, 177), 589, 80);
		TFactory(Syria, "Syria", true, false, true, 61, new Color(186, 186, 186), 943, 771);
		TFactory(Trieste, "Trieste", true, true, true, 62, new Color(197, 197, 197), 586, 576);
		TFactory(Tunis, "Tunis", true, true, true, 63, new Color(75, 75, 75), 480, 785);
		TFactory(Tus, "Tus", true, false, true, 64, new Color(95, 95, 95), 525, 637);
		TFactory(Tyrol, "Tyrol", true, false, false, 65, new Color(30, 30, 30), 532, 541);
		TFactory(TyrrhenianSea, "Tyrrhenian Sea", false, false, false, 66, new Color(115, 205, 229), 503, 673);
		TFactory(Ukraine, "Ukraine", true, false, false, 67, new Color(193, 193, 193), 772, 440);
		TFactory(Venice, "Venice", true, true, true, 68, new Color(100, 100, 100), 535, 583);
		TFactory(Vienna, "Vienna", true, true, false, 69, new Color(205, 205, 205), 610, 531);
		TFactory(Wa, "Wa", true, true, true, 70, new Color(135, 135, 135), 327, 383);
		TFactory(Warsaw, "Warsaw", true, true, false, 71, new Color(196, 196, 196), 674, 432);
		TFactory(WestMed, "Western Medditerian Sea", false, false, false, 72, new Color(170, 205, 229), 231, 694);
		TFactory(York, "York", true, false, true, 73, new Color(125, 125, 125), 409, 329);
		
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
	 * Creates a Territory in the territories array and sets its position on the map.
	 * 
	 * @param ss -> the Spitesheet containing the territories images
	 * @param name -> the name to be displayed when the country is selected
	 * @param isLand -> boolean value to determine is territory is land or water
	 * @param hasSC -> boolean value to determine if the territory contains a supply center or not
	 * @param pos -> the position in the territories array as an int value
	 * @param color -> the color of the territory on the master map that will act as it's key value
	 * @param x -> the x position of the territory on the map
	 * @param y -> the y position of the territory on the map
	 */
	private void TFactory(SpriteSheet ss, String name, boolean isLand, boolean hasSC, boolean hasCoast, int pos, Color color, int x, int y){
		territories[pos] = new Territory(ss, name, isLand, hasSC, hasCoast, color);
		territories[pos].setX(x);
		territories[pos].setY(y);
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
					state = SELECT_SUPPORT_UNITS;
			}
			else if (currOrder.getCommand().equals("support")){
				currOrder.setTerr2(t);
				state = SELECT_SUPPORT_DESTINATION;
			}
			else if (currOrder.getCommand().equals("convoy")){
				currOrder.setTerr2(t);
				state = Canvas.SELECT_CONVOY;
			}
		}
		else if (state == SELECT_SUPPORT_DESTINATION){
			if (t.getUnit() != null){
				currOrder.setSupport(t.getUnit());
				state = NORM;
			}
		}
		else if (state == Canvas.SELECT_CONVOY){
			currOrder.setConvoyDestination(t);
			state = NORM;
		}
		else if (state == Canvas.SELECT_SUPPORT_UNITS){
			if (currOrder.isReady())
				state = NORM;
		}
			

	}

}
