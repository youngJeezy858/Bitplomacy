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
		resolveConvoy();
		resolveSupport();
		resolveDefend();
		resolveIdle();
		resolveAttack();
		resolveRetreats();
	}

	private void resolveRetreats() {
		
	}

	private void resolveAttack() {
		for (Order o : attackOrders){
			if (o.isSuccessful())
				resolveSameMoves(o);
		}
	}

	private void resolveSameMoves(Order o) {
		for (Order attack : attackOrders){
			if (o.getTerr2().equals(attack.getTerr2()) && !o.getTerr1().equals(attack.getTerr1())){
				if (o.getStrength() <= attack.getStrength()){
					o.adjudicate(false);
					retreatOrders.add(o);
					return;
				}
			}
		}
		o.getTerr2().setUnit(o.getUnit());
		o.getUnit().setTerritory(o.getTerr2());
		o.getTerr1().removeUnit();
		return;
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
			if (attack.getTerr2().equals(o.getTerr1())){
				if (attack.getStrength() > o.getStrength()){
					o.adjudicate(false);
					attack.adjudicate(true);
				}
				else
					o.adjudicate(true);
					attack.adjudicate(false);
			}
		}
	}

	private void resolveConvoy() {
		for (Order o : attackOrders){
			if (o.getTerr2().isLand() || !o.getUnit().isLand())
				continue;
			o.setConvoyDestination(o.getTerr1());
			o.adjudicate(findConvoyOrder(o));
		}
	}

	private boolean findConvoyOrder(Order o) {
		for (Order convoy: convoyOrders){
			if (convoy.getTerr1().equals(o.getTerr2()) && convoy.getTerr2().equals(o.getConvoyDestination())){
				if (convoy.getConvoyDestination().isLand()){
					o.setTerr2(convoy.getConvoyDestination());
					return true;
				}
				else{
					o.setConvoyDestination(convoy.getTerr1());
					o.setTerr2(convoy.getConvoyDestination());
					findConvoyOrder(o);
				}
			}
		}
		return false;
	}

	private void resolveSupport() {
		for (Order o : supportOrders){
			if (findSomethingToSupport(o, defendOrders))
				continue;
			if (findSomethingToSupport(o, blankOrders))
				continue;
			if (findSomethingToSupport(o, attackOrders))
				continue;
			o.adjudicate(false);
		}
	}

	private boolean findSomethingToSupport(Order o, ArrayList<Order> orders) {
		for (Order supported : orders){
			if (supported.getTerr1().equals(o.getSupportedUnit().getTerritory())){
				supported.incrementStrength();
				o.adjudicate(true);
				return true;
			}
		}
		return false;
	}
	
}
