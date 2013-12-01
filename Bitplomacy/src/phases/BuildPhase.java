package phases;

import gameObjects.Player;

import java.util.ArrayList;

import canvases.GameCanvas;

import orders.DisbandOrder;
import orders.Order;

/**
 * The Phase for Building Armies and Fleets and also for disbanding Units.
 */
public class BuildPhase extends Phase{

	
	/** The disband orders. */
	private ArrayList<DisbandOrder> disbandOrders;
	
	/** The build orders. */
	private ArrayList<Order> buildOrders;
	

	/**
	 * Instantiates a new builds the phase.
	 *
	 * @param season the season
	 * @param year the year
	 */
	public BuildPhase(String season, int year) {
		super(season, year);
		buildOrders = new ArrayList<Order>();
		disbandOrders = new ArrayList<DisbandOrder>();
	}

	/* (non-Javadoc)
	 * @see phases.Phase#addOrder(orders.Order)
	 */
	@Override
	public void addOrder(Order o) {
		disbandOrders.add(new DisbandOrder(o.getStartingTerritory()));
	}

	/* (non-Javadoc)
	 * @see phases.Phase#adjudicate()
	 */
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
	 * Adds a build order.
	 *
	 * @param order the build Order
	 */
	public void addBuildOrder(Order order) {
		int i;
		for (i = 0; i < buildOrders.size(); i++){
			Order o = buildOrders.get(i);
			if (o.getStartingTerritory().equals(order.getStartingTerritory()))
				break;
		}
		if (i != buildOrders.size())
			buildOrders.set(i, order);
		else 
			buildOrders.add(order);
	}

	/**
	 * Gets the build orders.
	 *
	 * @return the builds the orders
	 */
	public ArrayList<Order> getBuildOrders() {
		return buildOrders;
	}

	/**
	 * Gets the disband orders.
	 *
	 * @return the disband orders
	 */
	public ArrayList<DisbandOrder> getDisbandOrders() {
		return disbandOrders;
	}

}
