package phases;

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
	
	public abstract void adjudicate();
	public abstract void addOrder(Order o);
	
}
