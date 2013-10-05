package edu.smith.cs.csc260.solkim;

import edu.smith.csc.csc260.core.SmithPApplet;
import edu.smith.csc.csc260.interpolators.pointInterpolators.PointInterpolator;
import edu.smith.csc.csc260.interpolators.scalarInterpolators.ScalarInterpolator;
import edu.smith.csc.csc260.spites.Sprite;

public class RainbowSprite extends Sprite {

	public RainbowSprite() {
	}

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

	@Override
	public void updatePosition(SmithPApplet pApplet, long curTime, long lastTime, long elapsedTime) {
		if(locationInterpolator != null) {
			locationInterpolator.updateEasing(curTime);
			location = locationInterpolator.getPoint();
		}
		if(angleInterpolator != null) {
			angleInterpolator.updateEasing(curTime);
			angle = angleInterpolator.getScalar();
		}
	}
	
	@Override
	public void render(SmithPApplet pApplet) {
		pApplet.rect(-125,-125,200,200);
	}
}
