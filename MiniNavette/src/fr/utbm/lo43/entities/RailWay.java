package fr.utbm.lo43.entities;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import fr.utbm.lo43.logic.Map;

/**
 * Classe RailWay
 * 
 * A la manière des rivières dans mini metro, les voix ferrées représentent des obstacles
 * que les lignes ne peuvent traverser qu'avec des ponts
 * @author Thomas Gredin
 *
 */
public class RailWay extends Entity implements EntityDrawable
{
	/**
	 * Tracé de la voie ferrée
	 */
	private Polygon plot;
	
	/**
	 * Largeur du tracé
	 */
	private int plotWidth;
	
	/**
	 * La couleur du tracé
	 */
	private Color color;
	
	public RailWay() 
	{
		super(new Vector2f(0, 0));
		drawable = true;
		
		plot = new Polygon();
		plot.setClosed(false);
		plotWidth = 5;
		color = new Color(26, 31, 43);
		
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
		Rectangle map_rect = new Rectangle(-1, -1, Map.WIDTH + 10, Map.HEIGHT + 10);
		
		while(map_rect.contains(actualX, actualY))
		{
			actualX += startX == 0 ? (random.nextInt(2) == 0 ? 64: 0) : (random.nextInt(2) == 0 ? -64: 0);
			actualY += startY == 0 ? (random.nextInt(2) == 0 ? 64: 0) : (random.nextInt(2) == 0 ? -64: 0);
			
			plot.addPoint(actualX, actualY);
		}
	}

	@Override
	public void render(Graphics arg2) 
	{
		arg2.setLineWidth(plotWidth * 1.0f);
		arg2.setColor(color);
		arg2.draw(plot);
	}
	
	/**
	 * Permet de savoir si une autre instance de la classe shape est en collision
	 * avec la voie ferrée
	 * @param _shape la forme à vérifier
	 * @return vrai si il y à collision et faux si ce n'est pas le cas
	 */
	public boolean intersects(Shape _shape)
	{
		return plot.intersects(_shape);
	}
}
