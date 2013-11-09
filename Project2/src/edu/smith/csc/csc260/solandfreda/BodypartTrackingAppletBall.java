package edu.smith.csc.csc260.solandfreda;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;
import edu.smith.csc.csc260.core.SmithPApplet;
import fisica.FCircle;
import fisica.FWorld;
import fisica.Fisica;

public class BodypartTrackingAppletBall extends SmithPApplet {
	private static final long serialVersionUID = 1L;
	
	public SimpleOpenNI  simpleOpenNI;
	ConcurrentHashMap<Integer, Vector<BodypartTrackingSpriteBall>> tracks = new ConcurrentHashMap<Integer, Vector<BodypartTrackingSpriteBall>>();

	/** Fields */
	boolean userPresent = false;
	public FWorld world;
	//FCircle[] spriteArray = new FCircle(3);
	FCircle leftHandSprite;
	FCircle rightHandSprite;
	//FCircle headSprite = new FCircle(25);
	int ballCount = 2;
	int i = 5;
	FCircle ball;
	
	public void setup() {		// context = new SimpleOpenNI(this);
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
		
		//fisica setup
		Fisica.init(this);

		world = new FWorld();
		world.setEdges(this, 0);
	    
	    ball = new FCircle(25);
	    ball.setPosition(50, height/6);
	    ball.setRestitution((float).1);
	    ball.setNoStroke();
	    ball.setFill(100, 0, 120);
	    world.add(ball);
	    
	    rightHandSprite = new FCircle(25);
	    rightHandSprite.setPosition(50, height/6);
	    rightHandSprite.setRestitution((float).1);
	    rightHandSprite.setNoStroke();
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
				rightHandSprite.setPosition(convertedRightHand.x, convertedRightHand.y);
				//leftHandSprite.setPosition(convertedLeftHand.x, convertedLeftHand.y);
			}
			
			leftHandToHeadDist = leftHand.dist(head); //in cm?
			//System.out.println(leftHandToHeadDist);
			
		}
		world.step();
		world.draw(this);
		super.draw();
	}
}
