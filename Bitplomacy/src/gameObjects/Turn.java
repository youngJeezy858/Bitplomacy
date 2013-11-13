package gameObjects;

import java.util.ArrayList;

public class Turn {

	private String season;
	private int year;
	private ArrayList<Order> supportOrders;
	private ArrayList<Order> defendOrders;
	private ArrayList<Order> attackOrders;
	private ArrayList<Order> moveOrders;	
	private ArrayList<Order> convoyOrders;
	private ArrayList<Order> blankOrders;
	private ArrayList<Order> retreatOrders;

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
		checkSyntax();
		resolveSupport();	
		resolveMove();
		resolveAttack();
		resolveConvoy();
		resolveWaitingOnConvoy();
		resolveRetreats();
		resolveSuccessfulMoves();
	}

	private void checkSyntax() {
		for (Order o : supportOrders){
			if (!o.isValidOrder())
				o.adjudicate(Order.FAILED);
		}
		for (Order o : attackOrders){
			if (!o.isValidOrder() && !o.isValidOrder())
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

	private void resolveMove() {
		for (Order ao : moveOrders){
			if (ao.getTerr2().getUnit() != null || ao.getState() == Order.FAILED){
				ao.adjudicate(Order.FAILED);
				continue;
			}
			else if ()
		}
	}

	private void resolveSuccessfulMoves() {
		// TODO Auto-generated method stub
		
	}

	private void resolveWaitingOnConvoy() {
		// TODO Auto-generated method stub
		
	}

	private void resolveRetreats() {
		
	}

	private void resolveAttack() {
		for (Order o : attackOrders){
			if (o.getState() != Order.FAILED)
				resolveSameMoves(o);
		}
	}

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

}
