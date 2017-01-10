package fr.utbm.lo43.logic;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

import fr.utbm.lo43.dijkstra.DijkstraPathfinding;
import fr.utbm.lo43.entities.Bus;
import fr.utbm.lo43.entities.Passenger;
import fr.utbm.lo43.entities.RailWay;
import fr.utbm.lo43.entities.Station;

public class Map {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int GRID_SIZE = 32;
	public static final int GAMESPEED_MAX = 5; // valeur maximale du gamespeed 

	public ArrayList<Station> stations;
	private ArrayList<fr.utbm.lo43.logic.Line> lines;
	public ArrayList<Line> grid;
	public RailWay railWay;
	public DijkstraPathfinding<Station> pathfinding;

	public int gameSpeed; //pour gerer la vitesse du jeuet permettre ainsi d'accelerer
	
	/** Constructeur privé */
	private Map()
	{
		gameSpeed = 1;
		
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
	
	public Station createStation(){
		Random rand = new Random();
		boolean isRight = false;
		Vector2f _newposition = new Vector2f();
		
		while(!isRight){
			_newposition = new Vector2f(rand.nextInt(WIDTH/GRID_SIZE)*GRID_SIZE, 
					rand.nextInt(HEIGHT/GRID_SIZE)*GRID_SIZE);
			isRight = true;
			for (Station station : stations) {
				Vector2f center = new Vector2f(station.getPosition().x+GRID_SIZE, station.getPosition().y+GRID_SIZE);
				for(int i = -3; i < 4; i++){
					for(int j = -3; j < 4; j++){
						if (station.isOnStation(new Vector2f(_newposition.x+i*GRID_SIZE,_newposition.y+j*GRID_SIZE))){
							isRight = false;
							break;
						}
					}
				}
				
			}
			
			
			for(int i = -2 ; i < 3; i++){
				for(int j = -2; j < 3 ; j++){
					if(railWay.isOnRailWay(new Vector2f(_newposition.x+i*GRID_SIZE,_newposition.y+j*GRID_SIZE))){
						isRight = false;
						break;
					}
				}
			}
			
			if((_newposition.x <= 3*GRID_SIZE || _newposition.x >= (WIDTH-2*GRID_SIZE)) ||
					(_newposition.y <= 3*GRID_SIZE || _newposition.y >= HEIGHT- 4*GRID_SIZE)){
				isRight = false;
			}
		}
				
		_newposition = new Vector2f(_newposition.x-GRID_SIZE,_newposition.y-GRID_SIZE);
		Station station = new Station(_newposition, getFiliere(rand.nextInt(5)));
		stations.add(station);
		
		Inventory.getInstance().setRemainingStation(-1);
		return station;
	}
	
	
	public boolean CanCreateStation(){
		if(Inventory.getInstance().getRemainingStation() == 0)
			return false;
		
		
		
		return true;
	}
	
	
	private Filiere getFiliere(int index){
		switch(index){
			case 0: 
				return Filiere.EDIM;
			case 1:
				return Filiere.ENERGIE;
			case 2:
				return Filiere.GI;
			case 3: 
				return Filiere.GMC;
			default:
				return Filiere.IMSI;
		}
	}
	
	public void calculateNextStopStations(){
		synchronized(stations){
			pathfinding = new DijkstraPathfinding<>(stations);
			for(Station s : stations){
				s.setNextStop(pathfinding);
			}
		}
	}
	public int getStationsLenght(){
		return stations.size();
	}
	
	public synchronized ArrayList<Station> getStations(){
		synchronized(stations){
			return stations;
			
		}
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
