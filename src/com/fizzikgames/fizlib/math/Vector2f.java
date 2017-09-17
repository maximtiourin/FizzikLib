package com.fizzikgames.fizlib.math;

/**
 * Float vector implementation in the subspace R ^ 2.
 * @author Maxim Tiourin
 * @version 1.00
 */
public class Vector2f {
	private static final float DELTA = 0.0000001f;
	private float x;
	private float y;
	
	/**
	 * Creates the zero vector.
	 */
	public Vector2f() {
		x = 0f;
		y = 0f;
	}
	
	/**
	 * Creates a vector with the given entries.
	 */
	public Vector2f(float ax, float ay) {
		x = ax;
		y = ay;
	}
	
	/**
	 * Creates a vector at the origin with the given magnitude and direction in radians.
	 */
	public Vector2f(float magnitude, double theta) {
		x = magnitude;
		y = 0f;
		this.set(SquareMatrix2f.rotationMatrix(theta).applyTransform(this));
	}
	
	/**
	 * Adds the given vector to this vector.
	 */
	public Vector2f add(Vector2f b) {
		final Vector2f a = this;
		
		return new Vector2f(a.x() + b.x(), a.y() + b.y());
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 */
	public Vector2f subtract(Vector2f b) {
		final Vector2f a = this;
		
		return new Vector2f(a.x() - b.x(), a.y() - b.y());
	}
	
	/**
	 * Calculates the distance between this point and another given point.
	 */
	
	public float distance(Vector2f b) {
		final Vector2f a = this;
		
		return Vector2f.distance(a, b);
	}
	
	/**
	 * Calculates the distance squared between this point and another given point.
	 * Avoids using a sqrt.
	 */
	public float distanceSquared(Vector2f b) {
		return distanceSquared(this, b);
	}
	
	/**
	 * Returns true if this point is within a given distance of another point (exclusive).
	 * Uses an optimized distance check.
	 */
	public boolean withinDistanceOfPoint(float distance, Vector2f point) {
		final float distanceSquared = distance * distance;
		return (distanceSquared(point) < distanceSquared);
	}
	
	/**
	 * Calculates the inner product (dot product) of this vector and another given vector.
	 */
	public float dotProduct(Vector2f v) {
		final Vector2f u = this;
		
		return ((u.x() * v.x()) + (u.y() * v.y()));
	}
	
	/**
	 * Returns the vector as a normalized unit vector.
	 */
	public Vector2f normal() {
		return Vector2f.normalize(this);
	}
	
	/**
	 * Returns the negated vector.
	 */
	public Vector2f negate() {
		return new Vector2f(-x(), -y());
	}
	
	/**
	 * Returns the magnitude/length of the vector.
	 */
	public float magnitude() {
		return Vector2f.magnitude(this);
	}
	
	/**
	 * Returns the angle in radians between this vector and another given vector.
	 */
	public double angleBetween(Vector2f v) {
		return Vector2f.angleBetween(this, v);
	}
	
	/**
	 * Returns the angle of this vector in radians counter-clockwise from the positive x axis.
	 */
	public double theta() {
		return Math.atan(y() / x());
	}
	
	/**
	 * Returns this vector rotated to the given angle in radians counter-clockwise from the positive x-axis.
	 */
	public Vector2f setTheta(double theta) {
		final float length = this.magnitude();
		return new Vector2f(length, theta);
	}
	
	/**
	 * Returns the vector rotated by the given angle in radians counter-clockwise.
	 */
	public Vector2f rotate(double theta) {
		final SquareMatrix2f matrix = SquareMatrix2f.rotationMatrix(theta);
		return matrix.applyTransform(this);
	}
	
	/**
	 * Returns the vector scaled by a given scalar value.
	 */
	public Vector2f scale(float scalar) {
		return new Vector2f(x() * scalar, y() * scalar);
	}
	
	/**
	 * Sets the components of this vector.
	 */
	public void set(float ax, float ay) {
		x = ax;
		y = ay;
	}
	
	/**
	 * Sets the components of this vector to equal another given vector's components.
	 */
	public void set(Vector2f other) {
		x = other.x();
		y = other.y();
	}
	
	/**
	 * Returns the first entry of the vector.
	 */
	public float x() {
		return x;
	}
	
	/**
	 * Returns the second entry of the vector.
	 */
	public float y() {
		return y;
	}
	
	/**
	 * Returns a copy of this vector.
	 */
	public Vector2f copy() {
		return new Vector2f(this.x(), this.y());
	}
	
	@Override
	/**
	 * Returns the string representation of the set of entries of the vector
	 */
	public String toString() {
		return "{" + x() + ", " + y() + "}";
	}
	
	@Override
	/**
	 * Returns true if this vector equals another given vector.
	 */
	public boolean equals(Object o) {
		if (o instanceof Vector2f) {
			final Vector2f u = this;
			final Vector2f v = (Vector2f) o;
			return ((Math.abs(u.x() - v.x()) < DELTA) && (Math.abs(u.y() - v.y()) < DELTA));
		}
		
		return false;
	}
	
	/**
	 * Calculates the distance between two points.
	 */
	public static float distance(Vector2f a, Vector2f b) {
		Vector2f c = b.subtract(a);
		return c.magnitude();
	}
	
	/**
	 * Calculates the distance squared between two points.
	 * Avoids using a sqrt.
	 */
	public static float distanceSquared(Vector2f a, Vector2f b) {
		final Vector2f v = b.subtract(a);
		return v.dotProduct(v);
	}
	
	/**
	 * Returns the given vector as a unit vector.
	 */
	public static Vector2f normalize(Vector2f v) {
		final float length = v.magnitude();
		return new Vector2f(v.x() / length, v.y() / length);
	}
	
	
	/**
	 * Calculates the magnitude/length of the given vector.
	 */
	public static float magnitude(Vector2f v) {
		return (float) Math.sqrt(v.dotProduct(v));
	}
	
	/**
	 * Calculates the component of vector b in the direction of vector a
	 */
	public static float scalarProjection(Vector2f a, Vector2f b) {
		final float dot = a.dotProduct(b);
		final float magnitude = a.magnitude();
		return dot / magnitude;
	}
	
	/**
	 * Calculates the vector projection of b onto a.
	 */
	public static Vector2f vectorProjection(Vector2f a, Vector2f b) {
		final float compba = scalarProjection(a, b);
		final Vector2f unit = a.normal();
		return unit.scale(compba);
	}
	
	/**
	 * Returns the angle in radians between two vectors.
	 */
	public static double angleBetween(Vector2f u, Vector2f v) {
		final float ulength = u.magnitude();
		final float vlength = v.magnitude();
		final float dot = u.dotProduct(v);
		
		return Math.acos(dot / (ulength * vlength));
	}
}
