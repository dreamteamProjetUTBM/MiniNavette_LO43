package fr.utbm.lo43.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author Thomas Gredin
 *
 * Interface EntityUpdateable
 * Permet � une entit� de poss�der une logique.
 */
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
