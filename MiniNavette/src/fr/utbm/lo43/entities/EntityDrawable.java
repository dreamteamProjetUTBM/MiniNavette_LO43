package fr.utbm.lo43.entities;

import org.newdawn.slick.Graphics;

/**
 * 
 * @author Thomas Gredin
 * 
 * Interface Entity Drawable
 * Permet à une entité de se dessiner à l'écran.
 */
public interface EntityDrawable 
{
	/**
	 * Tout ce qui concerne le rendu
	 */
	void render(Graphics arg2);
}
