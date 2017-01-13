package fr.utbm.lo43.entities;

import java.util.ArrayList;
import java.util.Date;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.logic.Filiere;
import fr.utbm.lo43.logic.Map;
import fr.utbm.lo43.logic.Score;


/**
 * @author Quentin Nahil Thomas Jeremy 
 * Usager du réseau de mini-navette.
 * Il poppe sur une station et cherche à se rendre à une autre station.
 */
public class Passenger extends Entity implements EntityDrawable, EntityUpdateable
{
	protected Filiere filiere;
	private Image preview;
	protected Station nextStop;
	
	public Passenger(Vector2f _position, Filiere type) 
	{
		super(_position);
		filiere = type;
		try {
			preview = new Image("asset/"+filiere.toString().toLowerCase()+".png");
		}
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
		
		updatable = true;
		drawable = true;
	}
	
	/**
	 * Notification de l'arrivée d'un bus : 
	 * le passager va vérifier si la station dont il a besoin est desservie par le bus et monter si oui
	 * @param bus
	 * @param station
	 * @param nextStops arrêts desservis par le bus
	 */
	public void busArrived(Bus bus,Station station, ArrayList<Station> nextStops )
	{
		if(nextStops.contains(nextStop))
		{
			boolean success = bus.takeTheBus(this);
			if(success)
			{
				station.leaveStation(this);
			}
		}

	}
	
	/**
	 * Lorsqu'un passager arrive en station. 
	 * Il quitte le bus, et s'il est arrivée à sa destination finale, 
	 * il ne reste pas dans la station mais incrémente le score
	 * Sinon, il entre en station
	 * @param station
	 */
	public void leaveBus(Station station)
	{
		if(this.filiere == station.filiere)
		{
			Score.getInstance().incrementScore();
			return;
		}
		new Date();
		station.enterStation(this);		
	}

	@Override
	public void render(Graphics arg2) {
		preview.draw(getPosition().x,getPosition().y,Map.GRID_SIZE/2,Map.GRID_SIZE/2);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg,int delta) {
		// TODO Auto-generated method stub
		
	}
}
