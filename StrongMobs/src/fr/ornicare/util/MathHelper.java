package fr.ornicare.util;

public abstract class MathHelper {
	/**
	 * Randomize an int
	 * 
	 * @param i the integer
	 * @return an integer between 0 and i
	 */
	public static int randomize(int i) { return (int) (i*Math.random()+1);}
}
