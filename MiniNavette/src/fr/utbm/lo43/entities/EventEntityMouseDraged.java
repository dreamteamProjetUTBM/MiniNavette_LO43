package fr.utbm.lo43.entities;

/**
 * 
 * @author Thomas Gredin
 *
 * Interface EventEntityMouseDraged
 * Repr�sente les �v�nement qui sont appell� lors du drag and drop
 * d'une EntityDragable.
 */
public interface EventEntityMouseDraged 
{
	/**
	 * Tant que le bouton de la souris est enfonc� il
	 * se produit les �v�nements d�crient dans cette
	 * m�thode
	 */
	void mousePressed();
	
	/**
	 * D�crit ce qui se produit lorsque le clique de la souris
	 * est relach�
	 */
	void mouseReleased();
}
