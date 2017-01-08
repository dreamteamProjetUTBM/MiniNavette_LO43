package fr.utbm.lo43.entities;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.logic.Inventory;
import fr.utbm.lo43.logic.Map;

public class Segment extends EntityDragable implements EntityDrawable {

	public static final float SEGMENT_THICKNESS = 5;
	private Polygon polygon;
	private ArrayList<Station> stations;

	private ArrayList<Vector2f> ponts;
	private String iconPath;
	// Line de Slick2D
	Line line;
	fr.utbm.lo43.logic.Line line_bus;
	int lineIndex;
	

	public Segment(Vector2f _start, Vector2f _end, int index) {
		super(_start);
		polygon = new Polygon();
		polygon.setClosed(false);
		polygon.addPoint(_start.x, _start.y);
		Vector2f angle = calculateAnglePosition(_start, _end);
		polygon.addPoint(angle.x, angle.y);
		polygon.addPoint(_end.x, _end.y);

		lineIndex = index;
		
		ponts = intersectsRailway(Map.getInstance().railWay);
		dragedEvent = new EventEntityMouseDraged() {

			@Override
			public void mouseReleased() {
				// TODO Auto-generated method stub
				boolean notOnStation = true;
				boolean needMoreBridges = ponts.size()> Inventory.getInstance().getRemainingBridges() ;
				for (Station station : Map.getInstance().getStations()) {
					if (station.position == getEndSegment()) {
						notOnStation = false;
					}
				}
				if (notOnStation||needMoreBridges) {
					// J'adore faire des méthodes comme ça
					Map.getInstance().getLine(lineIndex).removeSegment(Map.getInstance().getLine(lineIndex)
							.getSegments().get(Map.getInstance().getLine(lineIndex).getSegments().size() - 1));
				}
				Inventory.getInstance().takeBridges(ponts.size()); //takeBridges devrait être écrite avec des exceptions
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

	public void setIcon(String imgPath) {
		iconPath = imgPath;
	}

	public Vector2f getPointPolygon(int index) {
		return new Vector2f(polygon.getPoint(index)[0], polygon.getPoint(index)[1]);
	}

	/***
	 * Get line number of the segment
	 * 
	 * @return
	 */
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
	 * Get all stations
	 * 
	 * @return
	 */
	public ArrayList<Station> getStations() {
		return stations;
	}

	public ArrayList<Vector2f> getPositions() {
		ArrayList<Vector2f> _positions = new ArrayList<>();

		for (int i = 0; i < polygon.getPointCount(); ++i) {
			_positions.add(new Vector2f(polygon.getPoint(i)[0], polygon.getPoint(i)[1]));
		}

		return _positions;
	}

	/**
	 * Renvoie le segment inverse (le point de depart deviens le point d'arriv�
	 * et inversement)
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
		Line tempLine;
		int offset = 0;
		int dxStart = 0;
		int dyStart = 0;
		int dxEnd = 0;
		int dyEnd = 0;
		for (int i = lineIndex; i < Map.getInstance().getLines().size(); i++) {
			fr.utbm.lo43.logic.Line _line = Map.getInstance().getLine(i);
			for (Segment segment : _line.getSegments()) {
				if (!isCrossing(segment) && (segment.hasSameVectors(this) || isOnSegment(segment.getAngle())|| segment.isOnSegment(getAngle()))) { // donc
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

		arg2.setLineWidth(SEGMENT_THICKNESS);
		arg2.setColor(Map.getInstance().getLine(lineIndex).getColor());

		Polygon _polygonrender = new Polygon();
		_polygonrender.setClosed(false);



		for (int i = 0; i < polygon.getPointCount(); ++i) {
			// if(polygon.getPoint(i)[0] == polygon.getPoint(i+1)[0] ||
			// polygon.getPoint(i)[1] == polygon.getPoint(i+1)[1]){
			_polygonrender.addPoint(polygon.getPoint(i)[0], polygon.getPoint(i)[1]);
			// }

		} 

		try{
			tempLine =  new Line(getPositions().get(0), getPositions().get(1));


			dxStart = (int) tempLine.getDX();
			dyStart = (int) tempLine.getDY();

			try{
				dxStart = Math.abs(dxStart)/dxStart;
			}catch(ArithmeticException e)
			{

			}
			try{
				dyStart = Math.abs(dyStart)/dyStart;
			}catch(ArithmeticException e){

			}

			if(Math.abs(dxStart) == 1 && Math.abs(dyStart) == 0){
				_polygonrender.setLocation(_polygonrender.getX(), _polygonrender.getY() +SEGMENT_THICKNESS*offset);
			}
			if(Math.abs(dxStart) == 0 && Math.abs(dyStart) == 1){
				_polygonrender.setLocation(_polygonrender.getX()+SEGMENT_THICKNESS*offset, _polygonrender.getY());
			}
			if(dxStart == dyStart){
				_polygonrender.setLocation(_polygonrender.getX()+SEGMENT_THICKNESS*offset, _polygonrender.getY()-SEGMENT_THICKNESS*offset);
			}
			if(dxStart == -dyStart){
				_polygonrender.setLocation(_polygonrender.getX()+SEGMENT_THICKNESS*offset, _polygonrender.getY()+SEGMENT_THICKNESS*offset);
			}
			
		if(isFirstinLine()){
			tempLine = new Line(new Vector2f(_polygonrender.getPoint(0)[0], _polygonrender.getPoint(0)[1]), new Vector2f(_polygonrender.getPoint(0)[0]+ (-2)*dxStart*Map.GRID_SIZE, _polygonrender.getPoint(0)[1]+(-2)*dyStart*Map.GRID_SIZE));
			arg2.draw(tempLine);
		}

		}catch(IndexOutOfBoundsException e){

		}

		
		
		try{
			tempLine =  new Line(getPositions().get(_polygonrender.getPointCount()-1), getPositions().get(_polygonrender.getPointCount()-2));


			dxEnd = (int) tempLine.getDX();
			dyEnd = (int) tempLine.getDY();

			try{
				dxEnd= Math.abs(dxEnd)/dxEnd;
			}catch(ArithmeticException e)
			{

			}
			try{
				dyEnd = Math.abs(dyEnd)/dyEnd;
			}catch(ArithmeticException e){

			}

		if(isLastinLine()){
			tempLine = new Line(new Vector2f(_polygonrender.getPoint(_polygonrender.getPointCount()-1)[0], _polygonrender.getPoint(_polygonrender.getPointCount()-1)[1]), new Vector2f(_polygonrender.getPoint(_polygonrender.getPointCount()-1)[0]+ (-2)*dxEnd*Map.GRID_SIZE, _polygonrender.getPoint(_polygonrender.getPointCount()-1)[1]+(-2)*dyEnd*Map.GRID_SIZE));
			arg2.draw(tempLine);
		}
		}catch(IndexOutOfBoundsException e){

		}
		
		

		//_polygonrender.setLocation(_polygonrender.getX()+5*offset, _polygonrender.getY() +5*offset);
		arg2.draw(_polygonrender);
		

		
		for(int i = 0; i<_polygonrender.getPointCount(); ++i){
		
			arg2.fillOval(_polygonrender.getPoint(i)[0]-SEGMENT_THICKNESS/2, _polygonrender.getPoint(i)[1] -SEGMENT_THICKNESS/2, SEGMENT_THICKNESS, SEGMENT_THICKNESS);
		}

		
		try {
			Image imgPont = new Image("asset/bridge.png");
			for(Vector2f pont : ponts){
				imgPont.drawFlash(pont.x - Map.GRID_SIZE + SEGMENT_THICKNESS*offset, pont.y - Map.GRID_SIZE + SEGMENT_THICKNESS*offset, Map.GRID_SIZE*2,
						Map.GRID_SIZE*2, Map.getInstance().getLine(lineIndex).getColor());
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
	 * To know if a segment cross another one
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

		// System.out.println(_segment.line.getpo);
	}

	public boolean hasSameVectors(Segment _seg) {
		if ((_seg.getStartSegment().distance(getStartSegment()) == 0
				&& _seg.getEndSegment().distance(getEndSegment()) == 0))
			return true;
		return false;
	}

	/**
	 * Renvoie la liste des intersections avec un railway
	 * @param r
	 * @return
	 */
	public ArrayList<Vector2f> intersectsRailway(RailWay r){
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
				if(intersection != null){
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

	// moi j'aime bien faire des methodes comme ça aussi
	public Segment getNextSegment() {
		return (line_bus.getSegments().indexOf(this) + 1) <= (line_bus.getSegments().size())
				? line_bus.getSegments().get(line_bus.getSegments().indexOf(this) + 1) : null;
	}

	public Segment getPreviousSegment() {
		return (line_bus.getSegments().indexOf(this) - 1) > 0
				? line_bus.getSegments().get(line_bus.getSegments().indexOf(this) + 1) : null;
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

}