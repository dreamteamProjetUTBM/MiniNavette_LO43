package fr.utbm.lo43.components;

import fr.utbm.lo43.entities.Entity;

public abstract class Component 
{
	private Entity parent;

	public Component(Entity p)
	{
		parent = p;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void render();
	public abstract void cleanUp();
	
}
