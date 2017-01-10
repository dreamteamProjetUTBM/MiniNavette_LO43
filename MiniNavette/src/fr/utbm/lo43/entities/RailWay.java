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
 * A la mani�re des rivi�res dans mini metro, les voix ferr�es repr�sentent des obstacles
 * que les lignes ne peuvent traverser qu'avec des ponts
 * @author Thomas Gredin
 *
 */
public class RailWay extends Entity implements EntityDrawable
{
	/**
	 * Trac� de la voie ferr�e
	 */
	protected Polygon plot;
	
	/**
	 * Largeur du trac�
	 */
	private static final float RAILWAY_THICKNESS = 5;
	
	/**
	 * La couleur du trac�
	 */
	private Color color;
	
	public RailWay() 
	{
		super(new Vector2f(0, 0));
		drawable = true;
		
		plot = new Polygon();
		plot.setClosed(false);

		color = new Color(26, 31, 43);
		
		int startX = 0;
		int startY = 0;
		
		/* D�terminer si on part d'un plan horizontal ou vertical */
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
		arg2.setAntiAlias(true);
		arg2.setLineWidth(RAILWAY_THICKNESS * 1.0f);
		arg2.setColor(color);
		arg2.draw(plot);
		for(int i = 0; i<plot.getPointCount(); ++i){
			
			arg2.fillOval(plot.getPoint(i)[0]-RAILWAY_THICKNESS/2, plot.getPoint(i)[1] -RAILWAY_THICKNESS/2, RAILWAY_THICKNESS, RAILWAY_THICKNESS);
		}

	}
	
	/**
	 * Permet de savoir si une autre instance de la classe shape est en collision
	 * avec la voie ferr�e
	 * @param _shape la forme � v�rifier
	 * @return vrai si il y � collision et faux si ce n'est pas le cas
	 */
	public boolean intersects(Shape _shape)
	{
		return plot.intersects(_shape);
	}

	
	public boolean isOnRailWay(Vector2f _vector){
		return plot.contains(_vector.x, _vector.y);
	}

	
}
