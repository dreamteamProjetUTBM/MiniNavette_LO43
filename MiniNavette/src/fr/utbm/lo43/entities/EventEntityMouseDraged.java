package fr.utbm.lo43.entities;

/**
 * 
 * @author Thomas Gredin
 *
 * Interface EventEntityMouseDraged
 * Représente les évènement qui sont appellé lors du drag and drop
 * d'une EntityDragable.
 */
public interface EventEntityMouseDraged 
{
	/**
	 * Tant que le bouton de la souris est enfoncé il
	 * se produit les évènements décrient dans cette
	 * méthode
	 */
	void mousePressed();
	
	/**
	 * Décrit ce qui se produit lorsque le clique de la souris
	 * est relaché
	 */
	void mouseReleased();
}
