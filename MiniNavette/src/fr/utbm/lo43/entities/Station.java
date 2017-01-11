package fr.utbm.lo43.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.dijkstra.DijkstraPathfinding;
import fr.utbm.lo43.dijkstra.Dijkstrable;
import fr.utbm.lo43.dijkstra.Path;
import fr.utbm.lo43.logic.Filiere;
import fr.utbm.lo43.logic.Game;
import fr.utbm.lo43.logic.Line;
import fr.utbm.lo43.logic.Map;

public class Station extends EntityClickable implements EntityDrawable, Dijkstrable
{
	//24 taille case
	//Quadrillage
	//Jerem: rajout de ma part, à voir s'il ne faut pas le mettre dans le diagramme
	//En FR en plus
	protected volatile Filiere filiere;
	public static final int MAXIMUM_PASSENGER = 8;
	public static final int CRITICAL_PASSENGER = 6;	
	private float waitedTime ;

	private static final float MAX_WAITING_TIME = 30;
	private static final float BONUS_WAITING_TIME = 15;
	
	private volatile Image preview;
		
	private  boolean alcoolized;
	
	private CamembertCounter camembert;
	//Compteur pour le temps depuis le dernier appel de update
	private float cpt = 0;
	
	private volatile List<Passenger> waitingPassenger;
	
	//HashMap qui donne la prochaine station a atteindre pour atteindre une filiere a partir de cette station 
	private volatile HashMap<Filiere, Station> nextStop;

	
	public Station(Vector2f _position, Filiere type) 
	{
		super(_position);
		filiere = type;
		waitingPassenger = new ArrayList<Passenger>();
		Map.getInstance().calculateNextStopStations();
		try 
		{
			preview = new Image("asset/"+filiere.toString().toLowerCase()+".png");
			
		}
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
		
		size.x = Map.GRID_SIZE*2;
		size.y = Map.GRID_SIZE*2;
		
		camembert = new CamembertCounter(new Vector2f(getPosition().x ,getPosition().y), Map.getInstance().GRID_SIZE*2);
		drawable = true;
	}
	
	
	public  List<Passenger> getWaitingPassenger(){
//		synchronized(waitingPassenger){
			return waitingPassenger ;
			
//		}
	}
	
	public Passenger newPassenger()
	{
		
		Filiere passenger_type = filiere;
		
		int stations_size = Map.getInstance().getStationsLenght();
		Random rand = new Random();
		
		
		while(passenger_type == filiere){
			int index = rand.nextInt(stations_size);
			passenger_type = Map.getInstance().stations.get(index).filiere;
		}
		
		float offsetX,offsetY;

		offsetX = waitingPassenger.size()%4;
		offsetY = 0;
			
		offsetX = getPosition().x + offsetX * Map.GRID_SIZE/2;
		//offsetY = getPosition().y + waitingPassenger.size()/4 * Map.GRID_SIZE*1.5f ;*
		offsetY = getPosition().y + waitingPassenger.size()/4 * Map.GRID_SIZE/2 ;
	
		Passenger p = new Passenger(new Vector2f(offsetX,offsetY), passenger_type);

		synchronized(waitingPassenger){
				waitingPassenger.add(p);
		}
		return p;
		
		
	}
	
	/**
	 * Permet de remplir la HashMap nextStop
	 */
	public void setNextStop(DijkstraPathfinding<Station> pathfinding){
		nextStop = new HashMap<>();
		Path<Station> shortestPath;
		Station tempStation;
		float minDistance = -1;
		
		for(Filiere f : Filiere.values()){
	
			minDistance = -1;
			tempStation = null;
			for(Station s : Map.getInstance().stations){
				if(s.filiere == f){
					shortestPath = pathfinding.getShortestPath(this, s);

					if((shortestPath.getWeight()<minDistance || minDistance == -1) && shortestPath.getWeight()!=-1){
						minDistance = shortestPath.getWeight();
						//tempStation = s.getNextConnection(shortestPath);
						tempStation = shortestPath.get(0);
					}
					
					
				}
			}
			nextStop.put(f, tempStation);
			
		}
		
	}
	
	/**
	 * Retourne la premiere correspondance dans un path de stations
	 * @param shortestPath
	 * @return
	 */
	public Station getNextConnection(Path<Station> shortestPath){
		
		if(shortestPath.getElements().get(0)==null){
			return null;
		}
		
		ArrayList<Line> lineInCommon = new ArrayList<>(this.getLines());
		lineInCommon.retainAll(shortestPath.getElements().get(0).getLines());
		for(int i = 1; i<shortestPath.getElements().size();++i){
			lineInCommon.retainAll(shortestPath.getElements().get(i).getLines());
			if(lineInCommon.size() == 0){
				return shortestPath.getElements().get(i-1);
			}
		}

		return shortestPath.getElements().get(shortestPath.getElements().size()-1);
		
	}
	
	
	
	public ArrayList<Line> getLines(){
		ArrayList<Line> lines = new ArrayList<>();
		for(Line l : Map.getInstance().getLines()){
			if(l.getStations().contains(this)){
				lines.add(l);
			}
		}
		
		return lines;
	}
	
	public synchronized boolean canAddPassenger(){
		synchronized(waitingPassenger){
			if(waitingPassenger.size() >= MAXIMUM_PASSENGER){
				return false;
			}
			return true;
		}
	}
	
	public synchronized boolean isCriticalPassenger(){
		synchronized(waitingPassenger){
			return (waitingPassenger.size() >= CRITICAL_PASSENGER) ;
		}
	}
	
	private void checkWaitingTime()
	{

		if(this.alcoolized)
		{
			
			if(waitedTime >= MAX_WAITING_TIME + BONUS_WAITING_TIME)
			{
				Game.setGameOver(true);
				System.out.println("Perdu !! Les passagers ont attendu "+ waitedTime + "secondes");
			}
			else System.out.println("Attention, une station est en état critique : "+ ( MAX_WAITING_TIME + BONUS_WAITING_TIME- waitedTime)+ " secondes avant la défaite");
			camembert.setPercentage(waitedTime*100/MAX_WAITING_TIME + BONUS_WAITING_TIME);
		}
		else
		{
			
			if(waitedTime >= MAX_WAITING_TIME)
			{
				Game.setGameOver(true);
				System.out.println("Perdu !! Les passagers ont attendu "+ waitedTime + "secondes");
			}
			else System.out.println("Attention, une station est en état critique : "+ ( MAX_WAITING_TIME - waitedTime)+ " secondes avant la défaite");
			
		}
		
	}
	
	public synchronized void alcoolise()
	{
		alcoolized = true;
	}
	
	public void notifyBus(Bus bus)
	{

		System.out.println("Station.notifyBus");
		
			
		
	
			if(!bus.isEmpty()) bus.unload(this);

				for(Passenger passenger : waitingPassenger)
				{
					//on met à jour l'arrêt suivant de chaque passager qui attend à la station avant qu'ils vérifient si le bus y va
					passenger.nextStop = this.nextStop.get(passenger.filiere);
	
					System.out.println("Passenger a un nouveau stop : " + this.nextStop.get(passenger.filiere));
				}
			if(waitingPassenger.size() > 0) bus.load(this);
		
		
	}
	
	public void enterStation(Passenger passenger)
	{
		boolean success = false ;

		synchronized(waitingPassenger){
			success =waitingPassenger.add(passenger);

		}
			if(success)
			{
				for(Passenger p : waitingPassenger)
				{
					float offsetX = waitingPassenger.indexOf(p)%4;
					p.setPosition(
							new Vector2f(
									getPosition().x + offsetX * Map.GRID_SIZE/2,
									//getPosition().y + waitingPassenger.size()/4 * Map.GRID_SIZE*1.5f
									getPosition().y + waitingPassenger.indexOf(p)/4 * Map.GRID_SIZE/2
							)
					);
				}
			}
	}
	
	public void leaveStation(Passenger passenger)
	{
		synchronized(waitingPassenger){
		
			System.out.println("PASSAGERS AVANT SUPPRESSION : " + waitingPassenger.size());
			
			if(waitingPassenger.remove(passenger))
			{
				for(Passenger p : waitingPassenger)
				{
					float offsetX = waitingPassenger.indexOf(p)%4;
					p.setPosition(
							new Vector2f(
									getPosition().x + offsetX * Map.GRID_SIZE/2,
									//getPosition().y + waitingPassenger.size()/4 * Map.GRID_SIZE*1.5f
									getPosition().y + waitingPassenger.indexOf(p)/4 * Map.GRID_SIZE/2
							)
					);
				}
			}
		
		}
	}
	
	public Station getNextStation(Passenger passenger)
	{
		return null;
	}

	public Filiere getFiliere(){
		return filiere;
	}
	
	public boolean isOnStation(Vector2f _position){
		return getRect().contains((int)_position.x, (int)_position.y);

	}
	
	
	@Override
	public void render(Graphics arg2) {
		// TODO Auto-generated method stub
	
		arg2.setColor(Color.darkGray);
		arg2.setLineWidth(1);
		Rectangle rec = new Rectangle(getRect().getX(), getRect().getY(), getRect().getWidth(), getRect().getHeight());
		arg2.setColor(new Color(238,238,238));
		arg2.fill(rec);
		arg2.draw(rec);
		
		float offset = 0;
		arg2.setLineWidth(4);
		for(Line l : Map.getInstance().getLines()){
			if(l.getStations().contains(this)){
				arg2.setColor(l.getColor());
				arg2.drawRect(getRect().getX()-offset*arg2.getLineWidth()/2, getRect().getY()-offset*arg2.getLineWidth()/2, getRect().getWidth()+offset*arg2.getLineWidth(), getRect().getHeight()+offset*arg2.getLineWidth());
				offset = offset + 2;
			}
		}
		arg2.setLineWidth(1);
		arg2.setColor(Color.darkGray);
		preview.draw(getPosition().x+Map.GRID_SIZE/2,getPosition().y+Map.GRID_SIZE/2,Map.GRID_SIZE,Map.GRID_SIZE);
		synchronized(waitingPassenger){
			for (Passenger passenger : waitingPassenger) {
				passenger.render(arg2);
			}
		}
		camembert.render(arg2);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		// TODO Auto-generated method stub
		super.update(gc, sbg,delta);
		
		cpt+=delta/Map.getInstance().gameSpeed ;
		float cptCamembert;
		cpt+=delta ;
		if(waitedTime == 0){
			cptCamembert = 0;
		}else{
			cptCamembert = cpt;
		}
		camembert.setPercentage((waitedTime+cptCamembert/1000)*100/MAX_WAITING_TIME);
		if(cpt>1000){
			if(isCriticalPassenger()){
				waitedTime ++; 
				checkWaitingTime();
			}else{
				if(waitedTime > 0 ) waitedTime --;
			}
			cpt = 0;
		}
	}
	
	private float calculateDistance(Station d){
		float weight = -1;
		for(Line l : Map.getInstance().getLines()){
			
			for(Segment s : l.getSegments()){
				if(s.getStationDepart() == this && s.getStationArrival()==d || s.getStationDepart() == d && s.getStationArrival()==this){
					weight = s.getLength();
				}
			}
		}
		return weight;
	}
	
	@Override
	public float calculateWeight(Dijkstrable d){

		return this.calculateDistance((Station) d);
	}

	@Override
	public boolean isConnected(Dijkstrable d) {
		return (calculateWeight(d) != -1);
	}
}
