package fr.utbm.lo43.entities;

public interface EventEntityMouseDraged {
	/**
	 * Tant que le bouton de la souris est enfonc� il se produit les �v�nements
	 * d�crient dans cette m�thode
	 */
	void mousePressed();

	/**
	 * D�crit ce qui se produit lorsque le clique de la souris est relach�
	 */
	void mouseReleased();
}
