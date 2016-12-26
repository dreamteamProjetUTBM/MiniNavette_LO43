package fr.utbm.lo43.entities;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

public class Entity 
{
	/**
	 * La position de l'entit�
	 */
	protected Vector2f position;
	protected Vector2f size;
	
	public Entity(Vector2f _position, Vector2f _size)
	{
		position = new Vector2f(_position.x, _position.y);
		size = new Vector2f(_size.x, _size.y);
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
	
	/**
	 * Permet de r�cup�rer un vecteur qui donne la taille de l'entit�
	 * (sa largeur dans x et sa hauteur dans y)
	 * @return le vecteur contenant la taille
	 */
	public Vector2f getSize()
	{
		return size;
	}
	
	/**
	 * Permet de d�finir la taille de l'entit�
	 * @param _size la composante x repr�sente la largeur et la composante
	 * 				y repr�sente la hauteur de l'entit�
	 */
	public void setSize(Vector2f _size)
	{
		size.x = _size.x;
		size.y = _size.y;
	}
	
	/**
	 * Permet de r�cup�rer un rectangle dont les composantes repr�sentent
	 * la position ainsi que la taille de l'entit� :
	 * 		- x : position en x
	 * 		- y : position en y
	 * 		- width : largeur de l'entit�
	 * 		- height : hauteur de l'entit�
	 * Ce rectangle peut servir dans le cadre de la d�tection d'un clique souris
	 * par exemple
	 * @return
	 */
	public Rectangle getRect()
	{
		Rectangle rect = new Rectangle();
		rect.setX((int) position.x);
		rect.setY((int) position.y);
		rect.setWidth((int) size.x);
		rect.setHeight((int) size.y);
		
		return rect;
	}
}
