package guis;

import canvases.GameCanvas;

import com.erebos.engine.graphics.EAnimation;
import commands.AttackCommand;
import commands.BuildArmyCommand;
import commands.BuildNavyCommand;
import commands.Commands;
import commands.ConvoyCommand;
import commands.DefendCommand;
import commands.DisbandCommand;
import commands.DiscardOrderCommand;
import commands.MoveCommand;
import commands.RetreatCommand;
import commands.SetOrderCommand;
import commands.SupportCommand;

/**
 * The GUI for selecting a command for an Orderd.
 */
public class CommandGUI {

	/** The build/remove commands. */
	private Commands[] buildRemoveCommands = { new BuildArmyCommand(1166, 635),
			new BuildNavyCommand(1258, 635),
			new DisbandCommand(1212, 729)};
	
	/** The planning commands. */
	private Commands[] planningCommands = { new AttackCommand(1166, 635),
			new DefendCommand(1126, 729),
			new SupportCommand(1212, 729),
			new ConvoyCommand(1296, 729),
			new MoveCommand(1258, 635)};
	
	/** The retreat/disband commands. */
	private Commands[] retreatDisbandCommands = { new DisbandCommand(1166, 635),
			new RetreatCommand(1258, 635)};
	
	/** The set/discard Order commands. */
	private Commands[] setDiscardCommands = { new SetOrderCommand(805, 31),
			new DiscardOrderCommand(1057, 31)};
	
	/** The current commands (depends on the current Phase). */
	private Commands[] currCommands;
	
	/**
	 * Instantiates a new command gui.
	 */
	public CommandGUI(){
		buildRemoveCommands[0].setEA(new EAnimation(EAnimation.loadImage("/images/Icon_BuildArmy_updated.png")));
		buildRemoveCommands[1].setEA(new EAnimation(EAnimation.loadImage("/images/Icon_BuildFleet_updated.png")));
		buildRemoveCommands[2].setEA(new EAnimation(EAnimation.loadImage("/images/Icon_Disband_updated.png")));
		
		planningCommands[0].setEA(new EAnimation(EAnimation.loadImage("/images/Icon_Attack_updated.png")));
		planningCommands[1].setEA(new EAnimation(EAnimation.loadImage("/images/Icon_Defend_updated.png")));
		planningCommands[2].setEA(new EAnimation(EAnimation.loadImage("/images/Icon_Support_updated.png")));
		planningCommands[3].setEA(new EAnimation(EAnimation.loadImage("/images/Icon_Convoy_updated.png")));
		planningCommands[4].setEA(new EAnimation(EAnimation.loadImage("/images/Icon_Move_updated.png")));
		
		retreatDisbandCommands[0].setEA(new EAnimation(EAnimation.loadImage("/images/Icon_Disband_updated.png")));
		retreatDisbandCommands[1].setEA(new EAnimation(EAnimation.loadImage("/images/Icon_Retreat_updated.png")));

		setDiscardCommands[0].setEA(new EAnimation(EAnimation.loadImage("/images/Icon_SetOrder_updated.png")));
		setDiscardCommands[1].setEA(new EAnimation(EAnimation.loadImage("/images/Icon_DiscardOrder_updated.png")));
	}

	/**
	 * Sets the coordinates for drawing this GUI.
	 *
	 * @param mx the new x coordinate of this GUI
	 * @param my the new y coordinate of this GUI
	 */
	public void setCoord(int mx, int my) {
		if (GameCanvas.getC().getPhase().getSeason().contains("Retreats"))
			currCommands = retreatDisbandCommands;
		else if (GameCanvas.getC().getPhase().getSeason().equals("Build/Remove"))
			currCommands = buildRemoveCommands;
		else
			currCommands = planningCommands;
		
		if (my + currCommands[0].getHeight() > 715)
			my -= currCommands[0].getHeight();
		if (mx + (currCommands[0].getWidth()*currCommands.length) + (10*(currCommands.length-1)) > 806)
			mx -= (currCommands[0].getWidth()*currCommands.length) + (10*(currCommands.length-1));
		
		for (Commands c : currCommands){
			c.setX(mx);
			c.setY(my);
			mx += c.getWidth() + 10;
		}
	}

	/**
	 * Checks if a command was selected and updates appropriately.
	 *
	 * @param mx the x coordinate of the Mouse cursor
	 * @param my the y coordinate of the Mouse cursor
	 */
	public void update(int mx, int my) {
		for (Commands c : currCommands){
			if (mx >= c.getX() && mx <= c.getWidth() + c.getX()
					&& my >= c.getY() && my <= c.getHeight() + c.getY())
				c.update(mx, my);
		}
	}

	/**
	 * Draws the GUI to the screen.
	 */
	public void draw() {
		for (Commands c : currCommands)
			c.draw();
	}
	
	/**
	 * Updates the set/discard Order commands.
	 *
	 * @param mx the x coordinate of the Mouse cursor
	 * @param my the y coordinate of the Mouse cursor
	 */
	public void updateSetDiscard(int mx, int my){
		for (Commands c : setDiscardCommands){
			if (mx >= c.getX() && mx <= c.getWidth() + c.getX()
					&& my >= c.getY() && my <= c.getHeight() + c.getY())
				c.update(mx, my);
		}
	}
	
	/**
	 * Draws the set/discard Order commands to the screen.
	 */
	public void drawSetDiscard(){
		for (Commands c : setDiscardCommands)
			c.draw();
	}
}
