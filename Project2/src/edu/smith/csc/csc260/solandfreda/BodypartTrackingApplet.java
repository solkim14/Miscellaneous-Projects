package edu.smith.csc.csc260.solandfreda;

import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import SimpleOpenNI.SimpleOpenNI;
import edu.smith.csc.csc260.core.SmithPApplet;

public class BodypartTrackingApplet extends SmithPApplet {
	private static final long serialVersionUID = 1L;
	public SimpleOpenNI  simpleOpenNI;
	ConcurrentHashMap<Integer, Vector<BodypartTrackingSprite>> tracks = new ConcurrentHashMap<Integer, Vector<BodypartTrackingSprite>>();

	public void setup() {		// context = new SimpleOpenNI(this);
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
	public void onNewUser(SimpleOpenNI curContext, int userId)
	{
		simpleOpenNI.startTrackingSkeleton(userId);
		
		Vector<BodypartTrackingSprite> newTracks = new Vector<BodypartTrackingSprite>();
		BodypartTrackingSprite bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_LEFT_HAND);
		addSprite(bts);
		newTracks.add(bts);
		
		bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_RIGHT_HAND);
		addSprite(bts);
		newTracks.add(bts);

		bts = new BodypartTrackingSprite(this, userId, SimpleOpenNI.SKEL_HEAD);
		addSprite(bts);
		newTracks.add(bts);

		tracks.put(userId, newTracks);
		
	}

	public void onLostUser(SimpleOpenNI curContext, int userId)
	{

		Vector<BodypartTrackingSprite> btsVec = tracks.get(userId);
		for(BodypartTrackingSprite bts : btsVec) {
			removeSprite(bts);
			
		}

	}
	public void draw()
	{
		simpleOpenNI.update();
		image(simpleOpenNI.rgbImage(), 0, 0);
		super.draw();
	}
}
