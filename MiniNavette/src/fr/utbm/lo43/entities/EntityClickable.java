package fr.utbm.lo43.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class EntityClickable extends Entity implements EntityUpdateable 
{
	private EventEntityMouseClicked clickedEvent;
	
	public EntityClickable(EventEntityMouseClicked _clickedEvent)
	{
		super();
		
		clickedEvent = _clickedEvent;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg) 
	{
		Input input = gc.getInput();
	}
}
