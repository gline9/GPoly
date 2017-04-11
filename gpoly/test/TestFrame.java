package gpoly.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import gmath.types.PlanarVector;
import gpoly.shapes.Heart;
import gpoly.shapes.Shape;
import gpoly.shapes.util.ShapeTools;

public class TestFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private Shape shape1;
	private Shape shape2;
	private Shape shape3;
	private Shape shape4;
	private PlanarVector pos1;
	private PlanarVector pos2;
	private PlanarVector pos3;
	private PlanarVector pos4;
	private double angle1;
	private double angle2;
	private double angle3;
	private double angle4;
	public TestFrame() {
		super("Test Frame");
		shape1 = new Heart(Heart.WHOLE, 1);
		pos1 = new PlanarVector(25, 150);
		angle1 = 0;
		shape2 = new Heart(Heart.THREE_QUARTERS, 1);
		pos2 = new PlanarVector(75, 150);
		angle2 = 0;
		shape3 = new Heart(Heart.HALF, 1);
		pos3 = new PlanarVector(125, 150);
		angle3 = 0;
		shape4 = new Heart(Heart.ONE_QUARTER, 1);
		pos4 = new PlanarVector(175, 150);
		angle4 = 0;
		setSize(500, 500);
		setVisible(true);
	}
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		setFill(g2d);
		if (ShapeTools.doShapesCollide(shape1, shape2, pos1, pos2, angle1, angle2)){
			g.setColor(Color.RED);
		}
		translate(g2d, pos1);
		g2d.rotate(angle1);
		shape1.getApproximation().draw(g);
		setOutline(g2d);
		shape1.getApproximation().outline(g);
		g2d.rotate(-1 * angle1);
		translate(g2d, pos1.negate());
		translate(g2d, pos2);
		g2d.rotate(angle2);
		setFill(g2d);
		shape2.getApproximation().draw(g);
		setOutline(g2d);
		shape1.outline(g);
		g2d.rotate(-1 * angle2);
		translate(g2d, pos2.negate());
		translate(g2d, pos3);
		g2d.rotate(angle3);
		setFill(g2d);
		shape3.getApproximation().draw(g);
		setOutline(g2d);
		shape1.outline(g);
		g2d.rotate(-1 * angle3);
		translate(g2d, pos3.negate());
		translate(g2d, pos4);
		g2d.rotate(angle4);
		setFill(g2d);
		shape4.getApproximation().draw(g);
		setOutline(g2d);
		shape1.outline(g);
	}
	private void translate(Graphics2D g2d, PlanarVector vec) {
		g2d.translate(vec.getXPos(), vec.getYPos());
	}
	private void setFill(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(1));
	}
	private void setOutline(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
	}
	
}
