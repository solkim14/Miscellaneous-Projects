package edu.smith.csc.csc260.solandfreda;

import edu.smith.csc.csc260.core.SmithPApplet;
import fisica.*;

/**
 *  Buttons and bodies
 *
 *  by Ricard Marxer
 *
 *  This example shows how to create bodies.
 *  It also demonstrates the use of bodies as buttons.
 */

public class Buttons extends SmithPApplet {
	private static final long serialVersionUID = 1L;
	
	/** Constructor */
	public Buttons() {
	}

	FBox boxButton;
	FCircle circleButton;
	FPoly polyButton;
	int width = 400;
	int height = 400;
	
	//Color buttonColor = new Color(255,0,255,255);
	int buttonColor = 5;
	int bodyColor = 100;
	int hoverColor = 255;
	
	FWorld world;

	void setup(SmithPApplet pApplet) {
	  pApplet.size(height, width);
	  pApplet.smooth();

	  Fisica.init(pApplet); //weird?

	  world = new FWorld();
	  world.setEdges();
	 // world.remove(world.left);
	  //world.remove(world.right);
	  //world.remove(world.top);

	  boxButton = new FBox(40, 40);
	  boxButton.setPosition(width/4, 100);
	  boxButton.setStatic(true);
	  boxButton.setFillColor(buttonColor);
	  boxButton.setNoStroke();
	  world.add(boxButton);

	  circleButton = new FCircle(40);
	  circleButton.setPosition(2*width/4, 100);
	  circleButton.setStatic(true);
	  circleButton.setFillColor(buttonColor);
	  circleButton.setNoStroke();
	  world.add(circleButton);

	  polyButton = new FPoly();
	  polyButton.vertex(20, 20);
	  polyButton.vertex(-20, 20);
	  polyButton.vertex(0, -20);
	  polyButton.setPosition(3*width/4, 100);
	  polyButton.setStatic(true);
	  polyButton.setFillColor(buttonColor);
	  polyButton.setNoStroke();
	  world.add(polyButton);

	}

	public void draw() {
		background(255);
		world.step();
		world.draw();
		super.draw();
	}

	public void mousePressed() {
	  FBody pressed = world.getBody(mouseX, mouseY);
	  if (pressed == boxButton) {
	    FBox myBox = new FBox(40, 40);
	    myBox.setPosition(width/4, 200);
	    myBox.setRotation(random(TWO_PI));
	    myBox.setVelocity(0, 200);
	    myBox.setFillColor(bodyColor);
	    myBox.setNoStroke();
	    world.add(myBox);
	  } 
	  else if (pressed == circleButton) {
	    FCircle myCircle = new FCircle(40);
	    myCircle.setPosition(2*width/4, 200);
	    myCircle.setRotation(random(TWO_PI));
	    myCircle.setVelocity(0, 200);
	    myCircle.setFillColor(bodyColor);
	    myCircle.setNoStroke();
	    world.add(myCircle);
	  } 
	  else if (pressed == polyButton) {
	    FPoly myPoly = new FPoly();
	    myPoly.vertex(20, 20);
	    myPoly.vertex(-20, 20);
	    myPoly.vertex(0, -20);
	    myPoly.setPosition(3*width/4, 200);
	    myPoly.setRotation(random(TWO_PI));
	    myPoly.setVelocity(0, 200);
	    myPoly.setFillColor(bodyColor);
	    myPoly.setNoStroke();
	    world.add(myPoly);
	  }
	}

	public void mouseMoved() {
	  FBody hovered = world.getBody(mouseX, mouseY);
	  if (hovered == boxButton 
	      || hovered == circleButton
	      || hovered == polyButton) {
	    hovered.setFillColor(hoverColor);

	  } else {
	    boxButton.setFillColor(buttonColor);
	    circleButton.setFillColor(buttonColor);
	    polyButton.setFillColor(buttonColor);
	  }
	}

	public void keyPressed() {
	  try {
	    saveFrame("screenshot.png");
	  } 
	  catch (Exception e) {
	  }	
	}
}