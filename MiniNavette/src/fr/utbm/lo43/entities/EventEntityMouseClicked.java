package fr.utbm.lo43.entities;

/**
 * 
 * @author Thomas Gredin
 *
 * Interface EventEntityClicked
 * Repr�sente les �v�nement qui sont appell� lors du drag and drop
 * d'une EntityClickable.
 */
public interface EventEntityMouseClicked 
{
	/**
	 * Cette m�thode repr�sente ce qui devra se produire
	 * lors de l'appuie de la souris dans une zone donn�e
	 */
	void mouseClicked();
}
