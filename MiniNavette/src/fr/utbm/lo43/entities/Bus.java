package fr.utbm.lo43.entities;

import org.newdawn.slick.geom.Vector2f;

public abstract class Bus extends EntityDragable implements EntityDrawable
{
	protected int capacity;
	protected int direction;
	protected Station[] listStation;
	protected float segmentProgress;
	
	public Bus(Vector2f _position) 
	{
		super(_position);
	}
	
	public abstract void move();
	
	public void load(Station station)
	{
		
	}
	
	public void unload(Station station)
	{
		
	}
	
	public void nextSegment()
	{
		
	}
	
	private void updateNextStation()
	{
		
	}
	
	private void removePassenger(Passenger passenger)
	{
		
	}
	
	private void changeDirection()
	{
		
	}
}
