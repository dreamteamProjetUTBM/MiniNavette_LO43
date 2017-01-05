package fr.utbm.lo43.dijkstra;

public interface Dijkstrable {

	/**
	 * Calcule le poids (distance) entre this et un Dijkstrable d
	 * @param d
	 * @return
	 */
	 default int calculateWeight(Dijkstrable d){
		 return 1;
	 };
	 
	 
}
