package fr.utbm.lo43.entities;

/**
 * 
 * @author Thomas Gredin
 *
 * Interface EventEntityClicked
 * Représente les évènement qui sont appellé lors du drag and drop
 * d'une EntityClickable.
 */
public interface EventEntityMouseClicked 
{
	/**
	 * Cette méthode représente ce qui devra se produire
	 * lors de l'appuie de la souris dans une zone donnée
	 */
	void mouseClicked();
}
