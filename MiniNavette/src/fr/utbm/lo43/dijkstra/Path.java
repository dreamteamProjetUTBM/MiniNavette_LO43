package fr.utbm.lo43.dijkstra;

import java.util.ArrayList;



public class Path<T> {
	
	private ArrayList<T> elements;
	protected float weight;
	
	public Path() {
		super();
		this.elements = new ArrayList<>();
		this.weight = -1;
	}

	public float getWeight() {
		return weight;
	}
	
	public void add(T element){
		elements.add(0, element);
	}
	
	public T get(int index){
		return elements.get(index);
	}

	public ArrayList<T> getElements() {
		return elements;
	}
	
	
}
