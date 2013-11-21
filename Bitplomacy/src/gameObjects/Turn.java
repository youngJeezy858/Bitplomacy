package gameObjects;

import gui.Canvas;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
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
	
	private ArrayList<Order> disbandOrders;
 
	private ArrayList<Order> buildOrders;

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
		else if (o.getCommand().contains("build"))
			buildOrders.add(o);
		else if (o.getCommand().equals("idle")){
			if (season.contains("Retreats"))
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
			else if (o.getConvoyUnits().size() == 0)
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
			if (so.getState() == Order.FAILED)
				continue;
			else if (isSupportCut(so)){
				so.adjudicate(Order.FAILED);
				continue;
			}
			else if (!so.isValidSupport())
				so.adjudicate(Order.FAILED);
			else{
				so.adjudicate(Order.PASSED);
				so.getSupportedUnit().getOrder().incrementStrength();
			}
		}
	}

	private boolean isSupportCut(Order so) {
		for (Order ao : attackOrders){
			if (ao.getState() != Order.CHECKED_WAITING)
				continue;
			else if (ao.getTerr2().equals(so.getTerr1()))
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
			if (ao.getState() == Order.CHECKED_WAITING && ao.getTerr2().getUnit() != null && !ao.getTerr2().isLand()
					&& ao.getTerr2().getUnit().getOrder().equals("convoy")){
				if (ao.getStrength() > 1){
					ao.adjudicate(Order.CHECKED_WAITING);
					ao.getTerr2().getUnit().getOrder().adjudicate(Order.FAILED);
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

	private int resolveMove(Order o){
		
		if (o.getState() == Order.CHECKING)
			return 3;
		
		else if (o.getState() == Order.FAILED ||
				(o.getUnit().isLand() && !o.getTerr2().isLand()) ||
				(!o.getUnit().isLand() && o.getTerr2().isLand() && !o.getTerr2().hasCoast()) || 
				!Order.findConvoyPath(o.getUnit(), o.getTerr2(), o.getConvoyUnits())){
			return 0;
		}
		
		else if (o.getTerr2().getUnit() != null){
			Order occupyingUnitOrder = o.getTerr2().getUnit().getOrder();
			if (occupyingUnitOrder.getState() == Order.FAILED)
				return 0;
			else if (!(occupyingUnitOrder.equals("attack") || occupyingUnitOrder.equals("move")))
				return 0;
			else if (occupyingUnitOrder.equals("attack")){
				if (occupyingUnitOrder.getTerr2().equals(o.getTerr1()))
					return 0;
				else if (resolveAttack(occupyingUnitOrder) == 1)
					return 3;
			}
			else if (occupyingUnitOrder.equals("move")){
				if (occupyingUnitOrder.getTerr2().equals(o.getTerr1())){
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

	private int resolveAttack(Order o) {
		if (o.getState() == Order.FAILED ||
				(o.getUnit().isLand() && !o.getTerr2().isLand()) ||
				(!o.getUnit().isLand() && o.getTerr2().isLand() && !o.getTerr2().hasCoast()) || 
				!Order.findConvoyPath(o.getUnit(), o.getTerr2(), o.getConvoyUnits())){
			return 0;
		}
		
		else if (o.getTerr2().getUnit() != null){
			Order occupyingUnitOrder = o.getTerr2().getUnit().getOrder();
			
			if (occupyingUnitOrder.getState() == Order.CHECKING)
				return 1;
			
			else if (occupyingUnitOrder.equals("attack") && occupyingUnitOrder.getState() != Order.FAILED){
				if (occupyingUnitOrder.getTerr2().equals(o.getTerr1())){
					if (occupyingUnitOrder.getStrength() >= o.getStrength())
						return 0;
					else {
						retreatingTerritories.add(occupyingUnitOrder.getTerr1());
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
						retreatingTerritories.add(occupyingUnitOrder.getTerr1());
						if (i == 2)
							occupyingUnitOrder.getTerr2().getUnit().getOrder().adjudicate(Order.FAILED);
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
					retreatingTerritories.add(occupyingUnitOrder.getTerr1());
					return 1;
				}
				else
					return 0;
			}
			
			else {
				if (o.getStrength() > 1) {
					retreatingTerritories.add(occupyingUnitOrder.getTerr1());
					return 1;
				} else
					return 0;
			}
		}
		return 1;
	}

	private void resolveConflicts() {
		for (Order ao : attackOrders){
			if (ao.getState() == Order.CHECKED_WAITING){
				if (hasConflict(ao))
					ao.adjudicate(Order.CHECKER);
				else if (ao.getTerr2().getUnit() == null)
					ao.adjudicate(Order.PASSED);
			}
		}
		
		for (Order mo : moveOrders){
			if (mo.getState() == Order.CHECKED_WAITING){
				if (hasConflict(mo))
					mo.adjudicate(Order.CHECKER);
				else if (mo.getTerr2().getUnit() == null)
					mo.adjudicate(Order.PASSED);
			}
		}
	}

	private boolean hasConflict(Order o) {
		for (Order ao : attackOrders){
			if (ao.getState() != Order.CHECKED_WAITING && ao.getState() != Order.FOLLOWING && ao.getState() != Order.CHECKER)
				continue;
			else if (ao.getTerr1().equals(o.getTerr1()))
				continue;
			
			else if (ao.getTerr2().equals(o.getTerr2())){
				if (ao.getStrength() >= o.getStrength())
					return true;
			}
		}
		for (Order mo : moveOrders){
			if (mo.getState() != Order.CHECKED_WAITING || mo.getState() != Order.FOLLOWING)
				continue;
			else if (mo.getTerr1().equals(o.getTerr1()))
				continue;
			
			else if (mo.getTerr2().equals(o.getTerr2())){
				if (mo.getStrength() >= o.getStrength())
					return true;
			}
		}
		return false;
	}

	private void movePassedFollowingUnits() {
		for (Order ao : attackOrders){
			if (ao.getState() == Order.PASSED || ao.getState() == Order.FOLLOWING || ao.getState() == Order.CHECKING)
				ao.getTerr1().removeUnit();
		}
		for (Order mo : moveOrders){
			if (mo.getState() == Order.PASSED || mo.getState() == Order.FOLLOWING || mo.getState() == Order.CHECKING)
				mo.getTerr1().removeUnit();
		}
		for (Order ao : attackOrders)
			moveUnit(ao);
		for (Order mo : moveOrders)
			moveUnit(mo);
	}

	public void resolveRetreats(){
		for (Order o : disbandOrders){
			for (Territory t : retreatingTerritories){
				if (o.getTerr1().equals(t)){
					Canvas.getC().removeUnit(o.getUnit());
					break;
				}
			}
		}
		for (Order o : retreatOrders){
			for (Territory t : retreatingTerritories){
				if (o.getTerr1().equals(t)){
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
				ro1.getTerr1().removeUnit();
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
				ao.getTerr1().removeUnit();
				moveUnit(ao);
			}
		}
	}

	private boolean hasRetreatConflict(Order o){
		for (Order ro2 : retreatOrders){
			if (o.getTerr1().equals(ro2.getTerr1()))
				continue;
			else if (ro2.getState() != Order.CHECKED_WAITING && ro2.getState() != Order.CHECKER)
				continue;
			else if (ro2.getTerr2().equals(o.getTerr2()))
				return true;
		}
		return false;
	}
	
	private boolean resolveRetreat(Order o) {
		if (o.getUnit().isLand() && !o.getTerr2().isLand())
			return false;
		else if (!o.getUnit().isLand() && o.getTerr2().isLand() && !o.getTerr2().hasCoast())
			return false;
		else if (o.getTerr2().getUnit() != null)
			return false;
		return o.getTerr1().isAdjacent(o.getTerr2());
	}

	public void setSeason(String string) {
		season = string;
	}

	private void moveUnit(Order o) {
		if (o.getState() == Order.PASSED || o.getState() == Order.FOLLOWING || o.getState() == Order.CHECKING){
			o.adjudicate(Order.PASSED);
			o.getTerr2().setUnit(o.getUnit());
			if (!o.getTerr2().hasSC())
				o.getTerr2().setOwner(o.getUnit().getOwner());
			o.getUnit().setTerritory(o.getTerr2());
			o.adjudicate(Order.DONE);
		}	
	}

	public void resolveBuildRemove() {

	}

}
