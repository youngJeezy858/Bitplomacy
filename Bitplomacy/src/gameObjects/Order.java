package gameObjects;

import java.util.ArrayList;

import gui.Canvas;

public class Order {

	private Territory terr1;
	private Territory terr2;
	private Territory convoyDestination;
	private Unit unit;
	private Unit supportedUnit;
	private ArrayList<Unit> convoyUnits;
	private String command;
	private int strength;
	
	private int state;
	public static final int NOT_CHECKED = 0;
	public static final int CHECKED_WAITING = 1;
	public static final int FAILED = 2;
	public static final int PASSED = 3;
	public static final int AMPHIBIOUS_ATTACK = 4;
		
	public Order(Territory t1){
		terr1 = t1;
		unit = terr1.getUnit();
		strength = 1;
		command = "idle";
		state = 0;
		convoyUnits = new ArrayList<Unit>();
	}
	
	public String toString() {
		String s = terr1.getOwnerName() + " unit in \n" + terr1.getName() + "\n";
		if (command != "idle")
			s += command + "\n";
		if (supportedUnit != null)
			s += "for " + Territory.getOwnerName(supportedUnit.getOwner()) + "\nat ";
		if (terr2 != null)
			s += terr2.getName() + "\n";
		if (convoyUnits.size() > 0){
			s += "by way of \n";
			for (Unit u : convoyUnits)
				s += u.getTerritory().getName() + "\n";
		}
		if (convoyDestination != null)
			s += "to " + convoyDestination.getName();
		return s;
	}

	public void addConvoyUnit(Unit u){
		convoyUnits.add(u);
	}

	public void adjudicate(int i){
		state = i;
	}

	public void execute(){
		Canvas.getC().addOrder(this);	
	}

	public String getCommand() {
		return command;
	}

	public Territory getConvoyDestination(){
		return convoyDestination;
	}

	public int getStrength(){
		return strength;
	}

	public Unit getSupportedUnit() {
		return supportedUnit;
	}

	public Territory getTerr1() {
		return terr1;
	}

	public Territory getTerr2() {
		return terr2;
	}

	public Unit getUnit() {
		return unit;
	}

	public void incrementStrength(){
		strength++;
	}

	public int getState() {
		return state;
	}

	public boolean isValidOrder(){
		
		if (command.equals("attack") && terr2 != null)
			return terr1.isValidAttack(terr2, convoyUnits);
		else if (command.equals("move") && terr2 != null)
			return true;
		else if (command.equals("support") && supportedUnit != null && terr2 != null)
			return true;
		else if (command.equals("defend"))
			return true;
		else if (command.equals("convoy") && terr2 != null && convoyDestination != null)
			return true;
		else
			return false;
		
	}

	public boolean isValidSupport() {
		
		if (!unit.isLand() && terr2.isLand() && !terr2.hasCoast())
			return false;
		if (unit.isLand() && !terr2.isLand())
			return false;
		
		String supportedCommand = supportedUnit.getOrder().command;
		if (!(supportedCommand.equals("attack") || supportedCommand.equals("move") || supportedCommand.equals("defend"))) 
			return false;
		
		return terr1.isAdjacent(terr2) && 
				supportedUnit.getOrder().getTerr2().isAdjacent(terr2);
	}

	public void setCommand(String c){
		command = c;
	}

	public void setConvoyDestination(Territory t) {
		convoyDestination = t;
	}

	public void setSupport(Unit u) {
		supportedUnit = u;
	}

	public void setTerr1(Territory t){
		terr1 = t;
	}

	public void setTerr2(Territory t2){
		terr2 = t2;
	}

	public boolean expectingConvoy() {
		if (!terr1.isAdjacent(terr2) && unit.isLand() && terr2.isLand())
			return true;
		return false;
	}

	public void pushOrder() {
		unit.setOrder(this);
	}

	public boolean checkConvoyingUnits() {	
		Order o = convoyUnits.get(0).getOrder();
		if (!o.getCommand().equals("convoy") || !o.getTerr2().equals(terr1))
			return false;
		o = convoyUnits.get(convoyUnits.size()).getOrder();
		if (!o.getCommand().equals("convoy") || !o.getConvoyDestination().equals(terr2))
			return false;
		for (int i = 1; i < convoyUnits.size(); i++) {
			o = convoyUnits.get(i).getOrder();
			if (!o.getCommand().equals("convoy")
					|| !o.getTerr2().equals(convoyUnits.get(i-1).getOrder()))
				return false;
		}
		return true;
	}
	
	
	
}
