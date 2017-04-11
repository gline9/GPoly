package gpoly.shapes;

import java.awt.Graphics;

import gpoly.shapes.util.ApproximationTool;

public class Hypocycloid extends Shape{
	private final int cusps;
	private final double outerRadius;
	private final Polygon approximation;
	private final double innerRadius;
	public Hypocycloid (double outerRadius, int cusps) {
		this.cusps = cusps;
		this.outerRadius = outerRadius;
		this.innerRadius = outerRadius / cusps;
		approximation = ApproximationTool.generateApproximation(
				(double t) -> (outerRadius - innerRadius) * Math.cos(t) + innerRadius * Math.cos((outerRadius - innerRadius) * t / innerRadius), 
				(double t) -> (outerRadius - innerRadius) * Math.sin(t) - innerRadius * Math.sin((outerRadius - innerRadius) * t / innerRadius), 
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
	public double getOuterRadius() {
		return outerRadius;
	}
	public int getCusps() {
		return cusps;
	}
	@Override
	public Polygon getApproximation() {
		return approximation;
	}

}
