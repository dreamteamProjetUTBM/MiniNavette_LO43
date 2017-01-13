package fr.utbm.lo43.entities;

import org.newdawn.slick.Graphics;

/**
 * 
 * @author Thomas Gredin
 * 
 * Interface Entity Drawable
 * Permet � une entit� de se dessiner � l'�cran.
 */
public interface EntityDrawable 
{
	/**
	 * Tout ce qui concerne le rendu
	 */
	void render(Graphics arg2);
}
