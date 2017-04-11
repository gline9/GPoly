package gpoly.shapes;

import java.util.ArrayList;
import java.util.Iterator;

import gmath.types.PlanarVector;

public class PolygonMaker {
	private ArrayList<Point> points = new ArrayList<>();
	public PolygonMaker() {}
	public PolygonMaker(PolygonMaker polygon) {
		this.points = new ArrayList<>(polygon.points);
	}
	public PolygonMaker(Polygon poly) {
		points = new ArrayList<>(poly.getPoints());
	}
	public static Polygon makeSquare(double sideLength){
		return makeNgon(sideLength, 4);
	}
	public static Polygon makeNgon(double sideLength, int numberOfSides){
		if (numberOfSides < 3) throw new IllegalArgumentException(numberOfSides + " sides is too few for a polygon");
		PolygonMaker ngon = new PolygonMaker();
		double angle = 2 * Math.PI / (double)numberOfSides;
		double xPos = 0;
		double yPos = 0;
		double currentAngle = 0;
		for (int x = 0; x < numberOfSides; x++) {
			ngon.addPoint(xPos, yPos);
			xPos += sideLength * Math.cos(currentAngle);
			yPos += sideLength * Math.sin(currentAngle);
			currentAngle += angle;
		}
		return ngon.build();
	}
	public static Polygon makeNgonInnerRadius(double innerRadius, int numberOfSides) {
		double sideLength = 2 * innerRadius * Math.tan(Math.PI / (double)numberOfSides);
		return makeNgon(sideLength, numberOfSides);
	}
	public static Polygon makeNgonOuterRadius(double outerRadius, int numberOfSides) {
		double sideLength = 2 * outerRadius * Math.sin(Math.PI / (double)numberOfSides);
		return makeNgon(sideLength, numberOfSides);
	}
	public PolygonMaker addPoint(double xPos, double yPos) {
		Point point = new Point(xPos, yPos);
		points.add(point);
		return this;
	}
	public PolygonMaker addPoint(Point point){
		points.add(point);
		return this;
	}
	public Polygon build() {
		PolygonMaker preserver = new PolygonMaker(this);
		preserver.centerPolygon();
		return preserver.convertToPolygon();
	}
	public Polygon conversionlessBuild() {
		return new Polygon(points);
	}
	private Polygon convertToPolygon() {
		return new Polygon(points);
	}
	public ArrayList<PlanarVector> getEdgesAsVectors(){
		Iterator<Point> iterator = new ArrayList<>(points).iterator();
		ArrayList<PlanarVector> edges = new ArrayList<>();
		Point current = iterator.next();
		Point first = current;
		while (iterator.hasNext()){
			Point next = iterator.next();
			edges.add(next.toVector().subtract(current.toVector()));
			current = next;
		}
		edges.add(first.toVector().subtract(current.toVector()));
		return edges;
	}
	private Point centroid(){
		PlanarVector sum = new PlanarVector(0, 0);
		double area = 0;
		Iterator<Point> iterator = new ArrayList<>(points).iterator();
		Point current = iterator.next();
		Point first = current;
		while(iterator.hasNext()){
			Point next = iterator.next();
			double contributingArea = current.getX() * next.getY() - next.getX() * current.getY();
			double xAddition = (current.getX() + next.getX()) * contributingArea;
			double yAddition = (current.getY() + next.getY()) * contributingArea;
			area += contributingArea;
			sum = sum.add(new PlanarVector(xAddition, yAddition));
			current = next;
		}
		double contributingArea = current.getX() * first.getY() - first.getX() * current.getY();
		double xAddition = (current.getX() + first.getX()) * contributingArea;
		double yAddition = (current.getY() + first.getY()) * contributingArea;
		area += contributingArea;
		area /= 2;
		sum = sum.add(new PlanarVector(xAddition, yAddition));
		sum = sum.scale((double)(1)/(double)(6*area));
		return new Point(sum.getXPos(), sum.getYPos());
	}
	private void centerPolygon(){
		ArrayList<Point> newPoints = new ArrayList<>();
		Iterator<Point> iterator = new ArrayList<>(points).iterator();
		Point center = centroid();
		while (iterator.hasNext()){
			Point next = iterator.next();
			newPoints.add(new Point(next.getX() - center.getX(), next.getY() - center.getY()));
		}
		points = newPoints;
	}
}
