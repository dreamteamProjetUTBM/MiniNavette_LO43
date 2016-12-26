package fr.utbm.lo43.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public interface EntityUpdateable 
{
	void update(GameContainer gc, StateBasedGame sbg);
}
