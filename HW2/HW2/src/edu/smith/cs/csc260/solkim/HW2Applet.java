package edu.smith.cs.csc260.solkim;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.sprites.BezierSprite;
import edu.smith.csc.csc260.util.Point;

public class HW2Applet extends SmithPApplet {

	private static final long serialVersionUID = 1L;
	
	/**Fields*/
	public BezierSprite sprite;
	public Point p1, p2, p3;
	int x1, x2, x3, y1, y2, y3;
	int filler;
	
	/** Constructor */
	public HW2Applet() {
		x1 = 75;
		x2 = 50;
		x3 = 0;
		y1 = 0;
		y2 = 50;
		y3 = 50;		
		
		for (int i=0; i<1200; i=i+35) {
			p1 = new Point(x1, y1);
			p2 = new Point(x2, y2);
			p3 = new Point(x3, y3);
	
			sprite = new BezierSprite(p1, p2, p3, 50);
	
			addSprite(sprite);
						
			x1 = x1+55;
			x2 = x2+55;
			x3 = x3+47;

			y3 = y3+(i/50)-1;
			y2 = y2+(i/50)-2;
			y1 = y1+(i/50);
		}
	}
	
	public void setup() {
		size(1200,150,P2D);		
	}
	
	public void draw() {
		background(255);
		super.draw();
	}

}
