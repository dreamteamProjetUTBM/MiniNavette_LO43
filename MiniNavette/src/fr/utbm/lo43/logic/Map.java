package fr.utbm.lo43.logic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ConfigurableEmitter.RandomValue;

import fr.utbm.lo43.entities.Station;

public class Map {
	
	public ArrayList<Station> stations;
	private ArrayList<fr.utbm.lo43.logic.Line> lines;
	public ArrayList<Line> grid;

	private int stationsLenght;
	
	/** Constructeur privé */
	private Map()
	{
		stations = new ArrayList<>();
		grid = new ArrayList<>();
		lines = new ArrayList<>();
		
		//Création de la grille
		for(int i = 0;i < 1080 ; i+=24){
			grid.add(new Line(i,0,i,720));
		}
		for(int i = 0;i < 720 ; i+=24){
			grid.add(new Line(0,i,1080,i));
		}
		
		//Initialisation des trois premières stations
		stations.add(new Station(new Vector2f(33*24,12*24),Filiere.GI));
		stations.add(new Station(new Vector2f(20*24,20*24),Filiere.EDIM));
		stations.add(new Station(new Vector2f(37*24,26*24),Filiere.IMSI));

		stationsLenght = stations.size();
		
	}
 
	/** Instance unique pré-initialisée */
	private static Map INSTANCE = new Map();
 
	/** Point d'accès pour l'instance unique du singleton */
	public static Map getInstance()
	{	return INSTANCE;
	}
	
	public int getStationsLenght(){
		return stationsLenght;
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

}
