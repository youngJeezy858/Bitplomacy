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
	
	public void adjustNumSC(){
		int i = 0;
		for (Unit u : units){
			if (u.getTerritory().hasSC())
				i++;
		}
		supplyCenterCount = i;
	}

	public void executeOrders() {
		for (Unit u : units)
			u.executeOrder();
	}
	
}
