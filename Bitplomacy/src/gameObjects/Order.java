package gameObjects;

import gui.Canvas;

public class Order {

	private Unit unit;
	private Territory terr1;
	private String command;
	private Territory terr2;
	private Unit supportedUnit;
	private Territory convoyDes;
	private int strength;
	private boolean successfulMove;
	
	public Order(Territory t1){
		terr1 = t1;
		unit = terr1.getUnit();
		strength = 1;
		command = "idle";
		successfulMove = true;
	}
	
	public void setCommand(String c){
		command = c;
	}
	
	public void setTerr2(Territory t2){
		terr2 = t2;
	}
	
	public void execute(){
		Canvas.getC().addOrder(this);	
	}

	public boolean isValidOrder(){
		
		if (command.equals("attack") && terr1.isValidAttack(terr2))
			return true;
		else if (command.equals("support") && terr1.isValidSupport(terr2, supportedUnit))
			return true;
		else if (command.equals("defend"))
			return true;
		else if (command.equals("convoy") && terr1.isValidConvoy(terr2, convoyDes))
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
		if (convoyDes != null)
			s += "to " + convoyDes.getName();
		return s;
	}

	public Territory getTerr2() {
		return terr2;
	}

	public Unit getUnit() {
		return unit;
	}

	public String getCommand() {
		return command;
	}

	public void setSupport(Unit u) {
		supportedUnit = u;
	}

	public boolean isReady() {
		if (command == "idle")
			return false;
		else if (command.equals("attack") && terr2 == null)
			return false;
		else if (command.equals("support") && (terr2 == null || supportedUnit == null))
			return false;
		else if (command.equals("convoy") && (terr2 == null || convoyDes == null))
			return false;
		return true;
	}
	
	public void setConvoyDestination(Territory t){
		convoyDes = t;
	}
	
	public int getStrength(){
		return strength;
	}
	
	public void incrementStrength(){
		strength++;
	}

	public Unit getSupportedUnit() {
		return supportedUnit;
	}

	public Territory getTerr1() {
		return terr1;
	}

	public void adjudicate(boolean b){
		successfulMove = b;
	}
	
	public Territory getConvoyDestination(){
		return convoyDes;
	}
	
	public void setTerr1(Territory t){
		terr1 = t;
	}

	public boolean isSuccessful() {
		return successfulMove;
	}
	
}
