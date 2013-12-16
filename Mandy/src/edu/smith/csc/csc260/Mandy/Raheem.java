//WILL NOT USE DELETE LATER

/**
 * File: Raheem.java
 * Author: Sol Kim
 * Description: an interactive slider modeled after a radio.
 * Version: 2013 Nov 26
 */

package edu.smith.csc.csc260.Mandy;

import processing.core.PApplet;

public class Raheem {
	
	/** FIELDS */
	private float x;
	private int rHeight = 25; //height of slider
	private int rWidth = 5;	//width of slider
	private int winHeight;
	//private int winWidth;
	//private int offset = 15; //offset of raheem in pApplet
	private boolean isOver = false;
	private float raheemXConstraint;
	private int yPos;
	
	/** CONSTRUCTOR */
	public Raheem(PApplet mandyApplet, float xPos, int windowHeight, int windowWidth) {
		x = xPos;
		winHeight = windowHeight;
		//winWidth = windowWidth;
	}
	
	/** ACCESSOR FOR X COORD */
	public float getX() {
		return x;
	}
	/** MANIPULATOR FOR X COORD */
	public void setX(int mouseX) {
		x = mouseX;
	}
	
	public boolean isOver(PApplet pApplet) {
		//raheemXConstraint = pApplet.constrain(x,offset,winWidth-(offset+rWidth));
		yPos = winHeight-50;
		if (pApplet.mouseX >= raheemXConstraint && pApplet.mouseX <= raheemXConstraint+rWidth && 
			pApplet.mouseY >= yPos && pApplet.mouseY <= yPos+rHeight) {
			isOver = true;
		} else {
			isOver = false;
		}
		return isOver;
	}
	
	//fix
	/** DRAW RAHEEM */
	public void render(PApplet pApplet) {
		//raheemXConstraint = pApplet.constrain(x,offset,winWidth-(offset+rWidth));
		yPos = winHeight-50;
		pApplet.rect(raheemXConstraint,yPos,rWidth,rHeight);
	}
}


