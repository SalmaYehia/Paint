package eg.edu.alexu.csd.oop.paint;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Stack;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Oval extends ClosedShape {

	protected Ellipse ellipse = new Ellipse();
	protected Point start, end;
	// start corner and end corner
	protected Point Center;
	protected Double rad1, rad2;
	protected Anchor top, bottom, left, right;
	///===NA
	String type = "";

	public Oval(Point center, Double radius1, Double radius2, Color color, Color border, Group Root, LinkedList<TheShape> AllShapes,
			Stack<Action> undoStack) {
		Center = center;
		rad1 = radius1;
		rad2 = radius2;
		ellipse.setCenterX(center.getX());
		ellipse.setCenterY(center.getY());
		ellipse.setRadiusX(radius1);
		ellipse.setRadiusY(radius2);
		start = end = center;

		setShape(ellipse);

		setEverything(color, border,  Root, AllShapes, undoStack);

	}

	@Override
	public void dragIt(Point x) {

		end = x;
		rad1 = Math.abs(0.5 * (end.getX() - start.getX()));
		rad2 = Math.abs(0.5 * (end.getY() - start.getY()));

		ellipse.setRadiusX(Math.abs(0.5 * (end.getX() - start.getX())));
		ellipse.setRadiusY(Math.abs(0.5 * (end.getY() - start.getY())));
		ellipse.setCenterX(start.getX() + 0.5 * (end.getX() - start.getX()));
		ellipse.setCenterY(start.getY() + 0.5 * (end.getY() - start.getY()));

		setCorners();
	}

	@Override
	public void createControlAnchorsFor(Group root, Stack<Action> undoStack) {

		setShapeAnchors(new Anchor[4]);

		getShapeAnchors()[0] = top = new Anchor(getPoints()[0], getPoints()[1], this, root, undoStack);
		getShapeAnchors()[1] = right = new Anchor(getPoints()[2], getPoints()[3], this, root, undoStack);

		getShapeAnchors()[2] = bottom = new Anchor(getPoints()[4], getPoints()[5], this, root, undoStack);
		getShapeAnchors()[3] = left = new Anchor(getPoints()[6], getPoints()[7], this, root, undoStack);

		root.getChildren().addAll(top.circle, bottom.circle, right.circle, left.circle);
	}

	@Override
	public void moveWithAnchors(Anchor anchor, double endX, double endY, Group root) {
		double startX = anchor.circle.getCenterX();
		double startY = anchor.circle.getCenterY();
		/// upper left
		if (anchor.equals(top)) {
			ellipse.setRadiusY(Math.max(ellipse.getRadiusY() + 0.5 * (startY - endY), 0.0));

		} else if (anchor.equals(bottom)) {
			ellipse.setRadiusY(Math.max(ellipse.getRadiusY() - 0.5 * (startY - endY), 0.0));

		} else if (anchor.equals(left)) {
			ellipse.setRadiusX(Math.max(ellipse.getRadiusX() + 0.5 * (startX - endX), 0.0));

		} else if (anchor.equals(right)) {
			ellipse.setRadiusX(Math.max(ellipse.getRadiusX() - 0.5 * (startX - endX), 0.0));

		}
		setCorners();
		updateAnchors(root);

	}

	@Override
	public void setCorners() {

		setPoints(new double[8]);

		// top
		getPoints()[0] = ellipse.getCenterX();
		getPoints()[1] = ellipse.getCenterY() - ellipse.getRadiusY();
		// right
		getPoints()[2] = ellipse.getCenterX() + ellipse.getRadiusX();
		getPoints()[3] = ellipse.getCenterY();
		// bottom
		getPoints()[4] = ellipse.getCenterX();
		getPoints()[5] = ellipse.getCenterY() + ellipse.getRadiusY();
		// left
		getPoints()[6] = ellipse.getCenterX() - ellipse.getRadiusX();
		getPoints()[7] = ellipse.getCenterY();

	}

	@Override
	public void updateAnchors(Group root) {
		setCorners();

		top.move(getPoints()[0], getPoints()[1]);
		right.move(getPoints()[2], getPoints()[3]);
		bottom.move(getPoints()[4], getPoints()[5]);
		left.move(getPoints()[6], getPoints()[7]);
	}

	@Override
	public void MoveParameters() {
		// TODO Auto-generated method stub
		ellipse.setCenterX(getPoints()[4]);
		ellipse.setCenterY(getPoints()[3]);

	}

	public  Double getCenterX() {
		return ellipse.getCenterX();
	}
	public  Double getCenterY() {
		return ellipse.getCenterY();
	}
	public  Double getRadiusX() {
		return ellipse.getRadiusX();
	}
	public  Double getRadiusY() {
		return ellipse.getRadiusY();
	}
}
