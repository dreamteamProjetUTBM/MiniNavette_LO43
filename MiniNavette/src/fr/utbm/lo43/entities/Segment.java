package fr.utbm.lo43.entities;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fr.utbm.lo43.logic.Map;

public class Segment extends EntityDragable implements EntityDrawable
{
	private float lenght;
	
	private ArrayList<Station> stations;
	
	//Line de Slick2D
	Line line;
	int lineIndex;
	
	public Segment(Vector2f _start, Vector2f _end,int index) 
	{
		super(_start);
		//line = new Line(_start, _end);
		line = new Line(_start.x, _start.y, _end.x, _end.y);
		lineIndex = index;
		dragedEvent = new EventEntityMouseDraged() {
			
			@Override
			public void mouseReleased() {
				// TODO Auto-generated method stub
				boolean notOnStation = true;
				for (Station station : Map.getInstance().getStations()) {
					if(station.position == line.getEnd())
					{
						notOnStation = false;
					}
				}
				if(notOnStation)
				{
					//J'adore faire des méthodes comme ça
					Map.getInstance().getLine(lineIndex).removeSegment(Map.getInstance().getLine(lineIndex).getSegments().get(Map.getInstance().getLine(lineIndex).getSegments().size()-1));
				}
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
	
	public ArrayList<Station> getStations()
	{
		return stations;
	}
	
	public ArrayList<Vector2f> getPositions(){
		ArrayList<Vector2f> _positions = new ArrayList<>();
		Vector2f _start = new Vector2f(line.getX1(),line.getY1());
		Vector2f _end = new Vector2f(line.getX2(),line.getY2());

		_positions.add(_start);
		_positions.add(_end);
		return _positions;
	}

	@Override
	public void render(Graphics arg2) {
		arg2.setLineWidth(3);
		arg2.setColor(Map.getInstance().getLine(lineIndex).getColor());
		arg2.draw(line);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg)
	{
		super.update(gc, sbg);
		Input input = gc.getInput();

		
		if(getRect().contains(input.getMouseX(), input.getMouseY()) && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{

			if(isGrabed == true)
			{				
				position.x = input.getMouseX() - (size.x / 2.0f);
				position.y = input.getMouseY() - (size.y / 2.0f);
				if(dragedEvent != null)
					dragedEvent.mousePressed();
			}
			else
			{				
				isGrabed = true;
			}
		}
		else
		{

			if(isGrabed != false)
			{
				if(dragedEvent != null)
					dragedEvent.mouseReleased();
				isGrabed = false;
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub		
		if(obj.getClass() != getClass())
			return false;
		
		Segment _obj = (Segment) obj;
		
		if(obj == this)
			return true;
		
		else if((_obj.getPositions().get(0).distance(getPositions().get(0)) == 0 &&
				_obj.getPositions().get(1).distance(getPositions().get(1)) == 0) ||
				(_obj.getPositions().get(0).distance(getPositions().get(1)) == 0 &&
				_obj.getPositions().get(1).distance(getPositions().get(0)) == 0)
				){
			return true;
		}

		return false;
	}
}
