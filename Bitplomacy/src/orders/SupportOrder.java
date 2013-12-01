package orders;

import org.newdawn.slick.Graphics;

import gameObjects.Territory;

public class SupportOrder extends Order {

	private Territory supported;
	
	public SupportOrder(Territory t) {
		super(t);
		command = "support";
	}

	@Override
	public boolean isValidOrder() {
		
		if (supported == null || destinationTerritory == null)
			return false;
		
		Order supportedOrder = supported.getUnit().getOrder();
		
		if (!unit.isArmy() && destinationTerritory.isLand() && !destinationTerritory.hasCoast())
			return false;
		
		else if (unit.isArmy() && !destinationTerritory.isLand())
			return false;
		
		else if (supportedOrder.equals("attack") && destinationTerritory.getUnit() != null &&
					destinationTerritory.getUnit().getOwner() == unit.getOwner())
			return false;
		
		else if (supportedOrder.equals("attack") && supportedOrder.equals("move") 
					&& supportedOrder.equals("defend"))
			return false;
				
		else
			return isAdjacent();
	}	

	@Override
	public void addAdditionalTerritory(Territory t) {
		supported = t;
	}

	public void support() {
		supported.getUnit().getOrder().incrementStrength();
	}
	
	public String toString(){
		String s = currentTerritory.getName() + "\n";
		s += command + "\n";
		if (supported != null && supported.equals(destinationTerritory))
			s += "unit defending " + supported.getName();
		else if (supported != null){
			s += "unit at " + supported.getName() + "\n";
			s += "to " + destinationTerritory.getName();
		}
		else if (destinationTerritory != null){
			s += "to " + destinationTerritory.getName() + "\n";
			s += "[SELECT UNIT\n TO SUPPORT]";
		}
		else
			s += "[SELECT DESTINATION\n OF SUPPORT]";
		return s;
	}

	@Override
	public String toShortString() {
		String s = " Sup ";
		if (destinationTerritory != null)
			s += destinationTerritory.getName().substring(0, 4);
		if (supported != null){
			if (supported.getUnit() != null){
				if (supported.getUnit().isArmy())
					s += " A";
				else
					s += " N";
			}
			s += " in "+ supported.getName().substring(0, 4);
		}
		return s;
	}

	
	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawString(currentTerritory.getName() + " supporting", x, y);
		y += 10;
		if (supported != null) 
			g.drawString(supported.getName() + " to", x, y);
		else if (destinationTerritory != null){
			g.drawString("[SELECT UNIT TO SUPPORT]", x, y);	
		}
		y += 10;
		if (destinationTerritory != null)
			g.drawString(destinationTerritory.getName(), x, y);
		else{
			g.drawString("[SELECT DESTINATION OF SUPPORT]", x, y);
			return;
		}
	}
}
