package fr.utbm.lo43.dijkstra;

import java.util.ArrayList;
/**Classe r�utilisable impl�mentant l'algorithme de Dijkstra pour trouver
 * le plus court chemin entre deux �lements de type Dijkstrable
 * 
 * @author Nahil Zamiati
 *
 * @param <T>
 * @see Dijkstrable, Node, Path
 */
public class DijkstraPathfinding<T extends Dijkstrable> {

	private ArrayList<Node<T>> nodesList;


	public DijkstraPathfinding(ArrayList<T> elementsList) {
		super();
		nodesList = new ArrayList<>();

		for(T element : elementsList ){

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
	 * Retourne le noeud qui correspond a l'�l�ment pass� en parametre
	 * @param element
	 * @return
	 */
	public Node<T> getNode(T element){
		Node<T> node = null;
		for(Node<T> n : nodesList){
			if(n.element == element){
				node = n;
			}
		}
		return node;

	}

	
	
	
	
	/**
	 * Recupere le plus court chemin entre deux elements sous forme de Path (qui contient une liste et le float weight qui correspond au poids du chemin)
	 * Retourne une liste contenant un element vide et un poid de -1 dans le cas ou il n'existe aucun chemin entre les deux elements
	 * @param start Element de depart
	 * @param end Element d'arrive
	 * @return liste des elements a parcourir dans l'ordre pour arriver a l'element end
	 * 			
	 */
	public Path<T> getShortestPath(T start, T end){

		Path<T> shortestPath = new Path<>();
		Node<T> nodeEnd = getNode(end);
		Node<T> nodeStart = getNode(start);
		Node<T> n;		
		init(start);
		
		
		while(getLightestUnreachedNode() != null && getLightestUnreachedNode() != nodeEnd){

			n = getLightestUnreachedNode();
			n.reached = true;

			for(Node<T> node : nodesList){
		
				if(!node.reached){
		
					if(n.element.isConnected(node.element)){
						
						if(node.weight == -1 || node.weight > n.weight + n.element.calculateWeight(node.element)){
							node.weight = n.weight + n.element.calculateWeight(node.element);
							node.antecedent = n;
							
						}
					}
				}
			}
		}
		
		
		n = nodeEnd;
		shortestPath.weight = nodeEnd.weight;

		if (n.antecedent == null){
		
			shortestPath.add(null);
			return shortestPath;
		}

		do{
			shortestPath.add(n.element);
			n = n.antecedent;
		}while(n != nodeStart);
		
		
		return shortestPath;
	}


	/**
	 * Recupere le noeud le plus leger non parcouru
	 * @return
	 */
	public Node<T> getLightestUnreachedNode(){

		Node<T> lightestNode = null;
		float minWeight = -1;

		for (Node<T> n : nodesList){
			if(!n.reached && n.weight != -1 && (n.weight <= minWeight || minWeight == -1)){
				minWeight = n.weight;
				lightestNode = n;	
			}
		}
		return lightestNode;

	}



}
