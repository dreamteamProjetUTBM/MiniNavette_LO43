package fr.utbm.lo43.logic;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.entities.Segment;
import fr.utbm.lo43.logic.Line;

public class ClassicLine extends Line{

	
	
	public ClassicLine(Color _color){
		super(_color);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg) {
		for (Segment segment : getSegments()) {
			segment.update(gc, sbg);
		}
	}

	@Override
	public void render(Graphics arg2) {
		for (Segment segment : getSegments()) {
			segment.render(arg2);
		}
	}
	
}