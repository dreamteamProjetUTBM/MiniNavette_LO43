package fr.utbm.lo43.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author Thomas Gredin
 *
 * Classe EntityDragable
 * Est une sorte d'entité qui peut être déplacer par une action de glissé-déposé
 * effectué avec le curseur de la souris.
 */
public class EntityDragable extends Entity implements EntityUpdateable
{
	protected EventEntityMouseDraged dragedEvent;
	protected boolean isGrabed;
	
	/**
	 * Avec ce constructeur on donne directement le EventEntityMouseDraged
	 * @param _dragedEvent	Le EventEntityMouseDraged qui contient directement les actions
	 * @param _position		La position de l'entitï¿½
	 * @param _size			La taille de l'entitï¿½
	 */
	public EntityDragable(EventEntityMouseDraged _dragedEvent, Vector2f _position, Vector2f _size)
	{
		super(_position, _size);
		
		dragedEvent = _dragedEvent;
		isGrabed = false;
		
		updatable = true;
	}
	
	/**
	 * Avec ce constructeur on est obligï¿½ de dï¿½finir le EventEntityMouseDraged dans la classe qui hï¿½rite
	 * @param _position	La position de l'entitï¿½
	 * @param _size		La taille de l'entitï¿½
	 */
	public EntityDragable(Vector2f _position, Vector2f _size)
	{
		super(_position, _size);
		
		isGrabed = false;
		
		updatable = true;
	}
	
	/**
	 * Avec ce constructeur vous devez tout initialiser dans la classe qui hï¿½rite sauf la position
	 * donnï¿½e en paramï¿½tre
	 * @param _position La position de l'entitï¿½
	 */
	public EntityDragable(Vector2f _position)
	{
		super(_position);
		
		isGrabed = false;
		
		updatable = true;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg,int delta) 
	{
		Input input = gc.getInput();
		
		if(getRect().contains(input.getMouseX(), input.getMouseY()) && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{
			if(isGrabed == true)
			{
				position.x = input.getMouseX() - (size.x / 2.0f);
				position.x = input.getMouseY() - (size.y / 2.0f);
				if(dragedEvent != null)
					dragedEvent.mousePressed();
			}
			else
			{
				isGrabed = true;
			}
		}
		else
		{
			if(isGrabed != false)
			{
				if(dragedEvent != null)
					dragedEvent.mouseReleased();
				isGrabed = false;
			}
		}
	}
	
	public void setEventCallback(EventEntityMouseDraged _mouseDraged)
	{
		dragedEvent = _mouseDraged;
	}
}
