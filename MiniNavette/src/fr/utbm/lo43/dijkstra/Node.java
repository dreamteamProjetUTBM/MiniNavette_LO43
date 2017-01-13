package fr.utbm.lo43.dijkstra;

/**
 * Noeud contenant un element, utilisé pour l'algorithme de Dijkstra
 * 
 * @author Nahil Zamiati
 *
 * @param <T>
 */
public class Node<T extends Dijkstrable> {

	protected T element;
	protected float weight;
	protected boolean reached;
	protected Node<T> antecedent;

	public Node(T element) {
		super();
		this.element = element;

	}

}
