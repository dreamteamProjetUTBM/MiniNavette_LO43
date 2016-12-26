package fr.utbm.lo43.entities;

import org.lwjgl.util.vector.Vector2f;

public class Entity 
{
	/**
	 * La position de l'entit�
	 */
	protected Vector2f position;
	
	public Entity()
	{
		
	}
	
	/**
	 * R�cup�rer la position actuelle de l'entit�
	 * @return la position
	 */
	public Vector2f getPosition()
	{
		return position;
	}
	
	/**
	 * Donner une position qui sera la nouvelle position de
	 * l'entit�
	 * @param _position la nouvelle position
	 */
	public void setPosition(Vector2f _position)
	{
		position.x = _position.x;
		position.y = _position.y;
	}
	
	/**
	 * Permet de d�placer l'entit� par la valeur de l'offset
	 * donn� sur les axes x et y
	 * @param _offset les offset de d�placement sur x et y
	 */
	public void move(Vector2f _offset)
	{
		position.x += _offset.x;
		position.y += _offset.y;
	}
}
