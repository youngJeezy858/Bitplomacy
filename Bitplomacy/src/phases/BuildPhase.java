package phases;

import java.util.ArrayList;

import canvases.GameCanvas;

import com.erebos.engine.graphics.EAnimation;

import commands.BuildArmyCommand;
import commands.BuildNavyCommand;
import commands.Commands;
import commands.DisbandCommand;
import commands.DiscardOrderCommand;
import commands.SetOrderCommand;
import commands.SubmitCommand;

import orders.DisbandOrder;
import orders.Order;

public class BuildPhase extends Phase{

	/** commands used during the Build/Remove phase. */
	private Commands[] buildRemoveCommands = { new BuildArmyCommand(1166, 635, 100),
			new BuildNavyCommand(1258, 635, 25),
			new DisbandCommand(1212, 729, 50),
			new SubmitCommand(1152, 546, 200),
			new SetOrderCommand(1152, 450, 75),
			new DiscardOrderCommand(1269, 450, 125)};
	
	/** The disband orders. */
	private ArrayList<DisbandOrder> disbandOrders;
	
	/** The build orders. */
	private ArrayList<Order> buildOrders;
	
	
	public BuildPhase(String season, int year) {
		super(season, year);
		buildRemoveCommands[0].setEA(new EAnimation(EAnimation.loadImage("/images/BuildArmyIcon.png")));
		buildRemoveCommands[1].setEA(new EAnimation(EAnimation.loadImage("/images/BuildNavyIcon.png")));
		buildRemoveCommands[2].setEA(new EAnimation(EAnimation.loadImage("/images/RemoveUnitIcon.png")));
		buildRemoveCommands[3].setEA(new EAnimation(EAnimation.loadImage("/images/SubmitIcon.png")));
		buildRemoveCommands[4].setEA(new EAnimation(EAnimation.loadImage("/images/SetOrderIcon.png")));
		buildRemoveCommands[5].setEA(new EAnimation(EAnimation.loadImage("/images/DiscardOrderIcon.png")));
		GameCanvas.getC().setCommands(buildRemoveCommands);
	}

	@Override
	public void adjudicate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addOrder(Order o) {
		// TODO Auto-generated method stub
		
	}

}
