package phases;

import gameObjects.Player;

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
	private Commands[] buildRemoveCommands = { new BuildArmyCommand(1166, 635),
			new BuildNavyCommand(1258, 635),
			new DisbandCommand(1212, 729),
			new SubmitCommand(1152, 546),
			new SetOrderCommand(1152, 450),
			new DiscardOrderCommand(1269, 450)};
	
	/** The disband orders. */
	private ArrayList<DisbandOrder> disbandOrders;
	
	/** The build orders. */
	private ArrayList<Order> buildOrders;
	

	public BuildPhase(String season, int year) {
		super(season, year);
		buildOrders = new ArrayList<Order>();
		disbandOrders = new ArrayList<DisbandOrder>();
		
		buildRemoveCommands[0].setEA(new EAnimation(EAnimation.loadImage("/images/BuildArmyIcon.png")));
		buildRemoveCommands[1].setEA(new EAnimation(EAnimation.loadImage("/images/BuildNavyIcon.png")));
		buildRemoveCommands[2].setEA(new EAnimation(EAnimation.loadImage("/images/RemoveUnitIcon.png")));
		buildRemoveCommands[3].setEA(new EAnimation(EAnimation.loadImage("/images/SubmitIcon.png")));
		buildRemoveCommands[4].setEA(new EAnimation(EAnimation.loadImage("/images/SetOrderIcon.png")));
		buildRemoveCommands[5].setEA(new EAnimation(EAnimation.loadImage("/images/DiscardOrderIcon.png")));
		GameCanvas.getC().setCommands(buildRemoveCommands);
	}

	@Override
	public void addOrder(Order o) {
		disbandOrders.add(new DisbandOrder(o.getStartingTerritory()));
	}

	@Override
	public void adjudicate() {
		
		for (Player p : GameCanvas.getC().getPlayers()){
			while (p.getSupplyCenterCount() != p.getNumUnits()){
			
				if (p.getSupplyCenterCount() > p.getNumUnits()){
					int i;
					for (i = 0; i < buildOrders.size(); i++){
						Order o = buildOrders.get(i);
						if (o.getStartingTerritory().getOwner() == p.getOwnerKey() && 
                                o.getUnit() == null &&
                                o.getStartingTerritory().isHomeCity(p.getOwnerKey())){
							if (o.getCommand().contains("army") && o.getStartingTerritory().isLand()){
								GameCanvas.getC().createUnit(o.getStartingTerritory(), true, p);
								break;
							}
							else if (o.getCommand().contains("navy") && (!o.getStartingTerritory().isLand() || o.getStartingTerritory().hasCoast())){
								GameCanvas.getC().createUnit(o.getStartingTerritory(), false, p);
								break;
							}
						}
					}
					if (i != buildOrders.size())
						buildOrders.remove(i);
					else
						break;
				}
				
				else if (p.getSupplyCenterCount() < p.getNumUnits()){
					int i;
					for (i = 0; i < disbandOrders.size(); i++){
						Order o = disbandOrders.get(i);
						if (o.getUnit() != null && o.getUnit().getOwner() == p.getOwnerKey()){
							GameCanvas.getC().removeUnit(o.getUnit());
							break;
						}
					}
					if (i != disbandOrders.size())
						disbandOrders.remove(i);
					else if (p.getAUnit() != null)
						GameCanvas.getC().removeUnit(p.getAUnit());
				}
			}
		}
		
	}

	/**
	 * Adds the build order.
	 *
	 * @param currOrder the curr order
	 */
	public void addBuildOrder(Order currOrder) {
		int i;
		for (i = 0; i < buildOrders.size(); i++){
			Order o = buildOrders.get(i);
			if (o.getStartingTerritory().equals(currOrder.getStartingTerritory()))
				break;
		}
		if (i != buildOrders.size())
			buildOrders.set(i, currOrder);
		else 
			buildOrders.add(currOrder);
	}

}
