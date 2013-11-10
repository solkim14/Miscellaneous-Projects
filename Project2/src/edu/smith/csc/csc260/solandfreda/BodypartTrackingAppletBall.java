package edu.smith.csc.csc260.solandfreda;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;
import edu.smith.csc.csc260.core.SmithPApplet;
import fisica.FBox;
import fisica.FCircle;
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
	AudioPlayer player;
	
	String[] neutralSounds = new String[7];
	String[] owwSounds = new String[6];
	
	boolean userPresent = false;
	public FWorld world;
	//FCircle[] spriteArray = new FCircle(3);
	FBox leftHandSprite;
	FBox rightHandSprite;
	//FCircle headSprite = new FCircle(25);
	int ballCount = 2;
	int i = 5;
	FCircle ball;
	
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
		//neutralSounds[1] = "still1";
		//owwSounds[1] = "move1";
		
		for (int i=0; i<7; i++) {
			neutralSounds[i] = "still"+i+".wav";
		}
		
		for (i=0; i<6; i++) {
			owwSounds[i] = "move"+i+".wav";
		}
		
		
		//fisica setup
		Fisica.init(this);

		world = new FWorld();
		world.setEdges(this, 0);
	    
	    ball = new FCircle(150);
	    ball.attachImage(ballNeutral); //edit
	    ball.setPosition(width/2, height-85);
	    ball.setRestitution((float).3);
	    ball.setDensity(10);
	    ball.setRotatable(false);
	    ball.setFriction(25);
	    ball.setNoStroke();
	    ball.setFill(100, 0, 120);
	    world.add(ball);
	    
	    
	    rightHandSprite = new FBox(150,150);
	    rightHandSprite.setPosition(200, height/6);
	    rightHandSprite.setRestitution((float).25);
	    rightHandSprite.setNoStroke();
	    rightHandSprite.setRotatable(false);
	    rightHandSprite.setFill(50, 80, 120);
	    rightHandSprite.setName("RIGHT");
	    world.add(rightHandSprite);
	    
	    
		System.out.println("SET UP!!!");
		  
		  //world.setEdgesRestitution(0.0);
	}
	
	public void onNewUser(SimpleOpenNI curContext, int userId) {
		userPresent = true;
		simpleOpenNI.startTrackingSkeleton(userId);
		
		Vector<BodypartTrackingSpriteBall> newTracks = new Vector<BodypartTrackingSpriteBall>();
		BodypartTrackingSpriteBall bts = new BodypartTrackingSpriteBall(this, userId, SimpleOpenNI.SKEL_LEFT_HAND);
		//world.add(leftHandSprite);
		addSprite(bts);
		newTracks.add(bts);
		
		bts = new BodypartTrackingSpriteBall(this, userId, SimpleOpenNI.SKEL_RIGHT_HAND);
		//world.add(rightHandSprite);
		addSprite(bts);
		newTracks.add(bts);

		bts = new BodypartTrackingSpriteBall(this, userId, SimpleOpenNI.SKEL_HEAD);
		addSprite(bts);
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
				//rightHandSprite.setPosition(convertedRightHand.x, convertedRightHand.y);
				//leftHandSprite.setPosition(convertedLeftHand.x, convertedLeftHand.y);
			}
			
			leftHandToHeadDist = leftHand.dist(head); //in cm?
			//System.out.println(leftHandToHeadDist);
			
		}
		if (!ball.isResting()) {
			ball.dettachImage();
		    ball.attachImage(ballOww); //edit
		    
		    //player = minim.loadFile(owwSounds[generator.nextInt(6)], 2048);
		    //player = minim.loadFile("move1.wav", 2048);
			//player.play();
		} else if (ball.isResting()) {
			ball.dettachImage();
		    ball.attachImage(ballNeutral); //edit
		    
		    //player = minim.loadFile("still1.wav", 2048);
		    //player = minim.loadFile(neutralSounds[generator.nextInt(6)], 2048);
			//player.play();
		}
		
		world.step();
		world.draw(this);
		super.draw();
	}
}
