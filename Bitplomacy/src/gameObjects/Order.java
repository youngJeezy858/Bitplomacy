package gameObjects;

public class Order {

	private Unit unit;
	private Territory terr1;
	private String command;
	private Territory terr2;
	
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

		if (command.equals("attack") && terr2 != null){
			terr1.removeUnit();
			unit.setTerritory(terr2);
			terr2.setUnit(unit);
		}
		
	}

	public String toString() {
		String s = terr1.getName() + " ";
		if (command != null)
			s += command + " ";
		if (terr2 != null)
			s += terr2.getName();
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

}
