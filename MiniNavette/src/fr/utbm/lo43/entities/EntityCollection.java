package fr.utbm.lo43.entities;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class EntityCollection implements EntityUpdateable, EntityDrawable
{
	ArrayList<Entity> entities;
	
	public EntityCollection()
	{
		entities = new ArrayList<Entity>();
	}
	
	public void add(Entity _entity)
	{
		
	}
	
	public void delete(Entity _entity)
	{
		
	}

	@Override
	public void render(Graphics arg2) 
	{
		for(Entity entitie : entities)
		{
			
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg) 
	{
		
	}
}
