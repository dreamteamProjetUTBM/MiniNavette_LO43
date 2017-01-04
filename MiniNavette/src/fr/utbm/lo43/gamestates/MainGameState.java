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
import fr.utbm.lo43.entities.EventEntityMouseClicked;
import fr.utbm.lo43.entities.Passenger;
import fr.utbm.lo43.entities.Station;
import fr.utbm.lo43.entities.ToggledButton;
import fr.utbm.lo43.logic.ClassicLine;
import fr.utbm.lo43.logic.Filiere;
import fr.utbm.lo43.logic.Game;
import fr.utbm.lo43.logic.Map;

public class MainGameState extends BasicGameState
{

	Game game;
	int counter = 0;
	EntityCollection entities;
	
	Rectangle menu_inventary;
	ArrayList<ToggledButton> lines_button;
	int current_line;
	
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

		for (fr.utbm.lo43.logic.Line line : Map.getInstance().getLines()) {
			entities.add(line);
		}

		
		for(int i = 0 ; i < 6 ; i++){
			ToggledButton line_b = new ToggledButton(new Vector2f(400+48*i,720-36),new Vector2f(24,24),"asset/lines1_idle.png","asset/lines1_hover.png","asset/lines1_pressed.png");
			lines_button.add(line_b);
			if(i == 0){
				line_b.setToggled(true);
			}
			line_b.setEventCallback(new EventEntityMouseClicked() {
				@Override
				public void mouseClicked() {
					for (ToggledButton toggledButton : lines_button) {
						toggledButton.setToggled(false);
					}
					line_b.setToggled(!line_b.getToggled());
					current_line = lines_button.indexOf(line_b);
				}
			});
			entities.add(line_b);

		}
	
		Station station1 = new Station(new Vector2f(33*24,12*24),Filiere.GI);
		Station station2 = new Station(new Vector2f(20*24,20*24),Filiere.EDIM);
		Station station3 = new Station(new Vector2f(37*24,26*24),Filiere.IMSI);
		
		Map.getInstance().addStation(station1);
		Map.getInstance().addStation(station2);
		Map.getInstance().addStation(station3);

		entities.add(station1);
		entities.add(station2);
		entities.add(station3);

		
		current_line = 0;
		
		bus_test = new ClassicBus(new org.newdawn.slick.geom.Vector2f(50,50));
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException 
	{
		
		arg2.setColor(new Color(238,238,238));
		arg2.setLineWidth(1);
		for (Line grid_line : game.map.grid) {
			arg2.draw(grid_line);
		}
		
		arg2.setColor(Color.gray);
		arg2.draw(menu_inventary);
		arg2.fill(menu_inventary);
		
		entities.render(arg2);

		/*
		for (Button button : lines_button) {
			button.render(arg2);
		}
		
		for (Station stat : game.map.stations) {
			stat.render(arg2);
		}
		
		for (fr.utbm.lo43.logic.Line line : Map.getInstance().getLines()) {
			line.render(arg2);
		}
		
		
		
		bus_test.render(arg2);*/
		

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException 
	{
		entities.update(arg0, arg1);
		Random rand = new Random();
		counter += arg2;
				
		
		if(counter >5000){
			entities.add(game.map.getStations().get(rand.nextInt(game.map.getStationsLenght())).newPassenger());
			counter = 0;
		}
		
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}

}
