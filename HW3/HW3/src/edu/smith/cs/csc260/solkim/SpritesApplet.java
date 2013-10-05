package edu.smith.cs.csc260.solkim;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.interpolation.easing.LinearEasingFunction;
import edu.smith.csc.csc260.util.Color;
import edu.smith.csc.csc260.util.Point;

public class SpritesApplet extends SmithPApplet {
	
	private static final long serialVersionUID = 1L;

	public void setup() {
		super.setup();
		
		size(800,800);
		frameRate(30);
		setBackgroundColor(new Color(0,0,0,75));
		
		
		LaserSprite l0 = new LaserSprite(new Point(0,.003f,0),0); 
		addSprite(l0);
		
		LaserSprite l1 = new LaserSprite(new Point(0,.005f,0),0); 
		addSprite(l1);
		
		LaserSprite l2 = new LaserSprite(new Point(0,.01f,0),0); 
		addSprite(l2);
		
		LaserSprite l3 = new LaserSprite(new Point(0,.015f,0),0); 
		addSprite(l3);
		
		LaserSprite l4 = new LaserSprite(new Point(0,.020f,0),0); 
		addSprite(l4);
		
		LaserSprite l5 = new LaserSprite(new Point(0,.025f,0),0); 
		addSprite(l5);
		
		LaserSprite l6 = new LaserSprite(new Point(0,.030f,0),0); 
		addSprite(l6);
		
		LaserSprite l7 = new LaserSprite(new Point(0,.035f,0),0); 
		addSprite(l7);
		
		LaserSprite l9 = new LaserSprite(new Point(0,.040f,0),0); 
		addSprite(l9);
		
		RainbowSprite c1 = new RainbowSprite();
		c1.setFill(new Color(255,0,0,50));
		c1.setLocationInterpolator(new RainbowInterpolator(new Point(400,400), 75, new LinearEasingFunction(9*1000,13*1000,0)));
		addSprite(c1);
		
		RainbowSprite c2 = new RainbowSprite();
		c2.setFill(new Color(255,128,0,50));
		c2.setLocationInterpolator(new RainbowInterpolator(new Point(400,400), 125, new LinearEasingFunction(3*1000,13*1000,0)));
		addSprite(c2);
		
		RainbowSprite c3 = new RainbowSprite();
		c3.setFill(new Color(255,255,0,50));
		c3.setLocationInterpolator(new RainbowInterpolator(new Point(400,400), 175, new LinearEasingFunction(2*1000,13*1000,0)));
		addSprite(c3);
		
		RainbowSprite c4 = new RainbowSprite();
		c4.setFill(new Color(0,255,0,50));
		c4.setLocationInterpolator(new RainbowInterpolator(new Point(400,400), 200, new LinearEasingFunction(9*1000,13*1000,0)));
		addSprite(c4);
		
		RainbowSprite c5 = new RainbowSprite();
		c5.setFill(new Color(0,0,255,50));
		c5.setLocationInterpolator(new RainbowInterpolator(new Point(400,400), 250, new LinearEasingFunction(0*1000,13*1000,0)));
		addSprite(c5);
		
		RainbowSprite c6 = new RainbowSprite();
		c6.setFill(new Color(102,102,255,50));
		c6.setLocationInterpolator(new RainbowInterpolator(new Point(400,400), 300, new LinearEasingFunction(7*1000,13*1000,0)));
		addSprite(c6);
		
		RainbowSprite c7 = new RainbowSprite();
		c7.setFill(new Color(127,0,255,50));
		c7.setLocationInterpolator(new RainbowInterpolator(new Point(400,400), 350, new LinearEasingFunction(5*1000,13*1000,0)));
		addSprite(c7);
	}
}
		