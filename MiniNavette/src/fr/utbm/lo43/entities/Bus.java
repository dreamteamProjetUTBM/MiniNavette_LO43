package fr.utbm.lo43.entities;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import fr.utbm.lo43.logic.Map;

public abstract class Bus extends EntityDragable implements EntityDrawable, EntityUpdateable
{
	protected int capacity;
	protected boolean direction;
//	protected Station[] listStation;
	protected float segmentProgress;
	List<Passenger> passengers ; 
	Segment currentSegment ;
	protected Color color;
	
	public Bus(Vector2f _position, Color _color) 
	{
		super(_position);

		passengers = new ArrayList<Passenger>()  ; 
		drawable = true;
		color = _color;
	}
	
	public abstract void move();
	
	
	
	public void load(Station station)
	{

		System.out.println("Bus.load");
		ArrayList<Station> nextStops = Map.getInstance().getNextStops(this, station);
		
		ArrayList<Passenger> copy = new ArrayList<Passenger>(station.waitingPassenger);
		
		for(Passenger passenger : copy)
		{
			if(passengers.size() >= capacity)
			{
				System.out.println("Bus a maintenant "+ passengers.size() + " passager(s).");
				return;
			}
			passenger.busArrived(this,station,nextStops);
		}

		System.out.println("Bus a maintenant "+ passengers.size() + " passager(s).");
	}
	
	public void unload(Station station)
	{
		System.out.println("Bus.unload");
		for(Passenger passenger : passengers)
		{
			if(passenger.nextStop==station)
			{
				passenger.leaveBus(station);;
				removePassenger(passenger);
			}
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
	

	
	public void nextSegment()
	{
		if(endLine())changeDirection();
		currentSegment = direction ? currentSegment.getNextSegment() : currentSegment.getPreviousSegment();
	}
	
	private void updateNextStation()
	{
		
	}
	
	private void removePassenger(Passenger passenger)
	{
		
	}
	
	private void changeDirection()
	{
		direction = !direction ;
	}
	
	private boolean endLine()
	{
		return currentSegment.getNextSegment()==null;
	}
}
