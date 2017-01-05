package fr.utbm.lo43.djikstra;

import java.util.ArrayList;

public class DjikstraPathfinding<T> {

	private ArrayList<Node<T>> nodesList;

	public DjikstraPathfinding(ArrayList<T> ElementsList) {
		super();
		nodesList = new ArrayList<>();
		for(T element : ElementsList ){
			nodesList.add(new Node<T>(element));
		}
		
		
	}
	
	public ArrayList<T> getShortestPath(T start, T end){
		for (Node<T> n : nodesList){
			if (n.element == start){
				
			}
		}
		return null;
		
	}
	
	

}
