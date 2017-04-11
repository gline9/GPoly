package gpoly.shapes;

import java.awt.Graphics;

import gmath.types.PlanarVector;

public class Edge {
	private final Point start;
	private final Point end;
	private final PlanarVector difference;
	public Edge(Point a, Point b){
		this.start = a;
		this.end = b;
		this.difference = b.toVector().subtract(a.toVector());
	}
	public Point centerPoint(){
		return new Point(start.toVector().add(end.toVector()).scale(.5));
	}
	public PlanarVector toVector(){
		return difference;
	}
	public Edge shift(PlanarVector vector){
		return new Edge(new Point(start.toVector().add(vector)), new Point(end.toVector().add(vector)));
	}
	public double distanceToPoint(Point point){
		double nonNormDist = Math.abs(difference.perpendicular().dotProduct(point.toVector()) + start.toVector().perpendicular().dotProduct(end.toVector()));
		double edgeLength = difference.magnitude();
		return nonNormDist / edgeLength;
	}
	public boolean intersectsEdge(Edge edge) {
		if (start.equals(edge.start) || end.equals(edge.end) ||
			start.equals(edge.end) || end.equals(edge.start)) return true;
		if (Math.max(start.getX(), end.getX()) < Math.min(edge.start.getX(), edge.end.getX()) || 
			Math.max(edge.start.getX(), edge.end.getX()) < Math.min(start.getX(), end.getX())) {
			return false;
		}
		if (start.getX() == end.getX()) 
			return handleZero(start.getY(), end.getY(), edge.start.getY(), edge.end.getY());
		if (edge.start.getX() == edge.end.getX()) 
			return handleZero(edge.start.getY(), edge.end.getY(), start.getY(), end.getY());
		double A1 = (start.getY() - end.getY()) / (start.getX() - end.getX());
		double A2 = (edge.start.getY() - edge.end.getY()) / (edge.start.getX() - edge.end.getX());
		double b1 = start.getY() - A1 * start.getX();
		double b2 = edge.start.getY() - A2 * edge.start.getX();
		if (A1 == A2) return b1 == b2;
		double Xa = (b2 - b1) / (A1 - A2);
		if (Xa < Math.max(Math.min(start.getX(), end.getX()), Math.min(edge.start.getX(), edge.end.getX())) ||
			Xa > Math.min(Math.max(start.getX(), end.getX()), Math.max(edge.start.getX(), edge.end.getX()))){
			return false;
		} else {
			return true;
		}
		
	}
	private boolean handleZero(double Y1, double Y2, double Y3, double Y4) {
		double t3 = Math.min(Y3, Y4);
		double t4 = Math.max(Y3, Y4);
		double t1 = Math.min(Y1, Y2);
		double t2 = Math.max(Y1, Y2);
		Y1 = t1; Y2 = t2; Y3 = t3; Y4 = t4;
		if (Y3 < Y1 && Y4 > Y1) {
			return true;
		}
		if (Y3 < Y2 && Y4 > Y2) {
			return true;
		}
		if (Y1 < Y3 && Y2 > Y3) {
			return true;
		}
		if (Y1 < Y4 && Y2 > Y4) {
			return true;
		}
		return false;
	}
	public double zCrossProduct(Point point) {
		double dx1 = end.getX() - start.getX();
		double dx2 = point.getX() - end.getX();
		double dy1 = end.getY() - start.getY();
		double dy2 = point.getY() - end.getY();
		return dx1 * dy2 - dx2 * dy1;
	}
	public void draw(Graphics g) {
		g.drawLine((int)start.getX(), (int)start.getY(), (int)end.getX(), (int)end.getY());
	}
	public String toString(){
		return start.toString() + " , " + end.toString();
	}
}
