package Objects;

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
		if (u.getT().hasSC())
			supplyCenterCount++;
	}
	
	public int getSupplyCount(){
		return supplyCenterCount;
	}
	
	public String getName(){
		return name;
	}
}
