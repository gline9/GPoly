package gpoly.shapes;

import java.awt.Graphics;

import gpoly.shapes.util.ApproximationTool;

public class Ellipse extends Shape{
	private final double xRadius;
	private final double yRadius;
	private final Polygon approximation;
	public Ellipse(double xRadius, double yRadius) {
		this.xRadius = xRadius;
		this.yRadius = yRadius;
		approximation = ApproximationTool.generateApproximation(
				(double t) -> this.xRadius * Math.cos(t),
				(double t) -> this.yRadius * Math.sin(t),
				2 * Math.PI, Math.PI / 100, .1);
	}
	public double getMinorRadius() {
		return Math.min(xRadius, yRadius);
	}
	public double getMajorRadius() {
		return Math.max(xRadius, yRadius);
	}
	public double getXRadius() {
		return xRadius;
	}
	public double getYRadius() {
		return yRadius;
	}
	@Override
	public void draw(Graphics g) {
		g.fillOval((int)(-1 * xRadius), (int)(-1 * yRadius), (int)(2 * xRadius), (int)(2 * yRadius));
	}

	@Override
	public void outline(Graphics g) {
		g.drawOval((int)(-1 * xRadius), (int)(-1 * yRadius), (int)(2 * xRadius), (int)(2 * yRadius));
	}

	@Override
	public Polygon getApproximation() {
		return approximation;
	}
}
