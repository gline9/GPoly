package gpoly.shapes;

import java.awt.Graphics;

import gpoly.shapes.util.ApproximationTool;

public class Cardioid extends Shape{
	private final double radius;
	private final double circRadius;
	private final Polygon approximation;
	public Cardioid(double radius) {
		this.radius = radius;
		this.circRadius = radius / 2;
		this.approximation = ApproximationTool.generateApproximation(
				(double t) -> circRadius * (2 * Math.cos(t) - Math.cos(2 * t)), 
				(double t) -> circRadius * (2 * Math.sin(t) - Math.sin(2 * t)), 
				2 * Math.PI, Math.PI / 100, .1);
	}
	@Override
	public void draw(Graphics g) {
		getApproximation().draw(g);
	}

	@Override
	public void outline(Graphics g) {
		getApproximation().outline(g);
	}
	public double getRadius() {
		return radius;
	}
	@Override
	public Polygon getApproximation() {
		return approximation;
	}

}
