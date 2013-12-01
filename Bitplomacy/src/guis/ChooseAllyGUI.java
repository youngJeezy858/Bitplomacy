package guis;

import gameObjects.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

import buttons.AllyChoice;
import canvases.GameCanvas;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

/**
 * GUI for displaying and updating when teams need to choose allies.
 */
public class ChooseAllyGUI extends ImageEntity{

	/** The Player currently selecting an ally. */
	private int currentTeam;
	
	/** The Buttons for selecting an ally. */
	private AllyChoice[] flags;
	
	/** To draw the background of this GUI. */
	private EAnimation background;
	
	/** The ally selections of each Player. */
	private int[] allies;
	
	/** Used to see if a team chose no one as an ally. */
	private static final int NOBODY = 7;
	
	/**
	 * Instantiates a new choose ally gui.
	 *
	 * @param flags the Images for each ally choice
	 * @param background the background of this GUI
	 * @param gc the gc
	 */
	public ChooseAllyGUI(Image[] flags, Image background, GameContainer gc){
		super(background);
		this.background = new EAnimation(background);
		currentTeam = 0;
		allies = new int[flags.length];
		this.flags = new AllyChoice[flags.length];
		for (int i = 0; i < flags.length; i++)
			this.flags[i] = new AllyChoice(flags[i]);
	}
	
	/* (non-Javadoc)
	 * @see com.erebos.engine.entity.ImageEntity#draw()
	 */
	public void draw(){
		background.draw(this.getX(), this.getY());
		int i = 0;
		int rowX = (int) (this.getX());
		int rowY = (int) (this.getY() + 100);
		for (AllyChoice flag : flags){
			if (i == currentTeam){
				flag.setX(this.getX() + 225);
				flag.setY(this.getY() + 50);
				flag.draw();
			}
			else{
				flag.setX(rowX);
				flag.setY(rowY);
				flag.draw();
				rowX += 78;
			}
			i++;
		}
	}
	
	/**
	 * Checks if a choice was made and updates the selection appropriately.
	 *
	 * @param mx the x coordinate of the Mouse cursor
	 * @param my the y coordinate of the Mouse cursor
	 */
	public void update(int mx, int my){
		for (int i = 0; i < flags.length; i++){
			if (i == currentTeam)
				continue;
			else if (mx > flags[i].getX() && mx < flags[i].getX() + flags[i].getWidth() &&
					my > flags[i].getY() && my < flags[i].getY() + flags[i].getHeight() &&
					flags[i].isMouseOver(mx, my)){
				allies[currentTeam] = i;
				currentTeam++;
				if (currentTeam == NOBODY){
					currentTeam = 0;
					GameCanvas.getC().setState(GameCanvas.ADJUDICATE_ALLIES);
				}
				return;
			}
		}
	}
	
	/**
	 * Checks if two Players selected each other as allies. If both of their supply center
	 * counts added together is greater than or equal to 24 they are both determined to be
	 * winners
	 *
	 * @return the winners, null if none were found.
	 */
	public String adjucate(){
		String s = null;
		for(int i = 0; i < allies.length - 1; i++){
			if (allies[i] == NOBODY)
				continue;
			else if (allies[allies[i]] == i){
				Player[] p = GameCanvas.getC().getPlayers();
				if (p[i].getSupplyCenterCount() + p[allies[i]].getSupplyCenterCount() >= 24){
					s = new String(p[i].getName() + " and " + p[allies[i]].getName()) + " WIN!!!";
					break;
				}
			}
		}
		return s;
	}
}
