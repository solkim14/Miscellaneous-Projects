package edu.smith.csc.csc260.solandfreda;


import processing.core.PApplet;
import edu.smith.csc.csc260.core.SmithPApplet;
import fisica.FBox;
import fisica.FCircle;
import fisica.FWorld;
import fisica.Fisica;


public class TestApplet extends SmithPApplet {
	private static final long serialVersionUID = 1L;

		/** Constructor */
		public TestApplet() {
		}

		FWorld world;
		int ballCount = 10;
		int i = 5;
		FCircle b;
		
		double distance;

		public void setup() {
		  size(400, 400);
		  smooth();

		  Fisica.init(this);

		  world = new FWorld();
		  world.setEdges(this, 0);
		  
		  b = new FCircle(25);
		  b.setPosition(PApplet.map(i, 0, ballCount-1, 40, width-40), height/6);
		  b.setRestitution(PApplet.map(i, 0, ballCount-1, 0.0f, 1.0f));
		  b.setNoStroke();
		  b.setFill(map(i, 0, ballCount-1, 60, 255), 80, 120);
		  world.add(b);
		 
		  //distance = Math.sqrt(Math.pow((x2-x1),2) + Math.pow((y2-y1)));
		}

		public void draw() {
		  background(255);
		  
		  ellipse(height/2, width/2, 50, 50);

		  world.step();
		  world.draw();
		  super.draw();
		}
		//FMouseJoint(FBody body, float x, float y) 

}
