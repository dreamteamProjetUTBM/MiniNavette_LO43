package fr.utbm.lo43.entities;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

import fr.utbm.lo43.logic.Map;

public class RailWay extends Entity implements EntityDrawable
{
	private Polygon plot;
	private int plotWidth;
	
	public RailWay() 
	{
		super(new Vector2f(0, 0));
		
		plot = new Polygon();
		plot.setClosed(false);
		plotWidth = Map.GRID_SIZE;
		
		int startX = 0;
		int startY = 0;
		
		/* Déterminer si on part d'un plan horizontal ou vertical */
		Random random = new Random();
		if(random.nextInt(1) == 0)
		{
			/* plan horizontal */
			startX = random.nextInt((Map.WIDTH - 200)) + 200;
			startY = 0;
			
			plot.addPoint(startX, startY);
		}
		else
		{
			/* plan vertical */
			startX = 0;
			startY = random.nextInt((Map.HEIGHT - 200)) + 200;
			
			plot.addPoint(startX, startY);
		}
		
		int actualX = startX;
		int actualY = startY;
	}

	@Override
	public void render(Graphics arg2) 
	{
		
	}
}
