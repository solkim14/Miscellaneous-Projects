package edu.smith.cs.csc260.solkim;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.ConstantVelocitySprite;
import edu.smith.csc.csc260.util.Point;

public class LaserSprite extends ConstantVelocitySprite {

	/** Constructor */
	public LaserSprite(Point velocity, float angularVelocity) {
		super(velocity, angularVelocity);
	}
	
	public void render(SmithPApplet pApplet) {
		int min = 0;
		int max = 750;
		int x = (int)(Math.random() * (max - min) + min);
		pApplet.rect(x,0,x+50,75);
		//pApplet.rect(x,0,x+50,50);
	}
	
	
}
