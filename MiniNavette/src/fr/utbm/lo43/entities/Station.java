package fr.utbm.lo43.entities;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
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
	private Filiere filiere;
	private Image preview;
	
	Segment seg;
	
	private boolean alcoolized;
	private int maxWaitingTime;
	private int extraTime;
	
	private ArrayList<Passenger> waitingPassenger;
	
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
		
		size.x = 24;
		size.y = 24;
		seg = new Segment(new Vector2f(), new Vector2f(),0);
		
		drawable = true;
	}
	
	public void newPassenger()
	{
		Filiere passenger_type = filiere;
		
		int stations_size = Map.getInstance().getStationsLenght();
		Random rand = new Random();
		
		
		while(passenger_type == filiere){
			int index = rand.nextInt(stations_size);
			passenger_type = Map.getInstance().stations.get(index).filiere;
		}
		
		float offsetX,offsetY;
		
		offsetX = waitingPassenger.size()%3;
		offsetY = 0;
		
		offsetX = getPosition().x + offsetX * 12;
		offsetY = getPosition().y + 26 + waitingPassenger.size()/3 * 14 ;
		
		Passenger p = new Passenger(new Vector2f(offsetX,offsetY), passenger_type);
		waitingPassenger.add(p);
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

	public Filiere getFiliere(){
		return filiere;
	}
	
	@Override
	public void render(Graphics arg2) {
		// TODO Auto-generated method stub
		preview.draw(getPosition().x,getPosition().y,24,24);
		
		for (Passenger passenger : waitingPassenger) {
			passenger.render(arg2);
		}
		arg2.setColor(Color.blue);
		seg.render(arg2);
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
