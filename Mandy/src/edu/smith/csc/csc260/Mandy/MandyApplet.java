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
import ddf.minim.AudioSnippet;
import ddf.minim.Minim;
import gifAnimation.*;

/**TO DEBUG:
 * 
 * fix sound loops 
 *
 *
 */

public class MandyApplet extends PApplet { //REMOVE FOR PROCESSING
	private static final long serialVersionUID = 1L; //REMOVE FOR PROCESSING

	/** FIELDS */
	private boolean displayTitleCard = true;
	private Gif titleCard;
   	private String instructions1;
   	private String instructions2;

	
	private Mandy mandy;
	private float mandyXPos;
	private float mandyYPos;
	//private Raheem raheem;
	//private boolean over;
	//private boolean deadMandy = false; //for state changes
	
	//slider fields
	private ControlP5 radio;
	private Slider raheem;
	private int sliderOffsetWidth = 100;
	private int sliderOffsetHeight = 650;
	private float sliderVal;
	private float constrainedSliderVal;
	
	//image fields
	private PImage radioTic;
	private PImage img1Background;
	private PImage img1Window;
	private PImage img2;
	private PImage img3;
	 
	private int mode;
	private int transparency;
	
	//audio player
	Minim minim;
	AudioSnippet mode0Music;
	boolean mode0MusicPlay = false;
	AudioSnippet mode1Music;
	boolean mode1MusicPlay = false;
	AudioSnippet mode1Rain;
	boolean mode1RainPlay = false;
	AudioSnippet mode2Music;
	boolean mode2MusicPlay = false;
	AudioSnippet mode3Music;
	boolean mode3MusicPlay = false;

	
	//mode 1 - rain
	private boolean rain = false;
	int[] speed;
	int[] pos;


	
	/** CONSTRUCTOR */ //REMOVE CONSTRUCTOR FOR PROCESSING
	public MandyApplet() {
	}
	
	/** SETUP */
	public void setup() {
	    size(1024,768);
	    smooth();
	    
	    //title card
		titleCard  = new Gif(this, "titlecard-border.gif");
		titleCard.play();
       	instructions1 = "Use the arrow keys to change the station and 'space' to explore";
       	instructions2 = "[Press Space to Start]";

	    
	    //setup mandy
	    mandyXPos = width/2;
		mandyYPos = height/2+100;
	    mandy = new Mandy(mandyXPos,mandyYPos);
	    
	    radioTic = loadImage("radio-tics.png");
	    
	    //setup raheem and radio
	    //raheem = new Raheem(this,width/2+50,height,width);
	    sliderVal = (float)103.5;
	    constrainedSliderVal = constrain(sliderVal, (float)87.5, (float)108.0);
	    radio = new ControlP5(this);
	    raheem = new Slider(radio,"raheem")
	       .setPosition(sliderOffsetWidth, sliderOffsetHeight)
	       .setSize(width-(sliderOffsetWidth*2),50)
	       .setRange((float)87.5,(float)108.0) // values can range from big to small as well
	       .setValue(constrainedSliderVal)
	       .setSliderMode(Slider.FLEXIBLE)
	       ;
	    radio.getController("raheem").getValueLabel().align(ControlP5.LEFT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(-1000000);
	    radio.getController("raheem").getCaptionLabel().align(ControlP5.RIGHT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(-1000000);
	  
	    //faded images
	    transparency = 0;
	    img1Background = loadImage("mode1-background.png");
	    img1Window = loadImage("mode1-window.png");
	    img2 = loadImage("mode2-background.jpg");
	    img3 = loadImage("mode3-background.jpg");
	    
	    //audio setup
	    minim = new Minim(this);
	    mode0Music = minim.loadSnippet("mode0-static.wav");
	    mode1Music = minim.loadSnippet("mode1-music-v2.mp3");
	    mode1Rain = minim.loadSnippet("mode1-rain-v2.wav");
	    mode2Music = minim.loadSnippet("mode2-music.mp3");
	    mode3Music = minim.loadSnippet("mode3-music-v2.wav");
	    
	    //rain
	    speed = new int[150];
	    pos = new int[150];
	    for( int i = 0; i<100; i++) {
	      speed[i] = (int)(random(10, 30));
	      pos[i] = (int)(random(0,100));
	    }
	}
	
	/** DRAW */
	public void draw() {
		background(0);
		//raheem.render(this);
		
	    if ((raheem.getValue() >= 87) && (raheem.getValue() <= 90) && (transparency >= 0) && (transparency <= 255)) {
	    	mode = 1;
	    	//turn off static
	        if (mode0MusicPlay == true) {
	            mode0MusicPlay = false; 
	            mode0Music.pause();
	         }
	        
	        if (mode1MusicPlay == false) {
	            mode1MusicPlay = true; 
	            //mode1Music.loop();
	            mode1Music.play();
	        }
	    	
	    	//mandyXPos moves to 750
	    	if (mandy.getX() < 750) {
	    		mandyXPos = mandyXPos+2;
	    		mandy.setX(mandyXPos);
	    	} else if (mandy.getX() > 750) {
	    		mandyXPos = mandyXPos-2;
	    		mandy.setX(mandyXPos);
	    	} 
	    	
	    	//mandyYPos moves to 550
	    	if (mandy.getY() < 550) {
	    		mandyYPos = mandyYPos+1;
	    		mandy.setY(mandyYPos);
	    	} else if (mandy.getY() > 550) {
	    		mandyYPos = mandyYPos-1;
	    		mandy.setY(mandyYPos);
	    	} 
	    	
	        tint(255,transparency);
	        
	        image(img1Background, 0, 0);
	        
	        //image(radioTic,0,0); //figure out later
	        if (keyPressed) {
	            if (key == ' ') {
	            	if (rain == true) {
	            		rain = false;
	        	        mode1Rain.pause();
	        	        
	            	} else {
	            		rain = true;
	            		if (mode1Rain.position() == mode1Rain.length()) {
		            		System.out.println("IT'S EQUAL!!!");
	            			mode1Rain.close();	
	            			//mode1Rain.rewind();	
		            		//mode1Rain.play();	
	            		}
	            		mode1Rain.play();
	            		mode1Rain.loop(); //fix loop stuff
	            	}
	            }
	        }
	        
	        //System.out.println("Pos: " + mode1Rain.position());
	        //System.out.println("Len: " + mode1Rain.length());
	        
	        if (rain == true) {
	        	  for( int i = 0; i<150; i++) {
	        		    line(i* 11,pos[i], i * 11, pos[i] + speed[i]*10);
	        		    pos[i] += speed[i];
	        		    pos[i] = pos[i] % width;
	        		  }
	        }
	        
	        image(img1Window, 0, 0);
	        if (transparency < 255) {
	        	transparency = transparency + 5;
	        }
	    } else if ((raheem.getValue() >= 95) && (raheem.getValue() <= 100) && (transparency >= 0) && (transparency <= 255)) {
	    	mode = 2;
	    	//mandyXPos moves to 440
	    	
	    	//turn off static
	        if (mode0MusicPlay == true) {
	            mode0MusicPlay = false; 
	            mode0Music.pause();
	         }
	    	
	        if (mode2MusicPlay == false) {
	        	mode2MusicPlay = true; 
	        	//mode2Music.loop();
	        	mode2Music.play();
	        }
	        
	    	if (mandy.getX() < 440) {
	    		mandyXPos = mandyXPos+2;
	    		mandy.setX(mandyXPos);
	    	} else if (mandy.getX() > 440) {
	    		mandyXPos = mandyXPos-2;
	    		mandy.setX(mandyXPos);
	    	} 
	    	
	    	//mandyYPos moves to 425
	    	if (mandy.getY() < 425) {
	    		mandyYPos = mandyYPos+1;
	    		mandy.setY(mandyYPos);
	    	} else if (mandy.getY() > 425) {
	    		mandyYPos = mandyYPos-1;
	    		mandy.setY(mandyYPos);
	    	} 
	    	tint(255,transparency);
	    	image(img2, 0, 0);
	    	if (transparency < 255) {
	    		transparency = transparency + 5;
	    	}
	    } 
	    
	    //Work on 3rd mode if there is time
	    else if ((raheem.getValue() >= 105) && (raheem.getValue() <= 108) && (transparency >= 0) && (transparency <= 255)) {
	    	mode = 3;
	    	
	    	//turn off static
	        if (mode0MusicPlay == true) {
	            mode0MusicPlay = false; 
	            mode0Music.pause();
	         }
	         
	        if (mode3MusicPlay == false) {
	        	mode3MusicPlay = true; 
	        	//mode2Music.loop();
	        	mode3Music.play();
	        }
	        
	    	if (mandy.getX() < 250) {
	    		mandyXPos = mandyXPos+2;
	    		mandy.setX(mandyXPos);
	    	} else if (mandy.getX() > 250) {
	    		mandyXPos = mandyXPos-2;
	    		mandy.setX(mandyXPos);
	    	} 
	    	
	    	//mandyYPos moves to
	    	if (mandy.getY() < 550) {
	    		mandyYPos = mandyYPos+5;
	    		mandy.setY(mandyYPos);
	    	} else if (mandy.getY() > 500) {
	    		mandyYPos = mandyYPos-5;
	    		mandy.setY(mandyYPos);
	    	} 
	    	//
	    	tint(255,transparency);
	    	image(img3, 0, 0);
	    	if (transparency < 255) {
	    		transparency += 5; //NOTE: change all to +=
	    	}
	    } else {	
	    	//may or may not scrap moving back to default?
	    	/**
	    	//mandyXPos moves to width/2
	    	if (mandy.getX() < width/2) {
	    		mandyXPos = mandyXPos+2;
	    		mandy.setX(mandyXPos);
	    	} else if (mandy.getX() > width/2) {
	    		mandyXPos = mandyXPos-2;
	    		mandy.setX(mandyXPos);
	    	} 
	    	
	    	//mandyYPos moves to height/2+100
	    	if (mandy.getY() < height/2+100) {
	    		mandyYPos = mandyYPos+1;
	    		mandy.setY(mandyYPos);
	    	} else if (mandy.getY() > height/2+100) {
	    		mandyYPos = mandyYPos-1;
	    		mandy.setY(mandyYPos);
	    	} 
	    	*/
	    	noTint();
	        image(radioTic,0,0); //figure out later
	    	
	        if (mode0MusicPlay == false) {
	            mode0MusicPlay = true; 
	            //mode1Music.loop();
	            mode0Music.play();
	        }
	    	
	        if (displayTitleCard) {
		        if (keyPressed) {
		            if (key == ' ') {
			      		displayTitleCard = false;
			    		titleCard.stop();
		            }
		       	}		  	
		       	image(titleCard,140,-15);
		       	text(instructions1, width/2-175, height/2+205);  // Text wraps within text box
		       	text(instructions2, width/2-63, height/2+220);  // Text wraps within text box
		       	
	        } else {
		    	tint(255,transparency);
		    	if (mode == 1) {
			        image(img1Background, 0, 0);
			        image(img1Window, 0, 0);
			        
			        if (mode1MusicPlay == true) {
			            mode1MusicPlay = false; 
			            mode1Music.pause();
			         }
			        
	            	if (rain == true) {
	            		rain = false;
	        	        mode1Rain.pause();   
	            	}
			    } else if (mode == 2) {
		    		image(img2, 0, 0);
		    		
			        if (mode2MusicPlay == true) {
			        	mode2MusicPlay = false; 
			            mode2Music.pause();
			         }
		    	} if (mode == 3) {
		    		image(img3, 0, 0);
		    		
			        if (mode3MusicPlay == true) {
			        	mode3MusicPlay = false; 
			            mode3Music.pause();
			         }
		    	}
	
		    	if (transparency > 0) {
		    		transparency = transparency - 5;
		    	}
	        } 
	    }
	    //mandy.setTint(mode);
        //image(radioTic,0,0); //figure out later
		//mandy title card
	    mandy.render(this);	    
	}
	
	public void mousePressed() {
	}
		
	//public void key
	
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
	
	public void keyPressed() {
		if (!displayTitleCard) {
		  if (key == CODED) {
		    if (keyCode == LEFT) {
		    	constrainedSliderVal -= .2;
		    	raheem.setValue(constrainedSliderVal);
		    	System.out.println(constrainedSliderVal);
		    } else if (keyCode == RIGHT) {
		    	constrainedSliderVal += .2;
		    	raheem.setValue(constrainedSliderVal);
		    	System.out.println(constrainedSliderVal);
		    } 
		  } 
		}
	}
	
	 public void stop() {
	 //player.close();
	 mode1Music.close();
	 minim.stop();
	 super.stop();
	 } 
}

