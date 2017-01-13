package fr.utbm.lo43.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Classe Label Permet d'afficher un text donnï¿½ sur l'ï¿½cran
 * 
 * @author Thomas Gredin
 *
 */
public class Label extends Entity implements EntityDrawable {
	/**
	 * Le text affichï¿½ lors du rendu du Label
	 */
	private String text;

	/**
	 * Couleur du texte à rendre
	 */
	private Color color;

	/**
	 * Permet de construire un Label en renseignant sont texte ainsi que la
	 * position ou il sera rendu
	 * 
	 * @param _text
	 *            Le texte qui sera rendu par le Label
	 * @param _position
	 *            Position de rendu du texte sur l'ï¿½cran
	 */
	public Label(String _text, Vector2f _position) {
		super(_position);

		text = _text;
		color = Color.black;

		drawable = true;
	}

	@Override
	public void render(Graphics arg2) {
		arg2.setColor(color);
		arg2.drawString(text, position.x, position.y);
	}

	/**
	 * Permet de renseigner le texte qui est affichï¿½ par le Label
	 * 
	 * @param _text
	 *            Le nouveau texte
	 */
	public void setText(String _text) {
		text = _text;
	}

	/**
	 * Permet de rï¿½cupï¿½rer le texte courant du Label
	 * 
	 * @return Le texte actuel du Label
	 */
	public String getText() {
		return text;
	}

	/**
	 * Permet de changer la couleur de rendu du texte
	 * 
	 * @param _color
	 *            La nouvelle couleur de rendu du texte
	 */
	public void setColor(Color _color) {
		color = _color;
	}

	/**
	 * Permet de récupérer la couleur de rendu actuelle du texte
	 * 
	 * @return La couleur de rendu actuelle du texte
	 */
	public Color getColor() {
		return color;
	}
}
