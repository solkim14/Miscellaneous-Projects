package edu.smith.csc.csc260.solandfreda;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import ddf.minim.*;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;
import edu.smith.csc.csc260.core.SmithPApplet;
import fisica.FBody;
import fisica.FBox;
import fisica.FCircle;
import fisica.FMouseJoint;
import fisica.FWorld;
import fisica.Fisica;

public class BodypartTrackingAppletBall extends SmithPApplet {
	private static final long serialVersionUID = 1L;
	
	public SimpleOpenNI  simpleOpenNI;
	ConcurrentHashMap<Integer, Vector<BodypartTrackingSpriteBall>> tracks = new ConcurrentHashMap<Integer, Vector<BodypartTrackingSpriteBall>>();

	/** Fields */
	PImage ballNeutral;
	PImage ballOww;
	
	//audio player
	Minim minim;
	String[] oww;
	String[] neut;
	AudioSnippet[] oww2;
	AudioSnippet[] neut2;
	Random rand;
	
	boolean userPresent = false;
	public FWorld world;
	//FCircle[] spriteArray = new FCircle(3);
	FBox leftHandSprite;
//	FBox rightHandSprite;
	FBox rightHandSprite2;
	//FCircle headSprite = new FCircle(25);
	FBox bar;
	int ballCount = 2;
	int i = 5;
	FCircle ball;
	
	
	FBox[] sprites = new FBox[1];

	
	//draw() fields
	FMouseJoint rightJoint;
	
	PVector head = new PVector();
	PVector leftHand = new PVector();
	PVector rightHand = new PVector();
	PVector convertedRightHand = new PVector();
	PVector convertedLeftHand = new PVector();
	
	float confHead;
	float confLeft;
	float confRight;
	float leftHandToHeadDist;
	float threshold = 170;
	Random generator = new Random();
	
	long lastSoundTime = millis() + 10000;
	
	
	public void setup() {		// context = new SimpleOpenNI(this);
	
		//size(1024, 780);
		//simpleOpenNI setup
		simpleOpenNI = new SimpleOpenNI(this,SimpleOpenNI.RUN_MODE_MULTI_THREADED);
		if(simpleOpenNI.isInit() == false) {
			System.out.println("Can't init SimpleOpenIN, maybe the camera is not connected!"); 
			exit();
			return;
		}
		simpleOpenNI.setMirror(false);
		simpleOpenNI.enableDepth(); // needs to be enable for skeletons to work right
		simpleOpenNI.enableRGB();
		simpleOpenNI.enableUser();

		size(simpleOpenNI.rgbWidth(), simpleOpenNI.rgbHeight()); 
		smooth();
		
		//image setup
		ballNeutral = loadImage("ballneutral.png");
		ballOww = loadImage("balloww.png");
		
		//sound setup
		minim = new Minim(this);
		
		 //create the file array

		  oww = new String[6];

		  neut = new String[7];

		  oww2 = new AudioSnippet[6];

		  neut2 = new AudioSnippet[7];
		  
		  //crappy hardcoding in the sounds
		  oww[0]="move1.wav";

		  oww[1]="move2.wav";

		  oww[2]="move3.wav";

		  oww[3]="move4.wav";

		  oww[4]="move5.wav";

		  oww[5]="move6.wav";

		  

		  neut[0]="still1.wav";

		  neut[1]="still2.wav";

		  neut[2]="still3.wav";

		  neut[3]="still4.wav";

		  neut[4]="still5.wav";

		  neut[5]="still6.wav";

		  neut[6]="still7.wav";
		  
		  rand = new Random(); 
		  
		  for(int j=0; j < oww.length; j++){

		        oww2[j] = minim.loadSnippet(oww[j]);

		    }

		    for(int k=0; k < neut.length; k++){

		        neut2[k] = minim.loadSnippet(neut[k]);

		    }
		
		
		//fisica setup
		Fisica.init(this);

		world = new FWorld();
		world.setEdges(this, 0);
	    
	    ball = new FCircle(150);
	    ball.attachImage(ballNeutral); //edit
	    ball.setPosition(width/2, height-85);
	    //ball.setPosition(width/2, height-200);
	    ball.setRestitution((float).3);
	    ball.setDensity(10);
	    ball.setRotatable(false);
	    ball.setFriction(25);
	    ball.setNoStroke();
	    ball.setFill(100, 0, 120);
	    world.add(ball);
	    
	    
	    bar = new FBox(width,25);
	    bar.setPosition(width/2, height-5);
	    bar.setRestitution((float).25);
	    bar.setNoStroke();
	    bar.setStaticBody(true);
	    bar.setGrabbable(false);
	    bar.setRotatable(false);
	    bar.setFill(0,0,0,0);
	    world.add(bar);
	    
	    rightHandSprite2 = new FBox(50,50);
	    //rightHandSprite2.setPosition(200, height/6);
	    rightHandSprite2.setRestitution((float).25);
	    rightHandSprite2.setNoStroke();
	    rightHandSprite2.setRotatable(false);
	    rightHandSprite2.setFill(50, 80, 120);
	    //world.add(rightHandSprite);
	    
//	    rightHandSprite2.setStaticBody(true);
	    //world.add(rightHandSprite2);
	    //rightJoint = new FMouseJoint(ball,50,50);
	    //rightJoint.setFrequency(1);
	    //rightJoint.setDamping(0);
	    
	    //world.add(rightJoint);
	    
	    lastSoundTime = millis() + 5000;
		System.out.println("SET UP!!!");
		  
		//world.setEdgesRestitution(0.0);
	}
	
	
	public void onNewUser(SimpleOpenNI curContext, int userId) {
		userPresent = true;
		simpleOpenNI.startTrackingSkeleton(userId);
		
		Vector<BodypartTrackingSpriteBall> newTracks = new Vector<BodypartTrackingSpriteBall>();
		BodypartTrackingSpriteBall bts = new BodypartTrackingSpriteBall(this, userId, SimpleOpenNI.SKEL_LEFT_HAND);
		//world.add(leftHandSprite);
		//sprites.add(leftHandSprite);
		//addSprite(bts);
		newTracks.add(bts);
		
		bts = new BodypartTrackingSpriteBall(this, userId, SimpleOpenNI.SKEL_RIGHT_HAND);
		//world.add(rightHandSprite);
//		sprites[0] = rightHandSprite;
		//addSprite(bts);
		newTracks.add(bts);

		bts = new BodypartTrackingSpriteBall(this, userId, SimpleOpenNI.SKEL_HEAD);
		//addSprite(bts);
		newTracks.add(bts);

		tracks.put(userId, newTracks);
	}

	public void onLostUser(SimpleOpenNI curContext, int userId) {
		Vector<BodypartTrackingSpriteBall> btsVec = tracks.get(userId);
		for(BodypartTrackingSpriteBall bts : btsVec) {
			removeSprite(bts);
		}
		userPresent = false;
	}

	float x;
	float y;
	
	FBody test;
	
	//FMouseJoint rightJoint;
	public void draw() {
		simpleOpenNI.update();
		image(simpleOpenNI.rgbImage(), 0, 0);
		
		int[] users = simpleOpenNI.getUsers();
		for(int user : users) {
			confHead = simpleOpenNI.getJointPositionSkeleton(user, SimpleOpenNI.SKEL_HEAD, head);
			
			confLeft = simpleOpenNI.getJointPositionSkeleton(user, SimpleOpenNI.SKEL_LEFT_HAND, leftHand);
			simpleOpenNI.convertRealWorldToProjective(leftHand, convertedLeftHand);
			
			confRight = simpleOpenNI.getJointPositionSkeleton(user, SimpleOpenNI.SKEL_RIGHT_HAND, rightHand);
			simpleOpenNI.convertRealWorldToProjective(rightHand, convertedRightHand);
			
			
			if (userPresent) {
			    //rightJoint.setTarget(convertedRightHand.x, convertedRightHand.y);
			    //rightJoint.setTarget(ball.getX(), ball.getY());
				//rightJoint.setGrabbedBodyAndTarget(rightHandSprite, convertedRightHand.x, convertedRightHand.y);
		//		world.remove(rightHandSprite2);
//				rightHandSprite2.setPosition(convertedRightHand.x, convertedRightHand.y);
				float x = ball.getX();
				float y  = ball.getY();
				
				float d = dist(x, y, convertedRightHand.x, convertedRightHand.y);
				float dLeft = dist(x, y, convertedLeftHand.x, convertedLeftHand.y);

				if(d< 85) {
					float dx = x-convertedRightHand.x;
					float dy = y-convertedRightHand.y;
					
					float dInv = 1.0f/d;
					
					float contactX = x+ 75 *  dx * dInv;
					float contactY = y+ 75 *  dy * dInv;
					
					float fScale = 10000000.0f * dInv;
							
					float fx = fScale* (dx);
					float fy = fScale * (dy);
					
					ball.addForce(fx, fy, contactX, contactY);
					
				} else if(dLeft< 85) {
					
					float dxLeft = x-convertedLeftHand.x;
					float dyLeft = y-convertedLeftHand.y;
					
					float dInvLeft = 1.0f/dLeft;
					
					float contactXLeft = x+ 75 *  dxLeft * dInvLeft;
					float contactYLeft = y+ 75 *  dyLeft * dInvLeft;
					
					float fScale = 10000000.0f * dInvLeft;
					
					float fxLeft = fScale * (dxLeft);
					float fyLeft = fScale * (dyLeft);
					
					ball.addForce(fxLeft, fyLeft, contactXLeft, contactYLeft);
				}

		
		//		world.add(rightHandSprite2);

//				tidyUp(world, ball, rightHandSprite2);
				//leftHandSprite.setPosition(convertedLeftHand.x, convertedLeftHand.y);
			} else if (!userPresent) {
				//tidyUp(world, ball, rightHandSprite);
				cleanWorld(world, ball, bar);
				//world.remove(rightHandSprite);
			}
			
			leftHandToHeadDist = leftHand.dist(head); //in cm?
			//System.out.println(leftHandToHeadDist);
		}
		
		//if MOVING
		
		if (! ball.isResting()) {
			
			ball.dettachImage();
		    ball.attachImage(ballOww); //edit
		    
		    if (millis() - lastSoundTime >= 1500) {
		    	println("statement called");
			    int value = rand.nextInt(6);
			    oww2[value].rewind();
			    oww2[value].play();
			    lastSoundTime = millis();
		    	println("statement ended");
		    }
		    //print(value);
		} else {  // is resting
			ball.dettachImage();
		    ball.attachImage(ballNeutral); //edit
		    
		    if (millis() - lastSoundTime >= 1500) {
		    	println("statement called");
			    int value = rand.nextInt(6);
			    neut2[value].rewind();
			    neut2[value].play();
			    lastSoundTime = millis();
		    	println("statement ended");
		    }
		    //player = minim.loadFile("still1.wav", 2048);
		    //player = minim.loadFile(neutralSounds[generator.nextInt(6)], 2048);
			//player.play();
		}
		
		world.step();
		world.draw(this);
		//rightJoint.draw(this);
		//world.drawDebug();
		super.draw();
	}
	
	public void tidyUp(FWorld world, FBody ball, FBody rightHandSprite) {
		ArrayList<FBody> bodies;
		int numBodies;
		FBody bodyJar;
		FBody currentRight;
		
		currentRight = world.getBody(convertedRightHand.x, convertedRightHand.y);
		bodies = world.getBodies();
		numBodies = bodies.size();
		
		for (int i=0; i<numBodies; i++) {
			bodyJar = bodies.get(i);
			if (bodyJar != ball && bodyJar != rightHandSprite && bodyJar != currentRight && bodyJar != world.right &&
				bodyJar != world.left && bodyJar != world.top && bodyJar != world.bottom) {
				world.remove(bodyJar);
			}	
		}	
	}
	
	public void cleanWorld(FWorld world, FBody ball, FBody bar) {
		ArrayList<FBody> bodies;
		int numBodies;
		FBody bodyJar;
		
		bodies = world.getBodies();
		numBodies = bodies.size();
		
		for (int i=0; i<numBodies; i++) {
			bodyJar = bodies.get(i);
			if (bodyJar != ball && bodyJar != world.right && bodyJar != world.left
				&& bodyJar != world.top && bodyJar != world.bottom && bodyJar != bar) {
				world.remove(bodyJar);
			}	
		}
		//world.setEdges(this, 0);
	}
}
