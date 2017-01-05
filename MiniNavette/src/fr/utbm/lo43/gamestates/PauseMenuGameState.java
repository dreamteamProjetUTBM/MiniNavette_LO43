package fr.utbm.lo43.gamestates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.GameWindow;
import fr.utbm.lo43.entities.Button;
import fr.utbm.lo43.entities.EventEntityMouseClicked;

public class PauseMenuGameState extends BasicGameState
{
	Button back_button;
	Button quit_button;
	
	Image img_title;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException 
	{
		back_button = new Button(new Vector2f(100, 200),
				"asset/b_pause_resume_idle.png",
				"asset/b_pause_resume_hover.png",
				"asset/b_pause_resume_pressed.png");
		back_button.setEventCallback(new EventEntityMouseClicked() 
		{	
			@Override
			public void mouseClicked() 
			{
				arg1.enterState(GameWindow.GS_GAME);
			}
		});
		
		quit_button = new Button(new Vector2f(100, 270),
				"asset/b_pause_quit_idle.png",
				"asset/b_pause_quit_hover.png",
				"asset/b_pause_quit_pressed.png");
		quit_button.setEventCallback(new EventEntityMouseClicked() 
		{	
			@Override
			public void mouseClicked() 
			{
				try 
				{
					arg1.getState(GameWindow.GS_GAME).init(arg0, arg1);
					arg1.enterState(GameWindow.GS_MAIN_MENU);
				} 
				catch (SlickException e) 
				{
					e.printStackTrace();
				}
			}
		});
		
		img_title = new Image("asset/pause_title.png");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException 
	{
		img_title.draw();
		
		back_button.render(arg2);
		quit_button.render(arg2);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException 
	{
		back_button.update(arg0, arg1);
		quit_button.update(arg0, arg1);
	}

	@Override
	public int getID() 
	{
		return GameWindow.GS_PAUSE_MENU;
	}

}
