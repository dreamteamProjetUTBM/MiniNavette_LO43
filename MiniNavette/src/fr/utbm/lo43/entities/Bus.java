package fr.utbm.lo43.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import fr.utbm.lo43.logic.Map;

public abstract class Bus extends EntityDragable implements EntityDrawable
{
	protected int capacity;
	protected int direction;
//	protected Station[] listStation;
	protected float segmentProgress;
	
	protected Color color;
	
	public Bus(Vector2f _position, Color _color) 
	{
		super(_position);
		
		drawable = true;
		color = _color;
	}
	
	public abstract void move();
	
	public void load(Station station)
	{
		Map.getInstance().getNextStops(this, station);
		
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
