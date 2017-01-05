package fr.utbm.lo43.dijkstra;

public class Node<T extends Dijkstrable> {

	protected T element;
	protected int weight;
	protected boolean reached;
	protected T antecedent;
	
	public Node(T element) {
		super();
		this.element = element;
		
	}



	
	
	
	
	
}
