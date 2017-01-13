package fr.utbm.lo43.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author Thomas Gredin
 *
 * Classe EntityClickable
 * Est une sorte d'entité qui peut entrer en interaction avec le curseur de la
 * souris.
 */
public class EntityClickable extends Entity implements EntityUpdateable 
{
	protected EventEntityMouseClicked clickedEvent;
	protected boolean isMouseHover;
	protected boolean isMouseHoverAndPressed;
	protected boolean isMousePressed;

	/**
	 * Avec ce constructeur on donne directement le EventEntityMouseClicked
	 * @param _clickedEvent	Le EventEntityMouseClicked qui contient directement les actions
	 * @param _position		La position de l'entitï¿½
	 * @param _size			La taille de l'entitï¿½
	 */
	public EntityClickable(EventEntityMouseClicked _clickedEvent, Vector2f _position, Vector2f _size)
	{
		super(_position, _size);
		
		clickedEvent = _clickedEvent;
		isMouseHover = false;
		isMouseHoverAndPressed = false;
		isMousePressed = false;
		
		updatable = true;
	}
	
	/**
	 * Avec ce constructeur on est obligï¿½ de dï¿½finir le EventEntityMouseClicked dans la classe qui hï¿½rite
	 * @param _position La position de l'entitï¿½
	 * @param _size		La taille de l'entitï¿½
	 */
	public EntityClickable(Vector2f _position, Vector2f _size)
	{
		super(_position, _size);
		
		isMouseHover = false;
		isMouseHoverAndPressed = false;
		
		updatable = true;
	}
	
	/**
	 * Avec ce constructeur vous devez tout initialiser dans la classe qui hï¿½rite sauf la position
	 * donnï¿½e en paramï¿½tre
	 * @param _position La position de l'entitï¿½
	 */
	public EntityClickable(Vector2f _position)
	{
		super(_position);
		
		isMouseHover = false;
		isMouseHoverAndPressed = false;
		
		updatable = true;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg,int delta) 
	{
		Input input = gc.getInput();
		
		if(getRect().contains(input.getMouseX(), input.getMouseY())) 
		{
			isMouseHover = true;
			
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
			{
				isMouseHoverAndPressed = true;
				if(clickedEvent != null)
					clickedEvent.mouseClicked();
			}
			else
			{
				isMouseHoverAndPressed = false;
			}
		}
		else
		{
			isMouseHover = false;
			isMouseHoverAndPressed = false;
		}
	}
}
