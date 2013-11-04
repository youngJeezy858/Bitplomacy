package gameObjects;

import java.util.ArrayList;

public class Turn {

	private String season;
	private int year;
	private ArrayList<Order> supportOrders;
	private ArrayList<Order> defendOrders;
	private ArrayList<Order> attackOrders;
	private ArrayList<Order> convoyOrders;
	private ArrayList<Order> blankOrders;
	private ArrayList<Order> retreatOrders;

	public Turn(String season, int year){
		this.season = season;
		this.year = year;
		supportOrders = new ArrayList<Order>();
		defendOrders = new ArrayList<Order>();
		attackOrders = new ArrayList<Order>();
		convoyOrders = new ArrayList<Order>();
		blankOrders = new ArrayList<Order>();
		retreatOrders = new ArrayList<Order>();
	}
	
	public void addOrder(Order o){
		if (o.getCommand().equals("support"))
			supportOrders.add(o);
		else if (o.getCommand().equals("defend"))
			defendOrders.add(o);
		else if (o.getCommand().equals("attack"))
			attackOrders.add(o);
		else if (o.getCommand().equals("convoy"))
			convoyOrders.add(o);
		else
			blankOrders.add(o);
	}
	
	public String toString(){
		return season + " " + year;
	}
	
	public String getSeason(){
		return season;
	}
	
	public int getYear(){
		return year;
	}

	public void resolveOrders() {
		resolveSupport();
		resolveConvoy();
		resolveDefend();
		resolveIdle();
		resolveAttack();
		resolveFollowing();
		resolveRetreats();
	}

	private void resolveFollowing() {
		
		for (Order o : attackOrders){
			if (o.getState() == Order.CHECKED_WAITING){
				recursiveResolveAttack(o);
			}
		}
	}

	private void recursiveResolveAttack(Order o) {
		
		Unit u = o.getTerr2().getUnit();
		if (u != null && u.getOrder().getState() == Order.CHECKED_WAITING){
			recursiveResolveAttack(o.getTerr2().getUnit().getOrder());
		}
		else if (u == null || u.getOrder().getState() == Order.PASSED) {
			o.getTerr2().setUnit(o.getUnit());
			o.getUnit().setTerritory(o.getTerr2());
			o.getTerr1().removeUnit();
			o.adjudicate(Order.PASSED);
		}
		else if (u.getOrder().getState() == Order.FAILED && o.getStrength() > 1){
			o.getTerr2().setUnit(o.getUnit());
			o.getUnit().setTerritory(o.getTerr2());
			o.getTerr1().removeUnit();
			o.adjudicate(Order.PASSED);
		}
	}

	private void resolveRetreats() {
		
	}

	private void resolveAttack() {
		for (Order o : attackOrders){
			if (o.getState() != Order.FAILED)
				resolveSameMoves(o);
		}
	}

//	o.getTerr2().setUnit(o.getUnit());
//	o.getUnit().setTerritory(o.getTerr2());
//	o.getTerr1().removeUnit();
	
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
	
	private void resolveIdle() {
		for (Order o : blankOrders)
			findAttackers(o);
	}

	private void resolveDefend() {
		for (Order o : defendOrders){
			o.incrementStrength();
			findAttackers(o);
		}
	}

	private void findAttackers(Order o) {
		for (Order attack : attackOrders){
			if (attack.getTerr2().equals(o.getTerr1()) && attack.getState() != Order.FAILED){
				if (attack.getStrength() > o.getStrength()){
					attack.adjudicate(Order.PASSED);
					o.adjudicate(Order.FAILED);
					retreatOrders.add(o);
				}
				else{
					attack.adjudicate(Order.FAILED);
					o.adjudicate(Order.PASSED);
				}
			}
		}
	}

	private void resolveConvoy() {
		for (Order o : attackOrders){
			if (o.getState() == Order.AMPHIBIOUS_ATTACK){
				if (o.checkConvoyingUnits())
					o.adjudicate(Order.CHECKED_WAITING);
				else 
					o.adjudicate(Order.FAILED);
			}
		}
	}

	private void resolveSupport() {
		for (Order o : supportOrders){
			Order supported = o.getSupportedUnit().getOrder();
			if (supported.getTerr2().equals(o.getTerr2()) && 
					(supported.getCommand().equals("attack") || supported.getCommand().equals("defend")) ){
				supported.incrementStrength();
				o.adjudicate(Order.PASSED);
			}
			else
				o.adjudicate(Order.FAILED);
		}
	}

}
