package fr.utbm.lo43.logic;

import org.newdawn.slick.Color;
import fr.utbm.lo43.logic.Line;

public class Game {

	public Map map;
	
	private Score score;
	private Inventory inventory;
	private ModeGameStrategy strategy;
	
	
	public Game(){
		map = Map.getInstance();
		inventory = Inventory.getInstance();
		
		map.AddLine(new ClassicLine(Color.green));
	}
	
}
