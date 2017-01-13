package fr.utbm.lo43.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author Thomas Gredin
 *
 * Interface EntityUpdateable
 * Permet à une entité de possèder une logique.
 */
public interface EntityUpdateable 
{
	/**
	 * Cette mï¿½thode est vouï¿½ ï¿½ contenir la logique des entitï¿½s,
	 * c'est ï¿½ dire tout ce qui ne relï¿½ve pas d'un affichage
	 * @param gc  le conteneur de jeu
	 * @param sbg le game state
	 */
	void update(GameContainer gc, StateBasedGame sbg, int delta);
}
