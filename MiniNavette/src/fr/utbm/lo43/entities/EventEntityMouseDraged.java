package fr.utbm.lo43.entities;

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
