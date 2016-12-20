package fr.utbm.lo43.gamestates;

import java.io.Console;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuGameState extends BasicGameState
{

	private Image header;
	
	private Image startGame;
	private Image startGame_normal;
	private Image startGame_hover;
	private Image startGame_clicked;
	
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		// TODO Auto-generated method stub
		//header = new Image("asset/mini_navette.png");
		
		startGame_normal = new Image("asset/start_button.png");
		startGame_hover = new Image("asset/start_button_hover.png");
		startGame_clicked = new Image("asset/start_button_clicked.png");

		startGame = startGame_normal;
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		// TODO Auto-generated method stub
		startGame.draw(200,100);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		// TODO Auto-generated method stub
		int posX = arg0.getInput().getMouseX();
		int posY = arg0.getInput().getMouseY();
		
		if((posX > 200 && posX < 200+startGame.getWidth()) && (posY > 100 && posY < 100+startGame.getHeight())){
			startGame = startGame_hover;
			if(Mouse.isButtonDown(0))
			{
				startGame = startGame_clicked;
			}
		}
		else
		{
			startGame = startGame_normal;
		}
			
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
