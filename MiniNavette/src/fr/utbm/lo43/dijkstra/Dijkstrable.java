package fr.utbm.lo43.dijkstra;

public interface Dijkstrable {

	/**
	 * Calcule le poids (distance) entre this et un Dijkstrable d
	 * 
	 * @param d
	 * @return
	 */
	float calculateWeight(Dijkstrable d);

	/**
	 * Verifie si deux elements sont reliés entre eux
	 * 
	 * @param d
	 * @return
	 */
	boolean isConnected(Dijkstrable d);

}
