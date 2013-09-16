package edu.smith.csc260.skim;

import edu.smith.csc260.haiku.AbstractHaiku;
import edu.smith.csc260.haiku.Printable;

public class Haiku extends AbstractHaiku implements Printable {

	/** Constructor */
	public Haiku() {
	}

	@Override
	public void print() {
		super.printHaiku(); //call print haiku
	}

	@Override
	public String getLine1() {
		String line1 = "Aw, crap. I forgot";
		return line1;
	}

	@Override
	public String getLine2() {
		String line2 = "all my coding this summer.";
		return line2;
	}

	@Override
	public String getLine3() {
		String line3 = "Welp, here goes nothing.";
		return line3;
	}
	
	@Override
	public String toString() {
		return getLine1() + "\n" + 
			getLine2() + "\n" +
			getLine3();
	}
	

	public static void main(String[] args) {
		Haiku haiku = new Haiku();
		haiku.print();
	}
}
