package edu.smith.csc.csc260.Humdrum;

/**
 * File: Humdrum.pde
 * Author: Sol Kim
 * Description:
 * Version: 2013 Oct 24
*/

import edu.smith.csc.csc260.core.SmithPApplet;
import fisica.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.*;


public class Humdrum extends SmithPApplet {
	private static final long serialVersionUID = 1L;

	/** Constructor */
	public Humdrum() {
	}

	//write out files
	String startCounter = "startCounter.txt";
	String endCounter = "endCounter.txt";
	String why = "why.txt";

	//audio player
	Minim minim;
	AudioPlayer player;

	//game mode booleans
	boolean start = false;
	boolean end = false;
	boolean humdrum = false;
	boolean override = false; //OVERRIDE METHOD

	//dialogue fields
	ArrayList<PImage> dialogues = new ArrayList<PImage>();
	int numOfDialogues = 96; //number of dialogues in
	String imageNum;
	String imageName;
	PImage image;
	PImage drawImage;
	int counter = 0; //initialize counter
	long lastTime = 0;

	PImage startScreen;
	PImage whiteRoom;
	PImage jar;

	//array of cubes
	int numOfCubes = 650; //number of balls (650)
	FBox[] bouncingCubes = new FBox[numOfCubes]; //edit

	//Fisica objects
	FWorld world;
	FBox vortex;
	ArrayList bodies; //vortex test
	FBox jarBox;

	//font fields
	PFont font;
	PFont finalFont1;
	PFont finalFont2;
	int indent;
	String typing = "";
	String userInput = "";

	public void setup() {
	  collectData(startCounter, "1+"); //counts how many people start
	  
	  size(600, 850);
	  
	  //audio player
	  minim = new Minim(this);
	  player = minim.loadFile("tick.wav", 2048);
	  player.play();
	  player.loop();
	  
	  
	  //dialogues
	  for (int i=0; i<numOfDialogues; i++) {
	    imageNum = Integer.toString(i);
	    imageName = imageNum + ".png";
	    image = loadImage(imageName);
	    dialogues.add(image);
	  }
	  
	  //mise-en-scene images
	  startScreen = loadImage("start-screen-final.png");
	  whiteRoom = loadImage("white-room.png");
	  jar = loadImage("jar.png");
	  smooth();
	  
	  //set up end font
	  font = createFont("Arial",24,true);
	  
	  //set up final fonts
	  finalFont1 = createFont("Courier",100,true);
	  finalFont2 = createFont("Courier",18,true);

	  Fisica.init(this);

	  //set up world
	  world = new FWorld();
	  world.setEdges();      //boxes in the window. objects can not leave
	  world.setGravity(0, 2500);
	  
	  //when balls touch the vortex they are removed from the world; a part of the jar
	  vortex = new FBox(35, 5);
	  vortex.setPosition(width/2, height - 98);
	  vortex.setStaticBody(true);
	  vortex.setGrabbable(false); // unable to be moved by mouse
	  vortex.setFill(255, 255, 255, 0);
	  vortex.setNoStroke();
	  world.add(vortex);
	  
	  //this is the shape and structure of the jar; balls cannot go through it
	  jarBox = new FBox(50, 50);
	  jarBox.attachImage(jar); //edit
	  jarBox.setPosition(width/2, height - 75);
	  jarBox.setStaticBody(true);
	  jarBox.setGrabbable(false); // unable to be moved by mouse
	  jarBox.setFill(255, 255, 255, 0);
	  jarBox.setNoStroke();
	  world.add(jarBox);	  
	  
	  //set up cubes
	  for (int i=0; i<numOfCubes; i++) {
	    bouncingCubes[i] = new FBox(15,15); //edit
	    //float sz = random(30, 60);
	    bouncingCubes[i].setPosition(random(0+30, width-30), 800);
	    bouncingCubes[i].setStroke(0);
	    bouncingCubes[i].setStrokeWeight((float)0.5);
	    bouncingCubes[i].setFill(255, 255, 255, 150); 
	    bouncingCubes[i].setVelocity(0, 0);
	    bouncingCubes[i].setRestitution((float)0.01);
	    bouncingCubes[i].setDamping((float)0.1);
	    bouncingCubes[i].setRotatable(false);
	    world.add(bouncingCubes[i]);
	  }
	}

	public void draw() {
	  //checks to see how many bodies are in the world
	  bodies = world.getBodies();
	  background(whiteRoom);

	  if (start == false) {
	    // start screen
	    image(startScreen, 0, 0);
	  } else if (humdrum == true) {
	    background(0);
	    textFont(finalFont1);
	    fill(255);
	    text("Humdrum", 88, 425);
	    textFont(finalFont2);
	    fill(255);
	    text("By Sol Kim", 225, 475); //OVERRIDE METHOD (see below)
	  } else if (bodies.size() == 6 || override == true) {     //if there are only 6 bodies left then all of the cubes are gone
	    end = true;
	    
	    indent = 125;
	    textFont(font);
	    fill(0);        //make font black
	  
	    text("You finished.", indent, 200);
	    text("Why?", indent, 250);
	    text(typing,indent,300); 
	   
	  } else {    
	    if (counter<dialogues.size()) {
	      drawImage = dialogues.get(counter);
	      if (millis() - lastTime > 3000 ) {
	        counter = counter+1;
	        lastTime = millis();
	      }
	      image(drawImage, 135, 175);
	    } else {
	      drawImage = dialogues.get(0);
	      image(drawImage, 135, 175);
	    }
	    world.step();  
	    world.draw();
	  }
	}

	//handles the removal of objects from the world when they touch the vortex
	public void contactStarted(FContact c) {
	  FBody cube = null;
	  if (c.getBody1() == vortex) {
	    cube = c.getBody2();
	  } else if (c.getBody2() == vortex) {
	    cube = c.getBody1();
	  }
	  if (cube == null) {
	    return;
	  }
	  world.remove(cube);
	}


	public void keyPressed() {
	  //trigger start
	  if (start != true) {
	    start = true;
	    //add one to start file
	  }
	  
	  
	  //OVERRIDE METHOD
	  if (start == true && end != true) {
	    if (key == CODED) {
	      if (keyCode == UP) {
	        override = true;
	      }
	    }
	  }
	  
	  
	  if (end == true) {
	    //add one to end file
	    if (key == '\n' ) {
	      collectData(endCounter, "1+"); // counts how many people finished
	      //userInput = typing;
	      userInput = typing + "\n";

	      //save the user input into a file now
	      collectData(why, userInput);
	      
	      //trigger final humdrum display
	      humdrum = true;
	    } else {
	      typing = typing + key; //each character the user types is displayed
	    }
	  }
	}

	/** Writing out function */
	public void collectData(String file, String addText){
	  File testFile = new File(dataPath(file));
	  if(!testFile.exists()) {
	    createWriter(testFile);
	  }
	  try {
	    PrintWriter printedStatement = new PrintWriter(new BufferedWriter(new FileWriter(testFile, true)));
	    printedStatement.print(addText);
	    printedStatement.close();
	  } catch (IOException e) {
	    e.printStackTrace();
	  }
	}


	//presents sketch in full screen
	public boolean sketchFullScreen() {
	  return true;
	}
}
