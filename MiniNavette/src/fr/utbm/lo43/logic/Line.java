package fr.utbm.lo43.logic;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.entities.Entity;
import fr.utbm.lo43.entities.EntityDrawable;
import fr.utbm.lo43.entities.EntityUpdateable;
import fr.utbm.lo43.entities.Segment;

public abstract class Line extends Entity implements EntityUpdateable, EntityDrawable {

	protected Color color;
	
	private ArrayList<Segment> segments;
	
	public Line(Color _color){
		super(new Vector2f());
		color = _color;
		segments = new ArrayList<>();
		updatable = false;
	}
	
	public Color getColor(){
		return color;
	}
	
	public ArrayList<Segment> getSegments(){
		return segments;
	}
	
	public Segment getSegment(int index){
		return segments.get(index);
	}
	
	public void addSegment(Segment _seg,int index){
		segments.add(index, _seg);
		Map.getInstance().calculateNextStopStations();
	}
	
	/**
	 * Verfifie si la ligne est une boucle
	 * @return
	 */
	public boolean isLoop(){
		
		return segments.get(0).getStartSegment().distance( segments.get(segments.size()-1).getEndSegment()) == 0 ;
		
	}
	//only top and queue
	public boolean existingSegment(Segment _seg){
		for (Segment segment : segments) {
			if(segment.equals(_seg))
				return true;
		}
		return false;
	}
	
	//Return l'index ou l'insérer ou -1 s'il ne peut pas, -2 s'il existe
	public int canAddSegment(Segment _segment){
		
		if(existingSegment(_segment)){
			return -2;
		}
		if(segments.size() == 0)
			return 0;
		
		if(segments.size() > 0)
		{
			Segment first = segments.get(0), last = segments.get(segments.size()-1);

			if(first.getStartSegment().distance(_segment.getStartSegment()) == 0)
					return 0;
			else if(last.getEndSegment().distance(_segment.getStartSegment()) == 0)
				return segments.size();
			
		}
		
		return -1;
	}
	
	//En gros on peut créer un segment jusqu'à cette station
	//si cpt == 2 ca veut dire qu'un segment arrive et part de cette position
	public boolean canCreateSegment(Vector2f _vect)
	{
		int cpt = 0;
		for (Segment segment : segments) {
			if(segment.getStartSegment().distance(_vect) == 0 || segment.getEndSegment().distance(_vect) == 0)
				cpt++;
		}
		
		if(cpt > 1)
			return false;
		return true;
	}
	
	public boolean canRemove(Segment _seg){
		
		
		if(!segments.contains(_seg)){
			return false;
		}
		
		if(segments.size() == 0)
			return false;
		
		
		if(segments.get(segments.size()-1).getLineIndex() == _seg.getLineIndex()){
			//if(segments.get(segments.size()-1).equals(_seg))
			if(segments.get(segments.size()-1).equals(_seg))
				return true;
			else if(segments.get(0).equals(_seg))
				return true;
		}
		return false;
	}
	
	public boolean removeSegment(Segment _seg){
		// en cas de boucle, on decale les segments jusqu'a ce que _seg devienne le premier ou le dernier element
		if(!segments.contains(_seg)){
			return false;
		}

		
		boolean result = segments.remove(_seg);
		if(result){
			Map.getInstance().calculateNextStopStations();
		}
		return result;
	}
	
	public boolean isSegmentCrossingLine(Segment _segment){

		for (Segment segment : segments) {
	
			if(segment.isCrossing(_segment)){
				return true;
			}

				
		}
		return false;
	}

	
}
