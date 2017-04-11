package gpoly.shapes.util;

import java.util.ArrayList;
import java.util.Iterator;

import gpoly.shapes.Point;
import gpoly.shapes.Polygon;
import gpoly.shapes.PolygonMaker;

public final class PolygonTools {
	private PolygonTools() {}
	public static Polygon rotatePolygon(Polygon a, double angle) {
		angle *= -1;
		double cosAngle = Math.cos(angle);
		double sinAngle = Math.sin(angle);
		ArrayList<Point> points = a.getPoints();
		PolygonMaker maker = new PolygonMaker();
		Iterator<Point> iterator = points.iterator();
		while(iterator.hasNext()) {
			Point next = iterator.next();
			double xPos = cosAngle * next.getX() + sinAngle * next.getY();
			double yPos = -1 * sinAngle * next.getX() + cosAngle * next.getY();
			maker.addPoint(xPos, yPos);
		}
		return maker.build();
	}
}
