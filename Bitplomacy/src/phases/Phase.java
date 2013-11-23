package phases;

import gameObjects.Player;
import gameObjects.Territory;
import gameObjects.Unit;
import java.util.ArrayList;

import canvases.GameCanvas;

import orders.Order;

/**
 * The Class Turn.
 */
public abstract class Phase {

	/** The season. */
	private String season;
	
	/** The year. */
	private int year;


	/**
	 * Instantiates a new turn.
	 *
	 * @param season the season
	 * @param year the year
	 */
	public Phase(String season, int year){
		this.season = season;
		this.year = year;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return season + " " + year;
	}
	
	/**
	 * Gets the season.
	 *
	 * @return the season
	 */
	public String getSeason(){
		return season;
	}
	
	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public int getYear(){
		return year;
	}

	/**
	 * Resolve retreats.
	 */
	public void resolveRetreats(){
		for (Order o : disbandOrders){
			for (Territory t : retreatingTerritories){
				if (o.getStartingTerritory().equals(t)){
					GameCanvas.getC().removeUnit(o.getUnit());
					break;
				}
			}
		}
		for (Order o : retreatOrders){
			for (Territory t : retreatingTerritories){
				if (o.getStartingTerritory().equals(t)){
					if (resolveRetreat(o))
						o.adjudicate(Order.CHECKED_WAITING);
					else
						o.adjudicate(Order.FAILED);
				}
			}
			if (o.getState() != Order.CHECKED_WAITING && o.getState() != Order.FAILED)
				o.adjudicate(Order.DONE);
		}
		
		for (Order ro1 : retreatOrders){
			if (ro1.getState() == Order.DONE)
				continue;
			else if (hasRetreatConflict(ro1))
				ro1.adjudicate(Order.CHECKER);
			else{
				ro1.adjudicate(Order.PASSED);
				ro1.getStartingTerritory().removeUnit();
				moveUnit(ro1);
			}
		}
		for (Order o : retreatOrders){
			if (o.getState() == Order.FAILED || o.getState() == Order.CHECKER)
				GameCanvas.getC().removeUnit(o.getUnit());
		}
		
		for (Order ao : attackOrders){
			if (ao.getState() == Order.CHECKED_WAITING){
				ao.adjudicate(Order.PASSED);
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
		for (Order ro2 : retreatOrders){
			if (o.getStartingTerritory().equals(ro2.getStartingTerritory()))
				continue;
			else if (ro2.getState() != Order.CHECKED_WAITING && ro2.getState() != Order.CHECKER)
				continue;
			else if (ro2.getDestinationTerritory().equals(o.getDestinationTerritory()))
				return true;
		}
		return false;
	}
	
	/**
	 * Resolve retreat.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	private boolean resolveRetreat(Order o) {
		if (o.getUnit().isArmy() && !o.getDestinationTerritory().isLand())
			return false;
		else if (!o.getUnit().isArmy() && o.getDestinationTerritory().isLand() && !o.getDestinationTerritory().hasCoast())
			return false;
		else if (o.getDestinationTerritory().getUnit() != null)
			return false;
		return o.getStartingTerritory().isAdjacent(o.getDestinationTerritory());
	}

	/**
	 * Sets the season.
	 *
	 * @param string the new season
	 */
	public void setSeason(String string) {
		season = string;
	}

	/**
	 * Move unit.
	 *
	 * @param o the o
	 */
	protected void moveUnit(Order o) {
		if (o.getState() == Order.PASSED || o.getState() == Order.FOLLOWING || o.getState() == Order.CHECKING){
			o.setState(Order.PASSED);
			o.getDestinationTerritory().setUnit(o.getUnit());
			if (!o.getDestinationTerritory().hasSC())
				o.getDestinationTerritory().setOwner(o.getUnit().getOwner());
			o.getUnit().setTerritory(o.getDestinationTerritory());
			o.setState(Order.DONE);
		}	
	}

	/**
	 * Resolve build remove.
	 */
	public void resolveBuildRemove() {
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

	public abstract void adjudicate();
	public abstract void addOrder(Order o);
	public abstract void getRetreatingUnits
	
}
