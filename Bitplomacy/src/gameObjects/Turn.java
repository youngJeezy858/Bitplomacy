package gameObjects;

import java.util.ArrayList;

public class Turn {

	private String season;
	private int year;
	private ArrayList<Order> orders;
	
	public Turn(String season, int year){
		this.season = season;
		this.year = year;
		orders = new ArrayList<Order>();
	}
	
	public void addOrder(Order o){
		orders.add(o);
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
	
}
