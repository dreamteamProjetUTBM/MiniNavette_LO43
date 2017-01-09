package fr.utbm.lo43.entities;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.logic.Line;
import fr.utbm.lo43.logic.Map;

public class ClassicBus extends Bus
{
	int current_part_index;
	private Polygon polygon;
	
	Vector2f start,end;
	int local_direction = -1;
	
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
						
						if(direction){
							if(currentSegment.getNextSegment() == null){
								direction = false;
								System.out.println("Euh");
							}
							else{
								currentSegment = currentSegment.getNextSegment();
							}
						}
						else{
							if (currentSegment.getPreviousSegment() == null){
								direction = true;
								//direction = !direction;
								System.out.println("Euh");
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
				System.out.println("Quatre");
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
		
		polygon.setCenterX(getPosition().x);
		polygon.setCenterY(getPosition().y);

		
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
		
		cpt += delta;
		if(cpt >25)
		{
			cpt = 0;
			move();

		}
		
	}

}
