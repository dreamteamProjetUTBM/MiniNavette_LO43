package fr.utbm.lo43.logic;

import java.util.ArrayList;

import org.newdawn.slick.geom.Line;

import fr.utbm.lo43.dijkstra.DijkstraPathfinding;
import fr.utbm.lo43.entities.Bus;
import fr.utbm.lo43.entities.Passenger;
import fr.utbm.lo43.entities.RailWay;
import fr.utbm.lo43.entities.Station;

public class Map {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int GRID_SIZE = 32;
	
	public ArrayList<Station> stations;
	private ArrayList<fr.utbm.lo43.logic.Line> lines;
	public ArrayList<Line> grid;
	public RailWay railWay;
	public DijkstraPathfinding<Station> pathfinding;

	public int gameSpeed; //pour gerer la vitesse du jeuet permettre ainsi d'accelerer
	
	/** Constructeur privé */
	private Map()
	{
		gameSpeed = 3;
		
		stations = new ArrayList<>();
		grid = new ArrayList<>();
		lines = new ArrayList<>();
		
		//Création de la grille
		for(int i = 0;i < WIDTH ; i+=GRID_SIZE){
			grid.add(new Line(i,0,i,HEIGHT));
		}
		for(int i = 0;i < HEIGHT ; i+=GRID_SIZE){
			grid.add(new Line(0,i,WIDTH,i));
		}
	}

	/** Instance unique pré-initialisée */
	private static Map INSTANCE;
 
	/** Point d'accès pour l'instance unique du singleton */
	public static Map getInstance()
	{	
		if(INSTANCE == null)
			INSTANCE = new Map();
		return INSTANCE;
	}
	
	public void calculateNextStopStations(){
		pathfinding = new DijkstraPathfinding<>(stations);
		for(Station s : stations){
			s.setNextStop(pathfinding);
		}
	}
	public int getStationsLenght(){
		return stations.size();
	}
	
	public ArrayList<Station> getStations(){
		return stations;
	}
	
	public ArrayList<fr.utbm.lo43.logic.Line> getLines(){
		return lines;
	}
	
	public fr.utbm.lo43.logic.Line getLine(int index){
		return lines.get(index);
	}
	
	public void AddLine(fr.utbm.lo43.logic.Line _line){
		lines.add(_line);
	}
	
	public void addStation(Station _station){
		stations.add(_station);
	}
	
	public ArrayList<Station> getNextStops(Bus bus, Station _station){
		//renvoyer les stations suivantes du bus de sa ligne
		ArrayList<Station> nextStops = new ArrayList<>(bus.getCurrentSegment().getLine_bus().getStations());

		if(bus.getCurrentSegment().getLine_bus().isLoop()){
			nextStops.remove(_station);
		}else{
			if(bus.getDirection()){
				for(Station s : bus.getCurrentSegment().getLine_bus().getStations()){
					if(bus.getCurrentSegment().getLine_bus().getStations().indexOf(s)<= bus.getCurrentSegment().getLine_bus().getStations().indexOf(_station)){
						nextStops.remove(s);
					
					}
				}
			}else{
				for(Station s : bus.getCurrentSegment().getLine_bus().getStations()){
					if(bus.getCurrentSegment().getLine_bus().getStations().indexOf(s)>= bus.getCurrentSegment().getLine_bus().getStations().indexOf(_station)){
						nextStops.remove(s);
					
					}
				}
			}
		}

		return nextStops;
	}
	
	public Station getNextStop(Passenger passenger){
		//retourner la station dont il a besoin 
		return stations.get(1);
	}
}
