package fr.utbm.lo43.entities;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.logic.Map;

public class Segment extends EntityDragable implements EntityDrawable
{
	
	private Polygon polygon;
	private ArrayList<Station> stations;
	
	//Line de Slick2D
	Line line;
	fr.utbm.lo43.logic.Line line_bus;
	int lineIndex;
	
	public Segment(Vector2f _start, Vector2f _end,int index) 
	{
		super(_start);
		polygon = new Polygon();
		polygon.setClosed(false);
		polygon.addPoint(_start.x, _start.y);
		Vector2f angle = calculateAnglePosition(_start, _end);
		polygon.addPoint(angle.x, angle.y);
		polygon.addPoint(_end.x,_end.y);
		//line = new Line(_start, _end);
		//line = new Line(_start.x, _start.y, _end.x, _end.y);
		lineIndex = index;
		dragedEvent = new EventEntityMouseDraged() {
			
			@Override
			public void mouseReleased() {
				// TODO Auto-generated method stub
				boolean notOnStation = true;
				for (Station station : Map.getInstance().getStations()) {
					if(station.position == getEndSegment())
					{
						notOnStation = false;
					}
				}
				if(notOnStation)
				{
					//J'adore faire des méthodes comme ça
					Map.getInstance().getLine(lineIndex).removeSegment(Map.getInstance().getLine(lineIndex).getSegments().get(Map.getInstance().getLine(lineIndex).getSegments().size()-1));
				}
				System.out.println("Segment.Segment(...).new EventEntityMouseDraged() {...}.mouseReleased()");
			}
			
			@Override
			public void mousePressed() {
				// TODO Auto-generated method stub
				
				System.out.println("Segment.MousePresssed()");
			}
		};
		
		drawable = true;
		
	}
	
	
	public Vector2f getPointPolygon(int index){
		return new Vector2f(polygon.getPoint(index)[0], polygon.getPoint(index)[1]);
	}
	/***
	 * Get line number of the segment
	 * @return
	 */
	public int getLineIndex(){
		return lineIndex;
	}
	
	public Vector2f getStartSegment(){
		return getPointPolygon(0);
	}
	
	public Vector2f getEndSegment(){
		return getPointPolygon(polygon.getPointCount()-1);
	}
	
	/**
	 * Get all stations
	 * @return
	 */
	public ArrayList<Station> getStations()
	{
		return stations;
	}
	
	public ArrayList<Vector2f> getPositions(){
		ArrayList<Vector2f> _positions = new ArrayList<>();
		
		
		for(int i = 0; i<polygon.getPointCount();++i){
			_positions.add(new Vector2f(polygon.getPoint(i)[0], polygon.getPoint(i)[1]));
		}

		return _positions;
	}

	@Override
	public void render(Graphics arg2) {
		
		int offset = 0;
		for (int i = lineIndex ; i < Map.getInstance().getLines().size(); i++) {
			fr.utbm.lo43.logic.Line _line = Map.getInstance().getLine(i);
			for (Segment segment : _line.getSegments()) {
				if(segment.polygon.intersects(polygon) == false && segment.hasSameVectors(this)){ // donc parallèle
					offset++;
				}
			}
		}
		
		arg2.setLineWidth(5);
		arg2.setColor(Map.getInstance().getLine(lineIndex).getColor());
	

		Polygon _polygonrender = new Polygon();
		_polygonrender.setClosed(false);
		if(getStartSegment().x == getEndSegment().x){
			
			
			for(int i = 0; i<polygon.getPointCount();++i){
				_polygonrender.addPoint(polygon.getPoint(i)[0]+5*offset, polygon.getPoint(i)[1]);
		
			}
		}
		else
		{
			for(int i = 0; i<polygon.getPointCount();++i){
				_polygonrender.addPoint(polygon.getPoint(i)[0], polygon.getPoint(i)[1]+5*offset);
		
			}
		}
		arg2.draw(_polygonrender);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg,int delta)
	{
		super.update(gc, sbg,delta);
		Input input = gc.getInput();

		
		if(getRect().contains(input.getMouseX(), input.getMouseY()) && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{

			if(isGrabed == true)
			{				
				position.x = input.getMouseX() - (size.x / 2.0f);
				position.y = input.getMouseY() - (size.y / 2.0f);
				if(dragedEvent != null)
					dragedEvent.mousePressed();
			}
			else
			{				
				isGrabed = true;
			}
		}
		else
		{

			if(isGrabed != false)
			{
				if(dragedEvent != null)
					dragedEvent.mouseReleased();
				isGrabed = false;
			}
		}
	}

	
	/***
	 * To know if a segment cross another one
	 * @param _segment Segment to compare
	 * @return true if the segment cross the line
	 * false if not
	 */
	public boolean isCrossing(Segment _segment){
		boolean _intersect = polygon.intersects(_segment.polygon);
		
		if(_intersect == false){
			return false;
		}else
		{
			if(isOnSegment(_segment.getEndSegment()) || isOnSegment(_segment.getStartSegment())){
				return false;
			
			}
		return true;	
		}
		

		//System.out.println(_segment.line.getpo);
	}
	
	public boolean hasSameVectors(Segment _seg){
		if((_seg.getStartSegment().distance(getStartSegment()) == 0 &&
		_seg.getEndSegment().distance(getEndSegment()) == 0) ||
		(_seg.getStartSegment().distance(getEndSegment()) == 0 &&
		_seg.getEndSegment().distance(getStartSegment()) == 0)
		)
			return true;
		return false;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub		
		if(obj.getClass() != getClass())
			return false;
		
		Segment _obj = (Segment) obj;
		
		if(obj == this)
			return true;
		
		else 
			if(_obj.getLineIndex() == lineIndex)
				if((_obj.getStartSegment().distance(getStartSegment()) == 0 &&
				_obj.getEndSegment().distance(getEndSegment()) == 0) ||
				(_obj.getStartSegment().distance(getEndSegment()) == 0 &&
				_obj.getEndSegment().distance(getStartSegment()) == 0)
				){
					return true;
				}

		return false;
	}
	
	//moi j'aime bien faire des methodes comme ça aussi
	public Segment getNextSegment()
	{
		return (line_bus.getSegments().indexOf(this)+1) <= ( line_bus.getSegments().size() ) ? line_bus.getSegments().get(line_bus.getSegments().indexOf(this)+1) : null ;
	}
	
	public Segment getPreviousSegment()
	{
		return (line_bus.getSegments().indexOf(this)-1) > 0 ? line_bus.getSegments().get(line_bus.getSegments().indexOf(this)+1) : null ;
	}
	
	/**
	 * Retourne la longueur d'un segment
	 * @return
	 */
	public float getLength(){
		float length = 0;
		for(int i = 0; i<getPositions().size()-1;++i){
			length += getPositions().get(i).distance(getPositions().get(i+1));
		}
		return length;
	}
	
	public boolean isOnSegment(Vector2f _vector){
		Line _line;
		for(int i = 0; i<getPositions().size()-1; ++i ){
			_line = new Line(getPositions().get(i), getPositions().get(i+1));
			if(_line.on(_vector)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Calcule la position de l'angle du segment qui relie 2 positions
	 * @param position1
	 * @param position2
	 * @return
	 */
	public static Vector2f calculateAnglePosition(Vector2f position1, Vector2f position2){
		Vector2f anglePosition;
		float distancePosition1;
		float distancePosition2;
		Line lineTemp1 = new Line(position1.x, position1.y, 0, 1, true);
		Line lineTemp2 = new Line(position2.x, position2.y, 1, 1, true);
	
		distancePosition1 = position1.distance(lineTemp1.intersect(lineTemp2));
		distancePosition2 = position2.distance(lineTemp1.intersect(lineTemp2));
	
		anglePosition = lineTemp1.intersect(lineTemp2);
		
		lineTemp1 = new Line(position1.x, position1.y, 1, 0, true);
		lineTemp2 = new Line(position2.x, position2.y, 1, 1, true);
		if(distancePosition1>position1.distance(lineTemp1.intersect(lineTemp2))){
			anglePosition = lineTemp1.intersect(lineTemp2);
			distancePosition1 = position1.distance(lineTemp1.intersect(lineTemp2));
			distancePosition2 = position2.distance(lineTemp1.intersect(lineTemp2));
		}else if(distancePosition1==position1.distance(lineTemp1.intersect(lineTemp2))){
			if(distancePosition2>position2.distance(lineTemp1.intersect(lineTemp2))){
				anglePosition = lineTemp1.intersect(lineTemp2);
				distancePosition1 = position1.distance(lineTemp1.intersect(lineTemp2));
				distancePosition2 = position2.distance(lineTemp1.intersect(lineTemp2));
			}
		}
		
		lineTemp1 = new Line(position1.x, position1.y, 0, 1, true);
		lineTemp2 = new Line(position2.x, position2.y, -1, 1, true);	
		if(distancePosition1>position1.distance(lineTemp1.intersect(lineTemp2))){
			anglePosition = lineTemp1.intersect(lineTemp2);
			distancePosition1 = position1.distance(lineTemp1.intersect(lineTemp2));
			distancePosition2 = position2.distance(lineTemp1.intersect(lineTemp2));
		}else if(distancePosition1==position1.distance(lineTemp1.intersect(lineTemp2))){
			if(distancePosition2>position2.distance(lineTemp1.intersect(lineTemp2))){
				anglePosition = lineTemp1.intersect(lineTemp2);
				distancePosition1 = position1.distance(lineTemp1.intersect(lineTemp2));
				distancePosition2 = position2.distance(lineTemp1.intersect(lineTemp2));
			}
		}
		
		lineTemp1 = new Line(position1.x, position1.y, 1, 0, true);
		lineTemp2 = new Line(position2.x, position2.y, -1, 1, true);	
		if(distancePosition1>position1.distance(lineTemp1.intersect(lineTemp2))){
			anglePosition = lineTemp1.intersect(lineTemp2);
			distancePosition1 = position1.distance(lineTemp1.intersect(lineTemp2));
			distancePosition2 = position2.distance(lineTemp1.intersect(lineTemp2));
		}else if(distancePosition1==position1.distance(lineTemp1.intersect(lineTemp2))){
			if(distancePosition2>position2.distance(lineTemp1.intersect(lineTemp2))){
				anglePosition = lineTemp1.intersect(lineTemp2);
				distancePosition1 = position1.distance(lineTemp1.intersect(lineTemp2));
				distancePosition2 = position2.distance(lineTemp1.intersect(lineTemp2));
			}
		}
		return anglePosition;
	}
	
}