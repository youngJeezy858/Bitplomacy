package gameObjects;

import gui.Canvas;
import java.util.ArrayList;

// TODO: Javadocs and abstract this class
/**
 * The Class Turn.
 */
public class Turn {

	/** The season. */
	private String season;
	
	/** The year. */
	private int year;
	
	/** The support orders. */
	private ArrayList<Order> supportOrders;
	
	/** The defend orders. */
	private ArrayList<Order> defendOrders;
	
	/** The attack orders. */
	private ArrayList<Order> attackOrders;
	
	/** The move orders. */
	private ArrayList<Order> moveOrders;	
	
	/** The convoy orders. */
	private ArrayList<Order> convoyOrders;
	
	/** The blank orders. */
	private ArrayList<Order> blankOrders;
	
	/** The retreat orders. */
	private ArrayList<Order> retreatOrders;
	
	/** The disband orders. */
	private ArrayList<Order> disbandOrders;
 
	/** The build orders. */
	private ArrayList<Order> buildOrders;

	/** The retreating territories. */
	private ArrayList<Territory> retreatingTerritories;
	

	/**
	 * Instantiates a new turn.
	 *
	 * @param season the season
	 * @param year the year
	 */
	public Turn(String season, int year){
		this.season = season;
		this.year = year;
		supportOrders = new ArrayList<Order>();
		defendOrders = new ArrayList<Order>();
		attackOrders = new ArrayList<Order>();
		moveOrders = new ArrayList<Order>();
		convoyOrders = new ArrayList<Order>();
		blankOrders = new ArrayList<Order>();
		retreatOrders = new ArrayList<Order>();
		disbandOrders = new ArrayList<Order>();
		buildOrders = new ArrayList<Order>();
		retreatingTerritories = new ArrayList<Territory>();
	}
	
	/**
	 * Adds the order.
	 *
	 * @param o the order to be added
	 */
	public void addOrder(Order o){
		if (o.getCommand().equals("support"))
			supportOrders.add(o);
		else if (o.getCommand().equals("defend"))
			defendOrders.add(o);
		else if (o.getCommand().equals("attack"))
			attackOrders.add(o);
		else if (o.getCommand().equals("convoy"))
			convoyOrders.add(o);
		else if (o.getCommand().equals("move"))
			moveOrders.add(o);
		else if (o.getCommand().equals("retreat"))
			retreatOrders.add(o);
		else if (o.getCommand().equals("disband"))
			disbandOrders.add(o);
		else if (o.getCommand().equals("idle")){
			if (season.contains("Retreats") || season.equals("Build/Remove"))
				disbandOrders.add(o);
			else
				blankOrders.add(o);
		}
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
	 * Resolve orders.
	 */
	public void resolveOrders() {	
		checkSyntax();
		resolveSupport();
		resolveWaterAttacks();
		resolveMoves();
		resolveAttacks();
		resolveConflicts();
		movePassedFollowingUnits();
	}
	
	/**
	 * Check the syntax of the orders. Checks attack orders more deeply in case a support
	 * order was cut off.  Executed first during adjudication.
	 */
	private void checkSyntax() {
		for (Order o : supportOrders){
			if (!o.isValidOrder())
				o.adjudicate(Order.FAILED);
		}
		for (Order o : moveOrders){
			if (!o.isValidOrder())
				o.adjudicate(Order.FAILED);
		}
		for (Order o : defendOrders){
			if (!o.isValidOrder())
				o.adjudicate(Order.FAILED);
		}
		for (Order o : convoyOrders){
			if (!o.isValidOrder())
				o.adjudicate(Order.FAILED);
		}
		for (Order o : attackOrders){
			if (!o.isValidOrder())
				o.adjudicate(Order.FAILED);
			else if (o.getUnit().isArmy() && !o.getDestinationTerritory().isLand())
				o.adjudicate(Order.FAILED);
			else if (!o.getUnit().isArmy() && o.getDestinationTerritory().isLand() && 
					o.getDestinationTerritory().hasCoast())
				o.adjudicate(Order.FAILED);
			else if (o.isAdjacent() && o.getConvoyUnits().size() == 0)
				o.adjudicate(Order.CHECKED_WAITING);
		}
	}

	/**
	 * Resolves support orders.  Support orders that do not have correct syntax or have
	 * had their support cut because of an attack have already been marked as FAILED.  
	 * Executed second during adjudication.
	 */
	private void resolveSupport() {
		for (Order so : supportOrders){
			String supportedUnitCommand = so.getSupportedUnit().getOrder().getCommand();
			if (so.getState() == Order.FAILED || isSupportCut(so))
				so.adjudicate(Order.FAILED);
			else if (!so.getUnit().isArmy() && so.getDestinationTerritory().isLand() && so.getDestinationTerritory().hasCoast())
				so.adjudicate(Order.FAILED);
			else if (so.getUnit().isArmy() && so.getDestinationTerritory().isLand())
				so.adjudicate(Order.FAILED);
			else if (supportedUnitCommand.equals("attack") && so.getDestinationTerritory().getUnit() != null &&
					so.getDestinationTerritory().getUnit().getOwner() == so.getUnit().getOwner())
				so.adjudicate(Order.FAILED);
			else if (!supportedUnitCommand.equals("attack") && !supportedUnitCommand.equals("move") 
					&& !supportedUnitCommand.equals("defend"))
				so.adjudicate(Order.FAILED);
			else if (!so.isAdjacent())
				so.adjudicate(Order.FAILED);
			else{
				so.adjudicate(Order.PASSED);
				so.getSupportedUnit().getOrder().incrementStrength();
			}
		}
	}
	
	/**
	 * Checks if is support cut.
	 *
	 * @param so the so
	 * @return true, if is support cut
	 */
	private boolean isSupportCut(Order so) {
		for (Order ao : attackOrders){
			if (ao.getState() != Order.CHECKED_WAITING)
				continue;
			else if (ao.getDestinationTerritory().equals(so.getStartingTerritory()))
				return true;
		}
		return false;
	}

	/**
	 * Sees if any convoy orders were dislodged by checking all water attacks. Executed 
	 * third during adjudication.
	 */
	private void resolveWaterAttacks() {
		for (Order ao : attackOrders){
			if (ao.getState() == Order.CHECKED_WAITING && ao.getDestinationTerritory().getUnit() != null && !ao.getDestinationTerritory().isLand()
					&& ao.getDestinationTerritory().getUnit().getOrder().equals("convoy")){
				if (ao.getStrength() > 1){
					ao.adjudicate(Order.CHECKED_WAITING);
					ao.getDestinationTerritory().getUnit().getOrder().adjudicate(Order.FAILED);
				}
				else
					ao.adjudicate(Order.FAILED);
			}
		}
	}

	/**
	 * Resolves all move attacks.  If it passes it marks the order as CHECKED_WAITING
	 * since it will still need to see if any other moves/attacks are going to the same
	 * place.  Executed fourth during adjudication.  
	 */
	private void resolveMoves() {
		for (Order mo : moveOrders){
			int i = resolveMove(mo);
			if (i == 0)
				mo.adjudicate(Order.FAILED);
			else if (i == 1)
				mo.adjudicate(Order.CHECKED_WAITING);
			else if (i == 2)
				mo.adjudicate(Order.PASSED);
			else if (i == 3)
				mo.adjudicate(Order.FOLLOWING);
		}
	}

	/**
	 * Resolve move.
	 *
	 * @param o the o
	 * @return the int
	 */
	private int resolveMove(Order o){
		
		if (o.getState() == Order.CHECKING)
			return 3;
		
		else if (o.getState() == Order.FAILED ||
				(o.getUnit().isArmy() && !o.getDestinationTerritory().isLand()) ||
				(!o.getUnit().isArmy() && o.getDestinationTerritory().isLand() && !o.getDestinationTerritory().hasCoast()) || 
				!findConvoyPath(o.getUnit(), o.getDestinationTerritory(), o.getConvoyUnits())){
			return 0;
		}
		
		else if (o.getDestinationTerritory().getUnit() != null){
			Order occupyingUnitOrder = o.getDestinationTerritory().getUnit().getOrder();
			if (occupyingUnitOrder.getState() == Order.FAILED)
				return 0;
			else if (!(occupyingUnitOrder.equals("attack") || occupyingUnitOrder.equals("move")))
				return 0;
			else if (occupyingUnitOrder.equals("attack")){
				if (occupyingUnitOrder.getDestinationTerritory().equals(o.getStartingTerritory()))
					return 0;
				else if (resolveAttack(occupyingUnitOrder) == 1)
					return 3;
			}
			else if (occupyingUnitOrder.equals("move")){
				if (occupyingUnitOrder.getDestinationTerritory().equals(o.getStartingTerritory())){
					if (occupyingUnitOrder.getUnit().getOwner() == o.getUnit().getOwner())
						return 2;
					else
						return 0;
				}
				o.adjudicate(Order.CHECKING);
				int i = resolveMove(occupyingUnitOrder);
				if (i == 1 || i == 3)
					return 3;
			}
			return 0;
		}
		
		return 1;
	}

	
	/**
	 * Resolve attacks.
	 */
	private void resolveAttacks() {
		for (Order o : attackOrders){
			int i = resolveAttack(o);
			if (i == 0)
				o.adjudicate(Order.FAILED);
			else if (i == 1)
				o.adjudicate(Order.CHECKED_WAITING);
			else if (i == 2)
				o.adjudicate(Order.FOLLOWING);
		}
	}

	/**
	 * Resolve attack.
	 *
	 * @param o the o
	 * @return the int
	 */
	private int resolveAttack(Order o) {
		if (o.getState() == Order.FAILED ||
				(o.getUnit().isArmy() && !o.getDestinationTerritory().isLand()) ||
				(!o.getUnit().isArmy() && o.getDestinationTerritory().isLand() && !o.getDestinationTerritory().hasCoast()) || 
				!findConvoyPath(o.getUnit(), o.getDestinationTerritory(), o.getConvoyUnits())){
			return 0;
		}
		
		else if (o.getDestinationTerritory().getUnit() != null){
			Order occupyingUnitOrder = o.getDestinationTerritory().getUnit().getOrder();
			
			if (occupyingUnitOrder.getState() == Order.CHECKING)
				return 1;
			
			else if (occupyingUnitOrder.equals("attack") && occupyingUnitOrder.getState() != Order.FAILED){
				if (occupyingUnitOrder.getDestinationTerritory().equals(o.getStartingTerritory())){
					if (occupyingUnitOrder.getStrength() >= o.getStrength())
						return 0;
					else {
						retreatingTerritories.add(occupyingUnitOrder.getStartingTerritory());
						return 1;
					}
				}
				o.adjudicate(Order.CHECKING);
				int i = resolveAttack(occupyingUnitOrder);
				if (i == 0){
					if (o.getStrength() > 1)
						return 1;
					else
						return 0;
				}
				else
					return 2;
			}
			
			else if (occupyingUnitOrder.equals("move") && occupyingUnitOrder.getState() != Order.FAILED){
				int i = resolveMove(occupyingUnitOrder);
				if (i != 1){
					if (o.getStrength() > 1){
						occupyingUnitOrder.adjudicate(Order.FAILED);
						retreatingTerritories.add(occupyingUnitOrder.getStartingTerritory());
						if (i == 2)
							occupyingUnitOrder.getDestinationTerritory().getUnit().getOrder().adjudicate(Order.FAILED);
						return 1;
					}
					else 
						return 0;
				}
				else {
					return 2;
				}
			}
			
			else if (occupyingUnitOrder.getUnit().getOwner() == o.getUnit().getOwner())
				return 0;
			
			else if (occupyingUnitOrder.equals("defend") && occupyingUnitOrder.getState() != Order.FAILED){
				if (o.getStrength() > occupyingUnitOrder.getStrength()){
					retreatingTerritories.add(occupyingUnitOrder.getStartingTerritory());
					return 1;
				}
				else
					return 0;
			}
			
			else {
				if (o.getStrength() > 1) {
					retreatingTerritories.add(occupyingUnitOrder.getStartingTerritory());
					return 1;
				} else
					return 0;
			}
		}
		return 1;
	}
	
	/**
	 * Find convoy path.
	 *
	 * @param currUnit the curr unit
	 * @param t the t
	 * @param convoyUnits the convoy units
	 * @return true, if successful
	 */
	private boolean findConvoyPath(Unit currUnit, Territory t, ArrayList<Unit> convoyUnits) {
		
		if (convoyUnits.size() == 0){
			if (currUnit.getTerritory().isAdjacent(t))
				return true;
			else
				return false;
		}
		
		Unit temp = null;
		int i;
		for (i = 0; i < convoyUnits.size(); i++){
			if (currUnit.getTerritory().isAdjacent(convoyUnits.get(i).getTerritory())){
				temp = convoyUnits.get(i);
				if (temp.getOrder().getState() != Order.FAILED)
					temp.getOrder().adjudicate(Order.PASSED);
				else 
					temp = null;
				break;
			}
		}
		
		if (temp == null)
			return false;
		else{
			convoyUnits.remove(i);
			return findConvoyPath(temp, t, convoyUnits);
		}
	}

	/**
	 * Resolve conflicts.
	 */
	private void resolveConflicts() {
		for (Order ao : attackOrders){
			if (ao.getState() == Order.CHECKED_WAITING){
				if (hasConflict(ao))
					ao.adjudicate(Order.CHECKER);
				else if (ao.getDestinationTerritory().getUnit() == null)
					ao.adjudicate(Order.PASSED);
			}
		}
		
		for (Order mo : moveOrders){
			if (mo.getState() == Order.CHECKED_WAITING){
				if (hasConflict(mo))
					mo.adjudicate(Order.CHECKER);
				else if (mo.getDestinationTerritory().getUnit() == null)
					mo.adjudicate(Order.PASSED);
			}
		}
	}

	/**
	 * Checks for conflict.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	private boolean hasConflict(Order o) {
		for (Order ao : attackOrders){
			if (ao.getState() != Order.CHECKED_WAITING && ao.getState() != Order.FOLLOWING && ao.getState() != Order.CHECKER)
				continue;
			else if (ao.getStartingTerritory().equals(o.getStartingTerritory()))
				continue;
			
			else if (ao.getDestinationTerritory().equals(o.getDestinationTerritory())){
				if (ao.getStrength() >= o.getStrength())
					return true;
			}
		}
		for (Order mo : moveOrders){
			if (mo.getState() != Order.CHECKED_WAITING || mo.getState() != Order.FOLLOWING)
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

	/**
	 * Move passed following units.
	 */
	private void movePassedFollowingUnits() {
		for (Order ao : attackOrders){
			if (ao.getState() == Order.PASSED || ao.getState() == Order.FOLLOWING || ao.getState() == Order.CHECKING)
				ao.getStartingTerritory().removeUnit();
		}
		for (Order mo : moveOrders){
			if (mo.getState() == Order.PASSED || mo.getState() == Order.FOLLOWING || mo.getState() == Order.CHECKING)
				mo.getStartingTerritory().removeUnit();
		}
		for (Order ao : attackOrders)
			moveUnit(ao);
		for (Order mo : moveOrders)
			moveUnit(mo);
	}

	/**
	 * Resolve retreats.
	 */
	public void resolveRetreats(){
		for (Order o : disbandOrders){
			for (Territory t : retreatingTerritories){
				if (o.getStartingTerritory().equals(t)){
					Canvas.getC().removeUnit(o.getUnit());
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
				Canvas.getC().removeUnit(o.getUnit());
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
	private void moveUnit(Order o) {
		if (o.getState() == Order.PASSED || o.getState() == Order.FOLLOWING || o.getState() == Order.CHECKING){
			o.adjudicate(Order.PASSED);
			o.getDestinationTerritory().setUnit(o.getUnit());
			if (!o.getDestinationTerritory().hasSC())
				o.getDestinationTerritory().setOwner(o.getUnit().getOwner());
			o.getUnit().setTerritory(o.getDestinationTerritory());
			o.adjudicate(Order.DONE);
		}	
	}

	/**
	 * Resolve build remove.
	 */
	public void resolveBuildRemove() {
		for (Player p : Canvas.getC().getPlayers()){
			while (p.getSupplyCenterCount() != p.getNumUnits()){
			
				if (p.getSupplyCenterCount() > p.getNumUnits()){
					int i;
					for (i = 0; i < buildOrders.size(); i++){
						Order o = buildOrders.get(i);
						if (o.getStartingTerritory().getOwner() == p.getOwnerKey() && 
								o.getUnit() == null &&
								o.getStartingTerritory().isHomeCity(p.getOwnerKey())){
							if (o.getCommand().contains("army") && o.getStartingTerritory().isLand()){
								Canvas.getC().createUnit(o.getStartingTerritory(), true, p);
								break;
							}
							else if (o.getCommand().contains("navy") && (!o.getStartingTerritory().isLand() || o.getStartingTerritory().hasCoast())){
								Canvas.getC().createUnit(o.getStartingTerritory(), false, p);
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
							Canvas.getC().removeUnit(o.getUnit());
							break;
						}
					}
					if (i != disbandOrders.size())
						disbandOrders.remove(i);
					else if (p.getAUnit() != null)
						Canvas.getC().removeUnit(p.getAUnit());
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
