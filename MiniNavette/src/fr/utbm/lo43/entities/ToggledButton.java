package fr.utbm.lo43.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author Thomas Gredin Jeremy
 *
 * Classe ToggledButton
 * Classe qui permet de créer un bouton à état, c'est à dire qu'il reste activé quand
 * il est cliqué et ne sera désactivé que lors du prochain clique.
 */
public class ToggledButton extends EntityClickable implements EntityDrawable 
{
	private Image img_actual;
	
	private Image img_idle;
	private Image img_hover;
	private Image img_pressed;
	
	private boolean toggled;
	
	/**
	 * Constructeur.
	 * @param _clickedEvent
	 * @param _position
	 * @param _size
	 */
	public ToggledButton(EventEntityMouseClicked _clickedEvent, Vector2f _position, Vector2f _size) 
	{
		super(_clickedEvent, _position, _size);
		
		drawable = true;
		toggled = false;
	}
	
	/**
	 * Constructeur.
	 * @param _position
	 * @param _size
	 * @param _img_idle
	 * @param _img_hover
	 * @param _img_pressed
	 */
	public ToggledButton(Vector2f _position, Vector2f _size,String _img_idle, String _img_hover, String _img_pressed) 
	{
		super(_position);
		
		try 
		{
			img_idle = new Image(_img_idle);
			img_hover = new Image(_img_hover);
			img_pressed = new Image(_img_pressed);
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
		
		img_actual = img_idle;
		size.x = _size.x;
		size.y = _size.y;
		
		drawable = true;
		toggled = false;

	}
	
	/**
	 * Constructeur.
	 * @param _position
	 * @param _size
	 */
	public ToggledButton(Vector2f _position, Vector2f _size) 
	{
		super(_position, _size);
		
		drawable = true;
		toggled = false;

	}
	
	/**
	 * Constructeur.
	 * @param _position
	 */
	public ToggledButton(Vector2f _position) 
	{
		super(_position);
		
		drawable = true;
		toggled = false;

	}

	@Override
	public void render(Graphics arg2) 
	{
		img_actual.draw(position.x, position.y,size.x,size.y);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg,int delta)
	{
		super.update(gc, sbg,delta);
		
		/**
		 * Logique de changement d'image suivant la position de la souris
		 */
		if(toggled){
			img_actual = img_pressed;
		}
		else {
			if(isMouseHover)
			{
				
				if(isMouseHoverAndPressed)
				{
					img_actual = img_pressed;
				}
				else
				{
					img_actual = img_hover;
				}
			}
			else
			{
				img_actual = img_idle;
			}
		}
	}

	public void setToggled(boolean _bool){
		toggled = _bool;
	}
	
	public boolean getToggled(){
		return toggled;
	}
	
	/**
	 * Permet de mettre en place l'action qui sera menï¿½ quand le bouton de la souris sera
	 * cliquï¿½
	 * @param _mouseClicked L'interface surchargï¿½e
	 */
	public void setEventCallback(EventEntityMouseClicked _mouseClicked)
	{
		clickedEvent = _mouseClicked;
	}
}
