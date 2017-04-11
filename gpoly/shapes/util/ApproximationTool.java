package gpoly.shapes.util;

import java.util.ArrayList;

import gpoly.shapes.Edge;
import gpoly.shapes.Point;
import gpoly.shapes.Polygon;
import gpoly.shapes.PolygonMaker;
import gpoly.shapes.lambda.Parameterization;

public final class ApproximationTool {
	private ApproximationTool(){}
	public static Polygon generateApproximation(Parameterization xPar, Parameterization yPar, double maxPar, double step, double error) {
		return generateMakerApproximation(xPar, yPar, maxPar, step, error).build();
	}
	public static PolygonMaker generateMakerApproximation(Parameterization xPar, Parameterization yPar, double maxPar, double step, double error){
		PolygonMaker results = new PolygonMaker();
		boolean isPrevious = false;
		double curPar = 0;
		ArrayList<Point> pointsSinceLast = new ArrayList<>();
		ArrayList<Double> parameters = new ArrayList<>();
		double xVal = xPar.evaluate(curPar);
		double yVal = yPar.evaluate(curPar);
		results.addPoint(xVal, yVal);
		pointsSinceLast.add(new Point(xVal, yVal));
		parameters.add(curPar);
		while (curPar + step < maxPar){
			curPar += step;
			xVal = xPar.evaluate(curPar);
			yVal = yPar.evaluate(curPar);
			if (pointsSinceLast.size() <= 2){
				pointsSinceLast.add(new Point(xVal, yVal));
				parameters.add(curPar);
				continue;
			}
			Point first = pointsSinceLast.get(0);
			Point last = pointsSinceLast.get(pointsSinceLast.size() - 1);
			Edge approx = new Edge(first, last);
			double dist = 0;
			for (int x = 1; x < pointsSinceLast.size() - 1; x++){
				dist = approx.distanceToPoint(pointsSinceLast.get(x));
				if (dist > error) break;
			}
			if (dist < error) {
				pointsSinceLast.add(new Point(xVal, yVal));
				parameters.add(curPar);
				isPrevious = true;
				continue;
			}
			if (isPrevious){
				last = pointsSinceLast.get(pointsSinceLast.size() - 2);
				results.addPoint(last);
				pointsSinceLast = new ArrayList<>();
				parameters = new ArrayList<>();
				pointsSinceLast.add(last);
				parameters.add(curPar);
				isPrevious = false;
				continue;
			}
			while (error < dist){
				parameters.remove(2);
				pointsSinceLast.remove(2);
				double evalPar = (parameters.get(0) + parameters.get(1)) / 2;
				double evalX = xPar.evaluate(evalPar);
				double evalY = yPar.evaluate(evalPar);
				parameters.add(1, evalPar);
				pointsSinceLast.add(1, new Point(evalX, evalY));
				Edge edge = new Edge(pointsSinceLast.get(0), pointsSinceLast.get(2));
				dist = edge.distanceToPoint(pointsSinceLast.get(1));
			}
			last = pointsSinceLast.get(2);
			curPar = parameters.get(2);
			results.addPoint(last);
			pointsSinceLast = new ArrayList<>();
			parameters = new ArrayList<>();
			pointsSinceLast.add(last);
			parameters.add(curPar);
			isPrevious = false;
		}
		return results;
	}
}
