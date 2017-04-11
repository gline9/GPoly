package gpoly.shapes;

import java.awt.Graphics;

import gpoly.shapes.util.ApproximationTool;

public class Heart extends Shape{
	private final Polygon approximation;
	public static final double WHOLE = 1;
	public static final double THREE_QUARTERS = 0.72;
	public static final double HALF = 0.5;
	public static final double ONE_QUARTER = 0.3;
	public Heart(double radius) {
		this(WHOLE, radius);
	}
	public Heart(double percent, double radius) {
		double maxPar = Math.PI * 2 * percent;
		PolygonMaker approximationMaker = ApproximationTool.generateMakerApproximation(
				(double t) -> radius*16*Math.pow(Math.sin(t), 3), 
				(double t) -> radius*(-13*Math.cos(t)+5*Math.cos(2*t)+2*Math.cos(3*t)+Math.cos(4*t)), 
				maxPar, Math.PI / 100, .01);
		if (percent != 1) {
			approximationMaker.addPoint(0, 0);
		}
		approximation = approximationMaker.conversionlessBuild();
	}
	@Override
	public void draw(Graphics g) {
		getApproximation().draw(g);
	}

	@Override
	public void outline(Graphics g) {
		getApproximation().outline(g);
	}

	@Override
	public Polygon getApproximation() {
		return approximation;
	}

}
