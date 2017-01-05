package fr.utbm.lo43.gamestates;

import org.newdawn.slick.Color;
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

public class MainMenuGameState extends BasicGameState
{

	private Button play_button;
	private Button option_button;
	private Button quit_button;
	
	private Image logo;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException 
	{
		arg0.getGraphics().setBackground(Color.white);
		
		play_button = new Button(
				new Vector2f(75, 275),
				"asset/b_play_idle.png",
				"asset/b_play_hover.png",
				"asset/b_play_pressed.png"
		);
	
		//Change de State
		play_button.setEventCallback(
				new EventEntityMouseClicked() {
					
					@Override
					public void mouseClicked() {
						arg1.enterState(GameWindow.GS_GAME);
					}
				}
		);
		
		option_button = new Button(
				new Vector2f(75, 330), 
				"asset/b_play_idle.png", 
				"asset/b_play_hover.png", 
				"asset/b_play_pressed.png"
		);
		
		quit_button = new Button(
				new Vector2f(75, 385), 
				"asset/b_play_idle.png", 
				"asset/b_play_hover.png", 
				"asset/b_play_pressed.png"
		);
		quit_button.setEventCallback(new EventEntityMouseClicked() 
		{	
			@Override
			public void mouseClicked() 
			{
				arg0.exit();
			}
		});
		
		logo = new Image("asset/logo.png");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException 
	{
		play_button.render(arg2);
		option_button.render(arg2);
		quit_button.render(arg2);
		
		logo.draw((854 / 2) - (logo.getWidth() / 2), 0);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException 
	{
		play_button.update(arg0, arg1);
		option_button.update(arg0, arg1);
		quit_button.update(arg0, arg1);
	}

	@Override
	public int getID() 
	{
		return GameWindow.GS_MAIN_MENU;
	}

}
