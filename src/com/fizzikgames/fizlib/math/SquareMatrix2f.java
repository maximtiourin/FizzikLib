package com.fizzikgames.fizlib.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 2x2 Matrix that can be used to apply linear transformations to vectors or 2x2 Matrices
 * representing a set of vectors in the subspace R ^ 2.
 * @author Maxim Tiourin
 * @version 1.00
 */
public class SquareMatrix2f {
	public static final int ROUNDING_SCALE = 7;
	public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
	private float a;
	private float b;
	private float c;
	private float d;
	
	/**
	 * Creates a 2x2 identity matrix
	 */
	public SquareMatrix2f() {
		a = 1;
		b = 0;
		c = 0;
		d = 1;
	}
	
	/**
	 * Creates a 2x2 matrix with the given values
	 * @param a row 1 col 1
	 * @param b row 1 col 2
	 * @param c row 2 col 1
	 * @param d row 2 col 2
	 */
	public SquareMatrix2f(float a, float b, float c, float d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	/**
	 * Creates a 2x2 matrix with the given column vectors
	 */
	public SquareMatrix2f(Vector2f col1, Vector2f col2) {
		this.a = col1.x();
		this.b = col2.x();
		this.c = col1.y();
		this.d = col2.y();
	}
	
	/**
	 * Returns the vector attained from applying this linear transformation to a given vector.
	 */
	public Vector2f applyTransform(Vector2f v) {
		return SquareMatrix2f.multiply(this, v);
	}
	
	/**
	 * Returns the matrix attained from applying this linear transformation to a given matrix.
	 */
	public SquareMatrix2f applyTransform(SquareMatrix2f m) {
		return SquareMatrix2f.multiply(this, m);
	}
	
	/**
	 * Returns the determinant of the matrix
	 */
	public float determinant() {
		return ((a * d) - (b * c));
	}
	
	/**
	 * Returns the inverse of the matrix
	 */
	public SquareMatrix2f inverse() {
		final float divdet = 1f / determinant();
		
		return new SquareMatrix2f(d * divdet, (-b) * divdet, (-c) * divdet, a * divdet);
	}
	
	/**
	 * Returns the transpose of the matrix
	 */
	public SquareMatrix2f transpose() {
		return new SquareMatrix2f(a, c, b, d);
	}
	
	/**
	 * Returns the 'a' component of the 2x2 matrix.
	 */
	public float a() {
		return a;
	}
	
	/**
	 * Returns the 'b' component of the 2x2 matrix.
	 */
	public float b() {
		return b;
	}
	
	/**
	 * Returns the 'c' component of the 2x2 matrix.
	 */
	public float c() {
		return c;
	}
	
	/**
	 * Returns the 'd' component of the 2x2 matrix.
	 */
	public float d() {
		return d;
	}
	
	/**
	 * Returns the first column of the matrix as a vector2f.
	 */
	public Vector2f v1() {
		return new Vector2f(a, c);
	}
	
	/**
	 * Returns the second column of the matrix as a vector2f.
	 */
	public Vector2f v2() {
		return new Vector2f(b, d);
	}
	
	/**
	 * Returns a copy of this matrix.
	 */
	public SquareMatrix2f copy() {
		return new SquareMatrix2f(a, b, c, d);
	}
	
	@Override
	/**
	 * Returns the string representation of this matrix as a set of column vector entry sets.
	 */
	public String toString() {
		return "{" + v1().toString() + ", " + v2().toString() + "}";
	}
	
	@Override
	/**
	 * Returns true if this 2x2 matrix equals another given 2x2 matrix
	 */
	public boolean equals(Object o) {
		if (o instanceof SquareMatrix2f) {
			final SquareMatrix2f A = this;
			final SquareMatrix2f B = (SquareMatrix2f) o;
			return ((A.v1().equals(B.v1())) && (A.v2().equals(B.v2())));
		}
		
		return false;
	}
	
	/**
	 * Returns the standard rotation matrix for a linear transformation
	 * that rotates by the given angle in radians counter-clockwise.
	 */
	public static SquareMatrix2f rotationMatrix(double theta) {
		final double cos = Math.cos(theta);
		final double sin = Math.sin(theta);
		final float fcos = new BigDecimal(cos + "").setScale(ROUNDING_SCALE, ROUNDING_MODE).floatValue();
		final float fsin = new BigDecimal(sin + "").setScale(ROUNDING_SCALE, ROUNDING_MODE).floatValue();
		return new SquareMatrix2f(fcos, -fsin, fsin, fcos);
	}
	
	/**
	 * Returns the standard reflection matrix for a linear transformation that
	 * reflects through either the xAxis, yAxis, or both(origin).
	 */
	public static SquareMatrix2f axisReflectionMatrix(boolean xAxis, boolean yAxis) {
		return new SquareMatrix2f(yAxis ? (-1) : 1, 0, 0, xAxis ? (-1) : 1);
	}
	
	/**
	 * Returns the standard scale matrix for a linear transformation that contracts
	 * and expands by given scalars across the x and y axis.
	 */
	public static SquareMatrix2f scaleMatrix(float xScale, float yScale) {
		return new SquareMatrix2f(xScale, 0, 0, yScale);
	}
	
	/**
	 * Returns the standard shear matrix for a linear transformation that shears by
	 * the given amount in the x and y axis.
	 */
	public static SquareMatrix2f shearMatrix(float xShear, float yShear) {
		return new SquareMatrix2f(1f, xShear, yShear, 1f);
	}
	
	/**
	 * Returns the vector result of multiplying a given 2x2 matrix by a given vector.
	 */
	public static Vector2f multiply(SquareMatrix2f m, Vector2f v) {
		return new Vector2f(((m.a() * v.x()) + (m.b() * v.y())), ((m.c() * v.x()) + (m.d() * v.y())));
	}
	
	/**
	 * Returns the 2x2 matrix result of multiplying a given 2x2 matrix by another given 2x2 matrix.
	 */
	public static SquareMatrix2f multiply(SquareMatrix2f m1, SquareMatrix2f m2) {
		return new SquareMatrix2f((m1.a() * m2.a()) + (m1.b() * m2.c()), (m1.a() * m2.b()) + (m1.b() * m2.d()), 
				(m1.c() * m2.a()) + (m1.d() * m2.c()), (m1.c() * m2.b()) + (m1.d() * m2.d()));
	}
}
