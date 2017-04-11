package gpoly.shapes.util;

import java.util.ArrayList;
import java.util.Iterator;

import gcore.tuples.Pair;
import gmath.types.PlanarVector;
import gpoly.shapes.Edge;
import gpoly.shapes.Point;
import gpoly.shapes.Polygon;
import gpoly.shapes.Shape;

public final class ShapeTools {
	private ShapeTools(){}
	public static boolean doShapesCollide(Shape shapeA, Shape shapeB, PlanarVector aPosition, PlanarVector bPosition) {
		return doShapesCollide(shapeA, shapeB, aPosition, bPosition, 0, 0);
	}
	public static boolean doShapesCollide(Shape shapeA, Shape shapeB, PlanarVector aPosition, PlanarVector bPosition, double aAngle, double bAngle){
		double combinedRadii = shapeA.getApproximation().getMaxRadius() + shapeB.getApproximation().getMaxRadius();
		double separationDistance = aPosition.subtract(bPosition).magnitude();
		if (combinedRadii < separationDistance){
			return false;
		}
		Polygon a;
		Polygon b;
		if (aAngle != 0)
			a = PolygonTools.rotatePolygon(shapeA.getApproximation(), aAngle);
		else
			a = shapeA.getApproximation();
		if (bAngle != 0)
			b = PolygonTools.rotatePolygon(shapeB.getApproximation(), bAngle);
		else
			b = shapeB.getApproximation();
		if (!(shapeA.isConvex() && shapeB.isConvex())) {
			return doConcavePolygonsCollide(a, b, aPosition, bPosition);
		}
		boolean collision = true;
		ArrayList<Edge> aEdges = a.getEdges();
		ArrayList<Edge> bEdges = b.getEdges();
		Edge edge;
		for (int edgeIndex = 0; edgeIndex < aEdges.size() + bEdges.size(); edgeIndex++){
			if (edgeIndex < aEdges.size()){
				edge = aEdges.get(edgeIndex);
			}else{
				edge = bEdges.get(edgeIndex - aEdges.size());
			}
			PlanarVector axis = edge.toVector().normalPerpendicular();
			
			Pair<Double, Double> intervalA = projectPolygon(axis, a, aPosition);
			Pair<Double, Double> intervalB = projectPolygon(axis, b, bPosition);
			if (intervalDistance(intervalA, intervalB) > 0){
				collision = false;
			}
			if (!collision){
				break;
			}
		}
		return collision;
	}
	private static Pair<Double, Double> projectPolygon(PlanarVector axis, Polygon polygon, PlanarVector polyPosition){
		ArrayList<Point> points = polygon.getPoints();
		double dotProduct = axis.dotProduct(points.get(0).toVector().add(polyPosition));
		Pair<Double, Double> minMax = new Pair<>(dotProduct, dotProduct);
		for (int i = 0; i < points.size(); i++){
			dotProduct = points.get(i).toVector().add(polyPosition).dotProduct(axis);
			if (dotProduct < minMax.getFirst()){
				minMax = new Pair<>(dotProduct, minMax.getSecond());
			}else{
				if (dotProduct > minMax.getSecond()){
					minMax = new Pair<>(minMax.getFirst(), dotProduct);
				}
			}
		}
		return minMax;
	}
	private static double intervalDistance(Pair<Double, Double> intervalA, Pair<Double, Double> intervalB){
		if (intervalA.getFirst() < intervalB.getFirst()){
			return intervalB.getFirst() - intervalA.getSecond();
		}else{
			return intervalA.getFirst() - intervalB.getSecond();
		}
	}
	private static boolean doConcavePolygonsCollide(Polygon a, Polygon b, PlanarVector aPosition, PlanarVector bPosition) {
		ArrayList<Edge> aEdges = a.getEdges();
		ArrayList<Edge> bEdges = b.getEdges();
		Iterator<Edge> aEdgeIter = aEdges.iterator();
		while (aEdgeIter.hasNext()){
			Edge aEdge = aEdgeIter.next().shift(aPosition);
			Iterator<Edge> bEdgeIter = bEdges.iterator();
			while (bEdgeIter.hasNext()){
				Edge bEdge = bEdgeIter.next().shift(bPosition);
				if (aEdge.intersectsEdge(bEdge)){
					return true;
				}
			}
		}
		return false;
	}
}
