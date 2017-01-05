package fr.utbm.lo43.entities;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import fr.utbm.lo43.logic.Filiere;
import fr.utbm.lo43.logic.Map;

public class Station extends EntityClickable implements EntityDrawable
{
	//24 taille case
	//Quadrillage
	//Jerem: rajout de ma part, à voir s'il ne faut pas le mettre dans le diagramme
	//En FR en plus
	protected Filiere filiere;
	public static final int MAXIMUM_PASSENGER = 8;
	
	private Image preview;
		
	private boolean alcoolized;
	private int maxWaitingTime;
	private int extraTime;
	
	protected ArrayList<Passenger> waitingPassenger;
	
	public Station(Vector2f _position, Filiere type) 
	{
		super(_position);
		filiere = type;
		waitingPassenger = new ArrayList<>();
		
		try 
		{
			preview = new Image("asset/"+filiere.toString().toLowerCase()+".png");
			
		}
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
		
		size.x = Map.GRID_SIZE*2;
		size.y = Map.GRID_SIZE*2;
		
		drawable = true;
	}
	
	public Passenger newPassenger()
	{
		Filiere passenger_type = filiere;
		
		int stations_size = Map.getInstance().getStationsLenght();
		Random rand = new Random();
		
		
		while(passenger_type == filiere){
			int index = rand.nextInt(stations_size);
			passenger_type = Map.getInstance().stations.get(index).filiere;
		}
		
		float offsetX,offsetY;
		
		offsetX = waitingPassenger.size()%4;
		offsetY = 0;
		
		offsetX = getPosition().x + offsetX * Map.GRID_SIZE/2;
		offsetY = getPosition().y + waitingPassenger.size()/4 * Map.GRID_SIZE*1.5f ;
		
		Passenger p = new Passenger(new Vector2f(offsetX,offsetY), passenger_type);
		waitingPassenger.add(p);
		
		return p;
	}
	
	public boolean canAddPassenger(){
		if(waitingPassenger.size() == MAXIMUM_PASSENGER){
			return false;
		}
		return true;
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
		waitingPassenger.add(passenger);
	}
	
	public void leaveStation(Passenger passenger)
	{
		if(!waitingPassenger.remove(passenger)){
			//erreur
		}
	}
	
	public Station getNextStation(Passenger passenger)
	{
		return null;
	}
	
	public void keepPassenger(Passenger passenger)
	{
		
	}

	public Filiere getFiliere(){
		return filiere;
	}
	
	public boolean isOnStation(Vector2f _position){
		return getRect().contains((int)_position.x, (int)_position.y);
	}
	
	
	@Override
	public void render(Graphics arg2) {
		// TODO Auto-generated method stub
		arg2.setColor(Color.darkGray);
		arg2.setLineWidth(1);
		Rectangle rec = new Rectangle(getRect().getX(), getRect().getY(), getRect().getWidth(), getRect().getHeight());
		arg2.setColor(new Color(238,238,238));
		arg2.fill(rec);
		arg2.draw(rec);
		preview.draw(getPosition().x+Map.GRID_SIZE/2,getPosition().y+Map.GRID_SIZE/2,Map.GRID_SIZE,Map.GRID_SIZE);

		for (Passenger passenger : waitingPassenger) {
			passenger.render(arg2);
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg) {
		// TODO Auto-generated method stub
		super.update(gc, sbg);

		
		
		//Input input = gc.getInput();

		//if(isMouseHoverAndPressed){
			
			//Check si y'a des lignes dans inventaire
			//if(!Map.getInstance().getLine(0).getSegments().contains(seg))
				//Map.getInstance().getLine(0).addSegment(new Segment(getPosition(), new Vector2f(input.getMouseX(), input.getMouseY()),0));
			
			//Map.getInstance().AddLine(new ClassicLine(Color.blue));
		//}
	}
}
