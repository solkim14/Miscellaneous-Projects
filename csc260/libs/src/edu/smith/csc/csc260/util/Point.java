package edu.smith.csc.csc260.util;

public class Point {
	float x;
	float y;
	float z;

	
	public Point() {
		this(0,0,0);
	}
	
	public Point(Point p) {
		this(p.x, p.y, p.z);
	}
	
	public Point(float x, float y) {
		this(x, y, 0);
	}

	public Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }
	
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setZ(float z) { this.z = z; }
	
	public void set(Point p) {
		x = p.x;
		y = p.y;
		z = p.z;
	}
}
