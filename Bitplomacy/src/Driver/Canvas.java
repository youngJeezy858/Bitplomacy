package Driver;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import Objects.Territory;

import com.erebos.engine.core.*;
import com.erebos.engine.graphics.EAnimation;


public class Canvas extends ECanvas{

	/* Oceans */
	Territory[] territories;
	Graphics g;
	public int state;
	public String tName;
	final int NORM = 0;
	final int DIS_TERR = 1;
	public Image MasterMap;
	private Image NorthAfrica;
	private Image MidAtlantic;
	private Image NorthAtlantic;
	private Image NorwegianSea;
	private Image BarentSea;
	private Image StPetersburgh;
	private Image Linova;
	private Image IrishSea;
	private Image EnglishChannel;
	private Image NorthSea;
	private Image Hel;
	private Image Ska;
	private Image BalticSea;
	private Image GulfBothnia;
	private Image WestMed;
	private Image GulfLyon;
	private Image TyrrhenianSea;
	private Image IonianSea;
	private Image Borders;
	private Image EasternMed;
	private Image BlackSea;
	private Image Tunis;
	private Image Warsaw;
	private Image Moscow;
	private Image Ukraine;
	
	private Image Sevastopal;
	private Image Marseilles;
	private Image Brest;
	private Image Paris;
	private Image Bur;
	private Image Pic;
	private Image Gascony;
	private Image Wa;
	private Image London;
	private Image Liverpool;
	private Image Edinburgh;
	private Image York;
	private Image Cly;
	private Image Rome;
	private Image Naples;
	private Image Pie;
	private Image Venice;
	private Image Tus;
	private Image Kiel;
	private Image Berlin;
	private Image Prussia;
	private Image Silesia;
	private Image Ruhr;
	private Image Munich;
	private Image Tyrol;
	private Image Vienna;
	private Image Galicia;
	private Image Budapest;
	private Image Trieste;
	private Image Bohmeia;
	private Image Smyrna;
	private Image Syria;
	private Image Armenia;
	private Image Constantinople;
	private Image Holand;
	private Image Denmark;
	private Image Rumania;
	private Image Serbia;
	private Image Bulgaria;
	private Image Greece;
	private Image Finland;
	private Image Sweden;
	private Image Norway;
	private Image Portugal;
	private Image Spain;
	private Image Belgium;
	private SpriteSheet AdriaticSea;
	private SpriteSheet AegeanSea;
	private SpriteSheet Albania;
	private SpriteSheet Ankara;
	private SpriteSheet Apu;

	public Canvas(int ID) {
		super(ID);
		tName = "";
	}

	@Override
	public void eInit(GameContainer gc, EGame eg) {	
				
		MasterMap=EAnimation.loadImage("/images/MasterMap.png");
		Borders=EAnimation.loadImage("/images/Borders.png");
		AdriaticSea = new SpriteSheet(EAnimation.loadImage("/images/AdriaticSea.png"), 109, 116);
	    AegeanSea=new SpriteSheet(EAnimation.loadImage("/images/AegeanSea.png"), 70, 102);
	    Albania=new SpriteSheet(EAnimation.loadImage("/images/Albania.png"), 23, 54);
	    Ankara=new SpriteSheet(EAnimation.loadImage("/images/Ankara.png"), 173, 82);
	    Apu=new SpriteSheet(EAnimation.loadImage("/images/Apu.png"), 78, 47);

		MidAtlantic=EAnimation.loadImage("/images/MidAtlantic.png");
	    NorthAtlantic=EAnimation.loadImage("/images/NorthAtlantic.png");
	    NorwegianSea=EAnimation.loadImage("/images/NorwegianSea.png");
	    BarentSea=EAnimation.loadImage("/images/BarentSea.png");
	    IrishSea=EAnimation.loadImage("/images/IrishSea.png");
	    EnglishChannel=EAnimation.loadImage("/images/EnglishChannel.png");
	    NorthSea=EAnimation.loadImage("/images/NorthSea.png");
	    Hel=EAnimation.loadImage("/images/Hel.png");
	    Ska=EAnimation.loadImage("/images/Ska.png");
	    BalticSea=EAnimation.loadImage("/images/BalticSea.png");
	    GulfBothnia=EAnimation.loadImage("/images/GulfBothnia.png");
	    WestMed=EAnimation.loadImage("/images/WestMed.png");
	    GulfLyon=EAnimation.loadImage("/images/GulfLyon.png");
	    TyrrhenianSea=EAnimation.loadImage("/images/TyrrhenianSea.png");
	    IonianSea=EAnimation.loadImage("/images/IonianSea.png");
	    EasternMed=EAnimation.loadImage("/images/EasternMed.png");
	    BlackSea=EAnimation.loadImage("/images/BlackSea.png");
	    /* LAND */
	    NorthAfrica=EAnimation.loadImage("/images/NAfrica.png");
	    Tunis=EAnimation.loadImage("/images/Tunis.png");
	    Linova=EAnimation.loadImage("/images/Linova.png");
	    StPetersburgh=EAnimation.loadImage("/images/StPetersburgh.png");
	    Warsaw=EAnimation.loadImage("/images/Warsaw.png");
	    Moscow=EAnimation.loadImage("/images/Moscow.png");
	    Ukraine=EAnimation.loadImage("/images/Ukraine.png");
	    Sevastopal=EAnimation.loadImage("/images/Sevastopal.png");
	    Marseilles=EAnimation.loadImage("/images/Marseilles.png");
	    Brest=EAnimation.loadImage("/images/Brest.png");
	    Paris=EAnimation.loadImage("/images/Paris.png");
	    Bur=EAnimation.loadImage("/images/Bur.png");
	    Pic=EAnimation.loadImage("/images/Pic.png");
	    Gascony=EAnimation.loadImage("/images/Gascony.png");
	    Kiel=EAnimation.loadImage("/images/Kiel.png");
	    Wa=EAnimation.loadImage("/images/Wa.png");
	    London=EAnimation.loadImage("/images/London.png");
	    Liverpool=EAnimation.loadImage("/images/Liverpool.png");
	    Edinburgh=EAnimation.loadImage("/images/Edinburgh.png");
	    York=EAnimation.loadImage("/images/York.png");
	    Cly=EAnimation.loadImage("/images/Cly.png");
	    Rome=EAnimation.loadImage("/images/Rome.png");
	    Naples=EAnimation.loadImage("/images/Naples.png");
	    Pie=EAnimation.loadImage("/images/Pie.png");
	    Venice=EAnimation.loadImage("/images/Venice.png");
	    Tus=EAnimation.loadImage("/images/Tus.png");
	    Berlin=EAnimation.loadImage("/images/Berlin.png");
	    Prussia=EAnimation.loadImage("/images/Prussia.png");
	    Silesia=EAnimation.loadImage("/images/Silesia.png");
	    Ruhr=EAnimation.loadImage("/images/Ruhr.png");
	    Munich=EAnimation.loadImage("/images/Munich.png");
	    Tyrol=EAnimation.loadImage("/images/Tyrol.png");
	    Vienna=EAnimation.loadImage("/images/Vienna.png");
	    Galicia=EAnimation.loadImage("/images/Galicia.png");
	    Budapest=EAnimation.loadImage("/images/Budapest.png");
	    Trieste=EAnimation.loadImage("/images/Trieste.png");
	    Bohmeia=EAnimation.loadImage("/images/Bohmeia.png");
	    Smyrna=EAnimation.loadImage("/images/Smyrna.png");
	    Syria=EAnimation.loadImage("/images/Syria.png");
	    Armenia=EAnimation.loadImage("/images/Armenia.png");
	    Constantinople=EAnimation.loadImage("/images/Constantinople.png");
	    Holand=EAnimation.loadImage("/images/Holand.png");
	    Denmark=EAnimation.loadImage("/images/Denmark.png");
	    Rumania=EAnimation.loadImage("/images/Rumania.png");
	    Serbia=EAnimation.loadImage("/images/Serbia.png");
	    Bulgaria=EAnimation.loadImage("/images/Bulgaria.png");
	    Greece=EAnimation.loadImage("/images/Greece.png");
	    Finland=EAnimation.loadImage("/images/Finland.png");
	    Sweden=EAnimation.loadImage("/images/Sweden.png");
	    Norway=EAnimation.loadImage("/images/Norway.png");
	    Portugal=EAnimation.loadImage("/images/Portugal.png");
	    Spain=EAnimation.loadImage("/images/Spain.png");
	    Belgium=EAnimation.loadImage("/images/Belgium.png");

	    
	    createTerritories(gc);
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
		updateGame(delta);
	}

	private void updateGame(int delta) {
		for (Territory t: territories){
			int mx = Mouse.getX();
			int my = Math.abs(Mouse.getY() - 831);
			if (mx >= t.getX() && mx <= t.getWidth()+t.getX() && my >= t.getY() && my <= t.getHeight()+t.getY())
				t.update(delta);
		}
	}

	public void createTerritories(GameContainer gc){
		
		
		territories = new Territory[5];
		TFactory(AdriaticSea, "Adriatic Sea", false, 0, new Color(105, 205, 229), 569, 607);
		TFactory(AegeanSea, "Aegean Sea", false, 1, new Color(100, 205, 229), 738, 716);
		TFactory(Albania, "Albania", true, 2, new Color(182, 182, 182), 677, 674);
		TFactory(Ankara, "Ankara", true, 3, new Color(189, 189, 189), 872, 663);
		TFactory(Apu, "Apu", true, 4, new Color(85, 85, 85), 584, 683);


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
		territories[3] = new Territory(gc, BarentSea, "Barent's Sea", this, false, 155, 205, 229);
		territories[3].setX(716);
		territories[3].setY(0);
		territories[4] = new Territory(gc, NorthAtlantic, "North Atlantic Ocean", this, false, 180, 205, 229);
		territories[4].setX(0);
		territories[4].setY(0);
		territories[5] = new Territory(gc, EnglishChannel, "English Channel", this, false, 150, 205, 229);
		territories[5].setX(303);
		territories[5].setY(435);
		territories[6] = new Territory(gc, NorthSea, "North Sea", this, false, 145, 205, 229);
		territories[6].setX(427);
		territories[6].setY(230);
		territories[7] = new Territory(gc, Hel, "Hel", this, false, 140, 205, 229);
		territories[7].setX(505);
		territories[7].setY(353);
		territories[8] = new Territory(gc, Ska, "Ska", this, false, 135, 205, 229);
		territories[8].setX(535);
		territories[8].setY(303);
		territories[9] = new Territory(gc, BalticSea, "Baltic Sea", this, false, 130, 205, 229);
		territories[9].setX(574);
		territories[9].setY(315);
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
		territories[17] = new Territory(gc, EasternMed, "Eastern Medditerian Sea", this, false, 95, 205, 229);
		territories[17].setX(743);
		territories[17].setY(783);
		territories[18] = new Territory(gc, BlackSea, "Black Sea", this, false, 90, 205, 229);
		territories[18].setX(826);
		territories[18].setY(542);
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
		territories[28] = new Territory(gc, Bur, "Bur", this, true, 155, 155, 155);
		territories[28].setX(411);
		territories[28].setY(490);
		territories[29] = new Territory(gc, Pic, "Pic", this, true, 150, 150, 150);
		territories[29].setX(398);
		territories[29].setY(473);
		territories[30] = new Territory(gc, Gascony, "Gascony", this, true, 175, 175, 175);
		territories[30].setX(345);
		territories[30].setY(557);
		territories[31] = new Territory(gc, Brest, "Brest", this, true, 170, 170, 170);
		territories[31].setX(326);
		territories[31].setY(475);
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
		territories[35] = new Territory(gc, Cly, "Cly", this, true, 110, 110, 110);
		territories[35].setX(374);
		territories[35].setY(268);
		territories[36] = new Territory(gc, York, "York", this, true, 125, 125, 125);
		territories[36].setX(409);
		territories[36].setY(329);
		territories[37] = new Territory(gc, Liverpool, "Liverpool", this, true, 120, 120, 120);
		territories[37].setX(378);
		territories[37].setY(320);
		territories[38] = new Territory(gc, Edinburgh, "Edinburgh", this, true, 115, 115, 115);
		territories[38].setX(398);
		territories[38].setY(261);
		
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
		territories[46] = new Territory(gc, Berlin, "Berlin", this, true, 55, 55, 55);
		territories[46].setX(564);
		territories[46].setY(400);
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
		territories[53] = new Territory(gc, Galicia, "Galicia", this, true, 199, 199, 199);
		territories[53].setX(671);
		territories[53].setY(490);
		territories[54] = new Territory(gc, Budapest, "Budapest", this, true, 198, 198, 198);
		territories[54].setX(637);
		territories[54].setY(547);
		territories[55] = new Territory(gc, Trieste, "Trieste", this, true, 197, 197, 197);
		territories[55].setX(586);
		territories[55].setY(576);
		territories[56] = new Territory(gc, Bohmeia, "Bohmeia", this, true, 35, 35, 35);
		territories[56].setX(576);
		territories[56].setY(486);
		
		/* TURKEY 
		territories[57] = new Territory(gc, Smyrna, "Smyrna", this, true, 187, 187, 187);
		territories[57].setX(803);
		territories[57].setY(708);
		territories[58] = new Territory(gc, Syria, "Syria", this, true, 186, 186, 186);
		territories[58].setX(943);
		territories[58].setY(771);
		territories[59] = new Territory(gc, Armenia, "Armenia", this, true, 191, 191, 191);
		territories[59].setX(1021);
		territories[59].setY(664);
		
		territories[61] = new Territory(gc, Constantinople, "Constantinople", this, true, 188, 188, 188);
		territories[61].setX(793);
		territories[61].setY(689);
		
		/* RANDOM 
		territories[62] = new Territory(gc, Holand, "Holand", this, true, 140, 140, 140);
		territories[62].setX(472);
		territories[62].setY(418);
		territories[63] = new Territory(gc, Denmark, "Denmark", this, true, 40, 40, 40);
		territories[63].setX(552);
		territories[63].setY(323);	
		territories[64] = new Territory(gc, Rumania, "Rumania", this, true, 184, 184, 184);
		territories[64].setX(743);
		territories[64].setY(553);
		territories[65] = new Territory(gc, Serbia, "Serbia", this, true, 183, 183, 183);
		territories[65].setX(679);
		territories[65].setY(625);
		territories[67] = new Territory(gc, Bulgaria, "Bulgaria", this, true, 181, 181, 181);
		territories[67].setX(729);
		territories[67].setY(646);
		territories[68] = new Territory(gc, Greece, "Greece", this, true, 179, 179, 179);
		territories[68].setX(691);
		territories[68].setY(694);
		territories[69] = new Territory(gc, Finland, "Finland", this, true, 178, 178, 178);
		territories[69].setX(693);
		territories[69].setY(58);
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
		territories[74] = new Territory(gc, Belgium, "Belgium", this, true, 145, 145, 145);
		territories[74].setX(432);
		territories[74].setY(450);
		/* Land territories completed */
		
	}
	
	private void TFactory(SpriteSheet ss, String name, boolean isLand, int pos, Color color, int x, int y){
		territories[pos] = new Territory(ss, name, this, isLand, color);
		territories[pos].setX(x);
		territories[pos].setY(y);
	}
}
