package edu.smith.csc.csc260.solandfreda;

import processing.core.PVector;
import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Color;
import fisica.*;

public class BodypartTrackingSpriteBall extends Sprite {
	BodypartTrackingAppletBall handView;
	int id;
	int bodyPart;
	float conf = 0;

	public BodypartTrackingSpriteBall(BodypartTrackingAppletBall handView, int id, int bodyPart) {
		super();
		this.handView = handView;
		this.id = id;
		this.setFill(new Color((float)Math.random() * 255f, (float)Math.random() * 255f, (float) Math.random() * 255f, 200f));
		this.bodyPart = bodyPart;
	}

	@Override
	public void render(SmithPApplet pApplet) {
		if(conf > 0)
			pApplet.ellipse(0, 0, 15, 15);
		
			//instead of ellipse to pApplet need to add FBody to world
			//pApplet.world.add(FCircle(25));
			//add to world
	}


	PVector pvec = new PVector();
	PVector pvec2d = new PVector();
	public void updatePosition(SmithPApplet pApplet, long curTime, long lastTime, long elapsedTime) {
		conf = handView.simpleOpenNI.getJointPositionSkeleton(id, bodyPart, pvec);

		if (conf != 0) {
			handView.simpleOpenNI.convertRealWorldToProjective(pvec,pvec2d);
			this.location.set(pvec2d.x, pvec2d.y, 0);

		}
	}
	
	

}
