package fr.utbm.lo43.entities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.logic.Inventory;
import fr.utbm.lo43.logic.Map;

/**
 * @author Quentin Nahil Thomas Jeremy
 *
 */
public class Segment extends EntityDragable implements EntityDrawable {

	/**
	 * Epaisseur d'un segment (affichage)
	 */
	public static final float SEGMENT_THICKNESS = 5;

	/**
	 * Permet l'affichage du segments
	 */
	private volatile Polygon polygon;

	/**
	 * Permet la prévisualisation d'un segment pour le joueur
	 */
	private volatile Polygon polygonRender;

	/**
	 * Contient les coordonnées des ponts sur le segment
	 */
	private volatile ArrayList<Vector2f> bridges;

	/**
	 * Station de départ du segmebt
	 */
	private volatile Station stationDepart;

	/**
	 * Station d'arrivée du segment
	 */
	private volatile Station stationArrival;

	/**
	 * Contient le chemin du logo de l'opération effectué sur le segment
	 */
	private volatile String iconPath;

	/**
	 * Line correspondant au segment
	 */
	fr.utbm.lo43.logic.Line line_bus;

	/**
	 * Contient le numéro de Line
	 */
	int lineIndex;

	private boolean forbiddenBridges;

	public Segment(Vector2f _start, Vector2f _end, int index) {
		super(_start);
		polygon = new Polygon();
		polygon.setClosed(false);
		polygon.addPoint(_start.x, _start.y);
		Vector2f angle = calculateAnglePosition(_start, _end);
		polygon.addPoint(angle.x, angle.y);
		polygon.addPoint(_end.x, _end.y);
		forbiddenBridges = false;

		lineIndex = index;
		line_bus = Map.getInstance().getLine(lineIndex);

		bridges = intersectsRailway(Map.getInstance().railWay);

		setStations();
		setPolygonRender();
		dragedEvent = new EventEntityMouseDraged() {

			@Override
			public void mouseReleased() {
				// TODO Auto-generated method stub
				boolean notOnStation = true;

				for (Station station : Map.getInstance().getStations()) {
					if (station.position == getEndSegment()) {
						notOnStation = false;
					}
				}
				if (notOnStation) {
					// J'adore faire des méthodes comme ça
					Map.getInstance().getLine(lineIndex).removeSegment(Map.getInstance().getLine(lineIndex)
							.getSegments().get(Map.getInstance().getLine(lineIndex).getSegments().size() - 1));
				}
				Inventory.getInstance().takeBridges(bridges.size()); // takeBridges
																		// devrait
																		// être
																		// écrite
																		// avec
																		// des
																		// exceptions
				System.out.println("Segment.Segment(...).new EventEntityMouseDraged() {...}.mouseReleased()");
			}

			@Override
			public void mousePressed() {
				// TODO Auto-generated method stub

				System.out.println("Segment.MousePresssed()");
			}
		};

		drawable = true;

	}

	/**
	 * Permet de calculer l'offset d'un segment
	 */
	public int getOffset() {
		int offset = 0;
		for (int i = lineIndex; i < Map.getInstance().getLines().size(); i++) {
			fr.utbm.lo43.logic.Line _line = Map.getInstance().getLine(i);
			for (Segment segment : _line.getSegments()) {
				if (!isCrossing(segment) && (segment.hasSameVectors(this) || isOnSegment(segment.getAngle())
						|| segment.isOnSegment(getAngle()))) { // donc
					// parallèle
					offset++;

				}
			}
		}

		// permet de centrer les segments si il y a des offsets
		if (offset % 2 == 0) {
			offset = -offset;
		}
		offset = offset / 2;
		return offset;
	}

	/**
	 * Modifie la prévisualisation du segment
	 */
	public void setPolygonRender() {
		Line tempLine;

		int dxStart = 0;
		int dyStart = 0;
		int dxEnd = 0;
		int dyEnd = 0;

		polygonRender = new Polygon();
		polygonRender.setClosed(false);

		try {
			tempLine = new Line(getPositions().get(0), getPositions().get(1));

			dxStart = (int) tempLine.getDX();
			dyStart = (int) tempLine.getDY();
		} catch (IndexOutOfBoundsException e) {

		}
		try {

			dxStart = Math.abs(dxStart) / dxStart;
		} catch (ArithmeticException e) {

		}
		try {
			dyStart = Math.abs(dyStart) / dyStart;
		} catch (ArithmeticException e) {

		}
		try {
			if (isFirstinLine()) {
				polygonRender.addPoint(polygon.getPoint(0)[0] + (-2) * dxStart * Map.GRID_SIZE,
						polygon.getPoint(0)[1] + (-2) * dyStart * Map.GRID_SIZE);
			}
		} catch (IndexOutOfBoundsException e) {

		}

		for (int i = 0; i < polygon.getPointCount(); ++i) {
			polygonRender.addPoint(polygon.getPoint(i)[0], polygon.getPoint(i)[1]);
		}

		try {
			tempLine = new Line(getPositions().get(polygon.getPointCount() - 1),
					getPositions().get(polygon.getPointCount() - 2));
			dxEnd = (int) tempLine.getDX();
			dyEnd = (int) tempLine.getDY();
		} catch (IndexOutOfBoundsException e) {

		}
		try {
			dxEnd = Math.abs(dxEnd) / dxEnd;
		} catch (ArithmeticException e) {

		}
		try {
			dyEnd = Math.abs(dyEnd) / dyEnd;
		} catch (ArithmeticException e) {

		}

		try {
			if (isLastinLine()) {
				polygonRender.addPoint(polygon.getPoint(polygon.getPointCount() - 1)[0] + (-2) * dxEnd * Map.GRID_SIZE,
						polygon.getPoint(polygon.getPointCount() - 1)[1] + (-2) * dyEnd * Map.GRID_SIZE);
			}
		} catch (IndexOutOfBoundsException e) {

		}

		if (Math.abs(dxStart) == 1 && Math.abs(dyStart) == 0) {
			polygonRender.setLocation(polygon.getX(), polygon.getY() + SEGMENT_THICKNESS * this.getOffset());
		}
		if (Math.abs(dxStart) == 0 && Math.abs(dyStart) == 1) {
			polygonRender.setLocation(polygon.getX() + SEGMENT_THICKNESS * this.getOffset(), polygon.getY());
		}
		if (dxStart == dyStart) {
			polygonRender.setLocation(polygon.getX() + SEGMENT_THICKNESS * this.getOffset(),
					polygon.getY() - SEGMENT_THICKNESS * this.getOffset());
		}
		if (dxStart == -dyStart) {
			polygonRender.setLocation(polygon.getX() + SEGMENT_THICKNESS * this.getOffset(),
					polygon.getY() + SEGMENT_THICKNESS * this.getOffset());
		}

	}

	public fr.utbm.lo43.logic.Line getLine_bus() {
		return line_bus;
	}

	public void setForbiddenBridges(boolean forbiddenBridges) {
		this.forbiddenBridges = forbiddenBridges;
	}

	public Station getStationDepart() {
		return stationDepart;
	}

	public Station getStationArrival() {
		return stationArrival;
	}

	public ArrayList<Vector2f> getBridges() {
		return bridges;
	}

	public void setIcon(String imgPath) {
		iconPath = imgPath;
	}

	public Vector2f getPointPolygon(int index) {
		return new Vector2f(polygon.getPoint(index)[0], polygon.getPoint(index)[1]);
	}

	public int getLineIndex() {
		return lineIndex;
	}

	public Vector2f getStartSegment() {
		return getPointPolygon(0);
	}

	public Vector2f getEndSegment() {
		return getPointPolygon(polygon.getPointCount() - 1);
	}

	/**
	 * Enregistre la station de depart et d'arriv�
	 * 
	 * @return
	 */
	public void setStations() {
		for (Station station : Map.getInstance().getStations()) {
			if (station.isOnStation(getStartSegment())) {
				stationDepart = station;
			} else if (station.isOnStation(getEndSegment())) {
				stationArrival = station;
			}

		}

	}

	public ArrayList<Vector2f> getPositions() {
		ArrayList<Vector2f> _positions = new ArrayList<>();

		for (int i = 0; i < polygon.getPointCount(); ++i) {
			_positions.add(new Vector2f(polygon.getPoint(i)[0], polygon.getPoint(i)[1]));
		}

		return _positions;
	}

	/**
	 * Renvoie le segment inverse (le point de depart deviens le point
	 * d'arriv� et inversement)
	 * 
	 * @return
	 */
	public Segment reverse() {
		return new Segment(getEndSegment(), getStartSegment(), lineIndex);
	}

	public boolean isReverse(Segment _segment) {
		return hasSameVectors(_segment.reverse());

	}

	public Vector2f getMid() {
		Line tempLine = new Line(getStartSegment(), getEndSegment());
		return new Vector2f(tempLine.getCenterX(), tempLine.getCenterY());
	}

	public Vector2f getAngle() {

		return calculateAnglePosition(getStartSegment(), getEndSegment());

	}

	@Override
	public void render(Graphics arg2) {

		arg2.setAntiAlias(true);

		// permet de centrer les segments si il y a des offsets

		arg2.setLineWidth(SEGMENT_THICKNESS);
		arg2.setColor(Map.getInstance().getLine(lineIndex).getColor());

		setPolygonRender();
		// _polygonrender.setLocation(_polygonrender.getX()+5*this.getOffset(),
		// _polygonrender.getY() +5*this.getOffset());
		arg2.draw(polygonRender);

		try {
			if (isFirstinLine()) {
				arg2.fillOval(polygonRender.getPoint(0)[0] - SEGMENT_THICKNESS,
						polygonRender.getPoint(0)[1] - SEGMENT_THICKNESS, SEGMENT_THICKNESS * 2, SEGMENT_THICKNESS * 2);
			}
		} catch (IndexOutOfBoundsException e) {

		}
		try {
			if (isLastinLine()) {
				arg2.fillOval(polygonRender.getPoint(polygonRender.getPointCount() - 1)[0] - SEGMENT_THICKNESS,
						polygonRender.getPoint(polygonRender.getPointCount() - 1)[1] - SEGMENT_THICKNESS,
						SEGMENT_THICKNESS * 2, SEGMENT_THICKNESS * 2);
			}
		} catch (IndexOutOfBoundsException e) {

		}

		for (int i = 0; i < polygonRender.getPointCount(); ++i) {

			arg2.fillOval(polygonRender.getPoint(i)[0] - SEGMENT_THICKNESS / 2,
					polygonRender.getPoint(i)[1] - SEGMENT_THICKNESS / 2, SEGMENT_THICKNESS, SEGMENT_THICKNESS);
		}

		try {
			Image imgBridges = new Image("asset/bridge.png");
			Image imgForbidden = new Image("asset/forbidden.png");
			for (Vector2f bridge : bridges) {
				imgBridges.drawFlash(bridge.x - Map.GRID_SIZE + SEGMENT_THICKNESS * this.getOffset(),
						bridge.y - Map.GRID_SIZE + SEGMENT_THICKNESS * this.getOffset(), Map.GRID_SIZE * 2,
						Map.GRID_SIZE * 2, Map.getInstance().getLine(lineIndex).getColor());

				if (forbiddenBridges) {
					imgForbidden.drawFlash(bridge.x - Map.GRID_SIZE + SEGMENT_THICKNESS * this.getOffset(),
							bridge.y - Map.GRID_SIZE + SEGMENT_THICKNESS * this.getOffset(), Map.GRID_SIZE * 2,
							Map.GRID_SIZE * 2, Color.red);
				}
			}
		} catch (SlickException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (iconPath != null) {
			Image icon;
			try {
				icon = new Image(iconPath);
				icon.drawFlash(getMid().x - Map.GRID_SIZE / 2, getMid().y - Map.GRID_SIZE / 2, Map.GRID_SIZE,
						Map.GRID_SIZE, Map.getInstance().getLine(lineIndex).getColor());

			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public boolean isFirstinLine() {
		return Map.getInstance().getLines().get(lineIndex).getSegments().get(0) == this;
	}

	public boolean isLastinLine() {
		return Map.getInstance().getLines().get(lineIndex).getSegments()
				.get(Map.getInstance().getLines().get(lineIndex).getSegments().size() - 1) == this;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		super.update(gc, sbg, delta);
		Input input = gc.getInput();
		if (getRect().contains(input.getMouseX(), input.getMouseY())
				&& input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {

			if (isGrabed == true) {
				position.x = input.getMouseX() - (size.x / 2.0f);
				position.y = input.getMouseY() - (size.y / 2.0f);
				if (dragedEvent != null)
					dragedEvent.mousePressed();
			} else {
				isGrabed = true;
			}
		} else {

			if (isGrabed != false) {
				if (dragedEvent != null)
					dragedEvent.mouseReleased();
				isGrabed = false;
			}
		}
	}

	/***
	 * Permet de savoir si un segment en croise un autre
	 * 
	 * @param _segment
	 *            Segment to compare
	 * @return true if the segment cross the line false if not
	 */
	public boolean isCrossing(Segment _segment) {

		if (this == _segment) {
			return false;
		}

		Line tempLine1;
		Line tempLine2;
		Vector2f intersection;
		boolean intersect = false;
		for (int i = 0; i < polygon.getPointCount() - 1; ++i) {
			tempLine1 = new Line(new Vector2f(polygon.getPoint(i)[0], polygon.getPoint(i)[1]),
					new Vector2f(polygon.getPoint(i + 1)[0], polygon.getPoint(i + 1)[1]));
			for (int j = 0; j < _segment.polygon.getPointCount() - 1; ++j) {
				tempLine2 = new Line(new Vector2f(_segment.polygon.getPoint(j)[0], _segment.polygon.getPoint(j)[1]),
						new Vector2f(_segment.polygon.getPoint(j + 1)[0], _segment.polygon.getPoint(j + 1)[1]));
				intersection = null;
				intersection = tempLine1.intersect(tempLine2, true);

				if (intersection != null) {
					if (intersection.distance(_segment.getStartSegment()) == 0
							|| intersection.distance(_segment.getEndSegment()) == 0
							|| intersection.distance(_segment.getAngle()) == 0
							|| intersection.distance(getAngle()) == 0) {

					} else {
						intersect = true;
					}
				}

			}

		}
		return intersect;

	}

	public boolean hasSameVectors(Segment _seg) {
		if ((_seg.getStartSegment().distance(getStartSegment()) == 0
				&& _seg.getEndSegment().distance(getEndSegment()) == 0))
			return true;
		return false;
	}

	/**
	 * Renvoie la liste des intersections avec un railway
	 * 
	 * @param r
	 * @return
	 */
	public ArrayList<Vector2f> intersectsRailway(RailWay r) {
		ArrayList<Vector2f> intersections = new ArrayList<>();

		Line tempLine1;
		Line tempLine2;
		Vector2f intersection;

		for (int i = 0; i < polygon.getPointCount() - 1; ++i) {
			tempLine1 = new Line(new Vector2f(polygon.getPoint(i)[0], polygon.getPoint(i)[1]),
					new Vector2f(polygon.getPoint(i + 1)[0], polygon.getPoint(i + 1)[1]));

			for (int j = 0; j < r.plot.getPointCount() - 1; ++j) {
				tempLine2 = new Line(new Vector2f(r.plot.getPoint(j)[0], r.plot.getPoint(j)[1]),
						new Vector2f(r.plot.getPoint(j + 1)[0], r.plot.getPoint(j + 1)[1]));
				intersection = null;
				intersection = tempLine1.intersect(tempLine2, true);
				if (intersection != null) {
					intersections.add(intersection);
				}

			}
		}
		return intersections;

	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj.getClass() != getClass())
			return false;

		Segment _obj = (Segment) obj;

		if (obj == this)
			return true;

		else if (_obj.getLineIndex() == lineIndex)
			if ((_obj.getStartSegment().distance(getStartSegment()) == 0
					&& _obj.getEndSegment().distance(getEndSegment()) == 0)
					|| (_obj.getStartSegment().distance(getEndSegment()) == 0
							&& _obj.getEndSegment().distance(getStartSegment()) == 0)) {
				return true;
			}

		return false;
	}

	public Segment getNextSegment() {
		if (line_bus.getSegments().size() - 1 >= line_bus.getSegments().indexOf(this) + 1) {
			return line_bus.getSegment(line_bus.getSegments().indexOf(this) + 1);
		} else if (line_bus.isLoop()) {
			return line_bus.getSegment(0);
		}
		return null;
	}

	public Segment getPreviousSegment() {
		if (line_bus.getSegments().indexOf(this) - 1 >= 0) {
			return line_bus.getSegment(line_bus.getSegments().indexOf(this) - 1);
		} else if (line_bus.isLoop()) {
			return line_bus.getSegment(line_bus.getSegments().size() - 1);
		}
		return null;
	}

	/**
	 * Retourne la longueur d'un segment
	 * 
	 * @return
	 */
	public float getLength() {
		float length = 0;
		for (int i = 0; i < getPositions().size() - 1; ++i) {
			length += getPositions().get(i).distance(getPositions().get(i + 1));
		}
		return length;
	}

	/**
	 * Verifie si un point est sur le segment
	 * 
	 * @param _vector
	 * @return
	 */
	public boolean isOnSegment(Vector2f _vector) {
		Line _line;
		for (int i = 0; i < getPositions().size() - 1; ++i) {
			_line = new Line(getPositions().get(i), getPositions().get(i + 1));
			if (_line.on(_vector)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Calcule la position de l'angle du segment qui relie 2 positions
	 * 
	 * @param position1
	 * @param position2
	 * @return
	 */
	public static Vector2f calculateAnglePosition(Vector2f position1, Vector2f position2) {
		Vector2f anglePosition;
		float distancePosition1;
		float distancePosition2;
		Line lineTemp1 = new Line(position1.x, position1.y, 0, 1, true);
		Line lineTemp2 = new Line(position2.x, position2.y, 1, 1, true);

		distancePosition1 = position1.distance(lineTemp1.intersect(lineTemp2));
		distancePosition2 = position2.distance(lineTemp1.intersect(lineTemp2));

		anglePosition = lineTemp1.intersect(lineTemp2);

		lineTemp1 = new Line(position1.x, position1.y, 1, 0, true);
		lineTemp2 = new Line(position2.x, position2.y, 1, 1, true);
		if (distancePosition1 > position1.distance(lineTemp1.intersect(lineTemp2))) {
			anglePosition = lineTemp1.intersect(lineTemp2);
			distancePosition1 = position1.distance(lineTemp1.intersect(lineTemp2));
			distancePosition2 = position2.distance(lineTemp1.intersect(lineTemp2));
		} else if (distancePosition1 == position1.distance(lineTemp1.intersect(lineTemp2))) {
			if (distancePosition2 > position2.distance(lineTemp1.intersect(lineTemp2))) {
				anglePosition = lineTemp1.intersect(lineTemp2);
				distancePosition1 = position1.distance(lineTemp1.intersect(lineTemp2));
				distancePosition2 = position2.distance(lineTemp1.intersect(lineTemp2));
			}
		}

		lineTemp1 = new Line(position1.x, position1.y, 0, 1, true);
		lineTemp2 = new Line(position2.x, position2.y, -1, 1, true);
		if (distancePosition1 > position1.distance(lineTemp1.intersect(lineTemp2))) {
			anglePosition = lineTemp1.intersect(lineTemp2);
			distancePosition1 = position1.distance(lineTemp1.intersect(lineTemp2));
			distancePosition2 = position2.distance(lineTemp1.intersect(lineTemp2));
		} else if (distancePosition1 == position1.distance(lineTemp1.intersect(lineTemp2))) {
			if (distancePosition2 > position2.distance(lineTemp1.intersect(lineTemp2))) {
				anglePosition = lineTemp1.intersect(lineTemp2);
				distancePosition1 = position1.distance(lineTemp1.intersect(lineTemp2));
				distancePosition2 = position2.distance(lineTemp1.intersect(lineTemp2));
			}
		}

		lineTemp1 = new Line(position1.x, position1.y, 1, 0, true);
		lineTemp2 = new Line(position2.x, position2.y, -1, 1, true);
		if (distancePosition1 > position1.distance(lineTemp1.intersect(lineTemp2))) {
			anglePosition = lineTemp1.intersect(lineTemp2);
			distancePosition1 = position1.distance(lineTemp1.intersect(lineTemp2));
			distancePosition2 = position2.distance(lineTemp1.intersect(lineTemp2));
		} else if (distancePosition1 == position1.distance(lineTemp1.intersect(lineTemp2))) {
			if (distancePosition2 > position2.distance(lineTemp1.intersect(lineTemp2))) {
				anglePosition = lineTemp1.intersect(lineTemp2);
				distancePosition1 = position1.distance(lineTemp1.intersect(lineTemp2));
				distancePosition2 = position2.distance(lineTemp1.intersect(lineTemp2));
			}
		}
		return anglePosition;
	}

	public Vector2f getPosition(int index) {
		return getPositions().get(index);
	}

	/**
	 * Permet de savoir entre quels point se trouve la position
	 * 
	 * @param position
	 * @return une liste des vecteurs ou se trouve position
	 */
	public ArrayList<Vector2f> isBetween(Vector2f position) {
		ArrayList<Vector2f> vects = new ArrayList<>();

		for (int i = 0; i < getPositions().size() - 1; i++) {
			Line line = new Line(new Vector2f(getPosition(i).x, getPosition(i).y),
					new Vector2f(getPosition(i + 1).x, getPosition(i + 1).y));
			if (line.on(position)) {
				vects.add(new Vector2f(getPosition(i).x, getPosition(i).y));
				vects.add(new Vector2f(getPosition(i + 1).x, getPosition(i + 1).y));
			}
		}

		return vects;
	}

}