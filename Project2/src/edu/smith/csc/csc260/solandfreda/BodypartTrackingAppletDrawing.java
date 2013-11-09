package edu.smith.csc.csc260.solandfreda;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.util.Point;
import fisica.FCircle;
import fisica.FWorld;
import fisica.Fisica;

public class BodypartTrackingAppletDrawing extends SmithPApplet {
	private static final long serialVersionUID = 1L;
	public SimpleOpenNI  simpleOpenNI;
	ConcurrentHashMap<Integer, Vector<BodypartTrackingSpriteDrawing>> tracks = new ConcurrentHashMap<Integer, Vector<BodypartTrackingSpriteDrawing>>();

	/** Fields */
	Boolean userPresent = false;
	float distance;
	Vector<BodypartTrackingSpriteDrawing> newTracks = new Vector<BodypartTrackingSpriteDrawing>();
	Point rightHandPos;
	Point headPos;
	
	//fisica fields
	public FWorld world;
	int ballCount = 2;

	
	public void setup() {
		//context = new SimpleOpenNI(this);
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
		
		//fisica setup
		Fisica.init(this);

		world = new FWorld();
		world.setEdges();
		
	}

		public void onNewUser(SimpleOpenNI curContext, int userId) {
		simpleOpenNI.startTrackingSkeleton(userId);
		
		BodypartTrackingSpriteDrawing bts = new BodypartTrackingSpriteDrawing(this, userId, SimpleOpenNI.SKEL_LEFT_HAND);
		addSprite(bts);
		newTracks.add(bts);
		
		bts = new BodypartTrackingSpriteDrawing(this, userId, SimpleOpenNI.SKEL_RIGHT_HAND);
		addSprite(bts);
		newTracks.add(bts);

		bts = new BodypartTrackingSpriteDrawing(this, userId, SimpleOpenNI.SKEL_HEAD);
		addSprite(bts);
		newTracks.add(bts);		

		tracks.put(userId, newTracks);
		System.out.println("THERE YOU ARE");
		userPresent = true;	
	}

	public void onLostUser(SimpleOpenNI curContext, int userId) {

		Vector<BodypartTrackingSpriteDrawing> btsVec = tracks.get(userId);
		for(BodypartTrackingSpriteDrawing bts : btsVec) {
			removeSprite(bts);
		}
		userPresent = false;
		System.out.println("YOU'RE GONE!");

	}
	
	PVector head = new PVector();
	PVector leftHand = new PVector();
	PVector rightHand =new PVector();
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
			confRight = simpleOpenNI.getJointPositionSkeleton(user, SimpleOpenNI.SKEL_RIGHT_HAND, rightHand);
			leftHandToHeadDist = leftHand.dist(head); //in cm?
			System.out.println(leftHandToHeadDist);
			
			if (leftHandToHeadDist < threshold) {
				//simpleOpenNI.disableRGB();
				System.out.println("ON YO HEAD!!!");
			}
		}
		world.step();
		world.draw();
		super.draw();
	}
}

