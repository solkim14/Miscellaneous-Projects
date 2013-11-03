package edu.smith.csc.csc260.solandfreda;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import SimpleOpenNI.SimpleOpenNI;
import edu.smith.csc.csc260.core.SmithPApplet;
import fisica.*;

public class BodypartTrackingAppletBall extends SmithPApplet {
	private static final long serialVersionUID = 1L;
	public SimpleOpenNI  simpleOpenNI;
	ConcurrentHashMap<Integer, Vector<BodypartTrackingSpriteBall>> tracks = new ConcurrentHashMap<Integer, Vector<BodypartTrackingSpriteBall>>();

	/** Fields */
	public FWorld world;
	int ballCount = 2;
	
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
		
		//fisica setup
		  Fisica.init(this);

		  world = new FWorld();
		  world.setEdges();
		  //world.setEdgesRestitution(0.0);
		 
		  for (int i=0; i<ballCount; i++) {
		    FCircle b = new FCircle(25);
		    b.setPosition(map(i, 0, ballCount-1, 40, width-40), height/6);
		    //b.setRestitution(map(i, 0, ballCount-1, 0.0, 1.0));
		    b.setNoStroke();
		    b.setFill(map(i, 0, ballCount-1, 60, 255), 80, 120);
		    world.add(b);
		  }
	}
	
	public void onNewUser(SimpleOpenNI curContext, int userId) {
		simpleOpenNI.startTrackingSkeleton(userId);
		
		Vector<BodypartTrackingSpriteBall> newTracks = new Vector<BodypartTrackingSpriteBall>();
		BodypartTrackingSpriteBall bts = new BodypartTrackingSpriteBall(this, userId, SimpleOpenNI.SKEL_LEFT_HAND);
		addSprite(bts);
		newTracks.add(bts);
		
		bts = new BodypartTrackingSpriteBall(this, userId, SimpleOpenNI.SKEL_RIGHT_HAND);
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
	}
	
	public void draw() {
		simpleOpenNI.update();
		image(simpleOpenNI.rgbImage(), 0, 0);
		
		world.step();
		world.draw();
		
		super.draw();
	}
}
