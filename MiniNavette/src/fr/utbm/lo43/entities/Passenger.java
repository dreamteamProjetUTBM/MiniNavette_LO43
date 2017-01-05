package fr.utbm.lo43.entities;

import java.util.Date;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.logic.Filiere;


public class Passenger extends Entity implements EntityDrawable, EntityUpdateable
{
	private Date arrivalTime;
	private Filiere filiere;
	private Image preview;
	
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
	
	private void takeTheBus(Bus bus)
	{
		
	}
	
	public void busArrived(Bus bus)
	{
		
	}
	
	public void leaveBus(Station station)
	{
		
	}

	@Override
	public void render(Graphics arg2) {
		preview.draw(getPosition().x,getPosition().y,12,12);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg) {
		// TODO Auto-generated method stub
		
	}
}