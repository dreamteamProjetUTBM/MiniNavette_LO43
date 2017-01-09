package fr.utbm.lo43.gamestates;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.GameWindow;
import fr.utbm.lo43.entities.Bus;
import fr.utbm.lo43.entities.ClassicBus;
import fr.utbm.lo43.entities.Entity;
import fr.utbm.lo43.entities.EntityCollection;
import fr.utbm.lo43.entities.EventEntityMouseClicked;
import fr.utbm.lo43.entities.Label;
import fr.utbm.lo43.entities.RailWay;
import fr.utbm.lo43.entities.Segment;
import fr.utbm.lo43.entities.Station;
import fr.utbm.lo43.entities.ToggledButton;
import fr.utbm.lo43.logic.ClassicLine;
import fr.utbm.lo43.logic.Filiere;
import fr.utbm.lo43.logic.Game;
import fr.utbm.lo43.logic.Inventory;
import fr.utbm.lo43.logic.Map;
import fr.utbm.lo43.logic.Score;

public class MainGameState extends BasicGameState {

	Game game;
	int counter = 0;
	
	public EntityCollection entities;

	Image menu_inventory;
	ArrayList<ToggledButton> lines_button;
	ArrayList<String> lines_button_img;
	// Bus button
	private ToggledButton bus_button;
	private Label bus_label;
	private Label bridge_label;
	private Label score_label;

	int current_line;

	ClassicBus bus_test;

	private Segment segmentTemp = null;
	private boolean editLine;
	private Vector2f drag_station_position;

	//private RailWay railWay;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		game = new Game();
		entities = new EntityCollection();

		editLine = false;

		menu_inventory = new Image("asset/info_barre.png");
		//new Rectangle(0, Map.HEIGHT - Map.GRID_SIZE * 1.5f, Map.WIDTH, Map.GRID_SIZE * 1.5f);
				
		lines_button = new ArrayList<ToggledButton>();
		lines_button_img = new ArrayList<String>();

		lines_button_img.add("asset/lines_blue_idle.png");
		lines_button_img.add("asset/lines_blue_hover.png");
		lines_button_img.add("asset/lines_blue_pressed.png");

		lines_button_img.add("asset/lines_red_idle.png");
		lines_button_img.add("asset/lines_red_hover.png");
		lines_button_img.add("asset/lines_red_pressed.png");

		lines_button_img.add("asset/lines_yellow_idle.png");
		lines_button_img.add("asset/lines_yellow_hover.png");
		lines_button_img.add("asset/lines_yellow_pressed.png");

		lines_button_img.add("asset/lines_green_idle.png");
		lines_button_img.add("asset/lines_green_hover.png");
		lines_button_img.add("asset/lines_green_pressed.png");

		lines_button_img.add("asset/lines_orange_idle.png");
		lines_button_img.add("asset/lines_orange_hover.png");
		lines_button_img.add("asset/lines_orange_pressed.png");

		lines_button_img.add("asset/lines_purple_idle.png");
		lines_button_img.add("asset/lines_purple_hover.png");
		lines_button_img.add("asset/lines_purple_pressed.png");

		Map.getInstance().AddLine(new ClassicLine(Color.blue));
		Map.getInstance().AddLine(new ClassicLine(Color.red));
		Map.getInstance().AddLine(new ClassicLine(Color.yellow));
		Map.getInstance().AddLine(new ClassicLine(Color.green));
		Map.getInstance().AddLine(new ClassicLine(Color.orange));
		Map.getInstance().AddLine(new ClassicLine(Color.magenta));

		for (fr.utbm.lo43.logic.Line line : Map.getInstance().getLines()) {
			entities.add(line);
		}

		for (int i = 0; i < 6; i++) {
			ToggledButton line_b = new ToggledButton(
					new Vector2f(Map.WIDTH / 2 - 3 * Map.GRID_SIZE + Map.GRID_SIZE * i + 5 * i,
							Map.HEIGHT - Map.GRID_SIZE - Map.GRID_SIZE / 5),
					new Vector2f(Map.GRID_SIZE, Map.GRID_SIZE), lines_button_img.get((i * 3)),
					lines_button_img.get((i * 3) + 1), lines_button_img.get((i * 3) + 2));

			lines_button.add(line_b);
			if (i == 0) {
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

			Map.getInstance().railWay = new RailWay();
		}

		bus_button = new ToggledButton(new Vector2f(Map.GRID_SIZE / 2, Map.HEIGHT - Map.GRID_SIZE * 1.6f),
				new Vector2f(Map.GRID_SIZE * 2, Map.GRID_SIZE * 2), "asset/bus_b_idle.png", "asset/bus_b_hover.png",
				"asset/bus_b_pressed.png");


		
		bus_label = new Label(Integer.toString(game.getInventory().getRemainingBus()),
				new Vector2f(107, Map.HEIGHT - Map.GRID_SIZE / 1.1f));
		bus_label.setColor(Color.white);

		bridge_label = new Label(Integer.toString(game.getInventory().getRemainingBridges()),
				new Vector2f(274, Map.HEIGHT - Map.GRID_SIZE / 1.1f));
		bridge_label.setColor(Color.white);
		
		score_label = new Label(Integer.toString(Score.getInstance().getScore()),
				new Vector2f(Map.WIDTH - 102, Map.HEIGHT - Map.GRID_SIZE / 1.1f));
		score_label.setColor(Color.white);
		
		bus_button.setEventCallback(new EventEntityMouseClicked() {

			@Override
			public void mouseClicked() {
				bus_button.setToggled(!bus_button.getToggled());
			}
		});
		entities.add(bus_button);
		entities.add(bus_label);
		entities.add(bridge_label);
		entities.add(score_label);
		
		
		Station station1 = new Station(new Vector2f(10 * Map.GRID_SIZE * 2, 3 * Map.GRID_SIZE * 2), Filiere.GI);
		Station station2 = new Station(new Vector2f(5 * Map.GRID_SIZE * 2, 5 * Map.GRID_SIZE * 2), Filiere.EDIM);
		Station station3 = new Station(new Vector2f(7 * Map.GRID_SIZE * 2, 7 * Map.GRID_SIZE * 2), Filiere.IMSI);
		Station station4 = new Station(new Vector2f(3 * Map.GRID_SIZE * 2, 8 * Map.GRID_SIZE * 2), Filiere.GI);
		Station station5 = new Station(new Vector2f(13 * Map.GRID_SIZE * 2, 8 * Map.GRID_SIZE * 2), Filiere.ENERGIE);

		Map.getInstance().addStation(station1);
		Map.getInstance().addStation(station2);
		Map.getInstance().addStation(station3);
		Map.getInstance().addStation(station4);
		Map.getInstance().addStation(station5);

		entities.add(station1);
		entities.add(station2);
		entities.add(station3);
		entities.add(station4);
		entities.add(station5);

		current_line = 0;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		arg2.setColor(new Color(238, 238, 238));
		arg2.setLineWidth(1);
		for (Line grid_line : game.map.grid) {
			arg2.draw(grid_line);
		}

		Map.getInstance().railWay.render(arg2);

		arg2.setColor(Color.gray);
		menu_inventory.draw(0, Map.HEIGHT - Map.GRID_SIZE * 1.5f);

		entities.render(arg2);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

		entities.update(arg0, arg1, arg2);
		
		
		Random rand = new Random();
		counter += arg2;

		bus_label.setText(game.getInventory().getRemainingBus() + "");
		bridge_label.setText(game.getInventory().getRemainingBridges() + "");
		score_label.setText(Score.getInstance().getScore() + "");
		
		fr.utbm.lo43.logic.Line _line = Map.getInstance().getLine(current_line);

		if (counter > 5000) {
			int index_station = rand.nextInt(game.map.getStationsLenght());
			while (!game.map.getStations().get(index_station).canAddPassenger())
				index_station = rand.nextInt(game.map.getStationsLenght());
			game.map.getStations().get(index_station).newPassenger();
			counter = 0;
		}

		for (Entity _entity : entities.getEntities()) {

			if(_entity.getClass() == ClassicBus.class){
				ClassicBus _tmp = (ClassicBus) _entity;
				//System.out.println("Bus: y'en a un");
				if(_tmp.canBeRemove()){
					System.out.println("suppr");
					entities.deleteObject(_tmp);
				}
				break;
			}
			
		}
		
		Input input = arg0.getInput();

		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			System.out.println("Pause");
			arg1.enterState(GameWindow.GS_PAUSE_MENU);
		}

		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && bus_button.getToggled()) {
			for (fr.utbm.lo43.logic.Line line : game.map.getInstance().getLines()) {
				for (Segment segment : line.getSegments()) {

					for(int i = -10; i < 11; i++){
						for(int j = -10; j < 11 ; j++){
							int mouseX = (input.getMouseX())+i;
							int mouseY = (input.getMouseY())+j;
							
							if(segment.isOnSegment(new Vector2f(mouseX,mouseY)) && game.getInventory().getRemainingBus() > 0){
								//Alors on ajoute un bus sur le segment
								bus_button.setToggled(false);
								game.getInventory().setRemainingBus(-1);

								entities.add(new ClassicBus(new Vector2f(mouseX,mouseY),
										game.map.getLine(segment.getLineIndex()).getColor(), segment));
								return;
							}
						}
					}
					
				}
			}
		}

		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {

			Vector2f _position = new Vector2f(input.getMouseX(), input.getMouseY());
			if (!editLine) {

				for (Station station : Map.getInstance().getStations()) {
					if (station.isOnStation(_position)) {

						editLine = true;
						// On resize sur le centre de la case
						drag_station_position = new Vector2f(station.getPosition().x + Map.GRID_SIZE,
								station.getPosition().y + Map.GRID_SIZE);

					}
				}

			} else {

				entities.deleteObject(segmentTemp);
				boolean canAdd = false;
				Vector2f _positionFin = new Vector2f(input.getMouseX(), input.getMouseY());
				for (Station station : Map.getInstance().getStations()) {
					if (station.isOnStation(_position) && !station.isOnStation(drag_station_position)) {
						_positionFin = new Vector2f(station.getPosition().x + Map.GRID_SIZE,
								station.getPosition().y + Map.GRID_SIZE);
						canAdd = true;

					}
				}

				Segment previsualizedSegment = new Segment(drag_station_position, _positionFin, current_line);
				int indexLine = _line.canAddSegment(previsualizedSegment);

				if (_line.canAddSegment(previsualizedSegment) == -1) {
					canAdd = false;
				}
				if (_line.canRemove(previsualizedSegment)) {

					previsualizedSegment.setIcon("asset/poubelle.png");
					canAdd = false;
				}

				if (_line.canCreateSegment(_positionFin) && _line.canCreateSegment(drag_station_position)) {
					if (indexLine == 0 || indexLine == _line.getSegments().size()) {
						if (indexLine == 0) {
							previsualizedSegment = previsualizedSegment.reverse();
						}

					}

				} else {
					canAdd = false;
				}

				if (canAdd) {
					previsualizedSegment.setIcon("asset/add.png");
				}

				for (fr.utbm.lo43.logic.Line line : Map.getInstance().getLines()) {
					if (line.isSegmentCrossingLine(previsualizedSegment)) {

						previsualizedSegment.setIcon("asset/forbidden.png");
					}
				}

				for (Segment s : _line.getSegments()) {
					if (previsualizedSegment.isReverse(s)) {
						previsualizedSegment = previsualizedSegment.reverse();
					}
				}
				if (_line.canRemove(previsualizedSegment)) {

					previsualizedSegment.setIcon("asset/poubelle.png");
				}else{
					if(previsualizedSegment.getBridges().size()>0 && Inventory.getInstance().getRemainingBridges() <= 0){
						previsualizedSegment.setForbiddenBridges(true);
					}
				}


				segmentTemp = previsualizedSegment;
		
				entities.addAt(previsualizedSegment, 0);
			}
		}

		else {

			if (editLine) {
				entities.deleteObject(segmentTemp);
				Vector2f _final = new Vector2f(input.getMouseX(), input.getMouseY());
				for (Station station : Map.getInstance().getStations()) {
					if (station.isOnStation(_final) && !station.isOnStation(drag_station_position)) {
						// On resize sur le centre de la case
						Vector2f _end = new Vector2f(station.getPosition().x + Map.GRID_SIZE,
								station.getPosition().y + Map.GRID_SIZE);
						Segment _segment = new Segment(drag_station_position, _end, current_line);
						int index = _line.canAddSegment(_segment);

						if (_line.canRemove(_segment)) {
							_line.removeSegment(_segment);
							entities.delete(_segment);
							//Suppr bus ici
							if(_segment.getBridges().size()>0){
								Inventory.getInstance().addBridge();
							}
						}

						boolean canContinue = true;

						for (fr.utbm.lo43.logic.Line line : Map.getInstance().getLines()) {
							if (line.isSegmentCrossingLine(_segment)
									&& line.isSegmentCrossingLine(_segment.reverse())) {

								canContinue = false;
							}
						}

						if (canContinue && _line.canCreateSegment(_end)
								&& _line.canCreateSegment(drag_station_position)) {
							if (index == 0 || index == _line.getSegments().size()) {
								if (index == 0) {
									_segment = _segment.reverse();
								}
								
								for (fr.utbm.lo43.logic.Line line : Map.getInstance().getLines()) {
									if (line.isSegmentCrossingLine(_segment)) {

										canContinue = false;
									}
								}
								if(canContinue){
									
									if(_segment.getBridges().size() > 0){
										if(Inventory.getInstance().getRemainingBridges()>0){
											_line.addSegment(_segment, index);
											entities.addAt(_segment, 0);
											Inventory.getInstance().takeBridge();
										}
									}else{
									_line.addSegment(_segment, index);
									entities.addAt(_segment, 0);
									}

								}
							}
						}

					}
				}
				editLine = false;

			}
		}

	}

	@Override
	public int getID() {
		return GameWindow.GS_GAME;
	}

}
