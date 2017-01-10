package fr.utbm.lo43.logic;

public class Game {

	public Map map;
	
	private Score score;
	private Inventory inventory;
	private ModeGameStrategy strategy;
	
	private static boolean gameOver;
	
	public Game(){
		map = Map.getInstance();
		inventory = Inventory.getInstance();
		
		gameOver = false;
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public static void setGameOver(boolean _gameOver)
	{
		gameOver = _gameOver;
	}
	
	public static boolean getGameOver()
	{
		return gameOver;
	}
}
