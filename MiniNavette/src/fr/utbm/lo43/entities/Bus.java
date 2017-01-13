package fr.utbm.lo43.entities;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

import fr.utbm.lo43.logic.Map;

/**
 * @author Quentin Nahil Thomas Jeremy 
 * Classe abstraite bus, contient toute la logique commune aux differents bus.
 */
public abstract class Bus extends EntityDragable implements EntityDrawable, EntityUpdateable, Runnable
{
	protected Polygon polygon;

	/**
	 * Capacité d'un bus.
	 */
	protected int capacity;
	protected boolean direction;
	//protected float segmentProgress;
	
	/**
	 * Liste des passagers à bord
	 */
	protected volatile List<Passenger> passengers ; 
	
	/**
	 * Segment sur lequel se déplace le bus à un instant T
	 */
	protected volatile Segment currentSegment ;
	
	/**
	 * Couleur du bus, associée à celle de la ligne sur laquelle il se trouve
	 */
	protected volatile Color color;
	
	/**
	 * Image des passagers à afficher
	 */
	protected volatile ArrayList<Image> passenger_images;

	/*
	 * Si le bus est bloqué, il sera à True, sinon il sera à False
	 */
	protected volatile boolean lock ;


	/*
	 * Si le bus doit être supprimé, il sera à True
	 */
	protected volatile boolean canBeRemove ;


	public Bus(Vector2f _position, Color _color) 
	{
		super(_position);

		passengers = new ArrayList<Passenger>()  ; 
		passenger_images = new ArrayList<>();
		drawable = true;
		color = _color;
		lock = false;
		canBeRemove = false;

		polygon = new Polygon();
		polygon.addPoint(_position.x-Map.GRID_SIZE/2, _position.y+Map.GRID_SIZE*0.75f);
		polygon.addPoint(_position.x+Map.GRID_SIZE/2, _position.y+Map.GRID_SIZE*0.75f);
		polygon.addPoint(_position.x+Map.GRID_SIZE/2, _position.y-Map.GRID_SIZE*0.75f);
		polygon.addPoint(_position.x-Map.GRID_SIZE/2, _position.y-Map.GRID_SIZE*0.75f);
	}

	protected abstract void move();


	public boolean canBeRemoved(){
		return canBeRemove;
	}


	protected void setCanBeRemove(boolean value){
		canBeRemove = value;
	}

	public boolean isLock(){
		return lock;
	}

	protected void setLock(boolean value){
		lock = value;
	}



	public boolean getDirection() {
		return direction;
	}

	public Segment getCurrentSegment() {
		return currentSegment;
	}

	
	/**
	 * Logique de chargement en station.
	 * Le bus notifie les passagers de la station un à un jusqu'à ce qu'il soit plein
	 * @param station la station ou le bus arrive
	 */
	public void load(Station station)
	{


		try {
			Thread.sleep(500/Map.getInstance().gameSpeed);
		} catch (InterruptedException e) {
			Thread.currentThread().stop();
			e.printStackTrace();
		}

		ArrayList<Station> nextStops = Map.getInstance().getNextStops(this, station);

		ArrayList<Passenger> copy = new ArrayList<Passenger>(station.getWaitingPassenger());

		for(Passenger passenger : copy)
		{
			if(passengers.size() >= capacity)
			{
				return;
			}
			passenger.busArrived(this,station,nextStops);
			try {
				Thread.sleep(500/Map.getInstance().gameSpeed);
			} catch (InterruptedException e) {
				Thread.currentThread().stop();
				e.printStackTrace();
			}
		}


	}



	/**
	 * Logique de déchargement du bus en station.
	 * Va faire descendre du bus chaque passager arrivé à destination
	 * @param station
	 */
	public void unload(Station station)
	{
		try {
			Thread.sleep(500/Map.getInstance().gameSpeed);
		} catch (InterruptedException e) {
			Thread.currentThread().stop();
			e.printStackTrace();
		}

		ArrayList<Passenger> copy = new ArrayList<Passenger>(passengers);

		for(Passenger passenger : copy)
		{
			if(passenger.nextStop==station)
			{
				passenger.leaveBus(station);
				removePassenger(passenger);
				try {
					Thread.sleep(500/Map.getInstance().gameSpeed);
				} catch (InterruptedException e) {
					Thread.currentThread().stop();
					e.printStackTrace();
				}
			}
		}

	}


	/**
	 * Faire monter un passager dans le bus
	 * @param passenger
	 * @return réussite ou échec
	 */
	public boolean takeTheBus(Passenger passenger)
	{
		synchronized(passengers){
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
	}



	/**
	 * Méthode d'actualisation du segment où le bus se déplace
	 */
	protected void nextSegment()
	{

		if(currentSegment.getAngle().distance(getPosition())==0 && currentSegment.getStartSegment().distance(getPosition()) != 0 && currentSegment.getEndSegment().distance(getPosition()) != 0){

		}else{
			if(endLine()){
					changeDirection();
			}else{
				if(direction){
						currentSegment = currentSegment.getNextSegment();
					}else{
						currentSegment = currentSegment.getPreviousSegment();
					}

				}
			}
		}

	/**
	 * Supprimer un passager se trouvant dans le bus
	 * @param passenger passager à supprimer
	 */
	protected void removePassenger(Passenger passenger)
	{
		synchronized(passengers)
		{		
			passengers.remove(passenger);	
		}
	}

	/**
	 * Permet au bus de changer de direction
	 */
	private void changeDirection()
	{
		direction = !direction ;
	}

	
	/**
	 * Permet de savoir si le bus se trouve à la fin du segment
	 */
	private boolean endLine()
	{
		if(direction){
			return currentSegment.getNextSegment()==null;
		}else{

			return currentSegment.getPreviousSegment()==null;
		}

	}

	/**
	 * Permet de savoir si un bus contient des passagers
	 */
	public synchronized boolean isEmpty()
	{
		synchronized(passengers){
			return this.passengers.size() <=0 ;
		}
	}



}
