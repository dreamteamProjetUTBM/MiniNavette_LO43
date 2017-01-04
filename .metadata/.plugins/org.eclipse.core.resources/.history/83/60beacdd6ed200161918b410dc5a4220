package fr.utbm.lo43.gamestates;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.entities.ClassicBus;
import fr.utbm.lo43.entities.Segment;
import fr.utbm.lo43.entities.Station;
import fr.utbm.lo43.logic.ClassicLine;
import fr.utbm.lo43.logic.Filiere;
import fr.utbm.lo43.logic.Game;
import fr.utbm.lo43.logic.Map;

public class MainGameState extends BasicGameState
{

	Game game;
	int counter = 0;
	
	ClassicBus tes;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		game = new Game();
		tes = new ClassicBus(new org.newdawn.slick.geom.Vector2f(50,50));
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		
		arg2.setColor(new Color(238,238,238));
		for (Line grid_line : game.map.grid) {
			arg2.draw(grid_line);
		}
		
		for (Station stat : game.map.stations) {
			stat.render(arg2);
		}
		
		for (fr.utbm.lo43.logic.Line line : game.map.getLines()) {
			line.RenderSegments(arg2);
		}
		
		tes.render(arg2);
		

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		// TODO Auto-generated method stub
		Random rand = new Random();
		
		counter += arg2;
		
		if(counter % 500 > 0){
			tes.move();
			tes.update(arg0, arg1);
		}
		
		if(counter >5000){
			game.map.getStations().get(rand.nextInt(game.map.getStationsLenght())).newPassenger();
			counter = 0;
		}
		
		
		for (Station station : game.map.getStations()) {
			station.update(arg0, arg1);
		}
		
		for (fr.utbm.lo43.logic.Line line : game.map.getLines()) {
			line.UpdateSegments(arg0, arg1);
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}

}
