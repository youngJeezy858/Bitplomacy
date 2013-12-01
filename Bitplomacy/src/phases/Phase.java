package phases;

import orders.Order;

/**
 * Abstract class for defining different Phases of the game.
 */
public abstract class Phase {

	/** The current season. */
	private String season;
	
	/** The current year. */
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
	 * Sets the season.
	 *
	 * @param string the new season
	 */
	public void setSeason(String string) {
		season = string;
	}

	/**
	 * Moves the Unit in the input Order.
	 *
	 * @param o the Order containing the Unit to be moved
	 */
	protected void moveUnit(Order o) {
		if (o.getState() == Order.PASSED || o.getState() == Order.FOLLOWING || o.getState() == Order.CHECKING){
			o.setState(Order.PASSED);
			o.getDestinationTerritory().setUnit(o.getUnit());
			if (!o.getUnit().isArmy()) {
				if (o.getDestinationTerritory().isAdjacentNC(
						o.getStartingTerritory()))
					o.getDestinationTerritory().setNC(true);
				else if (o.getDestinationTerritory().isAdjacentSC(
						o.getStartingTerritory()))
					o.getDestinationTerritory().setSC(true);
			}
			if (!o.getDestinationTerritory().hasSC())
				o.getDestinationTerritory().setOwner(o.getUnit().getOwner());
			o.getUnit().setTerritory(o.getDestinationTerritory());
			o.setState(Order.DONE);
		}	
	}
	
	/**
	 * Adjudicates all orders for this Phase.
	 */
	public abstract void adjudicate();
	
	/**
	 * Adds an Order to this Phase.
	 *
	 * @param o the Order to be added
	 */
	public abstract void addOrder(Order o);
	
}
