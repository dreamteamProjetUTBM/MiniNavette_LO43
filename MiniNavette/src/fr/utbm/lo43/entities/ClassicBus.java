package fr.utbm.lo43.entities;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.gamestates.MainGameState;
import fr.utbm.lo43.logic.Inventory;
import fr.utbm.lo43.logic.Line;
import fr.utbm.lo43.logic.Map;

/**
 * @author roger
 *
 */
public class ClassicBus extends Bus {

	/**
	 * Start désigne le vecteur de départ, et end celui ou le bus
	 * doitd'arriver
	 */
	private volatile Vector2f start, end;

	/**
	 * Permet de connaitre la direction du bus
	 */
	int local_direction = -1;

	/**
	 * Si le bus est bloqué, il sera à True, sinon il sera à False
	 */
	boolean lock = false;

	/**
	 * Si le bus doit être supprimé, il sera à True
	 */
	volatile boolean canBeRemove = false;

	/**
	 * Contient l'angle de rotation du bus
	 */
	private float theta;

	/**
	 * Booleen
	 */
	private boolean canBeKilled;

	/**
	 * Compteur de temps du bus
	 */
	private int cpt = 0;

	/**
	 * Constructeur d'un bus de type classique
	 * 
	 * @param _position
	 * @param _color
	 * @param current
	 */
	public ClassicBus(Vector2f _position, Color _color, Segment current) {
		super(_position, _color);
		direction = true;
		capacity = 6;
		currentSegment = current;
		canBeKilled = false;

		theta = getAngle();
		polygon = (Polygon) polygon.transform(
				Transform.createRotateTransform((float) Math.toRadians(-getAngle()), getPosition().x, getPosition().y));

	}

	/**
	 * Permet de calculer l'angle de rotation du bus selon le segment ou il se
	 * trouve
	 */
	public synchronized float getAngle() {
		ArrayList<Vector2f> vects = currentSegment.isBetween(getPosition());
		float theta_bis = 0;
		Vector2f _start, _end;
		if (vects.size() > 0) {
			if (vects.get(0) == null || vects.get(1) == null)
				return 0;

			if (vects.size() == 4 && direction) {
				_start = vects.get(2);
				_end = vects.get(3);

			} else {
				if (direction) {
					_start = vects.get(0);
					_end = vects.get(1);
				} else {
					_start = vects.get(1);
					_end = vects.get(0);
				}
			}

			org.newdawn.slick.geom.Line line = new org.newdawn.slick.geom.Line(_start, _end);
			theta_bis = (float) Math.atan2(line.getDX(), line.getDY());
			theta_bis *= 180 / Math.PI;
		}
		return theta_bis;
	}

	@Override
	public synchronized void render(Graphics arg2)

	{
		Color colorTemp = new Color(color);
		if (lock) {
			colorTemp.a = 0.75f;
		}
		arg2.setColor(colorTemp);

		arg2.fill(polygon);
		arg2.draw(polygon);

		// Permet d'afficher les passagers à des emplacements différents
		if (passenger_images.size() > 0)
			passenger_images.get(0).draw(getPosition().x - Map.GRID_SIZE / 2, getPosition().y - Map.GRID_SIZE / 2,
					Map.GRID_SIZE / 2, Map.GRID_SIZE / 2);
		if (passenger_images.size() > 1)
			passenger_images.get(1).draw(getPosition().x - Map.GRID_SIZE / 2, getPosition().y, Map.GRID_SIZE / 2,
					Map.GRID_SIZE / 2);
		if (passenger_images.size() > 2)
			passenger_images.get(2).draw(getPosition().x, getPosition().y - Map.GRID_SIZE / 2, Map.GRID_SIZE / 2,
					Map.GRID_SIZE / 2);
		if (passenger_images.size() > 3)
			passenger_images.get(3).draw(getPosition().x, getPosition().y, Map.GRID_SIZE / 2, Map.GRID_SIZE / 2);
		if (passenger_images.size() > 4)
			passenger_images.get(4).draw(getPosition().x + Map.GRID_SIZE / 2, getPosition().y - Map.GRID_SIZE / 2,
					Map.GRID_SIZE / 2, Map.GRID_SIZE / 2);
		if (passenger_images.size() > 5)
			passenger_images.get(5).draw(getPosition().x + Map.GRID_SIZE / 2, getPosition().y, Map.GRID_SIZE / 2,
					Map.GRID_SIZE / 2);

	}

	/**
	 * Permet de savoir si le bus peut être supprimer
	 */
	public boolean CanBeKilled() {
		return canBeKilled;
	}

	@Override
	protected void move() {
		if (!currentSegment.line_bus.existingSegment(currentSegment)
				&& currentSegment.line_bus.getSegments().size() == 0)
			lock = true;

		for (Vector2f endpoint : currentSegment.getPositions()) {
			if (endpoint.distance(getPosition()) == 0 && (endpoint.equals(currentSegment.getEndSegment())
					|| endpoint.equals(currentSegment.getStartSegment()))) {
				// Alors on est arrivé soit dans une station soit à la fin
				// d'une partie du segment
				for (Station station : Map.getInstance().getStations()) {
					if (station.isOnStation(endpoint)) {

						if (lock) {

							ArrayList<Passenger> copy = new ArrayList<Passenger>(passengers);

							for (Passenger passenger : copy) {
								passenger.leaveBus(station);
								removePassenger(passenger);
							}

							canBeRemove = true;
							canBeKilled = true;
							return;
						}
						nextSegment();
						station.notifyBus(this);
						System.out.println("Bus a maintenant " + passengers.size() + " passager(s).");
						break;
					}
				}
			}
		}

		// On avance
		ArrayList<Vector2f> vects = currentSegment.isBetween(getPosition());

		if (vects.size() > 0) {
			if (vects.size() == 4 && direction) {
				start = vects.get(2);
				end = vects.get(3);

			} else { // ==2
				if (direction) {
					start = vects.get(0);
					end = vects.get(1);
				} else {
					start = vects.get(1);
					end = vects.get(0);
				}

			}
		}

		Vector2f newpos = new Vector2f();

		// Test les différentes combinaisons pour avancer
		if (start.x < end.x && start.y < end.y) {
			newpos = new Vector2f(getPosition().x - local_direction, getPosition().y - local_direction);
		} else if (start.x > end.x && start.y > end.y) {
			newpos = new Vector2f(getPosition().x + local_direction, getPosition().y + local_direction);
		} else if (start.x > end.x && start.y == end.y) {
			newpos = new Vector2f(getPosition().x + local_direction, getPosition().y);
		} else if (start.x > end.x && start.y < end.y) {
			newpos = new Vector2f(getPosition().x + local_direction, getPosition().y - local_direction);
		} else if (start.x == end.x && start.y > end.y) {
			newpos = new Vector2f(getPosition().x, getPosition().y + local_direction);
		} else if (start.x == end.x && start.y < end.y) {
			newpos = new Vector2f(getPosition().x, getPosition().y - local_direction);
		} else if (start.x < end.x && start.y > end.y) {
			newpos = new Vector2f(getPosition().x - local_direction, getPosition().y + local_direction);
		} else if (start.x < end.x && start.y == end.y) {
			newpos = new Vector2f(getPosition().x - local_direction, getPosition().y);
		}

		if (currentSegment.isOnSegment(newpos)) {
			setPosition(newpos);
		} else {
			// s'il la prochaine position n'est pas sur le segment
			// alors le bus fait demi-tour
			if (local_direction == -1)
				local_direction = 1;
			else
				local_direction = -1;
		}
	}

	/**
	 * Méthode pour supprimer un bus. Un clic droit le fera disparaître à la
	 * prochaine station. Deux clics le supprimera au deuxième clic
	 * 
	 * @param mouseX
	 * @param mouseY
	 */
	public void RightedClicked(float mouseX, float mouseY) {
		if (polygon.contains(mouseX, mouseY)) {
			if (lock) {
				setCanBeRemove(true);
				canBeKilled = true;
			}
			lock = true;
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {

		super.update(gc, sbg, delta);

		Input input = gc.getInput();

		cpt += delta;
		if (cpt > 15 && !isGrabed) {
			cpt = 0;
			// for(int i = 0; i<Map.getInstance().gameSpeed;++i){
			// move();
			// }
			if (getAngle() != theta) // Donc on a changé d'angle
			{
				polygon = (Polygon) polygon.transform(Transform.createRotateTransform(
						(float) Math.toRadians(theta - getAngle()), getPosition().x, getPosition().y));
				theta = getAngle();

			}

			passenger_images = new ArrayList<>();

			synchronized (passengers) {
				for (Passenger passenger : passengers) {
					try {
						passenger_images.add(new Image("asset/" + passenger.filiere.toString().toLowerCase() + ".png"));
					} catch (SlickException e) {
						// TODO Auto-generated catch block
						System.out.println("Erreur");
						e.printStackTrace();
					}

				}
			}

			polygon.setCenterX(getPosition().x + currentSegment.getOffset() * Segment.SEGMENT_THICKNESS / 2);
			polygon.setCenterY(getPosition().y + currentSegment.getOffset() * Segment.SEGMENT_THICKNESS / 2);

		}
	}

	@Override
	public synchronized boolean equals(Object obj) {
		// TODO Auto-generated method stub
		// return super.equals(obj);

		if (obj.getClass() != ClassicBus.class)
			return false;

		ClassicBus _obj = (ClassicBus) obj;

		if (color == _obj.color && currentSegment == _obj.currentSegment) { // Donc
																			// même
																			// ligne
			return true;
		}
		return false;
	}

	public void setCanBeKilled(boolean value) {
		canBeKilled = value;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!canBeKilled) {

			for (int i = 0; i < Map.getInstance().gameSpeed; ++i) {
				move();

			}
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				Thread.currentThread().stop();
				e.printStackTrace();
			}

		}
		System.out.println("fin du thread bus");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
