/**
 * File: MandyApplet.java
 * Author: Sol Kim
 * Description:
 * Version: 2013 Nov 26
 */

package edu.smith.csc.csc260.Mandy; //REMOVE FOR PROCESSING
import processing.core.PApplet; //REMOVE FOR PROCESSING
import processing.core.PImage;
import controlP5.*;

public class MandyApplet extends PApplet { //REMOVE FOR PROCESSING
	private static final long serialVersionUID = 1L; //REMOVE FOR PROCESSING

	/** FIELDS */
	private Mandy mandy;
	//private Raheem raheem;
	//private boolean over;
	
	//slider fields
	private ControlP5 radio;
	private Slider raheem;
	private int sliderOffsetWidth = 100;
	private int sliderOffsetHeight = 650;
	
	//image fields
	private PImage img1;
	private PImage img2;
	private PImage img3;
	 
	private int mode;
	private int transparency1;

	
	/** CONSTRUCTOR */ //REMOVE CONSTRUCTOR FOR PROCESSING
	public MandyApplet() {
	}
	
	/** SETUP */
	public void setup() {
	    size(1024,768);
	    smooth();
	    
	    //setup mandy
	    mandy = new Mandy(height/2,width/2);
	    
	    //setup raheem and radio
	    //raheem = new Raheem(this,width/2+50,height,width);
	    radio = new ControlP5(this);
	    raheem = new Slider(radio,"raheem")
	       .setPosition(sliderOffsetWidth, sliderOffsetHeight)
	       .setSize(width-(sliderOffsetWidth*2),50)
	       .setRange((float)87.5,(float)108.0) // values can range from big to small as well
	       .setValue((float)103.5)
	       .setSliderMode(Slider.FLEXIBLE)
	       ;
	    radio.getController("raheem").getValueLabel().align(ControlP5.LEFT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(-1000000);
	    radio.getController("raheem").getCaptionLabel().align(ControlP5.RIGHT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(-1000000);
	  
	    //faded images
	    transparency1 = 0;
	    img1 = loadImage("blue.jpeg");
	    img2 = loadImage("green.jpeg");
	    img3 = loadImage("yellow.jpeg");
	}
	
	/** DRAW */
	public void draw() {
	    background(0);
	    mandy.render(this);
	    //raheem.render(this);
	    
	    if ((raheem.getValue() >= 87) && (raheem.getValue() <= 90) && (transparency1 >= 0) && (transparency1 <= 255)) {
	        mode = 1;
	        tint(255,transparency1);
	        image(img1, 0, 0);
	        if (transparency1 < 255) {
	          transparency1 = transparency1 + 5;
	        }
	      } else if ((raheem.getValue() >= 95) && (raheem.getValue() <= 100) && (transparency1 >= 0) && (transparency1 <= 255)) {
	        mode = 2;
	        tint(255,transparency1);
	        image(img2, 0, 0);
	        if (transparency1 < 255) {
	          transparency1 = transparency1 + 5;
	        }
	      } else if ((raheem.getValue() >= 105) && (raheem.getValue() <= 108) && (transparency1 >= 0) && (transparency1 <= 255)) {
	        mode = 3;
	        tint(255,transparency1);
	        image(img3, 0, 0);
	        if (transparency1 < 255) {
	          transparency1 = transparency1 + 5;
	        }
	      } else {
	        tint(255,transparency1);
	        if (mode == 1) {
	          image(img1, 0, 0);
	        } else if (mode == 2) {
	          image(img2, 0, 0);
	        } if (mode == 3) {
	          image(img3, 0, 0);
	        }
	        if (transparency1 > 0) {
	          transparency1 = transparency1 - 5;
	        }
	      }
	}
	
	public void mousePressed() {
	}
		
	public void mouseDragged() {
		//if (raheem.isOver(this)) {
			//raheem.setX(mouseX);
		//}
	}

	public void mouseReleased() {
	}
	
	/** PRESENT MODE */ //REMOVE FOR PROCESSING
	public boolean sketchFullScreen() {
		return true;
	}
}

