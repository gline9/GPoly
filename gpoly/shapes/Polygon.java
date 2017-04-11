package gpoly.shapes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;


public class Polygon extends Shape{
	private final ArrayList<Point> points;
	private final ArrayList<Edge> edges;
	private final int[] x;
	private final int[] y;
	private final double maxRadius;
	private final double area;
	private final boolean isConvex;
	public Polygon(Polygon polygon) {
		this.points = new ArrayList<>(polygon.points);
		this.edges = new ArrayList<>(polygon.edges);
		x = polygon.x;
		y = polygon.y;
		maxRadius = polygon.maxRadius;
		area = polygon.area;
		isConvex = polygon.isConvex;
	}
	public Polygon(ArrayList<Point> points) {
		this.points = new ArrayList<>(points);
		this.edges = getEdgesArray();
		int[] x = new int[points.size()];
		int[] y = new int[points.size()];
		Iterator<Point> iterator = new ArrayList<>(points).iterator();
		int index = 0;
		while(iterator.hasNext()){
			Point next = iterator.next();
			x[index] = (int)next.getX();
			y[index] = (int)next.getY();
			index ++;
		}
		this.x = x;
		this.y = y;
		area = area(this.points);
		maxRadius = maxRadius(this.points);
		isConvex = isConvex(this.points);
	}
	private double area(ArrayList<Point> points){
		double area = 0;
		Iterator<Point> iterator = new ArrayList<>(points).iterator();
		Point current = iterator.next();
		Point first = current;
		while(iterator.hasNext()){
			Point next = iterator.next();
			double contributingArea = current.getX() * next.getY() - next.getX() * current.getY();
			area += contributingArea;
			current = next;
		}
		double contributingArea = current.getX() * first.getY() - first.getX() * current.getY();
		area += contributingArea;
		area /= 2;
		return area;
	}
	private double maxRadius(ArrayList<Point> points){
		double maxRadius = 0;
		Iterator<Point> iterator = points.iterator();
		while (iterator.hasNext()){
			Point next = iterator.next();
			maxRadius = Math.max(maxRadius, next.toVector().magnitude());
		}
		return maxRadius;
	}
	private boolean isConvex(ArrayList<Point> points) {
		double prev = 0;
		Iterator<Point> iterator = points.iterator();
		Point first = iterator.next();
		Point second = iterator.next();
		Point previous = second;
		Point preprevious = first;
		Point current = new Point(0, 0);
		while (iterator.hasNext()) {
			current = iterator.next();
			double cur = new Edge(previous, preprevious).zCrossProduct(current);
			if (prev == 0) prev = cur;
			else if (cur != 0){
				if ((prev > 0 && cur > 0) || (prev < 0 && cur < 0)) {
					prev = cur;
				}else {
					return false;
				}
			}
		}
		double cur = new Edge(previous, preprevious).zCrossProduct(first);
		double aCur = new Edge(first, previous).zCrossProduct(second);
		if (!((cur >= 0 && prev >= 0 && aCur >= 0) || (cur <= 0 && prev <= 0 && aCur <= 0)))
			return false;
		return true;
	}
	public ArrayList<Point> getPoints(){
		return new ArrayList<>(points);
	}
	public double getMaxRadius(){
		return maxRadius;
	}
	public double getPolyArea(){
		return area;
	}
	public boolean isPolyConvex() {
		return isConvex;
	}
	public ArrayList<Edge> getEdges(){
		return edges;
	}
	private ArrayList<Edge> getEdgesArray(){
		Iterator<Point> iterator = new ArrayList<>(points).iterator();
		ArrayList<Edge> edges = new ArrayList<>();
		Point current = iterator.next();
		Point first = current;
		while (iterator.hasNext()){
			Point next = iterator.next();
			edges.add(new Edge(next, current));
			current = next;
		}
		edges.add(new Edge(first, current));
		return edges;
	}
	@Override
	public void draw(Graphics g){
		g.fillPolygon(x, y, points.size());
	}
	@Override
	public void outline(Graphics g) {
		g.drawPolygon(x, y, points.size());
	}
	public boolean equals(Object obj) {
		if (obj instanceof Polygon) {
			Polygon polygon = (Polygon) obj;
			if (polygon.points.size() == this.points.size()) {
				Iterator<Point> iterator = new ArrayList<>(points).iterator();
				Iterator<Point> iteratorPoly = new ArrayList<>(polygon.points).iterator();
				boolean stillEqual = true;
				while(iterator.hasNext()) {
					stillEqual &= iterator.next().equals(iteratorPoly.next());
				}
				return stillEqual;
			}
		}
		return false;
	}
	public int hashCode() {
		return points.size() != 0 ? (int)(points.get(0).getX() * points.get(0).getY() * 1000) : 0;
	}
	@Override
	public Polygon getApproximation() {
		return this;
	}
}
