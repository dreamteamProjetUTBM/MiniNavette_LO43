package fr.utbm.lo43.entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class EntityDragable extends Entity implements EntityUpdateable
{
	private EventEntityMouseDraged dragedEvent;
	private boolean isGrabed;
	
	public EntityDragable(EventEntityMouseDraged _dragedEvent, Vector2f _position, Vector2f _size)
	{
		super(_position, _size);
		
		dragedEvent = _dragedEvent;
		isGrabed = false;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg) 
	{
		Input input = gc.getInput();
		
		if(getRect().contains(input.getMouseX(), input.getMouseY()) && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{
			if(isGrabed == true)
			{
				position.x = input.getMouseX() - (size.x / 2.0f);
				position.x = input.getMouseY() - (size.y / 2.0f);
				dragedEvent.mousePressed();
			}
			else
			{
				isGrabed = true;
			}
		}
		else
		{
			isGrabed = false;
		}
	}
}
