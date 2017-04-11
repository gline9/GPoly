package gpoly.shapes;


public class Circle extends Ellipse {
	private final double radius;
	public Circle(double radius) {
		super(radius, radius);
		this.radius = radius;
	}
	public double getRadius() {
		return radius;
	}
}
