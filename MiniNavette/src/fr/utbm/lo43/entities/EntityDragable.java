package fr.utbm.lo43.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class EntityDragable extends Entity implements EntityUpdateable
{
	private EventEntityMouseDraged dragedEvent;
	
	public EntityDragable(EventEntityMouseDraged _dragedEvent)
	{
		super();
		
		dragedEvent = _dragedEvent;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg) 
	{
		
	}
}
