package Driver;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import Objects.Territory;

import com.erebos.engine.core.*;
import com.erebos.engine.graphics.EAnimation;


public class Canvas extends ECanvas{

	Territory[] territories;
	Graphics g;
	private Territory disTerr;
	private int state;

	/* Singleton variable for the Canvas */
	private static Canvas c = null;
	
	public static final int START = 0;
	public static final int NORM = 1;
	public static final int DIS_TERR = 2;
	
	/* MasterMap contains the color keys for the individual territories.  It is 
	 * referenced in the Territory */
	private Image MasterMap;
	private Image Borders;
	
	/* SpriteSheets for units */
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
		state = 0;
	}
	
	public static Canvas getC() {
		if (c == null)
			c = new Canvas();
		return c;
	}

	@Override
	public void eInit(GameContainer gc, EGame eg) {			
		defineSS();
	    createTerritories();
	    setBoard();
	}

	/*
	 * Sets up the play area for a 7 teams.  Specifically it
	 * sets ownership for territories and generate units for each 
	 * team. 
	 */
	private void setBoard() {
		getT("Edinburgh").setOwner(Territory.ENGLAND, 2);
		getT("Liverpool").setOwner(Territory.ENGLAND, 1);
		getT("London").setOwner(Territory.ENGLAND, 2);
		getT("Vienna").setOwner(Territory.AUSTRIA_HUNGARY, 1);
		getT("Budapest").setOwner(Territory.AUSTRIA_HUNGARY, 1);
		getT("Trieste").setOwner(Territory.AUSTRIA_HUNGARY, 2);
		getT("Paris").setOwner(Territory.FRANCE, 1);
		getT("Brest").setOwner(Territory.FRANCE, 2);
		getT("Marseilles").setOwner(Territory.FRANCE, 1);
		getT("Berlin").setOwner(Territory.GERMANY, 1);
		getT("Kiel").setOwner(Territory.GERMANY, 2);
		getT("Munich").setOwner(Territory.GERMANY, 2);
		getT("Venice").setOwner(Territory.ITALY, 1);
		getT("Rome").setOwner(Territory.ITALY, 1);
		getT("Naples").setOwner(Territory.ITALY, 2);
		getT("St. Petersburgh").setOwner(Territory.RUSSIA, 2);
		getT("Moscow").setOwner(Territory.RUSSIA, 1);
		getT("Sevastopal").setOwner(Territory.RUSSIA, 2);
		getT("Warsaw").setOwner(Territory.RUSSIA, 1);
		getT("Ankara").setOwner(Territory.TURKEY, 2);
		getT("Constantinople").setOwner(Territory.TURKEY, 1);
		getT("Smyrna").setOwner(Territory.TURKEY, 1);
	}

	/*
	 * Defines all the SpriteSheet variables
	 */
	private void defineSS() {
		MasterMap=EAnimation.loadImage("/images/MasterMap.png");
		Borders=EAnimation.loadImage("/images/Borders.png");
		
		Image temp = EAnimation.loadImage("/images/LandUnit.png");
		landUnit = new SpriteSheet(temp, temp.getWidth()/7, temp.getHeight());
		temp = EAnimation.loadImage("/images/WaterUnit.png");
		waterUnit = new SpriteSheet(temp, temp.getWidth()/7, temp.getHeight());
		
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
		this.g = g;
		g.setColor(Color.white);
		g.fillRect(0, 0, 1126, gc.getHeight());
		g.setColor(Color.green);
		g.fillRect(1106, 0, 1400-1126, gc.getHeight());
		g.setColor(Color.black);
		g.drawString("Country:", 1150, 50);
		g.drawString("Owner:", 1150, 120);
		g.drawString("Contains Supply Center:", 1150, 180);
		g.drawString("Terrain type:", 1150, 240);
		g.drawImage(MasterMap, 0, 0);
		
		for (Territory t: territories)
			t.eDraw();
		g.drawImage(Borders, 0, 0);
		for (Territory t: territories){
			if (t.getUnit() == t.LAND)
				new EAnimation(landUnit.getSprite(t.getOwner()-1, 0)).draw(t.getX(), t.getY());
			else if (t.getUnit() == t.WATER)
				new EAnimation(waterUnit.getSprite(t.getOwner()-1, 0)).draw(t.getX(), t.getY());
		}
		
		switch (state) {
		case DIS_TERR: {
			g.drawString(disTerr.getName(), 1145, 70);
			g.drawString(disTerr.getOwnerName(), 1145, 140);
			if (disTerr.hasSC())
				g.drawString("Yes", 1145, 200);
			else
				g.drawString("No", 1145, 200);
			if (disTerr.isLand())
				g.drawString("Land", 1145, 260);
			else
				g.drawString("Water", 1145, 260);
		}
		}
	}

	@Override
	public void eUpdate(GameContainer gc, EGame eg, int delta) {		
		updateGame();
	}

	/*
	 * Updates the game on for the current frame. Game is set to run at 60 frames per second so this fires 60 times a second.
	 */
	private void updateGame() {
		int mx = Mouse.getX();
		int my = Math.abs(Mouse.getY() - 831);
		for (Territory t: territories){
			if (mx >= t.getX() && mx <= t.getWidth()+t.getX() && my >= t.getY() && my <= t.getHeight()+t.getY())
				t.update();
		}
	}

	/*
	 * As the name says.  Used when initializing the game.
	 */
	public void createTerritories(){
		
		territories = new Territory[75];
		TFactory(AdriaticSea, "Adriatic Sea", false, false, 0, new Color(105, 205, 229), 569, 607);
		TFactory(AegeanSea, "Aegean Sea", false, false, 1, new Color(100, 205, 229), 738, 716);
		TFactory(Albania, "Albania", true, false, 2, new Color(182, 182, 182), 677, 674);
		TFactory(Ankara, "Ankara", true, true, 3, new Color(189, 189, 189), 872, 663);
		TFactory(Apu, "Apu", true, false, 4, new Color(85, 85, 85), 584, 683);
		TFactory(Armenia, "Armenia", true, false, 5, new Color(191, 191, 191), 1021, 664);
		TFactory(BalticSea, "Baltic Sea", false, false, 6, new Color(130, 205, 229), 574, 315);
		TFactory(BarentSea, "Barent Sea", false, false, 7, new Color(155, 205, 229), 716, 0);
		TFactory(Belgium, "Belgium", true, true, 8, new Color(145, 145, 145), 432, 450);
		TFactory(Berlin, "Berlin", true, true, 9, new Color(55, 55, 55), 564, 400);
		TFactory(BlackSea, "Black Sea", false, false, 10, new Color(90, 205, 229), 826, 542);
		TFactory(Bohmeia, "Bohmeia", true, false, 11, new Color(35, 35, 35), 576, 486);
		TFactory(Brest, "Brest", true, true, 12, new Color(170, 170, 170), 326, 475);
		TFactory(Budapest, "Budapest", true, true, 13, new Color(198, 198, 198), 637, 547);
		TFactory(Bulgaria, "Bulgaria", true, true, 14, new Color(181, 181, 181), 729, 646);
		TFactory(Bur, "Bur", true, false, 15, new Color(155, 155, 155), 411, 490);
		TFactory(Cly, "Cly", true, false, 16, new Color(110, 110, 110), 374, 268);
		TFactory(Constantinople, "Constantinople", true, true, 17, new Color(188, 188, 188), 793, 689);
		TFactory(Denmark, "Denmark", true, true, 18, new Color(40, 40, 40), 552, 323);
		TFactory(EasternMed, "Eastern Medditerian Sea", false, false, 19, new Color(95, 205, 229), 743, 783);
		TFactory(Edinburgh, "Edinburgh", true, true, 20, new Color(115, 115, 115), 398, 261);	
		TFactory(EnglishChannel, "English Channel", false, false, 21, new Color(150, 205, 229), 303, 435);	
		TFactory(Finland, "Finland", true, true, 22, new Color(178, 178, 178), 693, 58);		
		TFactory(Galicia, "Galicia", true, false, 74, new Color(199, 199, 199), 671, 490);		
		TFactory(Gascony, "Gascony", true, false, 23, new Color(175, 175, 175), 345, 557);
		TFactory(Greece, "Greece", true, true, 24, new Color(179, 179, 179), 691, 694);
		TFactory(GulfBothnia, "Gulf of Bothnia", false, false, 25, new Color(125, 205, 229), 654, 156);
		TFactory(GulfLyon, "Gulf of Lyon", false, false, 26, new Color(120, 205, 229), 353, 630);
		TFactory(Hel, "Hel", false, false, 27, new Color(140, 205, 229), 505, 353);
		TFactory(Holand, "Holand", true, true, 28, new Color(140, 140, 140), 472, 418);
		TFactory(IonianSea, "Ionian Sea", false, false, 29, new Color(110, 205, 229), 529, 719);
		TFactory(IrishSea, "Irish Sea", false, false, 30, new Color(160, 205, 229), 258, 329);
		TFactory(Kiel, "Kiel", true, true, 31, new Color(60, 60, 60), 504, 375);
		TFactory(Linova, "Linova", true, false, 32, new Color(190, 190, 190), 698, 320);
		TFactory(Liverpool, "Liverpool", true, true, 33, new Color(120, 120, 120), 378, 320);
		TFactory(London, "London", true, true, 34, new Color(130, 130, 130), 397, 395);
		TFactory(Marseilles, "Marseilles", true, true, 35, new Color(160, 160, 160), 389, 571);
		TFactory(MidAtlantic, "Mid Atlantic Ocean", false, false, 36, new Color(175, 205, 229), 0, 467);
		TFactory(Moscow, "Moscow", true, true, 37, new Color(194, 194, 194), 765, 289);
		TFactory(Munich, "Munich", true, true, 38, new Color(65, 65, 65), 488, 460);
		TFactory(NorthAfrica, "North Africa", true, false, 39, new Color(200, 200, 200), 113, 750);
		TFactory(Naples, "Naples", true, true, 40, new Color(80, 80, 80), 588, 716);
		TFactory(NorthAtlantic, "North Atlantic Ocean", false, false, 41, new Color(180, 205, 229), 0, 0);
		TFactory(NorthSea, "North Sea", false, false, 42, new Color(145, 205, 229), 427, 230);
		TFactory(Norway, "Norway", true, true, 43, new Color(176, 176, 176), 523, 36);
		TFactory(NorwegianSea, "Norwegian Sea", false, false, 44, new Color(165, 205, 229), 239, 0);
		TFactory(Paris, "Paris", true, true, 45, new Color(165, 165, 165), 392, 508);
		TFactory(Pic, "Pic", true, false, 46, new Color(150, 150, 150), 398, 473);
		TFactory(Pie, "Pie", true, false, 47, new Color(105, 105, 105), 483, 588);
		TFactory(Portugal, "Portugal", true, true, 48, new Color(185, 185, 185), 181, 611);
		TFactory(Prussia, "Prussia", true, false, 49, new Color(50, 50, 50), 619, 374);
		TFactory(Rome, "Rome", true, true, 50, new Color(90, 90, 90), 551, 675);
		TFactory(Ruhr, "Ruhr", true, false, 51, new Color(70, 70, 70), 486, 443);
		TFactory(Rumania, "Rumania", true, true, 52, new Color(184, 184, 184), 743, 553);
		TFactory(Serbia, "Serbia", true, true, 53, new Color(183, 183, 183), 679, 625);
		TFactory(Sevastopal, "Sevastopal", true, true, 54, new Color(192, 192, 192), 832, 424);
		TFactory(Silesia, "Silesia", true, false, 55, new Color(45, 45, 45), 572, 449);
		TFactory(Ska, "Ska", false, false, 56, new Color(135, 205, 229), 535, 303);
		TFactory(Smyrna, "Smyrna", true, true, 57, new Color(187, 187, 187), 803, 708);
		TFactory(Spain, "Spain", true, true, 58, new Color(180, 180, 180), 206, 574);
		TFactory(StPetersburgh, "St. Petersburgh", true, true, 59, new Color(195, 195, 195), 723, 0);
		TFactory(Sweden, "Sweden", true, true, 60, new Color(177, 177, 177), 589, 80);
		TFactory(Syria, "Syria", true, false, 61, new Color(186, 186, 186), 943, 771);
		TFactory(Trieste, "Trieste", true, true, 62, new Color(197, 197, 197), 586, 576);
		TFactory(Tunis, "Tunis", true, true, 63, new Color(75, 75, 75), 480, 785);
		TFactory(Tus, "Tus", true, false, 64, new Color(95, 95, 95), 525, 637);
		TFactory(Tyrol, "Tyrol", true, false, 65, new Color(30, 30, 30), 532, 541);
		TFactory(TyrrhenianSea, "Tyrrhenian Sea", false, false, 66, new Color(115, 205, 229), 503, 673);
		TFactory(Ukraine, "Ukraine", true, false, 67, new Color(193, 193, 193), 772, 440);
		TFactory(Venice, "Venice", true, true, 68, new Color(100, 100, 100), 535, 583);
		TFactory(Vienna, "Vienna", true, true, 69, new Color(205, 205, 205), 610, 531);
		TFactory(Wa, "Wa", true, true, 70, new Color(135, 135, 135), 327, 383);
		TFactory(Warsaw, "Warsaw", true, true, 71, new Color(196, 196, 196), 674, 432);
		TFactory(WestMed, "Western Medditerian Sea", false, false, 72, new Color(170, 205, 229), 231, 694);
		TFactory(York, "York", true, false, 73, new Color(125, 125, 125), 409, 329);
		
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
	private void TFactory(SpriteSheet ss, String name, boolean isLand, boolean hasSC, int pos, Color color, int x, int y){
		territories[pos] = new Territory(ss, name, isLand, hasSC, color);
		territories[pos].setX(x);
		territories[pos].setY(y);
	}
	
	/*
	 * Gets the Color from the current mouse location.  Used to access color keys.
	 * 
	 * @returns Color -> the Color at the current mouse position
	 */
	public Color getCurrentColor(){
		return new Color(MasterMap.getColor(Mouse.getX(), Math.abs(Mouse.getY()-831)));
	}
	
	/*
	 * Sets the Territory name to be displayed
	 * 
	 * @param name -> the name of the territory 
	 */
	public void setDisTerr(Territory t){
		disTerr = t;
	}
	
	/*
	 * Sets the current state of the game.  Used for updating the game.
	 * 
	 * @param s -> the state of the game as an int value.  See class fields for state descriptions
	 */
	public void setState(int s){
		state = s;
	}
	
	private Territory getT(String name){
		for (Territory t : territories){
			System.out.println(t.getName());
			if (t.getName().equals(name))
				return t;
		}
		return null;
	}

}
