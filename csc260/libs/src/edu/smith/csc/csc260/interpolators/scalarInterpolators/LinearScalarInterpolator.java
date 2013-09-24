package edu.smith.csc.csc260.interpolators.scalarInterpolators;

import edu.smith.csc.csc260.interpolators.InterpolatorBase;

public class LinearScalarInterpolator extends InterpolatorBase implements ScalarInterpolator {
	float startValue;
	float endValue;
	
	public LinearScalarInterpolator(long startTime, long endTime, int loops, float startValue, float endValue) {
		super(startTime, endTime, loops);
		
		this.startValue = startValue;
		this.endValue = endValue;
	}

	@Override
	public float getScalar() {
		float oneMinT = 1.0f-t;
		return startValue * oneMinT + endValue * t;
	}

}
