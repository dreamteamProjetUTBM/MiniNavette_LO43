package fr.utbm.lo43.entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class EntityClickable extends Entity implements EntityUpdateable 
{
	private EventEntityMouseClicked clickedEvent;
	
	public EntityClickable(EventEntityMouseClicked _clickedEvent, Vector2f _position, Vector2f _size)
	{
		super(_position, _size);
		
		clickedEvent = _clickedEvent;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg) 
	{
		Input input = gc.getInput();
		
		if(getRect().contains(input.getMouseX(), input.getMouseY()) 
				&& input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{
			clickedEvent.mouseClicked();
		}
	}
}
