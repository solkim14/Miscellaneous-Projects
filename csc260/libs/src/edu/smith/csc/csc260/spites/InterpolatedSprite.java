package edu.smith.csc.csc260.spites;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.interpolators.pointInterpolators.PointInterpolator;
import edu.smith.csc.csc260.interpolators.scalarInterpolators.ScalarInterpolator;

public class InterpolatedSprite extends Sprite {
	PointInterpolator locationInterpolator;
	ScalarInterpolator angleInterpolator;
	
	public PointInterpolator getLocationInterpolator() {
		return locationInterpolator;
	}
	public void setLocationInterpolator(PointInterpolator locationInterpolator) {
		this.locationInterpolator = locationInterpolator;
	}
	public ScalarInterpolator getAngleInterpolator() {
		return angleInterpolator;
	}
	public void setAngleInterpolator(ScalarInterpolator angleInterpolator) {
		this.angleInterpolator = angleInterpolator;
	}

	public void updatePosition(long curTime, long lastTime, long elapsedTime) {
		if(locationInterpolator != null) {
			locationInterpolator.calcT(curTime);
			location = locationInterpolator.getPoint();
		}
		if(angleInterpolator != null) {
			angleInterpolator.calcT(curTime);
			angle = angleInterpolator.getScalar();
		}
	}
	public void render(SmithPApplet pApplet) {
		pApplet.rect(-10, -10, 20, 20);
	}
}
