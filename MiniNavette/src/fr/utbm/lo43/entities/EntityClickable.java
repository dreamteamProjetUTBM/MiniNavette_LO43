package fr.utbm.lo43.entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class EntityClickable extends Entity implements EntityUpdateable 
{
	protected EventEntityMouseClicked clickedEvent;
	protected boolean isMouseHover;
	protected boolean isMouseHoverAndPressed;

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
		isMouseHover = false;
		isMouseHoverAndPressed = false;
	}
	
	/**
	 * Avec ce constructeur on est oblig� de d�finir le EventEntityMouseClicked dans la classe qui h�rite
	 * @param _position La position de l'entit�
	 * @param _size		La taille de l'entit�
	 */
	public EntityClickable(Vector2f _position, Vector2f _size)
	{
		super(_position, _size);
		
		isMouseHover = false;
		isMouseHoverAndPressed = false;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg) 
	{
		Input input = gc.getInput();
		
		if(getRect().contains(input.getMouseX(), input.getMouseY())) 
		{
			isMouseHover = true;
			
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
			{
				isMouseHoverAndPressed = true;
				clickedEvent.mouseClicked();
			}
			else
			{
				isMouseHoverAndPressed = false;
			}
		}
		
		isMouseHover = false;
		isMouseHoverAndPressed = false;
	}
}
