package fr.utbm.lo43.entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class EntityDragable extends Entity implements EntityUpdateable
{
	private EventEntityMouseDraged dragedEvent;
	
	public EntityDragable(EventEntityMouseDraged _dragedEvent, Vector2f _position, Vector2f _size)
	{
		super(_position, _size);
		
		dragedEvent = _dragedEvent;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg) 
	{
		
	}
}
