package fr.utbm.lo43.entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class Button extends EntityClickable implements EntityDrawable 
{
	public Button(Vector2f _position, Vector2f _size) {
		super(_position, _size);
		
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg)
	{
		super.update(gc, sbg);
		
		/**
		 * Logique de changement d'image suivant la position de la souris
		 */
	}
}
