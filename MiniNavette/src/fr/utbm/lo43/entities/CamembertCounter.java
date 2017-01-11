package fr.utbm.lo43.entities;

import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class CamembertCounter extends Entity implements EntityDrawable {

	
	private float radius;
	private float percentage;
	private Color color;
	
	
	public CamembertCounter(Vector2f _position, float _radius) {
		super(_position);
		// TODO Auto-generated constructor stub
		percentage = 0;
		radius = _radius;
		color = new Color(0,0,0,50);
		drawable = true;
	}

	@Override
	public void render(Graphics arg2) {
	
	
		arg2.setAntiAlias(true);
		arg2.setColor(color);
		arg2.fillArc(getPosition().x, getPosition().y, radius, radius, 360, -90, 360 * percentage/100 -90);
		
		
		
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	
}
