package fr.utbm.lo43;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.gamestates.MainGameState;
import fr.utbm.lo43.gamestates.MainMenuGameState;
import fr.utbm.lo43.gamestates.PauseMenuGameState;

public class GameWindow extends StateBasedGame
{
	public static final int GS_MAIN_MENU = 0;
	public static final int GS_GAME = 1;
	public static final int GS_PAUSE_MENU = 2;

	public GameWindow(String name) 
	{
		super(name);
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException 
	{
		addState(new MainMenuGameState());
		addState(new MainGameState());
		addState(new PauseMenuGameState());
	}

}
