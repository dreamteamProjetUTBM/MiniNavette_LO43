package fr.utbm.lo43.gamestates;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.entities.Button;
import fr.utbm.lo43.entities.ClassicBus;
import fr.utbm.lo43.entities.EntityCollection;
import fr.utbm.lo43.entities.Station;
import fr.utbm.lo43.logic.ClassicLine;
import fr.utbm.lo43.logic.Game;
import fr.utbm.lo43.logic.Map;

public class MainGameState extends BasicGameState
{

	Game game;
	int counter = 0;
	EntityCollection entities;
	
	Rectangle menu_inventary;
	ArrayList<Button> lines_button;
	fr.utbm.lo43.logic.Line current_line;
	
	ClassicBus bus_test;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException 
	{
		game = new Game();
		entities = new EntityCollection();
		
		menu_inventary= new Rectangle(0, 720-24*2, 1080, 24*2);
		lines_button = new ArrayList<>();
		
		Map.getInstance().AddLine(new ClassicLine(Color.red));
		Map.getInstance().AddLine(new ClassicLine(Color.green));
		Map.getInstance().AddLine(new ClassicLine(Color.blue));
		Map.getInstance().AddLine(new ClassicLine(Color.yellow));
		Map.getInstance().AddLine(new ClassicLine(Color.orange));
		Map.getInstance().AddLine(new ClassicLine(Color.pink));

		
		for(int i = 0; i < 6; i++ ){
			lines_button.add(new Button(new Vector2f(400+48*i,720-36),24,24,"asset/lines1_idle.png","asset/lines1_hover.png","asset/lines1_pressed.png"));
		}
		current_line = Map.getInstance().getLine(0);
		
		bus_test = new ClassicBus(new org.newdawn.slick.geom.Vector2f(50,50));
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException 
	{
		entities.render(arg2);
		
		arg2.setColor(new Color(238,238,238));
		for (Line grid_line : game.map.grid) {
			arg2.draw(grid_line);
		}
		
		arg2.setColor(Color.gray);
		arg2.draw(menu_inventary);
		arg2.fill(menu_inventary);
		
		for (Button button : lines_button) {
			button.render(arg2);
		}
		
		for (Station stat : game.map.stations) {
			stat.render(arg2);
		}
		
		for (fr.utbm.lo43.logic.Line line : game.map.getLines()) {
			line.RenderSegments(arg2);
		}
		
		
		
		bus_test.render(arg2);
		

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException 
	{
		entities.update(gc, sbg);
		Random rand = new Random();
		
		counter += arg2;
		
		for (Button button : lines_button) {
			button.update(arg0, arg1);
			
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
