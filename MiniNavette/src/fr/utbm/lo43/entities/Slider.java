package fr.utbm.lo43.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Classe Slider
 * 
 * Permet de règler des paramètres comme le volume de la musique
 * par exemple
 * @author Thomas Gredin
 *
 */
public class Slider extends EntityClickable implements EntityDrawable
{
	private Line slider;
	private Rectangle indicator;
	private int indicator_size;
	
	private Color color;
	
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
		color = Color.blue;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		Input input = gc.getInput();
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{
			if(indicator.contains(input.getMouseX(), input.getMouseY()))
			{
				if(indicator.getCenterX() >= position.x && indicator.getCenterX() <= slider.getMaxX())
				{
					isSliding = true;
				}
				else
				{
					isSliding = false;
				}
			}
		}
		else
		{
			isSliding = false;
		}
		
		if(isSliding)
		{
			indicator.setCenterX(input.getMouseX());
		}
		
		if(indicator.getCenterX() < position.x)
		{
			indicator.setCenterX(position.x);
		}
		if(indicator.getCenterX() > slider.getMaxX())
		{
			indicator.setCenterX(slider.getMaxX());
		}
	}

	@Override
	public void render(Graphics arg2) 
	{
		arg2.setColor(color);
		arg2.setLineWidth(5);
		arg2.draw(slider);
		arg2.fill(indicator);
	}
	
	public float getValue()
	{
		float percentage = (indicator.getCenterX() - position.x);
		percentage *= 100;
		percentage /= (slider.getMaxX() - position.x);
		
		return percentage;
	}
}
