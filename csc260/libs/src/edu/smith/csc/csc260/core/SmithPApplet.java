package edu.smith.csc.csc260.core;

import java.util.concurrent.ConcurrentLinkedQueue;

import processing.core.PApplet;
import edu.smith.csc.csc260.spites.Sprite;
import edu.smith.csc.csc260.util.Point;

public class SmithPApplet extends PApplet {
	
	private static final long serialVersionUID = 1L; // added to avoid warning message
	
	long startTime;
	long lastTime;
	
	ConcurrentLinkedQueue<Sprite> renderList = new ConcurrentLinkedQueue<Sprite>(); // list of sprites to draw
	ConcurrentLinkedQueue<Sprite> newSpriteList = new ConcurrentLinkedQueue<Sprite>(); // list of sprites to draw
	
	Point bgColor = null;
	
	public void setup() {
		startTime = System.currentTimeMillis();
		lastTime = 0;
	}
	
	public void draw() {
		render();
		// there will be other stuff here in the future
	}
	
	public void setBackgroundColor(Point color) {
		this.bgColor = color;
	}
	public Point getBackgroundColor() {
		return bgColor;
	}
	
	public void setNoBackgroundColor(boolean b) {
		if(b) {
			bgColor = null;
		} else {
			if (bgColor == null) {
				bgColor = new Point(0,0,0);				
			}
		}
	}
	public boolean getNoBackgroundColor() {
		return bgColor == null;
	}
	public void size(int w, int h) {
		super.size(w, h, P2D);
	}
	
	public void render() {
		long curTime = System.currentTimeMillis() - startTime;
		long elapsedTime = curTime - lastTime;

		if(bgColor != null) {
			background(bgColor.getX(), bgColor.getY(), bgColor.getZ());
		}
		for(Sprite sprite : newSpriteList) {
			sprite.setup(this);
			renderList.add(sprite);
		}
		newSpriteList.clear();
		
		for(Sprite sprite : renderList) {
			sprite.display(this, curTime, lastTime, elapsedTime);
		}
		lastTime = curTime;
	}
	
	public void addSprite(Sprite sprite) {
		newSpriteList.add(sprite);
	}
	public void removeSprite(Sprite sprite) {
		newSpriteList.remove(sprite);
		renderList.remove(sprite);
	}
}
