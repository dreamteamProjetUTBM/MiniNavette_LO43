package fr.utbm.lo43.entities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import fr.utbm.lo43.logic.Map;

public abstract class Bus extends EntityDragable implements EntityDrawable
{
	protected int capacity;
	protected int direction;
//	protected Station[] listStation;
	protected float segmentProgress;
	ArrayList<Passenger> passengers ; 
	Segment currentSegment ;
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
		ArrayList<Station> nextStops = Map.getInstance().getNextStops(this, station);
		
		for(Passenger passenger : station.waitingPassenger)
		{
			if(passengers.size() >= capacity)
			{
				return;
			}
			passenger.busArrived(this,station,nextStops);
		}
		
	}
	
	
	public void takeTheBus(Passenger passenger)
	{

		if(passengers.size() >= capacity)
		{
			//exception
		}
		passengers.add(passenger);
	}
	
	public void unload(Station station)
	{
		for(Passenger passenger : passengers)
		{
			if(passenger.nextStop==station)
			{
				passenger.leaveBus(station);;
			}
		}
		
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
