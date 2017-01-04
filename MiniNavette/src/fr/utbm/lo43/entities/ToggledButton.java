package fr.utbm.lo43.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class ToggledButton extends EntityClickable implements EntityDrawable 
{
	private Image img_actual;
	
	private Image img_idle;
	private Image img_hover;
	private Image img_pressed;
	
	private boolean toggled;
	
	public ToggledButton(EventEntityMouseClicked _clickedEvent, Vector2f _position, Vector2f _size) 
	{
		super(_clickedEvent, _position, _size);
		
		drawable = true;
		toggled = false;
	}
	
	public ToggledButton(Vector2f _position, Vector2f _size) 
	{
		super(_position, _size);
		
		drawable = true;
	}
	
	public ToggledButton(Vector2f _position) 
	{
		super(_position);
		
		drawable = true;
	}

	@Override
	public void render(Graphics arg2) 
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
