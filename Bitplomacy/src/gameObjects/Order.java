package gameObjects;

public class Order {

	private Unit unit;
	private Territory terr1;
	private String command;
	private Territory terr2;
	private Unit supportedUnit;
	
	public Order(Territory t1){
		terr1 = t1;
		unit = terr1.getUnit();
	}
	
	public void addCommand(String c){
		command = c;
	}
	
	public void addTerr2(Territory t2){
		terr2 = t2;
	}
	
	public void execute(){

		if (command.equals("attack")){
			attack();
		}
		
	}

	public boolean isValidOrder(){
		
		if (command.equals("attack") && terr1.isValidAttack(terr2))
			return true;
		else if (command.equals("support") && terr1.isValidSupport(terr2, supportedUnit))
			return true;
		else if (command.equals("defend"))
			return true;
		else if (command.equals("convoy") && terr1.isValidConvoy(terr2, null))
			return true;
		else
			return false;
		
	}
	
	private void attack() {
		terr1.removeUnit();
		unit.setTerritory(terr2);
		terr2.setUnit(unit);	
	}

	public String toString() {
		String s = terr1.getName() + "\n";
		if (command != null)
			s += command + "\n";
		if (supportedUnit != null)
			s += "for " + Territory.getOwnerName(supportedUnit.getOwner()) + "\nat ";
		if (terr2 != null)
			s += terr2.getName() + "\n";
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
		if (command == null)
			return false;
		else if (command.equals("attack") && terr2 == null)
			return false;
		else if (command.equals("support") && (terr2 == null || supportedUnit == null))
			return false;
		else if (command.equals("convoy") && terr2 == null)
			return false;
		return true;
	}

}
