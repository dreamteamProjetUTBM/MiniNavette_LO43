package fr.utbm.lo43.entities;

import org.lwjgl.util.vector.Vector2f;

public class Station extends Entity
{
	private boolean alcoolized;
	private int maxWaitingTime;
	private int extraTime;
	
	public Station(Vector2f _position) 
	{
		super(_position);
	}
	
	public void newPassenger()
	{
		
	}
	
	public void checkWaitingTime()
	{
		
	}
	
	public void alcoolise()
	{
		
	}
	
	public void notifyBus(Bus bus)
	{
		
	}
	
	public void enterStation(Passenger passenger)
	{
		
	}
	
	public Station getNextStation(Passenger passenger)
	{
		return null;
	}
	
	public void keepPassenger(Passenger passenger)
	{
		
	}
}
