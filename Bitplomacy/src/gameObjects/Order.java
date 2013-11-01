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
	private boolean successfulMove;
	
	public Order(Territory t1){
		terr1 = t1;
		unit = terr1.getUnit();
		strength = 1;
		command = "idle";
		successfulMove = false;
		convoyUnits = new ArrayList<Unit>();
	}
	
	public boolean isValidOrder(){
		
		if (command.equals("attack") && terr1.isValidAttack(terr2, convoyUnits))
			return true;
		else if (command.equals("support") && terr1.isValidSupport(terr2, supportedUnit))
			return true;
		else if (command.equals("defend"))
			return true;
		else if (command.equals("convoy") && terr1.isValidConvoy(terr2, convoyDestination))
			return true;
		else
			return false;
		
	}

	public String toString() {
		String s = terr1.getName() + "\n";
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
		return s;
	}

	public void addConvoyUnit(Unit u){
		convoyUnits.add(u);
	}

	public void adjudicate(boolean b){
		successfulMove = b;
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

	public boolean isReady() {
		if (command == "idle")
			return false;
		else if (command.equals("attack") && terr2 == null)
			return false;
		else if (command.equals("support") && (terr2 == null || supportedUnit == null))
			return false;
		else if (command.equals("convoy") && (terr2 == null || convoyDestination == null))
			return false;
		else if (command.equals("attack") && Canvas.getC().getState() == Canvas.SELECT_CONVOY_UNITS && !this.isValidOrder()){
			System.out.println("fuck");
			return false;
		}
		return true;
	}

	public boolean isSuccessful() {
		return successfulMove;
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
		if (!terr1.isAdjacent(terr2) && unit.isLand() && terr2.isLand()){
			System.out.println("hiya");
			return true;
		}
		return false;
	}
	
}
