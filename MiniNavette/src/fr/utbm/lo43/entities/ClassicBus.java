package fr.utbm.lo43.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class ClassicBus extends Bus
{
	private Rectangle shape;
	private float angle;
	
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
		angle++;
		shape.transform(Transform.createRotateTransform(angle));
	}

}
