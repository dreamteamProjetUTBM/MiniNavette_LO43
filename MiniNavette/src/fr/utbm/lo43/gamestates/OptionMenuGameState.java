package fr.utbm.lo43.gamestates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.GameWindow;
import fr.utbm.lo43.entities.Button;
import fr.utbm.lo43.entities.EventEntityMouseClicked;
import fr.utbm.lo43.entities.Slider;
import fr.utbm.lo43.logic.Map;

public class OptionMenuGameState extends BasicGameState
{
	Button btn_quit;
	
	Slider slider_music;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException 
	{
		btn_quit = new Button(new Vector2f(50, Map.HEIGHT - 110), 
				"asset/b_pause_quit_idle.png",
				"asset/b_pause_quit_hover.png",
				"asset/b_pause_quit_pressed.png"
		);
		
		btn_quit.setEventCallback(new EventEntityMouseClicked() 
		{	
			@Override
			public void mouseClicked() 
			{
				arg1.enterState(GameWindow.GS_MAIN_MENU);
			}
		});
		
		slider_music = new Slider(new Vector2f(100, 100));
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException 
	{
		btn_quit.render(arg2);
		
		slider_music.render(arg2);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException 
	{
		btn_quit.update(arg0, arg1, arg2);
		
		slider_music.update(arg0, arg1, arg2);
	}

	@Override
	public int getID() 
	{
		return GameWindow.GS_OPTION_MENU;
	}
}
