package edu.smith.csc.csc260.sprites;

import processing.core.PShape;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Point;

public class BezierSprite extends Sprite {
	Point point1, point2, point3;
	int num_of_subdivisions;
	
	PShape bezier;
	
	/** Constructor */
	public BezierSprite(Point point1, Point point2, Point point3, int num_of_subdivisions) {
		super();
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;
		this.num_of_subdivisions = num_of_subdivisions;
	}
	
	/**
	 * Creates Bezier Curve using PShape
	 * 
	 * @param pApplet
	 */
	public void createBezier(SmithPApplet pApplet) {
		float t=0;
		float increment = (float)1/num_of_subdivisions;
		float xCo, yCo;
		
		bezier = pApplet.createShape();
		bezier.beginShape();
		
		//find vertices for bezier curve
		for(int i=0; i<num_of_subdivisions; i++) {			

			xCo = ((float)(Math.pow((1-t),2)*point1.getX())) + (2*t*(1-t)*point2.getX()) + ((float)Math.pow(t,2)*point3.getX());
			yCo = ((float)(Math.pow((1-t),2)*point1.getY())) + (2*t*(1-t)*point2.getY()) + ((float)Math.pow(t,2)*point3.getY());
			
			bezier.vertex(xCo,yCo);

			t = t+increment;
		}
		bezier.endShape(); //don't "CLOSE" a shape if you want it to be a path?
	}

	public void render(SmithPApplet pApplet) {
		if (bezier == null) {
			bezier = pApplet.createShape();
			createBezier(pApplet);
		}
		pApplet.shape(bezier);
	}
}
