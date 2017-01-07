package fr.utbm.lo43.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Slider extends EntityClickable implements EntityDrawable
{
	Line slider;
	Rectangle indicator;
	int indicator_size;
	
	boolean isSliding;
	
	public Slider(Vector2f _position) 
	{
		super(_position);
		indicator_size = 32;
		
		slider = new Line(_position, new Vector2f(_position.x + 200, _position.y));
		indicator = new Rectangle(
				_position.x - indicator_size / 4, 
				_position.y - indicator_size / 2, 
				indicator_size / 2, 
				indicator_size
		);
		
		isSliding = false;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		Input input = gc.getInput();
		
		if(indicator.contains(input.getMouseX(), input.getMouseY()))
		{
			System.out.println("Dedans");
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
			{
				System.out.println("cliqué");
				if(indicator.getCenterX() >= position.x && indicator.getCenterX() <= slider.getMaxX())
				{
					isSliding = true;
					System.out.println("bouge");
				}
				else
				{
					isSliding = false;
				}
			}
		}
		
		if(isSliding)
		{
			indicator.setCenterX(input.getMouseX());
		}
	}

	@Override
	public void render(Graphics arg2) 
	{
		arg2.setColor(Color.black);
		arg2.setLineWidth(5);
		arg2.draw(slider);
		arg2.fill(indicator);
	}	
}
