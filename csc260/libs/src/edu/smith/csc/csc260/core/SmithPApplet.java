package edu.smith.csc.csc260.core;

import java.util.concurrent.ConcurrentLinkedQueue;

import processing.core.PApplet;
import edu.smith.csc.csc260.spites.Sprite;

public class SmithPApplet extends PApplet {
	
	private static final long serialVersionUID = 1L; // added to avoid warning message
	
	long lastTime;
	
	ConcurrentLinkedQueue<Sprite> renderList = new ConcurrentLinkedQueue<Sprite>(); // list of sprites to draw
	
	public void setup() {
		lastTime = System.currentTimeMillis();
	}
	
	public void draw() {
		
		render();
		// there will be other stuff here in the future
	}
	
	public void size(int w, int h) {
		super.size(w, h, P2D);
	}
	
	public void render() {
		long curTime = System.currentTimeMillis();
		long elapsedTime = curTime - lastTime;
		
		for(Sprite sprite : renderList) {
			sprite.display(this, curTime, lastTime, elapsedTime);
		}
	}
	
	public void addSprite(Sprite sprite) {
		renderList.add(sprite);
	}
	public void removeSprite(Sprite sprite) {
		renderList.remove(sprite);
	}
}
