package fr.utbm.lo43.entities;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.gamestates.MainGameState;
import fr.utbm.lo43.logic.Inventory;
import fr.utbm.lo43.logic.Line;
import fr.utbm.lo43.logic.Map;

public class ClassicBus extends Bus
{
	ClassicBus thisone = this;
	
	
	private Polygon polygon;
	
	//Le bus navigue entre ces deux vecteurs
	private Vector2f start,end;
	//Direction du bus
	int local_direction = -1;
	
	//Contient l'actuelle angle du bus
	private float theta;
	//Compteur pour le temps depuis le dernier appel de update
	private int cpt = 0;
	
	public ClassicBus(Vector2f _position, Color _color,Segment current)
	{
		super(_position, _color);
		direction = true;
		capacity = 6;
		currentSegment = current;
		
		polygon = new Polygon();
		polygon.addPoint(_position.x-5, _position.y+15);
		polygon.addPoint(_position.x+5, _position.y+15);
		polygon.addPoint(_position.x+5, _position.y-15);
		polygon.addPoint(_position.x-5, _position.y-15);
		/*
		setEventCallback(new EventEntityMouseDraged() {
			
			@Override
			public void mouseReleased() {
				// TODO Auto-generated method stub
				boolean isOnSegment = false;
				for(Line line : Map.getInstance().getLines()){
					for (Segment seg : line.getSegments()) {
						if(seg.isOnSegment(getPosition()))
							isOnSegment = true;
					}
				}
				
				if(!isOnSegment){
					Inventory.getInstance().setRemainingBus(1);
					//MainGameState.entities.delete(thisone);
				}
			}
			
			@Override
			public void mousePressed() {
				// TODO Auto-generated method stub
			}
		}); */
		theta = getAngle();
		polygon = (Polygon) polygon.transform(Transform.createRotateTransform((float) Math.toRadians(-getAngle())));

	}
	
	public float getAngle(){
		ArrayList<Vector2f> vects = currentSegment.isBetween(getPosition());
		Vector2f _start,_end;
		
		if(vects.get(0) == null || vects.get(1) == null)
			return 0;
		
		if(vects.size() == 4 && direction){
			_start = vects.get(2);
			_end = vects.get(3);
			
		}
		else {
			if(direction){
				_start = vects.get(0);
				_end = vects.get(1);
			}
			else {
				_start = vects.get(1);
				_end = vects.get(0);
			}
		}
		
		org.newdawn.slick.geom.Line line= new org.newdawn.slick.geom.Line(_start, _end);
		float theta_bis = (float) Math.atan2(line.getDX(), line.getDY());
		theta_bis *= 180 / Math.PI;
		
		return theta_bis;
		/*
		System.out.println(theta);
		 return (line.getDX()/line.getDY());*/
		
	}
	
	@Override
	public void render(Graphics arg2) 
	
	{
		arg2.setColor(color);
		arg2.fill(polygon);
		arg2.draw(polygon);
	}

	@Override
	public void move() 
	{
		
		for (Vector2f endpoint : currentSegment.getPositions()) {
			if(endpoint.distance(getPosition()) == 0){
				//Alors on est arrivé soit dans une station soit à la fin d'une partie du segment
				for (Station station : Map.getInstance().getStations()) {
					if(station.isOnStation(endpoint)){
						
						/*
						 * ROGER ICI
						 */
						station.notifyBus(this);
						System.out.println("Bus "+ this.color +" a maintenant "+ passengers.size() + " passager(s).");
						
						if(direction){
							if(currentSegment.getNextSegment() == null){
								direction = false;
							}
							else{
								currentSegment = currentSegment.getNextSegment();
							}
						}
						else{
							if (currentSegment.getPreviousSegment() == null){
								direction = true;
								//direction = !direction;
							}
							else{
								currentSegment = currentSegment.getPreviousSegment();
							}
						}
						
						/*
						 * ROGER ICI
						 */
					}
				}
			}
		}
		
		//On avance
		
		ArrayList<Vector2f> vects = currentSegment.isBetween(getPosition());
		
		
		if(vects.size() > 0){
			if(vects.size() == 4 && direction){
				start = vects.get(2);
				end = vects.get(3);
				
			}
			else { //==2
				if(direction){
					start = vects.get(0);
					end = vects.get(1);
				}
				else {
					start = vects.get(1);
					end = vects.get(0);
				}

			}
		}
		

		Vector2f newpos = new Vector2f();
		if(start.x < end.x && start.y < end.y){
			newpos = new Vector2f(getPosition().x-local_direction,getPosition().y-local_direction);
		}
		else if(start.x > end.x && start.y > end.y){
			newpos = new Vector2f(getPosition().x+local_direction,getPosition().y+local_direction);
		}
		else if(start.x > end.x && start.y == end.y){
			newpos = new Vector2f(getPosition().x+local_direction,getPosition().y);
		}
		else if(start.x > end.x && start.y < end.y){
			newpos = new Vector2f(getPosition().x+local_direction,getPosition().y-local_direction);
		}
		else if(start.x == end.x && start.y > end.y){
			newpos = new Vector2f(getPosition().x,getPosition().y+local_direction);
		}
		else if(start.x == end.x && start.y < end.y){
			newpos = new Vector2f(getPosition().x,getPosition().y-local_direction);
		}
		else if(start.x < end.x && start.y > end.y){
			newpos = new Vector2f(getPosition().x-local_direction,getPosition().y+local_direction);
		}
		else if(start.x < end.x && start.y == end.y){
			newpos = new Vector2f(getPosition().x-local_direction,getPosition().y);
		}
		
		if(currentSegment.isOnSegment(newpos)){
			setPosition(newpos);
		}
		else{
			if(local_direction == -1)
				local_direction = 1;
			else
				local_direction = -1;
		}
		
		

		
		/*
		float a,b;	
		
		for (Vector2f endpoint : currentSegment.getPositions()) {
			
			if(endpoint.distance(getPosition()) == 0){
				//Alors on est arrivé soit dans une station soit à la fin d'une partie du segment
				for (Station station : Map.getInstance().getStations()) {
					if(station.isOnStation(endpoint)){
						//LOOP - Bug pour l'instant
						/*if(currentSegment.line_bus.isLoop()){
							if(direction && currentSegment.getNextSegment() == null){
								currentSegment = currentSegment.line_bus.getSegment(0);
							}
							else {
								currentSegment = currentSegment.line_bus.getSegment(currentSegment.line_bus.getSegments().size()-1);
							}
							//currentSegment = currentSegment.line_bus.getSegment(0);
						}
						else {*//*
						if(currentSegment.getNextSegment() == null || currentSegment.getPreviousSegment() == null){
							direction = !direction;
						}	
						else {
						if(direction){
								System.out.println("Direction");

								if(currentSegment.getNextSegment() == null){
									System.out.println("Next null");
									direction = !direction;
								}
								else{
									currentSegment = currentSegment.getNextSegment();
									System.out.println("!Next null");

								}
							}
							else{
								System.out.println("!Direction");

								if (currentSegment.getPreviousSegment() == null){
									System.out.println("Prev null");
									direction = !direction;
								}
								else{
									System.out.println("!Prev null");
									currentSegment = currentSegment.getPreviousSegment();
								}
							}
						}
						System.out.println("On station");
					}
					

						//
					//polygon.transform(Transform.createTranslateTransform(currentSegment.getAngle().x,currentSegment.getAngle().y));
						//Roger tu peux décharger et charger ici
					}
				
			}
			
			
		}
			
			
		if(currentSegment.isOnSegment(new Vector2f(getPosition().x+local_direction,getPosition().y+local_direction)))
			setPosition(new Vector2f(getPosition().x+local_direction,getPosition().y+local_direction));

		else if(currentSegment.isOnSegment(new Vector2f(getPosition().x,getPosition().y+local_direction)))
			setPosition(new Vector2f(getPosition().x,getPosition().y+local_direction));
			
		else if(currentSegment.isOnSegment(new Vector2f(getPosition().x+local_direction,getPosition().y)))
			setPosition(new Vector2f(getPosition().x+local_direction,getPosition().y));	
		
		else if (currentSegment.isOnSegment(new Vector2f(getPosition().x+local_direction,getPosition().y-local_direction)))
			setPosition(new Vector2f(getPosition().x+local_direction,getPosition().y-local_direction));	
		

		else{
			System.out.println("else");
			if(local_direction == 1)
				local_direction = -1;
			else
				local_direction = 1;
			
			if(currentSegment.isOnSegment(new Vector2f(getPosition().x+local_direction,getPosition().y+local_direction)))
				setPosition(new Vector2f(getPosition().x+local_direction,getPosition().y+local_direction));

			else if(currentSegment.isOnSegment(new Vector2f(getPosition().x,getPosition().y+local_direction)))
				setPosition(new Vector2f(getPosition().x,getPosition().y+local_direction));
				
			else if(currentSegment.isOnSegment(new Vector2f(getPosition().x+local_direction,getPosition().y)))
				setPosition(new Vector2f(getPosition().x+local_direction,getPosition().y));	
			
			else if (currentSegment.isOnSegment(new Vector2f(getPosition().x+local_direction,getPosition().y-local_direction)))
				setPosition(new Vector2f(getPosition().x+local_direction,getPosition().y-local_direction));
		}
		
		//shape.setBounds(getPosition().x-5, getPosition().y-15, 10, 30);
		polygon.setCenterX(getPosition().x);
		polygon.setCenterY(getPosition().y);*/
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg,int delta) {
		
		super.update(gc, sbg,delta);
		
		/*Input input = gc.getInput();
		
		if(polygon.contains(input.getMouseX(), input.getMouseY()) && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{
			if(isGrabed == true)
			{
				setPosition(new Vector2f(input.getMouseX(),input.getMouseY()));
				
				//position.x = input.getMouseX();
				//position.y = input.getMouseY();
				if(dragedEvent != null)
					dragedEvent.mousePressed();
			}
			else
			{
				isGrabed = true;
			}
		}
		else
		{
			if(isGrabed != false)
			{
				if(dragedEvent != null)
					dragedEvent.mouseReleased();
				isGrabed = false;
			}
		}*/
		
		cpt += delta;
		if(cpt >25 && !isGrabed)
		{
			cpt = 0;
			move();
			
			if(getAngle() != theta) //Donc on a changé d'angle
			{
				polygon = (Polygon) polygon.transform(Transform.createRotateTransform((float) Math.toRadians(theta-getAngle())));	
				theta = getAngle();

			}
			polygon.setCenterX(getPosition().x);
			polygon.setCenterY(getPosition().y);

		}
		
		

	}

}
