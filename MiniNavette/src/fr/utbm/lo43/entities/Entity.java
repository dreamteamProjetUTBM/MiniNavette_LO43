package fr.utbm.lo43.entities;

import java.util.ArrayList;

import fr.utbm.lo43.components.Component;

public class Entity 
{

	private ArrayList<Component> components;
	
	public Entity()
	{
		
	}
	
	public void init()
	{
		
	}
	
	public void update()
	{
		for(Component c : components)
		{
			c.update();
		}
	}
	
	public void render()
	{
		for(Component c : components)
		{
			c.render();
		}
	}
	
	public void cleanUp()
	{
		
	}
	
}
