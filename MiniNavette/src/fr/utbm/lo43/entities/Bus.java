package fr.utbm.lo43.entities;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

import fr.utbm.lo43.logic.Map;

public abstract class Bus extends EntityDragable implements EntityDrawable, EntityUpdateable, Runnable
{
	protected Polygon polygon;

	protected int capacity;
	protected boolean direction;
//	protected Station[] listStation;
	//protected float segmentProgress;
	protected volatile List<Passenger> passengers ; 
	protected volatile Segment currentSegment ;
	protected volatile Color color;
	protected volatile ArrayList<Image> passenger_images;
	
	public Bus(Vector2f _position, Color _color) 
	{
		super(_position);

		passengers = new ArrayList<Passenger>()  ; 
		passenger_images = new ArrayList<>();
		drawable = true;
		color = _color;
		
		polygon = new Polygon();
		polygon.addPoint(_position.x-Map.GRID_SIZE/2, _position.y+Map.GRID_SIZE*0.75f);
		polygon.addPoint(_position.x+Map.GRID_SIZE/2, _position.y+Map.GRID_SIZE*0.75f);
		polygon.addPoint(_position.x+Map.GRID_SIZE/2, _position.y-Map.GRID_SIZE*0.75f);
		polygon.addPoint(_position.x-Map.GRID_SIZE/2, _position.y-Map.GRID_SIZE*0.75f);
	}
	
	protected abstract void move();
	
	
	
	public boolean getDirection() {
		return direction;
	}

	public Segment getCurrentSegment() {
		return currentSegment;
	}

	public void load(Station station)
	{

		System.out.println("Bus.load");
		ArrayList<Station> nextStops = Map.getInstance().getNextStops(this, station);
		
		ArrayList<Passenger> copy = new ArrayList<Passenger>(station.getWaitingPassenger());
		
		for(Passenger passenger : copy)
		{
			//System.out.println("passenger � charger !!!!!!!!!!!!! ==> " + copy.size());
			if(passengers.size() >= capacity)
			{
				return;
			}
			passenger.busArrived(this,station,nextStops);
		}

	}
	
	
	
	public void unload(Station station)
	{
		System.out.println("Bus.unload");

			
			ArrayList<Passenger> copy = new ArrayList<Passenger>(passengers);
	
		for(Passenger passenger : copy)
		{
			if(passenger.nextStop==station)
			{
				passenger.leaveBus(station);
				removePassenger(passenger);
			}
		}
		
	}
	
	
	public boolean takeTheBus(Passenger passenger)
	{
		if(passengers.size() <= capacity)
		{
			synchronized(passengers)
			{		
				passengers.add(passenger);
			}
			return true;
		}
		return false;
	}
	

	
	protected void nextSegment()
	{
		if(endLine())changeDirection();
		else currentSegment = direction ? currentSegment.getNextSegment() : currentSegment.getPreviousSegment();
	}
	
	private void updateNextStation()
	{
		
	}
	
	protected void removePassenger(Passenger passenger)
	{
		synchronized(passengers)
		{		
			passengers.remove(passenger);	
		}
	}
	
	private void changeDirection()
	{
		direction = !direction ;
	}
	
	private boolean endLine()
	{
		return  direction ? currentSegment.getNextSegment()==null : currentSegment.getPreviousSegment()==null;
	}
	
	public synchronized boolean isEmpty()
	{
		synchronized(passengers){
			return this.passengers.size() <=0 ;
		}
	}
	
	
	
}
