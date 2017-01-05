package fr.utbm.lo43.dijkstra;

import java.util.ArrayList;

public class DijkstraPathfinding<T extends Dijkstrable> {

	private ArrayList<Node<T>> nodesList;

	public DijkstraPathfinding(ArrayList<T> ElementsList) {
		super();
		nodesList = new ArrayList<>();
		for(T element : ElementsList ){
			nodesList.add(new Node<T>(element));
		}
		
		
	}
	
	/**
	 * Initialise la liste des noeuds pour demarrer le pathfinding
	 * @param start Element de depart
	 */
	public void init(T start){
		for (Node<T> n : nodesList){
			if (n.element == start){
				n.weight = 0;
				
			}else{
				n.weight = -1;
			}	
			n.reached = false;
			n.antecedent = null;
		}
	}
	
	/**
	 * Recupere le plus court chemin sous forme de liste
	 * @param start Element de depart
	 * @param end Element d'arrive
	 * @return liste des elements a parcourir dans l'ordre pour arriver a l'element end
 	 */
	public ArrayList<T> getShortestPath(T start, T end){
		Node<T> n;		
		init(start);
		
		//while(){
			n = getLightestUnreachedNode();
			n.element.calculateWeight(n.element);
		//}
		
		return null;
	}
	
	
	/**
	 * Recupere le noeud le plus leger non parcouru
	 * @return
	 */
	public Node<T> getLightestUnreachedNode(){
		
		Node<T> lightestNode = null;
		int minWeight = 0;
		
		for (Node<T> n : nodesList){
			if(!n.reached && n.weight != -1 && n.weight < minWeight){
				minWeight = n.weight;
				lightestNode = n;	
			}
		}
		return lightestNode;
		
	}
	
	/**
	 * Recupere la liste des fils du noeud passé en parametre (noeuds qui lui sont reliés directement)
	 * @param n
	 * @return
	 */
	public ArrayList<Node<T>> getSons(Node<T> n){
		ArrayList<Node<T>> sons = new ArrayList<>();
			
		return null;
		
	}
	

}
