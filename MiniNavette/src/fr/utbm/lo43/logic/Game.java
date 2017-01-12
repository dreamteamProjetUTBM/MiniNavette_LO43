package fr.utbm.lo43.logic;

/**
 * @author Quentin Nahil Thomas Jeremy 
 * classe -sensée- contenant la logique du jeu.
 */
public class Game {

	public Map map;
	
	private Score score;
	private Inventory inventory;
	private ModeGameStrategy strategy;
	
	private static boolean gameOver;
	
	public Game(){
		Map.reInit();
		Inventory.reInit();
		Score.reInit();
		map = Map.getInstance();
		inventory = Inventory.getInstance();
		score = Score.getInstance();
	
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
