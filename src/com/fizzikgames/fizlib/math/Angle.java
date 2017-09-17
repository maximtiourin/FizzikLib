package com.fizzikgames.fizlib.math;

/**
 * Helper class for working with angles in both radians and degrees.
 * @author Maxim Tiourin
 * @version 1.00
 */
public class Angle {
	/**
	 * Returns the radian representation of the given angle in degrees.
	 */
	public static double degreesToRadians(double degrees) {
		return (degrees * Math.PI) / 180.0;
	}
	
	/**
	 * Returns the degree representation of the given angle in radians.
	 */
	public static double radiansToDegrees(double radians) {
		return (radians * 180.0) / Math.PI;
	}
}
