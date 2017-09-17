package com.fizzikgames.fizlib.test;

import java.awt.Rectangle;

import com.fizzikgames.fizlib.math.SquareMatrix2f;
import com.fizzikgames.fizlib.math.Vector2f;

public class TestMath {
	public static void main(String[] args) {
		Vector2f a = new Vector2f(1f, 0f);
		Vector2f b = new Vector2f(0f, 1f);
		SquareMatrix2f A = new SquareMatrix2f(1, 2, 3, 4);
		SquareMatrix2f B = new SquareMatrix2f(1, 3, 2, 4);
		
		System.out.println("Vector A = " + a.toString());
		System.out.println("Vector B = " + b.toString());
		
		//Addition
		System.out.println("\nVector Addition A + B = {1, 1} : " + (a.add(b)).toString());
		
		//Subtraction
		System.out.println("\nVector Subtraction A - B = {1, -1} : " + (a.subtract(b)).toString());
		
		//Magnitude
		System.out.println("\nVector Magnitude A = 1 : " + a.magnitude());
		System.out.println("Vector Magnitude B = 1 : " + b.magnitude());
		System.out.println("Distance between point A and B, Vector Magnitude  (A - B) = 1.41 : " + a.distance(b));
		
		//Dot Product
		System.out.println("\nVector Dot Product A . B = 0 : " + a.dotProduct(b));
		
		//Normal
		System.out.println("\nVector Normal 3 * A = {1, 0} : " + ((a.scale(3f)).normal()).toString());
		System.out.println("Vector Normal 365 * B = {0, 1} : " + ((b.scale(365)).normal()).toString());
		
		//Negate
		System.out.println("\nVector Negation A = {-1, 0} : " + (a.negate()).toString());
		System.out.println("Vector Negation B = {0, -1} : " + (b.negate()).toString());
		
		//Theta and other angles
		System.out.println("\nVector Angle (Theta) A = 0 : " + a.theta());
		System.out.println("Vector Angle (Theta) B = 1.57 : " + b.theta());
		System.out.println("Vector Angle between A and B = 1.57 : " + a.angleBetween(b));
		System.out.println("Vector A rotated by 90 degrees counter-clockwise = {0, 1} : " + a.rotate(Math.PI / 2));
		System.out.println("Vector A rotated by 180 degrees counter-clockwise = {-1, 0} : " + a.rotate(Math.PI));
		System.out.println("Vector B rotated by -90 degrees counter-clockwise = {1, 0} : " + b.rotate(-(Math.PI / 2)));
		System.out.println("Vector B rotated by -180 degrees counter-clockwise = {0, -1} : " + b.rotate(-(Math.PI)));
		System.out.println("Vector A + B rotated by 180 degrees counter-clockwise = {-1, -1} : " + (a.add(b)).rotate(Math.PI));
		System.out.println("Vector A + B rotated by -180 degrees counter-clockwise = {-1, -1} : " + (a.add(b)).rotate(-Math.PI));
		System.out.println("Vector B - A rotated by -90 degrees counter-clockwise = {1, 1} : " + (b.subtract(a)).rotate(-(Math.PI / 2)));
		System.out.println("Vector A rotated by 45 degrees counter-clockwise = {0.7071, 0.7071} : " + a.rotate(Math.PI / 4));
		System.out.println("Vector A rotated by 45 degrees counter-clockwise then normalized then negated = {-0.7071, -0.7071} : " 
				+ (a.rotate(Math.PI / 4)).negate());
		
		//Matrix and Vector miscellaneous
		System.out.println("\nTest Matrix A = " + A.toString());
		System.out.println("Test Matrix B = " + B.toString());
		System.out.println("Test Matrix A equals Test Matrix B = false : " + A.equals(B));
		System.out.println("Test Matrix B equals Test Matrix A = false : " + B.equals(A));
		System.out.println("Test Matrix A Transpose = " + (A.transpose()).toString());
		System.out.println("Test Matrix B Transpose = " + (B.transpose()).toString());
		System.out.println("Test Matrix A equals Test Matrix B Transpose = true : " + A.equals(B.transpose()));
		System.out.println("Test Matrix A Determinant = " + A.determinant());
		System.out.println("Test Matrix B Determinant = " + B.determinant());
		System.out.println("Test Matrix A Inverse = " + A.inverse().toString());
	}
}
