package fr.utbm.lo43.dijkstra;

import java.util.ArrayList;

/**
 * Classe Path, qui contient une liste de noeuds formant un chemin ainsi que son
 * poids, utilisé par l'algorithme de Dijkstra
 * 
 * @author Nahil
 *
 * @param <T>
 */
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

	public void add(T element) {
		elements.add(0, element);
	}

	public T get(int index) {
		return elements.get(index);
	}

	public ArrayList<T> getElements() {
		return elements;
	}

}
