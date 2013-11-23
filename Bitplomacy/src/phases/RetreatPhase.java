package phases;

import java.util.ArrayList;

import com.erebos.engine.graphics.EAnimation;

import orders.DisbandOrder;
import orders.Order;
import orders.RetreatOrder;
import canvases.GameCanvas;
import commands.Commands;
import commands.DisbandCommand;
import commands.DiscardOrderCommand;
import commands.RetreatCommand;
import commands.SetOrderCommand;
import commands.SubmitCommand;

public class RetreatPhase extends Phase{

	/** The retreat disband commands. */
	private Commands[] retreatDisbandCommands = { new DisbandCommand(1166, 635, 100),
			new RetreatCommand(1258, 635, 25),
			new SubmitCommand(1152, 546, 200),
			new SetOrderCommand(1152, 450, 75),
			new DiscardOrderCommand(1269, 450, 125)};
	
	/** The retreat orders. */
	private ArrayList<RetreatOrder> retreatOrders;
	
	/** The disband orders. */
	private ArrayList<DisbandOrder> disbandOrders;
	
	/** The retreating territories. */
	private ArrayList<Order> retreatingUnits;
	
	
	public RetreatPhase(String season, int year, ArrayList<Order> retreatingUnits) {
		super(season, year);
		retreatDisbandCommands[0].setEA(new EAnimation(EAnimation.loadImage("/images/RemoveUnitIcon.png")));
		retreatDisbandCommands[1].setEA(new EAnimation(EAnimation.loadImage("/images/RetreatIcon.png")));
		retreatDisbandCommands[2].setEA(new EAnimation(EAnimation.loadImage("/images/SubmitIcon.png")));
		retreatDisbandCommands[3].setEA(new EAnimation(EAnimation.loadImage("/images/SetOrderIcon.png")));
		retreatDisbandCommands[4].setEA(new EAnimation(EAnimation.loadImage("/images/DiscardOrderIcon.png")));
		GameCanvas.getC().setCommands(retreatDisbandCommands);
		this.retreatingUnits = retreatingUnits;
	}


	@Override
	public void adjudicate() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addOrder(Order o) {
		for (Order ro : retreatingUnits){
			if (ro.getStartingTerritory().equals(o.getStartingTerritory())){
				if (o.getCommand().equals("retreat"))
					retreatOrders.add((RetreatOrder) o);
				else
					disbandOrders.add((DisbandOrder) o);
			}
		}	
	}

}
