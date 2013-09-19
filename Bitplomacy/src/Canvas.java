import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import com.erebos.engine.core.*;
import com.erebos.engine.graphics.EAnimation;


public class Canvas extends ECanvas{

	/* Oceans */
	private Image NorthAfrica;
	private Image MidAtlantic;
	private Image NorthAtlantic;
	private Image NorwegianSea;
	private Image BarentSea;
	private Image StPetersburgh;
	Territory[] territories;
	private Image Linova;
	private Image IrishSea;
	private Image EnglishChannel;
	private Image NorthSea;
	Graphics g;
	public int state;
	public String tName;
	final int NORM = 0;
	final int DIS_TERR = 1;
	private Image Hel;
	private Image Ska;
	private Image BalticSea;
	private Image GulfBothnia;
	private Image WestMed;
	private Image GulfLyon;
	private Image TyrrhenianSea;
	private Image IonianSea;
	private Image AdriaticSea;
	public Image MasterMap;
	
	public Canvas(int ID) {
		super(ID);
		tName = "";
	}

	@Override
	public void eInit(GameContainer gc, EGame eg) {	
		//Oceans
		MasterMap=EAnimation.loadImage("/images/MasterMap.png");
		MidAtlantic=EAnimation.loadImage("/images/MidAtlantic.png");
	    NorthAtlantic=EAnimation.loadImage("/images/NorthAtlantic.png");
	    NorthAfrica=EAnimation.loadImage("/images/NAfrica.png");
	    NorwegianSea=EAnimation.loadImage("/images/NorwegianSea.png");
	    BarentSea=EAnimation.loadImage("/images/BarentSea.png");
	    StPetersburgh=EAnimation.loadImage("/images/StPetersburgh.png");
	    Linova=EAnimation.loadImage("/images/Linova.png");
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
	    AdriaticSea=EAnimation.loadImage("/images/AdriaticSea.png");
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
		g.drawImage(MasterMap, -10, -5);
		//for (Territory t: territories)
		// 	g.drawImage(t.animation().getImage(0),t.getX(),t.getY());
		
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
		
		
		territories = new Territory[38];
		/* Oceans first */
		territories[4] = new Territory(gc, NorthAtlantic, "North Atlantic Ocean", this, false, 180, 205, 229);
		territories[4].setX(0);
		territories[4].setY(0);
		territories[2] = new Territory(gc, MidAtlantic, "Mid Atlantic Ocean", this, false, 175, 205, 229);
		territories[2].setX(0);
		territories[2].setY(gc.getHeight()-372);
		territories[0] = new Territory(gc, NorwegianSea, "Norwegian Sea", this, false, 165, 205, 229);
		territories[0].setX(230);
		territories[0].setY(0);
		territories[3] = new Territory(gc, BarentSea, "Barent's Sea", this, false, 155, 205, 229);
		territories[3].setX(705);
		territories[3].setY(0);
		territories[1] = new Territory(gc, IrishSea, "Irish Sea", this, false, 160, 205, 229);
		territories[1].setX(254);
		territories[1].setY(322);
		territories[5] = new Territory(gc, EnglishChannel, "English Channel", this, false, 150, 205, 229);
		territories[5].setX(298);
		territories[5].setY(431);
		territories[6] = new Territory(gc, NorthSea, "North Sea", this, false, 145, 205, 229);
		territories[6].setX(420);
		territories[6].setY(223);
		territories[7] = new Territory(gc, Hel, "Hel", this, false, 140, 205, 229);
		territories[7].setX(498);
		territories[7].setY(347);
		territories[8] = new Territory(gc, Ska, "Ska", this, false, 135, 205, 229);
		territories[8].setX(527);
		territories[8].setY(305);
		territories[9] = new Territory(gc, BalticSea, "Baltic Sea", this, false, 130, 205, 229);
		territories[9].setX(566);
		territories[9].setY(315);
		territories[10] = new Territory(gc, GulfBothnia, "Gulf of Bothnia", this, false, 125, 205, 229);
		territories[10].setX(648);
		territories[10].setY(156);
		territories[11] = new Territory(gc, WestMed, "Western Medditerian Sea", this, false, 170, 205, 229);
		territories[11].setX(217);
		territories[11].setY(684);
		territories[12] = new Territory(gc, GulfLyon, "Gulf of Lyon", this, false, 120, 205, 229);
		territories[12].setX(340);
		territories[12].setY(620);
		territories[13] = new Territory(gc, TyrrhenianSea, "Tyrrhenian Sea", this, false, 115, 205, 229);
		territories[13].setX(490);
		territories[13].setY(663);
		territories[14] = new Territory(gc, IonianSea, "Ionian Sea", this, false, 110, 205, 229);
		territories[14].setX(520);
		territories[14].setY(710);
		territories[15] = new Territory(gc, AdriaticSea, "Adriatic Sea", this, false, 105, 205, 229);
		territories[15].setX(557);
		territories[15].setY(598);
		
		/* IMAGES STILL NEED UPLOADED */
		territories[16] = new Territory(gc, AdriaticSea, "Aegean Sea", this, false, 100, 205, 229);
		territories[16].setX(557);
		territories[16].setY(598);
		territories[17] = new Territory(gc, AdriaticSea, "Eastern Medditerian Sea", this, false, 95, 205, 229);
		territories[17].setX(557);
		territories[17].setY(598);
		territories[18] = new Territory(gc, AdriaticSea, "Eastern Medditerian Sea", this, false, 90, 205, 229);
		territories[18].setX(557);
		territories[18].setY(598);
		/* Water territories completed */
		
		/* Land Second */
		territories[19] = new Territory(gc, NorthAfrica, "North Africa", this, true, 200, 200, 200);
		territories[19].setX(100);
		territories[19].setY(745);
		territories[20] = new Territory(gc, StPetersburgh, "St. Petersburgh", this, true, 195, 195, 195);
		territories[20].setX(710);
		territories[20].setY(0);
		territories[21] = new Territory(gc, Linova, "Linova", this, true, 190, 190, 190);
		territories[21].setX(690);
		territories[21].setY(315);
		
		/* IMAGES STILL NEED UPLOADED */
		territories[22] = new Territory(gc, Linova, "Portugal", this, true, 185, 185, 185);
		territories[22].setX(690);
		territories[22].setY(315);
		territories[23] = new Territory(gc, Linova, "Spain", this, true, 180, 180, 180);
		territories[23].setX(690);
		territories[23].setY(315);
		
		/* FRANCE */
		territories[24] = new Territory(gc, Linova, "Gas", this, true, 175, 175, 175);
		territories[24].setX(690);
		territories[24].setY(315);
		territories[25] = new Territory(gc, Linova, "Bre", this, true, 170, 170, 170);
		territories[25].setX(690);
		territories[25].setY(315);
		territories[26] = new Territory(gc, Linova, "Paris", this, true, 165, 165, 165);
		territories[26].setX(690);
		territories[26].setY(315);
		territories[27] = new Territory(gc, Linova, "Mar", this, true, 160, 160, 160);
		territories[27].setX(690);
		territories[27].setY(315);
		territories[28] = new Territory(gc, Linova, "Bur", this, true, 155, 155, 155);
		territories[28].setX(690);
		territories[28].setY(315);
		territories[29] = new Territory(gc, Linova, "Pic", this, true, 150, 150, 150);
		territories[29].setX(690);
		territories[29].setY(315);
		
		territories[30] = new Territory(gc, Linova, "Belgium", this, true, 145, 145, 145);
		territories[30].setX(690);
		territories[30].setY(315);
		territories[31] = new Territory(gc, Linova, "Holand", this, true, 140, 140, 140);
		territories[31].setX(690);
		territories[31].setY(315);
		
		
		/* ENGLAND */
		territories[32] = new Territory(gc, Linova, "Wa", this, true, 135, 135, 135);
		territories[32].setX(690);
		territories[32].setY(315);
		territories[33] = new Territory(gc, Linova, "London", this, true, 130, 130, 130);
		territories[33].setX(690);
		territories[33].setY(315);
		territories[32] = new Territory(gc, Linova, "York", this, true, 125, 125, 125);
		territories[32].setX(690);
		territories[32].setY(315);
		territories[33] = new Territory(gc, Linova, "Liverpool", this, true, 120, 120, 120);
		territories[33].setX(690);
		territories[33].setY(315);
		territories[34] = new Territory(gc, Linova, "Edi", this, true, 115, 115, 115);
		territories[34].setX(690);
		territories[34].setY(315);
		territories[35] = new Territory(gc, Linova, "Cly", this, true, 110, 110, 110);
		territories[35].setX(690);
		territories[35].setY(315);
		
		/* ITALY */
		territories[36] = new Territory(gc, Linova, "Pie", this, true, 105, 105, 105);
		territories[36].setX(690);
		territories[36].setY(315);
		territories[37] = new Territory(gc, Linova, "Ven", this, true, 100, 100, 100);
		territories[37].setX(690);
		territories[37].setY(315);
		territories[38] = new Territory(gc, Linova, "Tus", this, true, 95, 95, 95);
		territories[38].setX(690);
		territories[38].setY(315);
		territories[39] = new Territory(gc, Linova, "Rome", this, true, 90, 90, 90);
		territories[39].setX(690);
		territories[39].setY(315);
		territories[40] = new Territory(gc, Linova, "Apu", this, true, 85, 85, 85);
		territories[40].setX(690);
		territories[40].setY(315);
		territories[41] = new Territory(gc, Linova, "Nap", this, true, 80, 80, 80);
		territories[41].setX(690);
		territories[41].setY(315);
		
		territories[42] = new Territory(gc, Linova, "Tunis", this, true, 75, 75, 75);
		territories[42].setX(690);
		territories[42].setY(315);
		
		/* GERMANY */
		territories[43] = new Territory(gc, Linova, "Ruhr", this, true, 70, 70, 70);
		territories[43].setX(690);
		territories[43].setY(315);
		territories[44] = new Territory(gc, Linova, "Munich", this, true, 65, 65, 65);
		territories[44].setX(690);
		territories[44].setY(315);
		territories[45] = new Territory(gc, Linova, "Kiel", this, true, 60, 60, 60);
		territories[45].setX(690);
		territories[45].setY(315);
		territories[46] = new Territory(gc, Linova, "Ber", this, true, 55, 55, 55);
		territories[46].setX(690);
		territories[46].setY(315);
		territories[47] = new Territory(gc, Linova, "Prussia", this, true, 50, 50, 50);
		territories[47].setX(690);
		territories[47].setY(315);
		territories[48] = new Territory(gc, Linova, "Silesia", this, true, 45, 45, 45);
		territories[48].setX(690);
		territories[48].setY(315);
		
		territories[49] = new Territory(gc, Linova, "Denmark", this, true, 40, 40, 40);
		territories[49].setX(690);
		territories[49].setY(315);
		
		/* AUSTRIA???? */
		territories[50] = new Territory(gc, Linova, "Bohmeia", this, true, 35, 35, 35);
		territories[50].setX(690);
		territories[50].setY(315);
		territories[51] = new Territory(gc, Linova, "Tunis", this, true, 75, 75, 75);
		territories[51].setX(690);
		territories[51].setY(315);
		/* Land territories completed */
	}
	
}
