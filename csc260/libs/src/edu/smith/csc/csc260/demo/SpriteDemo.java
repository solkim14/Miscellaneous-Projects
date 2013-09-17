package edu.smith.csc.csc260.demo;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.RectangleSprite;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;

public class SpriteDemo extends SmithPApplet {
	private static final long serialVersionUID = 1L;

	public void setup() {
		super.setup();
		
		RectangleSprite r = new RectangleSprite(10, 30);
		r.setLocation(new Point(5, 15));
		r.setFill(new Color(255,0,0,255));
		addSprite(r);
		
		r = new RectangleSprite(10, 30);
		r.setLocation(new Point(5, 15));
		r.setNoStroke(true);
		r.setAngle((float)Math.PI * 45.0f/180.0f);
		addSprite(r);
	}
	


}
