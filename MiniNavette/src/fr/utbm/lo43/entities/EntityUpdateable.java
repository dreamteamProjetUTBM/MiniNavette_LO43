package fr.utbm.lo43.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public interface EntityUpdateable 
{
	/**
	 * Cette m�thode est vou� � contenir la logique des entit�s,
	 * c'est � dire tout ce qui ne rel�ve pas d'un affichage
	 * @param gc  le conteneur de jeu
	 * @param sbg le game state
	 */
	void update(GameContainer gc, StateBasedGame sbg, int delta);
}
