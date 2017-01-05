package fr.utbm.lo43.djikstra;

public class Node<T> {

	protected T element;
	protected int weight;
	protected boolean reached;
	protected T antecedent;
	
	public Node(T element) {
		super();
		this.element = element;
		this.weight = -1;
		this.reached = false;
		this.antecedent = null;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	
	
	
	
}
