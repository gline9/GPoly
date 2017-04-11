package gpoly.shapes;

import java.awt.Graphics;

import gpoly.shapes.util.ApproximationTool;

public class SuperEllipse extends Shape{
	private final double xRadius;
	private final double yRadius;
	private final double rValue;
	private final Polygon approximation;
	public SuperEllipse(double xRadius, double yRadius, double rValue){
		this.xRadius = xRadius;
		this.yRadius = yRadius;
		this.rValue = rValue;
		this.approximation = ApproximationTool.generateApproximation(
				(double t) -> Math.cos(t) == 0 ? 0 : this.xRadius * Math.cos(t) / Math.abs(Math.cos(t)) * Math.pow(Math.abs(Math.cos(t)), 2 / this.rValue), 
				(double t) -> Math.sin(t) == 0 ? 0 : this.yRadius * Math.sin(t) / Math.abs(Math.sin(t)) * Math.pow(Math.abs(Math.sin(t)), 2 / this.rValue), 
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
	public double getR(){
		return rValue;
	}
	@Override
	public void draw(Graphics g) {
		approximation.draw(g);
	}

	@Override
	public void outline(Graphics g) {
		approximation.outline(g);
	}

	@Override
	public Polygon getApproximation() {
		return approximation;
	}

}
