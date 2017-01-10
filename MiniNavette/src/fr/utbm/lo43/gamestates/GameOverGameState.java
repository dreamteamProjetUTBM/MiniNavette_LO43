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
import fr.utbm.lo43.entities.Label;
import fr.utbm.lo43.logic.Map;
import fr.utbm.lo43.logic.Score;

public class GameOverGameState extends BasicGameState 
{
	private Image background;
	
	private Button btn_menu;
	private Button btn_restart;
	
	private Label score_label;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException 
	{
		background = new Image("asset/game_over.png");
		
		btn_menu = new Button(new Vector2f(), 
				"asset/b_go_menu_idle.png",
				"asset/b_go_menu_hover.png",
				"asset/b_go_menu_pressed.png"
		);
		btn_menu.setPosition(new Vector2f(Map.WIDTH / 2 - btn_menu.getSize().x / 2, 300));
		btn_menu.setEventCallback(new EventEntityMouseClicked() 
		{	
			@Override
			public void mouseClicked() 
			{
				arg1.enterState(GameWindow.GS_MAIN_MENU);
			}
		});
		
		btn_restart = new Button(new Vector2f(), 
				"asset/b_go_restart_idle.png",
				"asset/b_go_restart_hover.png",
				"asset/b_go_restart_pressed.png"
		);
		btn_restart.setPosition(new Vector2f(Map.WIDTH / 2 - btn_restart.getSize().x / 2, 365));
		btn_restart.setEventCallback(new EventEntityMouseClicked() 
		{	
			@Override
			public void mouseClicked() 
			{
				try 
				{
					arg1.getState(GameWindow.GS_GAME).init(arg0, arg1);
					arg1.enterState(GameWindow.GS_GAME);
				} 
				catch (SlickException e) 
				{
					e.printStackTrace();
				}
			}
		});
		
		score_label = new Label("" + Score.getInstance().getScore(), new Vector2f(75, Map.HEIGHT - Map.GRID_SIZE / 1.1f));
		score_label.setColor(Color.black);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException 
	{
		background.draw();
		
		btn_menu.render(arg2);
		btn_restart.render(arg2);
		
		score_label.render(arg2);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException 
	{
		btn_menu.update(arg0, arg1, arg2);
		btn_restart.update(arg0, arg1, arg2);
	}

	@Override
	public int getID() 
	{
		return GameWindow.GS_GAME_OVER;
	}
}
