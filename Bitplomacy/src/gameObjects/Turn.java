package gameObjects;

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
		else
			blankOrders.add(o);
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
		resolveRetreats();
		resolveSuccessfulMoves();
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
		for (Order o : attackOrders){
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
			for (Order ao : attackOrders){
				if (ao.getState() != Order.FAILED)
					continue;
				else if (ao.getTerr2().equals(so.getTerr1()) && !ao.getTerr1().equals(so.getTerr2()))
					so.adjudicate(Order.FAILED);
				else if (!so.isValidSupport())
					so.adjudicate(Order.FAILED);
				else{
					so.adjudicate(Order.PASSED);
					so.getSupportedUnit().getOrder().incrementStrength();
				}
					
			}
		}
	}

	/**
	 * Sees if any convoy orders were dislodged by checking all water attacks. Executed 
	 * third during adjudication.
	 */
	private void resolveWaterAttacks() {
		for (Order ao : attackOrders){
			if (ao.getState() != Order.FAILED && ao.getTerr2().getUnit() != null && !ao.getTerr2().isLand()
					&& !ao.getTerr2().getUnit().getOrder().equals("attack") && !ao.getTerr2().getUnit().getOrder().equals("move")){
				if (ao.getStrength() > ao.getTerr2().getUnit().getOrder().getStrength()){
					ao.adjudicate(Order.PASSED);
					ao.getTerr2().getUnit().getOrder().adjudicate(Order.FAILED);
					retreatOrders.add(ao.getTerr2().getUnit().getOrder());
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
			if (resolveMove(mo))
				mo.adjudicate(Order.FAILED);
			else
				mo.adjudicate(Order.CHECKED_WAITING);
		}
	}
	
	private boolean resolveMove(Order o){
		if (o.getState() == Order.FAILED ||
				(o.getUnit().isLand() && !o.getTerr2().isLand()) ||
				(!o.getUnit().isLand() && o.getTerr2().isLand() && !o.getTerr2().hasCoast())){
			return false;
		}
		
		else if (o.getTerr2().getUnit() != null){
			Order occupyingUnitOrder = o.getTerr2().getUnit().getOrder();
			if (occupyingUnitOrder.equals("attack")){
				if (occupyingUnitOrder.getTerr2().equals(o.getTerr1()) || !resolveAttack(occupyingUnitOrder))
					return false;
			}
			else if (occupyingUnitOrder.equals("move")){
				if (occupyingUnitOrder.getTerr2().equals(o.getTerr1())){
					if (occupyingUnitOrder.getUnit().getOwner() == o.getUnit().getOwner())
						return true;
					else
						return false;
				}
				else if (resolveMove)
					/////THis won't work for the rotating door case.
			}
		}
	}

	private boolean resolveAttack(Order occupyingUnitOrder) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Resolve successful moves.
	 */
	private void resolveSuccessfulMoves() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Resolve waiting on convoy.
	 */
	private void resolveWaitingOnConvoy() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Resolve retreats.
	 */
	private void resolveRetreats() {
		
	}

	/**
	 * Resolve attack.
	 */
	private void resolveAttacks() {
		for (Order o : attackOrders){
			if (o.getState() != Order.FAILED)
				resolveSameMoves(o);
		}
	}

	/**
	 * Resolve same moves.
	 *
	 * @param o the o
	 */
	private void resolveSameMoves(Order o) {
		for (Order attack : attackOrders){
			if (o.getTerr2().equals(attack.getTerr2()) && !o.getTerr1().equals(attack.getTerr1())){
				if (o.getStrength() <= attack.getStrength()){
					o.adjudicate(Order.FAILED);
					return;
				}
				else
					attack.adjudicate(Order.FAILED);
			}
		}
		if (o.getState() != Order.PASSED)
			o.adjudicate(Order.CHECKED_WAITING);
	}

}
