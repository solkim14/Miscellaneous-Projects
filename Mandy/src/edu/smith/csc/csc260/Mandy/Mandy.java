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
	private int tint = 0xFFFFFF; //DEFAULT WHITE

	
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
	
	/** ACCESSOR FOR POSITION */
	public int getTint() {
		return tint;
	}	
	/** MANIPULATOR FOR TINT */
	public void setTint(int mode) {
		if (mode == 0) {
			tint = 0xFFFFFF;
		} else if (mode == 1) {
			tint = 0xFDEFDB;
		} else if (mode == 2) {
			tint = 0x800000;
		} else if (mode == 3) {
			tint = 0xFFFFFF;
		}
	}
	
	/** DRAW MANDY */
	public void render(PApplet pApplet) {
		//pApplet.tint(tint);
	    pApplet.ellipse(x,y,150,150);
	}
}