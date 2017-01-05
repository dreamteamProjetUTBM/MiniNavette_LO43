package fr.utbm.lo43.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Classe Label
 * Permet d'afficher un text donné sur l'écran
 * @author Thomas Gredin
 *
 */
public class Label extends Entity implements EntityDrawable
{	
	/**
	 * Le text affiché lors du rendu du Label
	 */
	private String text;
	
	/**
	 * Permet de construire un Label en renseignant sont texte ainsi 
	 * que la position ou il sera rendu
	 * @param _text		Le texte qui sera rendu par le Label
	 * @param _position Position de rendu du texte sur l'écran
	 */
	public Label(String _text, Vector2f _position)
	{
		super(_position);
		
		text = _text;
		drawable = true;
	}

	@Override
	public void render(Graphics arg2) 
	{
		arg2.drawString(text, position.x, position.y);
	}
	
	/**
	 * Permet de renseigner le texte qui est affiché par le Label
	 * @param _text Le nouveau texte
	 */
	public void setText(String _text)
	{
		text = _text;
	}
	
	/**
	 * Permet de récupérer le texte courant du Label
	 * @return Le texte actuel du Label
	 */
	public String getText()
	{
		return text;
	}
}
