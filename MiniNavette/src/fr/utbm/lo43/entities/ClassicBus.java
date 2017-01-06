package fr.utbm.lo43.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class ClassicBus extends Bus
{
	private Rectangle shape;
	private float angle;
	
	//Compteur pour le temps depuis le dernier appel de update
	private int cpt = 0;
	
	public ClassicBus(Vector2f _position, Color _color)
	{
		super(_position, _color);
		capacity = 6;
		
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
		setPosition(new Vector2f(getPosition().x+dir,getPosition().y+dir));
		//angle++;
		//shape.transform(Transform.createRotateTransform(angle));
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg,int delta) {
		
		super.update(gc, sbg,delta);
		
		cpt += delta;
		if(delta >100)
		{
			cpt = 0;
			move();
		}
	}

}
