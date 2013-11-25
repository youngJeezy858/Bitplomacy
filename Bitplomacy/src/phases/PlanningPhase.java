package phases;

import java.util.ArrayList;

import canvases.GameCanvas;

import com.erebos.engine.graphics.EAnimation;

import orders.AttackOrder;
import orders.BlankOrder;
import orders.ConvoyOrder;
import orders.DefendOrder;
import orders.MoveOrder;
import orders.Order;
import orders.SupportOrder;
import commands.AttackCommand;
import commands.Commands;
import commands.ConvoyCommand;
import commands.DefendCommand;
import commands.DiscardOrderCommand;
import commands.MoveCommand;
import commands.SetOrderCommand;
import commands.SubmitCommand;
import commands.SupportCommand;

public class PlanningPhase extends Phase{

	private Commands[] planningCommands = { new AttackCommand(1166, 635),
			new DefendCommand(1126, 729),
			new SupportCommand(1212, 729),
			new ConvoyCommand(1296, 729),
			new SubmitCommand(1152, 546),
			new MoveCommand(1258, 635),
			new SetOrderCommand(1152, 450),
			new DiscardOrderCommand(1269, 450)};
	
	/** The support orders. */
	private ArrayList<SupportOrder> supportOrders;
	
	/** The defend orders. */
	private ArrayList<DefendOrder> defendOrders;
	
	/** The attack orders. */
	private ArrayList<AttackOrder> attackOrders;
	
	/** The move orders. */
	private ArrayList<MoveOrder> moveOrders;	
	
	/** The convoy orders. */
	private ArrayList<ConvoyOrder> convoyOrders;
	
	/** The blank orders. */
	private ArrayList<BlankOrder> blankOrders;
	
	/** The retreating territories. */
	private ArrayList<Order> retreatingUnits;
	
	
	public PlanningPhase(String season, int year) {
		super(season, year);
		
		supportOrders = new ArrayList<SupportOrder>();
		defendOrders = new ArrayList<DefendOrder>();
		attackOrders = new ArrayList<AttackOrder>();
		moveOrders = new ArrayList<MoveOrder>();
		convoyOrders = new ArrayList<ConvoyOrder>();
		blankOrders = new ArrayList<BlankOrder>();
		retreatingUnits = new ArrayList<Order>();
	
		planningCommands[0].setEA(new EAnimation(EAnimation.loadImage("/images/AttackIcon.png")));
		planningCommands[1].setEA(new EAnimation(EAnimation.loadImage("/images/DefendIcon.png")));
		planningCommands[2].setEA(new EAnimation(EAnimation.loadImage("/images/SupportIcon.png")));
		planningCommands[3].setEA(new EAnimation(EAnimation.loadImage("/images/ConvoyIcon.png")));
		planningCommands[4].setEA(new EAnimation(EAnimation.loadImage("/images/SubmitIcon.png")));
		planningCommands[5].setEA(new EAnimation(EAnimation.loadImage("/images/MoveIcon.png")));
		planningCommands[6].setEA(new EAnimation(EAnimation.loadImage("/images/SetOrderIcon.png")));
		planningCommands[7].setEA(new EAnimation(EAnimation.loadImage("/images/DiscardOrderIcon.png")));
		GameCanvas.getC().setCommands(planningCommands);
	}

	@Override
	public void addOrder(Order o) {
		if (o.getCommand().equals("support"))
			supportOrders.add((SupportOrder) o);
		else if (o.getCommand().equals("defend"))
			defendOrders.add((DefendOrder) o);
		else if (o.getCommand().equals("attack"))
			attackOrders.add((AttackOrder) o);
		else if (o.getCommand().equals("convoy"))
			convoyOrders.add((ConvoyOrder) o);
		else if (o.getCommand().equals("move"))
			moveOrders.add((MoveOrder) o);		
		else
			blankOrders.add((BlankOrder) o);
	}
	
	public void addRetreat(Order o){
		retreatingUnits.add(o);
	}
	
	public ArrayList<Order> getRetreatingUnits(){
		return retreatingUnits;
	}

	public ArrayList<AttackOrder> getAttackOrders(){
		return attackOrders;
	}
	
	@Override
	public void adjudicate() {
		checkSyntax();
		resolveSupport();

		for (AttackOrder ao : attackOrders){
			if (ao.getState() == Order.CHECKED_WAITING)
				ao.resolveWaterAttack(ao.getDestinationTerritory().getUnit());
		}
			
		for (MoveOrder mo : moveOrders){
			if (mo.getState() != Order.FAILED)
				mo.setState(mo.resolveMove());
		}
		
		for (AttackOrder ao : attackOrders){
			if (ao.getState() != Order.FAILED)
				ao.setState(ao.resolveAttack());
		}
		
		for (AttackOrder ao : attackOrders){
			if (ao.getState() == Order.CHECKED_WAITING){
				if (hasConflict(ao))
					ao.setState(Order.CHECKER);
				else if (ao.getDestinationTerritory().getUnit() == null)
					ao.setState(Order.PASSED);
			}
		}
		
		for (MoveOrder mo : moveOrders){
			if (mo.getState() == Order.CHECKED_WAITING){
				if (hasConflict(mo))
					mo.setState(Order.CHECKER);
				else if (mo.getDestinationTerritory().getUnit() == null)
					mo.setState(Order.PASSED);
			}
		}
		
		for (AttackOrder ao : attackOrders){
			if (ao.getState() == Order.PASSED || ao.getState() == Order.FOLLOWING || ao.getState() == Order.CHECKING)
				ao.getStartingTerritory().removeUnit();
		}
		
		for (MoveOrder mo : moveOrders){
			if (mo.getState() == Order.PASSED || mo.getState() == Order.FOLLOWING || mo.getState() == Order.CHECKING)
				mo.getStartingTerritory().removeUnit();
		}
		
		for (AttackOrder ao : attackOrders){
			if (ao.getState() == Order.PASSED || ao.getState() == Order.FOLLOWING || ao.getState() == Order.CHECKING)
				ao.moveUnit();
		}
		
		for (MoveOrder mo : moveOrders){
			if (mo.getState() == Order.PASSED || mo.getState() == Order.FOLLOWING || mo.getState() == Order.CHECKING)
				mo.moveUnit();
		}

	}
	
	private void checkSyntax() {
		
		for (SupportOrder o : supportOrders){
			if (!o.isValidOrder())
				o.setState(Order.FAILED);
		}
		
		for (MoveOrder o : moveOrders){
			if (!o.isValidOrder())
				o.setState(Order.FAILED);
		}
		
		for (ConvoyOrder o : convoyOrders){
			if (!o.isValidOrder())
				o.setState(Order.FAILED);
		}
		
		for (AttackOrder o : attackOrders){
			if (!o.isValidOrder())
				o.setState(Order.FAILED);
			else if (o.getConvoyPath().size() == 0)
				o.setState(Order.CHECKED_WAITING);
		}
	}
	
	private void resolveSupport() {
		for (SupportOrder so : supportOrders){
			
			if (so.getState() == Order.FAILED)
				continue;
			
			else if (isSupportCut(so))
				so.setState(Order.FAILED);
			
			else{
				so.setState(Order.PASSED);
				so.support();
			}	
		}
	}
	
	private boolean isSupportCut(Order so) {
		for (AttackOrder ao : attackOrders) {
			if (ao.getState() != Order.CHECKED_WAITING)
				continue;
			else if (ao.getDestinationTerritory().equals(
					so.getStartingTerritory()))
				return true;
		}
		return false;
	}

	/**
	 * Checks for conflict.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	private boolean hasConflict(Order o) {
		for (AttackOrder ao : attackOrders){
			if (ao.getState() != Order.CHECKED_WAITING && ao.getState() != Order.FOLLOWING 
					&& ao.getState() != Order.CHECKER && ao.getState() != Order.PASSED)
				continue;
			else if (ao.getStartingTerritory().equals(o.getStartingTerritory()))
				continue;
			
			else if (ao.getDestinationTerritory().equals(o.getDestinationTerritory())){
				if (ao.getStrength() >= o.getStrength())
					return true;
			}
		}
		for (MoveOrder mo : moveOrders){
			if (mo.getState() != Order.CHECKED_WAITING && mo.getState() != Order.FOLLOWING 
					&& mo.getState() != Order.CHECKER  && mo.getState() != Order.PASSED)
				continue;
			else if (mo.getStartingTerritory().equals(o.getStartingTerritory()))
				continue;
			
			else if (mo.getDestinationTerritory().equals(o.getDestinationTerritory())){
				if (mo.getStrength() >= o.getStrength())
					return true;
			}
		}
		return false;
	}



}
