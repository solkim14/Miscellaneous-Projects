package edu.smith.csc.csc260.solandfreda;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import SimpleOpenNI.SimpleOpenNI;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.util.Point;

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
	
	public void draw() {
		simpleOpenNI.update();
		image(simpleOpenNI.rgbImage(), 0, 0);
		
		if (userPresent == true) {
			rightHandPos = newTracks.get(1).getPosition(); // right hand
			headPos = newTracks.get(2).getPosition(); // head
			
			System.out.println(rightHandPos.getX());
			//System.out.println(headPos.getX());
			
			//distance = findDistance(rightHandPos.getX(),rightHandPos.getY(),headPos.getX(),headPos.getY());
			//System.out.println(distance);
		}
		
		super.draw();
	}
	
	/** find distance between two points */
	public float findDistance(float f, float h, double x2, double y2) {

		float distance = (float)Math.sqrt(((int)(x2-f)*(x2-f)) + ((int)(y2-h)*(y2-h)));

		//System.out.println(distance);

		return distance;
	}
	
}
