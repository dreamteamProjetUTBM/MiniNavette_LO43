package fr.utbm.lo43.entities;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.logic.Map;

/**
 * 
 * @author Thomas Gredin
 *
 * Classe EntityCollection
 * Est une classe qui permet de rassembler les entités, de mettre
 * à jour leur logique et de les rendre. Cette classe existe dans le but
 * d'allèger le MainGameState qui est déjà conséquent.
 */
public class EntityCollection implements EntityUpdateable, EntityDrawable
{
	ArrayList<Entity> entities;
	
	public EntityCollection()
	{
		entities = new ArrayList<Entity>();
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	public void add(Entity _entity)
	{
		entities.add(_entity);
	}
	
	public void addAt(Entity _entity, int index){
		entities.add(index, _entity);
	}
	
	public boolean delete(Entity _entity)
	{			
		return entities.remove(_entity);
		
	}

	/**
	 * Permet de supprimer avec une ï¿½galitï¿½ profonde, contrairement a delete (qui utilise la methode equals pour comparer les objets)
	 * @param _entity
	 * @return
	 */
	public boolean deleteObject(Entity _entity){
		for(int i = 0; i<entities.size(); ++i){
			if(entities.get(i) == _entity){
				entities.remove(i);
				return true;
			}
		}
		return false;
	}
	@Override
	public void render(Graphics arg2) 
	{
		for(Entity entitie : entities)
		{
			if(entitie.isDrawable())
			{
				((EntityDrawable)entitie).render(arg2);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg,int delta) 
	{
		for(Entity entitie : entities)
		{
			if(entitie.isUpdateble())
			{
				((EntityUpdateable)entitie).update(gc, sbg, delta*Map.getInstance().gameSpeed);;
			}
		}
	}

}
