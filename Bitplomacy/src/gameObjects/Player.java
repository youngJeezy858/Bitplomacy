package gameObjects;

import java.util.ArrayList;

public class Player {

	private ArrayList<Unit> units;
	private String name;
	private int supplyCenterCount;
	
	public Player(String name){
		this.name = name;
		units = new ArrayList<Unit>();
	}
	
	public void addUnit(Unit u){
		units.add(u);
	}
	
	public int getSupplyCount(){
		return supplyCenterCount;
	}
	
	public String getName(){
		return name;
	}
	
	public void adjustNumSC(int num){
		supplyCenterCount = num;
	}

	public void executeOrders() {
		for (Unit u : units)
			u.executeOrder();
	}

	public void resetOrders() {
		for (Unit u : units)
			u.resetOrder();
	}
	
}
