package fr.utbm.lo43.logic;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.entities.Segment;

public abstract class Line {

	protected Color color;
	
	private ArrayList<Segment> segments;
	
	public Line(Color _color){
		color = _color;
		segments = new ArrayList<>();
	}
	
	public ArrayList<Segment> getSegments(){
		return segments;
	}
	
	public void addSegment(Segment _seg){
		segments.add(_seg);
	}
	
	public void removeSegment(Segment _seg){
		segments.remove(_seg);
	}
	
	public void UpdateSegments(GameContainer gc, StateBasedGame sbg){
		for (Segment segment : segments) {
			segment.update(gc, sbg);
		}
	}
	
	public void RenderSegments(Graphics arg2){
		for (Segment segment : segments) {
			segment.render(arg2);
		}
	}
	
}
