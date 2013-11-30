package commands;

import gameObjects.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

import canvases.GameCanvas;

import com.erebos.engine.entity.ImageEntity;
import com.erebos.engine.graphics.EAnimation;

public class ChooseAlly extends ImageEntity{

	private int currentTeam;
	private AllyChoice[] flags;
	private EAnimation background;
	private int[] allies;
	private static final int NOBODY = 7;
	
	public ChooseAlly(Image[] flags, Image background, GameContainer gc){
		super(background);
		this.background = new EAnimation(background);
		currentTeam = 0;
		allies = new int[flags.length];
		this.flags = new AllyChoice[flags.length];
		for (int i = 0; i < flags.length; i++)
			this.flags[i] = new AllyChoice(flags[i]);
	}
	
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
