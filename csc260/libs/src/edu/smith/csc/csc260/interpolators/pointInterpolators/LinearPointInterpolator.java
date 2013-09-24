package edu.smith.csc.csc260.interpolators.pointInterpolators;

import edu.smith.csc.csc260.interpolators.InterpolatorBase;
import edu.smith.csc.csc260.util.Point;

public class LinearPointInterpolator extends InterpolatorBase implements PointInterpolator {
	Point startPoint;
	Point endPoint;
	Point curPoint;

	public LinearPointInterpolator(long startTime, long endTime, int loops, Point startPoint, Point endPoint) {
		super(startTime, endTime, loops);
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.curPoint = new Point(startPoint);
	}

	public Point getPoint() {

		float oneMinT = 1.0f-t;
		curPoint.set(
				startPoint.getX() * oneMinT + endPoint.getX() * t, 
				startPoint.getY() * oneMinT + endPoint.getY() * t, 
				startPoint.getZ() * oneMinT + endPoint.getZ() * t);
				
		return curPoint;
		
	}

}
