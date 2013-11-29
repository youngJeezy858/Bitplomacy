package phases;

import java.util.ArrayList;

import com.erebos.engine.graphics.EAnimation;

import orders.AttackOrder;
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
	private Commands[] retreatDisbandCommands = { new DisbandCommand(1166, 635),
			new RetreatCommand(1258, 635),
			new SubmitCommand(1152, 546),
			new SetOrderCommand(1152, 450),
			new DiscardOrderCommand(1269, 450)};
	
	/** The retreat orders. */
	private ArrayList<RetreatOrder> retreatOrders;
	
	/** The disband orders. */
	private ArrayList<DisbandOrder> disbandOrders;
	
	/** The retreating territories. */
	private ArrayList<Order> retreatingUnits;
	
	/** The retreating territories. */
	private ArrayList<AttackOrder> remainingAttacks;
	
	
	public RetreatPhase(String season, int year, ArrayList<Order> retreatingUnits, ArrayList<AttackOrder> remainingAttacks) {
		super(season, year);

		retreatOrders = new ArrayList<RetreatOrder>();
		disbandOrders = new ArrayList<DisbandOrder>();
		this.retreatingUnits = retreatingUnits;
		this.remainingAttacks = remainingAttacks;
	
		retreatDisbandCommands[0].setEA(new EAnimation(EAnimation.loadImage("/images/RemoveUnitIcon.png")));
		retreatDisbandCommands[1].setEA(new EAnimation(EAnimation.loadImage("/images/RetreatIcon.png")));
		retreatDisbandCommands[2].setEA(new EAnimation(EAnimation.loadImage("/images/SubmitIcon.png")));
		retreatDisbandCommands[3].setEA(new EAnimation(EAnimation.loadImage("/images/SetOrderIcon.png")));
		retreatDisbandCommands[4].setEA(new EAnimation(EAnimation.loadImage("/images/DiscardOrderIcon.png")));
		GameCanvas.getC().setCommands(retreatDisbandCommands);	}


	@Override
	public void addOrder(Order o) {
		for (Order ro : retreatingUnits){
			if (ro.getStartingTerritory().equals(o.getStartingTerritory())){
				if (o.getCommand().equals("retreat"))
					retreatOrders.add((RetreatOrder) o);
				else
					disbandOrders.add(new DisbandOrder(o.getStartingTerritory()));
			}
		}	
	}

	@Override
	public void adjudicate() {

		for (DisbandOrder o : disbandOrders)
			o.resolveDisband(retreatingUnits);
		
		for (RetreatOrder o : retreatOrders)
			o.resolveRetreat(retreatingUnits);
		
		for (RetreatOrder ro : retreatOrders){
			if (ro.getState() == Order.DONE)
				continue;
			else if (hasRetreatConflict(ro))
				ro.setState(Order.CHECKER);
			else if (ro.getState() == Order.CHECKED_WAITING){
				ro.setState(Order.PASSED);
				ro.getStartingTerritory().removeUnit();
				moveUnit(ro);
			}
		}
		
		for (RetreatOrder o : retreatOrders){
			if (o.getState() == Order.FAILED || o.getState() == Order.CHECKER)
				GameCanvas.getC().removeUnit(o.getUnit());
		}
		
		for (Order o : retreatingUnits){
			o.setState(Order.FAILED);
			o.getStartingTerritory().removeUnit();
			moveUnit(o);
		}
		
		for (AttackOrder ao : remainingAttacks){
			if (ao.getState() == Order.CHECKED_WAITING){
				ao.setState(Order.PASSED);
				ao.getStartingTerritory().removeUnit();
				moveUnit(ao);
			}
		}
	}

	/**
	 * Checks for retreat conflict.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	private boolean hasRetreatConflict(Order o){
		for (Order ro : retreatOrders){
			if (o.getStartingTerritory().equals(ro.getStartingTerritory()))
				continue;
			else if (ro.getState() != Order.CHECKED_WAITING && ro.getState() != Order.CHECKER)
				continue;
			else if (ro.getDestinationTerritory().equals(o.getDestinationTerritory()))
				return true;
		}
		return false;
	}
	
}
