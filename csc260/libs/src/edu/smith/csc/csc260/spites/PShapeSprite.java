package edu.smith.csc.csc260.spites;

import processing.core.PShape;
import edu.smith.csc.csc260.core.SmithPApplet;

public class PShapeSprite extends Sprite {
	PShape shape;
	
	public void render(SmithPApplet spa) {
		if(shape == null) {
			shape = spa.createShape();
			spa.beginShape();
			spa.vertex(-10,-10);
			spa.vertex(10,-10);
			spa.vertex(10,10);
			spa.vertex(-10,10);
			spa.endShape(SmithPApplet.CLOSE);
		}
		spa.shape(shape);
	}

}
