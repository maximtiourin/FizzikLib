package com.fizzikgames.fizlib.math.geom;

/**
 * Basic Helper class for representing the 2D geometrical shape formed by 4 points in the subspace R ^ 2.
 * Does not throw exceptions for invalid width and height.
 * @author Maxim Tiourin
 * @version 1.00
 */
public class Rectangle {
	private static final float DELTA = 0.0000001f;
	private float x;
	private float y;
	private float width;
	private float height;
	
	/**
	 * Creates a rectangle at the given point with the given width and height.
	 */
	public Rectangle(float ax, float ay, float awidth, float aheight) {
		this.x = ax;
		this.y = ay;
		this.width = awidth;
		this.height = aheight;
	}
	
	/**
	 * Creates a rectangle at the origin with the given width and height
	 */
	public Rectangle(float awidth, float aheight) {
		this.x = 0f;
		this.y = 0f;
		this.width = awidth;
		this.height = aheight;
	}
	
	/**
	 * Returns the minimum x coordinate of a point that can fit inside the rectangle.
	 */
	public float xmin() {
		return x;
	}
	
	/**
	 * Returns the maximum x coordinate of a point that can fit inside the rectangle.
	 */
	public float xmax() {
		return x + width;
	}
	
	/**
	 * Returns the minimum y coordinate of a point that can fit inside the rectangle.
	 */
	public float ymin() {
		return y;
	}
	
	/**
	 * Returns the maximum y coordinate of a point that can fit inside the rectangle.
	 */
	public float ymax() {
		return y + height;
	}
	
	/**
	 * Returns the x coordinate of the center of the rectangle.
	 */
	public float xcenter() {
		return (x + width) / 2;
	}
	
	/**
	 * Returns the y coordinate of the center of the rectangle.
	 */
	public float ycenter() {
		return (y + height) / 2;
	}
	
	/**
	 * Returns the width of the rectangle.
	 */
	public float width() {
		return width;
	}
	
	/**
	 * Returns the height of the rectangle.
	 */
	public float height() {
		return height;
	}
	
	/**
	 * Returns true if the rectangle contains the given point.
	 */
	public boolean contains(float ax, float ay) {
		if ((ax < xmin()) || (ax >= xmax())) {
			return false;
		}
		if ((ay < ymin()) || (ay >= ymax())) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Sets the boundaries of the rectangle.
	 */
	public void setBounds(float ax, float ay, float awidth, float aheight) {
		this.x = ax;
		this.y = ay;
		this.width = awidth;
		this.height = aheight;
	}
	
	/**
	 * Returns a copy of the rectangle.
	 */
	public Rectangle copy() {
		return new Rectangle(xmin(), ymin(), width(), height());
	}
	
	@Override
	public String toString() {
		return "{" + xmin() + ", " + ymin() + ", " + width() + ", " + height() + "}"; 
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Rectangle) {
			Rectangle a = this;
			Rectangle b = (Rectangle) o;
			return ((Math.abs(a.xmin() - b.xmin()) < DELTA) && (Math.abs(a.ymin() - b.ymin()) < DELTA)
					&& (Math.abs(a.width() - b.width()) < DELTA) && (Math.abs(a.height() - b.height()) < DELTA));
		}
		
		return false;
	}
}
