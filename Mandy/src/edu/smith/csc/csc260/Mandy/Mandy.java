/**
 * File: Mandy.java
 * Author: Sol Kim
 * Description:
 * Version: 2013 Nov 26
 */

package edu.smith.csc.csc260.Mandy;
import processing.core.PApplet;
import edu.smith.csc.csc260.util.Point;

public class Mandy {

	/** FIELDS */
	private float x;
	private float y;
	private Point position;
	
	/** CONSTRUCTOR */
	public Mandy(float xPos, float yPos) {
		x = xPos;
		y = yPos;
	}
	
	/** ACCESSOR FOR X COORD */
	public float getX() {
		return x;
	}
	/** MANIPULATOR FOR X COORD */
	public void setX(float xPos) {
		x = xPos;
	}
	
	/** ACCESSOR FOR Y COORD */
	public float getY() {
		return y;
	}
	/** MANIPULATOR FOR Y COORD */
	public void setY(float yPos) {
		y = yPos;
	}

	/** ACCESSOR FOR POSITION */
	public Point getPosition() {
		return position;
	}	
	/** MANIPULATOR FOR POSITION */
	public void setPosition(Point newPosition) {
		position = newPosition;
	}
	
	/** DRAW MANDY */
	public void render(PApplet pApplet) {
	    pApplet.ellipse(x,y,100,100);
	}
}