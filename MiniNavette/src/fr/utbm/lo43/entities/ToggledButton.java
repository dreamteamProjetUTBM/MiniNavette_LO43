package fr.utbm.lo43.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class ToggledButton extends EntityClickable implements EntityDrawable 
{

	public ToggledButton(EventEntityMouseClicked _clickedEvent, Vector2f _position, Vector2f _size) 
	{
		super(_clickedEvent, _position, _size);
		
		drawable = true;
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
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg)
	{
		super.update(gc, sbg);
		
		
	}

}
