package fr.utbm.lo43.logic;

public class Game {

	public Map map;
	
	private Score score;
	private Inventory inventory;
	private ModeGameStrategy strategy;
	
	
	public Game(){
		map = Map.getInstance();
		inventory = Inventory.getInstance();
	}
	
	public Inventory getInventory(){
		return inventory;
	}
}
