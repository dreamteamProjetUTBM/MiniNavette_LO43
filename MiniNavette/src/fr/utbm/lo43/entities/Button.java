package fr.utbm.lo43.entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Button extends EntityClickable implements EntityDrawable 
{
	private Image img_actual;
	
	private Image img_idle;
	private Image img_hover;
	private Image img_pressed;
	
	public Button(Vector2f _position, String _img_idle, String _img_hover, String _img_pressed) 
	{
		super(_position);
		
		try 
		{
			img_idle = new Image(_img_idle);
			img_hover = new Image(_img_hover);
			img_pressed = new Image(_img_pressed);
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
		
		img_actual = img_idle;
		size.x = img_actual.getWidth();
		size.y = img_actual.getHeight();
	}

	@Override
	public void render() 
	{
		img_actual.draw(position.x, position.y);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg)
	{
		super.update(gc, sbg);
		
		/**
		 * Logique de changement d'image suivant la position de la souris
		 */
		if(isMouseHover)
		{
			
			if(isMouseHoverAndPressed)
			{
				img_actual = img_pressed;
			}
			else
			{
				img_actual = img_hover;
			}
		}
		else
		{
			img_actual = img_idle;
		}
	}
}
