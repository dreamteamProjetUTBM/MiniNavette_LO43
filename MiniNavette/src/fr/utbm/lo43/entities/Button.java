package fr.utbm.lo43.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;




/**
 * 
 * @author Thomas Gredin
 * 
 * Classe Button
 * 
 * Cette classe permet de créer un boton à trois états (idle, hover, pressed)
 * On peut également mettre un callback qui permettra de décrire les actions
 * qui seront exécutés lors de l'appuie sur le bouton
 */
public class Button extends EntityClickable implements EntityDrawable 
{

	private Image img_actual;

	private Image img_idle;
	private Image img_hover;
	private Image img_pressed;



	
	/**
	 * Constructeur.
	 * @param _position
	 * @param _img_idle
	 * @param _img_hover
	 * @param _img_pressed
	 */
	public Button(Vector2f _position, String _img_idle, String _img_hover, String _img_pressed) 
	{

		super(_position);

		try {
			img_idle = new Image(_img_idle);
			img_hover = new Image(_img_hover);
			img_pressed = new Image(_img_pressed);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		img_actual = img_idle;
		size.x = img_actual.getWidth();
		size.y = img_actual.getHeight();

		drawable = true;
	}


	/**
	 * Constructeur.
	 * @param _position
	 * @param height
	 * @param width
	 * @param _img_idle
	 * @param _img_hover
	 * @param _img_pressed
	 */
	public Button(Vector2f _position, float height, float width,String _img_idle, String _img_hover, String _img_pressed) 
	{

		super(_position);

		try {
			img_idle = new Image(_img_idle);
			img_hover = new Image(_img_hover);
			img_pressed = new Image(_img_pressed);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		img_actual = img_idle;
		size.x = width;
		size.y = height;
	}

	@Override
	public void render(Graphics arg2) {
		img_actual.draw(position.x, position.y, size.x, size.y);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		super.update(gc, sbg, delta);

		/**
		 * Logique de changement d'image suivant la position de la souris
		 */
		if (isMouseHover) {

			if (isMouseHoverAndPressed) {
				img_actual = img_pressed;
			} else {
				img_actual = img_hover;
			}
		} else {
			img_actual = img_idle;
		}
	}

	/**
	 * Permet de mettre en place l'action qui sera menï¿½ quand le bouton de la
	 * souris sera cliquï¿½
	 * 
	 * @param _mouseClicked
	 *            L'interface surchargï¿½e
	 */
	public void setEventCallback(EventEntityMouseClicked _mouseClicked) {
		clickedEvent = _mouseClicked;
	}
}
