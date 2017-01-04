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
	
	public void addSegment(Segment _seg,int index){
		segments.add(index, _seg);
	}
	
	//only top and queue
	public boolean existingSemgent(Segment _seg){
		for (Segment segment : segments) {
			if(segment.equals(_seg))
				return true;
		}
		return false;
	}
	
	//Return l'index ou l'insérer ou -1 s'il ne peut pas, -2 s'il existe
	public int canAddSegment(Segment _segment){
		
		
		if(existingSemgent(_segment)){
			return -2;
		}

		if(segments.size() == 0)
			return 0;
		
		
		if(segments.size() > 0)
		{
			Segment first = segments.get(0), last = segments.get(segments.size()-1);

			if(first.getPositions().get(0).distance(_segment.getPositions().get(0)) == 0)
					return 0;
			else if(last.getPositions().get(1).distance(_segment.getPositions().get(0)) == 0 ||
					last.getPositions().get(1).distance(_segment.getPositions().get(1)) == 0)
				return segments.size();
			
		}
		
		return -1;
	}
	
	//En gros on peut créer un segment jusqu'à cette station
	//si cpt == 2 ca veut dire qu'un segment arrive et part de cette position
	public boolean canCreateSegment(Vector2f _vect)
	{
		int cpt = 0;
		System.out.println("Vec : " +_vect);
		for (Segment segment : segments) {
			System.out.println("1 : " +segment.getPositions().get(0));
			System.out.println("2 : " +segment.getPositions().get(1));

			if(segment.getPositions().get(0).distance(_vect) == 0 || segment.getPositions().get(1).distance(_vect) == 0)
				cpt++;
		}
		
		if(cpt > 1)
			return false;
		return true;
	}
	
	public boolean canRemove(Segment _seg){
		if(segments.size() == 0)
			return false;
		
		if(segments.get(segments.size()-1).equals(_seg))
			return true;
		else if(segments.get(0).equals(_seg))
			return true;
		return false;
	}
	
	public boolean removeSegment(Segment _seg){
		return segments.remove(_seg);
	}
	
}
