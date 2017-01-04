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
	
	public void addSegment(Segment _seg){
		segments.add(_seg);
	}
	
	public boolean existingSemgent(Segment _seg){
		for (Segment segment : segments) {
			if(_seg.getPositions().get(0).distance(segment.getPositions().get(0)) ==0 && _seg.getPositions().get(1).distance(segment.getPositions().get(1)) ==0){
				return true;
			}
		}
		return false;
	}
	
	public void removeSegment(Segment _seg){
		segments.remove(_seg);
	}
	
}
