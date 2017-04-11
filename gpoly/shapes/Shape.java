package gpoly.shapes;

import java.awt.Graphics;

public abstract class Shape {
	public abstract void draw(Graphics g);
	public abstract void outline(Graphics g);
	public abstract Polygon getApproximation();
	public final boolean isConvex() {
		return getApproximation().isPolyConvex();
	}
	public final double getArea(){
		return getApproximation().getPolyArea();
	}
}
