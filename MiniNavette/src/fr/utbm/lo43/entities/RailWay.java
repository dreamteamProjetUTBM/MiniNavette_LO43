package fr.utbm.lo43.entities;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import fr.utbm.lo43.logic.Map;

public class RailWay extends Entity implements EntityDrawable
{
	private Polygon plot;
	private int plotWidth;
	
	public RailWay() 
	{
		super(new Vector2f(0, 0));
		drawable = true;
		
		plot = new Polygon();
		plot.setClosed(false);
		plotWidth = 5;
		
		int startX = 0;
		int startY = 0;
		
		/* Déterminer si on part d'un plan horizontal ou vertical */
		Random random = new Random();
		if(random.nextInt(2) == 0)
		{
			/* plan horizontal */
			startX = random.nextInt((Map.WIDTH - 200)) + 200;
			startY = random.nextInt(2) == 0 ? 0 : Map.HEIGHT;
			
			plot.addPoint(startX, startY);
		}
		else
		{
			/* plan vertical */
			startX = random.nextInt(2) == 0 ? 0 : Map.WIDTH;
			startY = random.nextInt((Map.HEIGHT - 200)) + 200;
			
			plot.addPoint(startX, startY);
		}
		
		int actualX = startX;
		int actualY = startY;
		System.out.println("Actual X : " + actualX + " Actual Y : " + actualY);
		Rectangle map_rect = new Rectangle(-1, -1, Map.WIDTH + 10, Map.HEIGHT + 10);
		
		while(map_rect.contains(actualX, actualY))
		{
			actualX += startX == 0 ? (random.nextInt(2) == 0 ? 64: 0) : (random.nextInt(2) == 0 ? -64: 0);
			actualY += startY == 0 ? (random.nextInt(2) == 0 ? 64: 0) : (random.nextInt(2) == 0 ? -64: 0);
			
			System.out.println("Actual X : " + actualX + " Actual Y : " + actualY);
			
			plot.addPoint(actualX, actualY);
		}
	}

	@Override
	public void render(Graphics arg2) 
	{
		arg2.setLineWidth(plotWidth * 1.0f);
		arg2.setColor(new Color(26, 31, 43));
		arg2.draw(plot);
	}
}
