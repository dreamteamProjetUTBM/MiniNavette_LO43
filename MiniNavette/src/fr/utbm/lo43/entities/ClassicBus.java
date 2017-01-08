package fr.utbm.lo43.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.logic.Line;
import fr.utbm.lo43.logic.Map;

public class ClassicBus extends Bus
{
	private Rectangle shape;
	
	//Compteur pour le temps depuis le dernier appel de update
	private int cpt = 0;
	
	public ClassicBus(Vector2f _position, Color _color,Segment current)
	{
		super(_position, _color);
		capacity = 6;
		currentSegment = current;
		shape = new Rectangle(_position.x, _position.y, 10, 25);
	}
	
	@Override
	public void render(Graphics arg2) 
	{
		arg2.setColor(color);
		arg2.draw(shape);
		arg2.fill(shape);
	}

	@Override
	public void move() 
	{
		float dir = -1;
		if(direction)
			dir = 1;
		
		boolean isOnStation = false;
		Segment tmp;
		boolean isNextSegment = false;
		boolean isPreviousSegment = false;
		
		for (Vector2f endpoint : currentSegment.getPositions()) {
			if(endpoint.distance(getPosition()) == 0){
				//Alors on est arrivé soit dans une station soit à la fin du segment
				for (Station station : Map.getInstance().getStations()) {
					if(station.isOnStation(endpoint)){
						isOnStation = true;
						//Alors il est dans une station
						station.notifyBus(this);
					}
				}
				
				if(!isOnStation){
					System.out.println("Je suis pas sur la stationÒ");
					//Alors on change de segment
					Line currentLine = Map.getInstance().getLine(currentSegment.getLineIndex());
					for(int i = 0 ; i < currentLine.getSegments().size()-1 ; i ++){
						//Attention si c'est pas la fin
						if(direction && currentSegment.equals(currentLine.getSegment(i))){
							if(i+1 <= currentLine.getSegments().size()-1){
								currentSegment = currentLine.getSegment(i+1);
							}
							else{
								//Alors c'était le dernier segment et il repart
								direction = !direction;
							}
						}
						else if(!direction && currentSegment.equals(currentLine.getSegment(i))){
							if(i-1 >= 0){
								currentSegment = currentLine.getSegment(i-1);
							}
							else{
								direction = !direction;
							}
						}
					}
					
				}
				
			}
		}
		
		
		if(currentSegment.isOnSegment(new Vector2f(getPosition().x+dir,getPosition().y)))
			setPosition(new Vector2f(getPosition().x+dir,getPosition().y));
		
		else if(currentSegment.isOnSegment(new Vector2f(getPosition().x+dir,getPosition().y+dir)))
			setPosition(new Vector2f(getPosition().x+dir,getPosition().y+dir));

		else if(currentSegment.isOnSegment(new Vector2f(getPosition().x,getPosition().y+dir)))
			setPosition(new Vector2f(getPosition().x,getPosition().y+dir));
		
		
		shape.setBounds(getPosition().x-5, getPosition().y-15, 10, 30);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg,int delta) {
		
		super.update(gc, sbg,delta);
		
		cpt += delta;
		if(cpt >100)
		{
			cpt = 0;
			move();

		}
		
	}

}
