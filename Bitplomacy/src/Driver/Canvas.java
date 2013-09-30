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
	private String tName;
	private int state;

	/* Singleton variable for the Canvas */
	private static Canvas c = null;
	
	final int NORM = 0;
	final int DIS_TERR = 1;
	
	/* MasterMap contains the color keys for the individual territories.  It is 
	 * referenced in the Territory */
	private Image MasterMap;
	private Image Borders;
	
	/* Converting all these images to spritesheets to handle different team colors.  
	 * These Images will need to be remade into SpriteSheets as seen below.        */
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
	/* Handy dandy SpriteSheets for territories. */
	private SpriteSheet AdriaticSea;
	private SpriteSheet AegeanSea;
	private SpriteSheet Albania;
	private SpriteSheet Ankara;
	private SpriteSheet Apu;
	private SpriteSheet Armenia;

	private Canvas(){
		super(1);
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
	}

	/*
	 * Defines all the SpriteSheet variables
	 */
	private void defineSS() {
		MasterMap=EAnimation.loadImage("/images/MasterMap.png");
		Borders=EAnimation.loadImage("/images/Borders.png");
		
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
		g.drawString("Country:", 1190, 50);
		g.drawImage(MasterMap, 0, 0);
		for (Territory t: territories)
			t.eDraw();
		g.drawImage(Borders, 0, 0);
		
		switch (state){
		case DIS_TERR: {
			g.drawString(tName, 1185, 70);
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
		for (Territory t: territories){
			int mx = Mouse.getX();
			int my = Math.abs(Mouse.getY() - 831);
			if (mx >= t.getX() && mx <= t.getWidth()+t.getX() && my >= t.getY() && my <= t.getHeight()+t.getY())
				t.update();
		}
	}

	/*
	 * As the name says.  Used when initializing the game.
	 */
	public void createTerritories(){
		
		territories = new Territory[24];
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
		TFactory(EasternMed, "EasternMed", false, false, 19, new Color(95, 205, 229), 743, 783);
		TFactory(Edinburgh, "Edinburgh", true, true, 20, new Color(115, 115, 115), 398, 261);	
		TFactory(EnglishChannel, "EnglishChannel", false, false, 21, new Color(150, 205, 229), 303, 435);	
		TFactory(Finland, "Finland", true, true, 22, new Color(178, 178, 178), 693, 58);		
		TFactory(Gascony, "Gascony", true, false, 23, new Color(175, 175, 175), 345, 557);
/*
		TFactory(Budapest, "Budapest", true, true, 24, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 25, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 26, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 27, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 28, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 29, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 30, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 31, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 32, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 33, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 34, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 35, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 36, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 37, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 38, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 39, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 40, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 41, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 42, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 43, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 44, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 45, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 46, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 47, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 48, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 49, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 50, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 51, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 52, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 53, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 54, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 55, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 56, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 57, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 58, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 59, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 60, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 61, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 62, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 63, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 64, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 65, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 66, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 67, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 68, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 69, new Color(198, 198, 198), 637, 547);
		TFactory(Budapest, "Budapest", true, true, 70, new Color(198, 198, 198), 637, 547);

		/* Below still needs to be converted to spritesheets and Tfactory method calls. */
		
		/* Oceans first 
		territories[0] = new Territory(gc, NorwegianSea, "Norwegian Sea", this, false, 165, 205, 229);
		territories[0].setX(239);
		territories[0].setY(0);
		territories[1] = new Territory(gc, IrishSea, "Irish Sea", this, false, 160, 205, 229);
		territories[1].setX(258);
		territories[1].setY(329);
		territories[2] = new Territory(gc, MidAtlantic, "Mid Atlantic Ocean", this, false, 175, 205, 229);
		territories[2].setX(0);
		territories[2].setY(467);
		
		territories[4] = new Territory(gc, NorthAtlantic, "North Atlantic Ocean", this, false, 180, 205, 229);
		territories[4].setX(0);
		territories[4].setY(0);

		territories[6] = new Territory(gc, NorthSea, "North Sea", this, false, 145, 205, 229);
		territories[6].setX(427);
		territories[6].setY(230);
		territories[7] = new Territory(gc, Hel, "Hel", this, false, 140, 205, 229);
		territories[7].setX(505);
		territories[7].setY(353);
		territories[8] = new Territory(gc, Ska, "Ska", this, false, 135, 205, 229);
		territories[8].setX(535);
		territories[8].setY(303);
		
		territories[10] = new Territory(gc, GulfBothnia, "Gulf of Bothnia", this, false, 125, 205, 229);
		territories[10].setX(654);
		territories[10].setY(156);
		territories[11] = new Territory(gc, WestMed, "Western Medditerian Sea", this, false, 170, 205, 229);
		territories[11].setX(231);
		territories[11].setY(694);
		territories[12] = new Territory(gc, GulfLyon, "Gulf of Lyon", this, false, 120, 205, 229);
		territories[12].setX(353);
		territories[12].setY(630);
		territories[13] = new Territory(gc, TyrrhenianSea, "Tyrrhenian Sea", this, false, 115, 205, 229);
		territories[13].setX(503);
		territories[13].setY(673);
		territories[14] = new Territory(gc, IonianSea, "Ionian Sea", this, false, 110, 205, 229);
		territories[14].setX(529);
		territories[14].setY(719);
		territories[15] = new Territory(gc, AdriaticSea, "Adriatic Sea", this, false, 105, 205, 229);
		territories[15].setX(569);
		territories[15].setY(607);
		territories[16] = new Territory(gc, AegeanSea, "Aegean Sea", this, false, 100, 205, 229);
		territories[16].setX(738);
		territories[16].setY(716);

		
		/* Water territories completed */
		
		
		/* Land Second */
		/* AFRICA 
		territories[19] = new Territory(gc, NorthAfrica, "North Africa", this, true, 200, 200, 200);
		territories[19].setX(113);
		territories[19].setY(750);
		territories[20] = new Territory(gc, Tunis, "Tunis", this, true, 75, 75, 75);
		territories[20].setX(480);
		territories[20].setY(785);
		
		/* RUSSIA 
		territories[21] = new Territory(gc, StPetersburgh, "St. Petersburgh", this, true, 195, 195, 195);
		territories[21].setX(723);
		territories[21].setY(0);
		territories[22] = new Territory(gc, Linova, "Linova", this, true, 190, 190, 190);
		territories[22].setX(698);
		territories[22].setY(320);
		territories[23] = new Territory(gc, Warsaw, "Warsaw", this, true, 196, 196, 196);
		territories[23].setX(674);
		territories[23].setY(432);
		territories[24] = new Territory(gc, Moscow, "Moscow", this, true, 194, 194, 194);
		territories[24].setX(765);
		territories[24].setY(289);
		territories[25] = new Territory(gc, Ukraine, "Ukraine", this, true, 193, 193, 193);
		territories[25].setX(772);
		territories[25].setY(440);
		territories[26] = new Territory(gc, Sevastopal, "Sevastopal", this, true, 192, 192, 192);
		territories[26].setX(832);
		territories[26].setY(424);
		
		/* FRANCE 
		territories[27] = new Territory(gc, Marseilles, "Marseilles", this, true, 160, 160, 160);
		territories[27].setX(389);
		territories[27].setY(571);

		territories[29] = new Territory(gc, Pic, "Pic", this, true, 150, 150, 150);
		territories[29].setX(398);
		territories[29].setY(473);

		
		territories[32] = new Territory(gc, Paris, "Paris", this, true, 165, 165, 165);
		territories[32].setX(392);
		territories[32].setY(508);
		
		/* ENGLAND 
		territories[33] = new Territory(gc, Wa, "Wa", this, true, 135, 135, 135);
		territories[33].setX(327);
		territories[33].setY(383);
		territories[34] = new Territory(gc, London, "London", this, true, 130, 130, 130);
		territories[34].setX(397);
		territories[34].setY(395);
		
		territories[36] = new Territory(gc, York, "York", this, true, 125, 125, 125);
		territories[36].setX(409);
		territories[36].setY(329);
		territories[37] = new Territory(gc, Liverpool, "Liverpool", this, true, 120, 120, 120);
		territories[37].setX(378);
		territories[37].setY(320);

		
		/* ITALY 
		territories[39] = new Territory(gc, Rome, "Rome", this, true, 90, 90, 90);
		territories[39].setX(551);
		territories[39].setY(675);
		
		territories[41] = new Territory(gc, Naples,x "Naples", this, true, 80, 80, 80);
		territories[41].setX(588);
		territories[41].setY(716);
		territories[42] = new Territory(gc, Pie, "Pie", this, true, 105, 105, 105);
		territories[42].setX(483);
		territories[42].setY(588);
		territories[43] = new Territory(gc, Venice, "Venice", this, true, 100, 100, 100);
		territories[43].setX(535);
		territories[43].setY(583);
		territories[44] = new Territory(gc, Tus, "Tus", this, true, 95, 95, 95);
		territories[44].setX(525);
		territories[44].setY(637);
		
		/* GERMANY 
		territories[45] = new Territory(gc, Kiel, "Kiel", this, true, 60, 60, 60);
		territories[45].setX(504);
		territories[45].setY(375);
		
		territories[47] = new Territory(gc, Prussia, "Prussia", this, true, 50, 50, 50);
		territories[47].setX(619);
		territories[47].setY(374);
		territories[48] = new Territory(gc, Silesia, "Silesia", this, true, 45, 45, 45);
		territories[48].setX(572);
		territories[48].setY(449);
		territories[49] = new Territory(gc, Ruhr, "Ruhr", this, true, 70, 70, 70);
		territories[49].setX(486);
		territories[49].setY(443);
		territories[50] = new Territory(gc, Munich, "Munich", this, true, 65, 65, 65);
		territories[50].setX(488);
		territories[50].setY(460);
		
		/* AUSTRIA-HUNGARY 
		territories[51] = new Territory(gc, Tyrol, "Tyrol", this, true, 30, 30, 30);
		territories[51].setX(532);
		territories[51].setY(541);
		territories[52] = new Territory(gc, Vienna, "Vienna", this, true, 205, 205, 205);
		territories[52].setX(610);
		territories[52].setY(531);


		territories[55] = new Territory(gc, Trieste, "Trieste", this, true, 197, 197, 197);
		territories[55].setX(586);
		territories[55].setY(576);
		
		
		/* TURKEY 
		territories[57] = new Territory(gc, Smyrna, "Smyrna", this, true, 187, 187, 187);
		territories[57].setX(803);
		territories[57].setY(708);
		territories[58] = new Territory(gc, Syria, "Syria", this, true, 186, 186, 186);
		territories[58].setX(943);
		territories[58].setY(771);
		
		
	
		
		/* RANDOM 
		territories[62] = new Territory(gc, Holand, "Holand", this, true, 140, 140, 140);
		territories[62].setX(472);
		territories[62].setY(418);
		
		territories[64] = new Territory(gc, Rumania, "Rumania", this, true, 184, 184, 184);
		territories[64].setX(743);
		territories[64].setY(553);
		territories[65] = new Territory(gc, Serbia, "Serbia", this, true, 183, 183, 183);
		territories[65].setX(679);
		territories[65].setY(625);

		territories[68] = new Territory(gc, Greece, "Greece", this, true, 179, 179, 179);
		territories[68].setX(691);
		territories[68].setY(694);

		territories[70] = new Territory(gc, Sweden, "Sweden", this, true, 177, 177, 177);
		territories[70].setX(589);
		territories[70].setY(80);
		territories[71] = new Territory(gc, Norway, "Norway", this, true, 176, 176, 176);
		territories[71].setX(523);
		territories[71].setY(36);
		territories[72] = new Territory(gc, Portugal, "Portugal", this, true, 185, 185, 185);
		territories[72].setX(181);
		territories[72].setY(611);
		territories[73] = new Territory(gc, Spain, "Spain", this, true, 180, 180, 180);
		territories[73].setX(206);
		territories[73].setY(574);
		
		/* Land territories completed */
		
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
	public void setTName(String name){
		tName = name;
	}
	
	/*
	 * Sets the current state of the game.  Used for updating the game.
	 * 
	 * @param s -> the state of the game as an int value.  See class fields for state descriptions
	 */
	public void setState(int s){
		state = s;
	}
}
