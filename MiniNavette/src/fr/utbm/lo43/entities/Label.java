package fr.utbm.lo43.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Label extends Entity implements EntityDrawable
{
	private String text;
	
	public Label(String _text, Vector2f _position)
	{
		super(_position);
		
		text = _text;
	}

	@Override
	public void render(Graphics arg2) 
	{
		arg2.drawString(text, position.x, position.y);
	}
	
	public void setText(String _text)
	{
		text = _text;
	}
	
	public String getText()
	{
		return text;
	}
}
