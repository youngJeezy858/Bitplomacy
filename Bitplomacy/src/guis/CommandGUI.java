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

public class CommandGUI {

	private Commands[] buildRemoveCommands = { new BuildArmyCommand(1166, 635),
			new BuildNavyCommand(1258, 635),
			new DisbandCommand(1212, 729)};
	
	private Commands[] planningCommands = { new AttackCommand(1166, 635),
			new DefendCommand(1126, 729),
			new SupportCommand(1212, 729),
			new ConvoyCommand(1296, 729),
			new MoveCommand(1258, 635)};
	
	private Commands[] retreatDisbandCommands = { new DisbandCommand(1166, 635),
			new RetreatCommand(1258, 635)};
	
	private Commands[] setDiscardCommands = { new SetOrderCommand(805, 31),
			new DiscardOrderCommand(1057, 31)};
	
	private Commands[] currCommands;
	
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

	public void update(int mx, int my) {
		for (Commands c : currCommands){
			if (mx >= c.getX() && mx <= c.getWidth() + c.getX()
					&& my >= c.getY() && my <= c.getHeight() + c.getY())
				c.update(mx, my);
		}
	}

	public void draw() {
		for (Commands c : currCommands)
			c.draw();
	}
	
	public void updateSetDiscard(int mx, int my){
		for (Commands c : setDiscardCommands){
			if (mx >= c.getX() && mx <= c.getWidth() + c.getX()
					&& my >= c.getY() && my <= c.getHeight() + c.getY())
				c.update(mx, my);
		}
	}
	
	public void drawSetDiscard(){
		for (Commands c : setDiscardCommands)
			c.draw();
	}
}
