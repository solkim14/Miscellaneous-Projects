package edu.smith.csc.csc260.solandfreda;

/**
 *  Restitutions
 *
 *  by Ricard Marxer
 *
 *  This example shows how the restitution coefficients works.
 */


import edu.smith.csc.csc260.core.SmithPApplet;
import fisica.*;

public class Restitution extends SmithPApplet {
	private static final long serialVersionUID = 1L;

	public Restitution() {
		// TODO Auto-generated constructor stub
	}

	FWorld world;
	int ballCount = 2;

	public void setup() {
	  size(400, 400);
	  smooth();

	  Fisica.init(this);

	  world = new FWorld();
	  world.setEdges();
	  //world.setEdgesRestitution(0.0);
	 
	  for (int i=0; i<ballCount; i++) {
	    FCircle b = new FCircle(25);
	    b.setPosition(map(i, 0, ballCount-1, 40, width-40), height/6);
	    //b.setRestitution(map(i, 0, ballCount-1, 0.0, 1.0));
	    b.setNoStroke();
	    b.setFill(map(i, 0, ballCount-1, 60, 255), 80, 120);
	    world.add(b);
	  }
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
