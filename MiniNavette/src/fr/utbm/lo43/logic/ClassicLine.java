package fr.utbm.lo43.logic;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.entities.Segment;
import fr.utbm.lo43.logic.Line;

/**
 * @author roger Lignes sur lesquelles se déplacent les bus classiques.
 */
public class ClassicLine extends Line {

	public ClassicLine(Color _color) {
		super(_color);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		for (Segment segment : getSegments()) {
			segment.update(gc, sbg, delta);
		}
	}

	@Override
	public void render(Graphics arg2) {
		for (Segment segment : getSegments()) {
			segment.render(arg2);
		}
	}

}
