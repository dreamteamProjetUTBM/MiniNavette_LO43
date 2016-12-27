package fr.utbm.lo43.entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class EntityClickable extends Entity implements EntityUpdateable 
{
	private EventEntityMouseClicked clickedEvent;

	/**
	 * Avec ce constructeur on donne directement le EventEntityMouseClicked
	 * @param _clickedEvent	Le EventEntityMouseClicked qui contient directement les actions
	 * @param _position		La position de l'entit�
	 * @param _size			La taille de l'entit�
	 */
	public EntityClickable(EventEntityMouseClicked _clickedEvent, Vector2f _position, Vector2f _size)
	{
		super(_position, _size);
		
		clickedEvent = _clickedEvent;
	}
	
	/**
	 * Avec ce constructeur on est oblig� de d�finir le EventEntityMouseClicked dans la classe qui h�rite
	 * @param _position La position de l'entit�
	 * @param _size		La taille de l'entit�
	 */
	public EntityClickable(Vector2f _position, Vector2f _size)
	{
		super(_position, _size);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg) 
	{
		Input input = gc.getInput();
		
		if(getRect().contains(input.getMouseX(), input.getMouseY()) 
				&& input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{
			clickedEvent.mouseClicked();
		}
	}
}
