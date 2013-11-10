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

		public void setup() {
		  size(400, 400);
		  smooth();

		  Fisica.init(this);

		  world = new FWorld();
		  world.setEdges(this, 0);
		  //world.remove(world.left);
		  //world.remove(world.right);
		  //world.remove(world.top);
		  //world.setEdgesRestitution(0.0);
		 
		  /**
		  for (int i=0; i<ballCount; i++) {
		    FCircle b = new FCircle(25);
		    b.setPosition(PApplet.map(i, 0, ballCount-1, 40, width-40), height/6);
		    b.setRestitution(PApplet.map(i, 0, ballCount-1, 0.0f, 1.0f));
		    b.setNoStroke();
		    b.setFill(map(i, 0, ballCount-1, 60, 255), 80, 120);
		    world.add(b);
		  }
		  */
		  
		  /**
		    FCircle b = new FCircle(25);
		    b.setPosition(PApplet.map(i, 0, ballCount-1, 40, width-40), height/6);
		    b.setRestitution(PApplet.map(i, 0, ballCount-1, 0.0f, 1.0f));
		    b.setNoStroke();
		    b.setFill(map(i, 0, ballCount-1, 60, 255), 80, 120);
		    world.add(b);
		    */
		  
		    b = new FCircle(25);
		    b.setPosition(PApplet.map(i, 0, ballCount-1, 40, width-40), height/6);
		    b.setRestitution(PApplet.map(i, 0, ballCount-1, 0.0f, 1.0f));
		    b.setNoStroke();
		    b.setFill(map(i, 0, ballCount-1, 60, 255), 80, 120);
		    world.add(b);
		  
		}

		public void draw() {
		  background(255);

		  world.step();
		  world.draw();
		}

		public void keyPressed() {
		  try {
		    saveFrame("screenshot.png");
		  } 
		  catch (Exception e) {
		  }
		}
}
