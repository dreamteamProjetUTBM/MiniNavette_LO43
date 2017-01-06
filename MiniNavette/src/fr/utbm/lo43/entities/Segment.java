package fr.utbm.lo43.entities;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.logic.Map;

public class Segment extends EntityDragable implements EntityDrawable
{
	
	
	private ArrayList<Station> stations;
	
	//Line de Slick2D
	Line line;
	fr.utbm.lo43.logic.Line line_bus;
	int lineIndex;
	
	public Segment(Vector2f _start, Vector2f _end,int index) 
	{
		super(_start);
		//line = new Line(_start, _end);
		line = new Line(_start.x, _start.y, _end.x, _end.y);
		lineIndex = index;
		dragedEvent = new EventEntityMouseDraged() {
			
			@Override
			public void mouseReleased() {
				// TODO Auto-generated method stub
				boolean notOnStation = true;
				for (Station station : Map.getInstance().getStations()) {
					if(station.position == line.getEnd())
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
	
	/***
	 * Get line number of the segment
	 * @return
	 */
	public int getLineIndex(){
		return lineIndex;
	}
	
	public Vector2f getStartSegment(){
		return new Vector2f(line.getX1(),line.getY1());
	}
	
	public Vector2f getEndSegment(){
		return new Vector2f(line.getX2(),line.getY2());
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
		Vector2f _start = new Vector2f(line.getX1(),line.getY1());
		Vector2f _end = new Vector2f(line.getX2(),line.getY2());

		_positions.add(_start);
		_positions.add(_end);
		return _positions;
	}

	@Override
	public void render(Graphics arg2) {
		
		int offset = 0;
		for (int i = lineIndex ; i < Map.getInstance().getLines().size(); i++) {
			fr.utbm.lo43.logic.Line _line = Map.getInstance().getLine(i);
			for (Segment segment : _line.getSegments()) {
				Vector2f _intersect = segment.line.intersect(line);
				if(_intersect == null && segment.hasSameVectors(this)){ // donc parallèle
					offset++;
				}
			}
		}
		
		arg2.setLineWidth(5);
		arg2.setColor(Map.getInstance().getLine(lineIndex).getColor());
		Line _linerender;
		if(line.getX1() == line.getX2())
			_linerender = new Line(new Vector2f(line.getX1()+5*offset, line.getY1()), new Vector2f(line.getX2()+5*offset, line.getY2()));
		else
			_linerender = new Line(new Vector2f(line.getX1(), line.getY1()+5*offset), new Vector2f(line.getX2(), line.getY2()+5*offset));
		arg2.draw(_linerender);
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
		Vector2f _intersect = line.intersect(_segment.line,true);
		
		if(_intersect == null)
			return false;
		
		if(!_intersect.equals(_segment.getStartSegment()) && !_intersect.equals(_segment.getEndSegment()))
			return true;
		//System.out.println(_segment.line.getpo);
		return false;
	}
	
	public boolean hasSameVectors(Segment _seg){
		if((_seg.getPositions().get(0).distance(getPositions().get(0)) == 0 &&
				_seg.getPositions().get(1).distance(getPositions().get(1)) == 0) ||
					(_seg.getPositions().get(0).distance(getPositions().get(1)) == 0 &&
				_seg.getPositions().get(1).distance(getPositions().get(0)) == 0)
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
				if((_obj.getPositions().get(0).distance(getPositions().get(0)) == 0 &&
				_obj.getPositions().get(1).distance(getPositions().get(1)) == 0) ||
				(_obj.getPositions().get(0).distance(getPositions().get(1)) == 0 &&
				_obj.getPositions().get(1).distance(getPositions().get(0)) == 0)
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
	public float getLenght(){
		return line.length();
	
	}
	
	public boolean isOnSegment(Vector2f _vector){
		return line.contains(_vector.x, _vector.y);
	}
}
