package fr.utbm.lo43.entities;

import org.lwjgl.util.vector.Vector2f;

public abstract class Bus extends Entity
{
	private int capacity;
	private int direction;
	private Station[] listStation;
	private float segmentProgress;
	
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
