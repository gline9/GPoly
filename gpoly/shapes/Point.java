package gpoly.shapes;

import gcore.tuples.Pair;
import gmath.types.PlanarVector;

public class Point {
	private final Pair<Double, Double> coordinates;
	public Point(double xPos, double yPos){
		coordinates = new Pair<>(xPos, yPos);
	}
	public Point(PlanarVector vec){
		coordinates = new Pair<>(vec.getXPos(), vec.getYPos());
	}
	public double getX(){
		return coordinates.getFirst();
	}
	public double getY(){
		return coordinates.getSecond();
	}
	public PlanarVector toVector(){
		return new PlanarVector(coordinates.getFirst(), coordinates.getSecond());
	}
	public boolean equals(Object obj){
		if (obj instanceof Point){
			Point point = (Point) obj;
			return this.coordinates.equals(point.coordinates);
		}
		return false;
	}
	public int hashCode(){
		return coordinates.hashCode();
	}
	public String toString(){
		return "(" + coordinates.toString() + ")";
	}
}
