package edu.smith.cs.csc260.solkim;

import edu.smith.csc.csc260.interpolation.AbstractInterpolator;
import edu.smith.csc.csc260.interpolation.easing.EasingFunction;
import edu.smith.csc.csc260.interpolators.pointInterpolators.PointInterpolator;
import edu.smith.csc.csc260.util.Point;

public class RainbowInterpolator extends AbstractInterpolator implements
		PointInterpolator {

	public Point center;
	public float radius;

	/** Constructor */
	public RainbowInterpolator(Point center, float radius, EasingFunction easing) {
		super(easing);
		this.center = center;
		this.radius = radius;
	}

	@Override
	public Point getPoint() {
		float angle = easing.getT() * (float)Math.PI * 2.0f;
		float x = (float)Math.cos(angle) * radius + center.getX();
		float y = (float)Math.sin(angle) * radius + center.getY();
		
		return new Point(x,y,0);
	}
}
