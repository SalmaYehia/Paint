package eg.edu.alexu.csd.oop.paint;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class TriangleShape extends ClosedShape {
	private Polygon tri = new Polygon();
	private Anchor first, second, third;

	public TriangleShape(Point x, Point y, Color color, Color border, Group root, LinkedList<TheShape> AllShapes,
			Stack<Action> undoStack) {
		List<Double> values = new ArrayList<Double>();
		values.add((double) x.x);
		values.add((double) x.y);
		values.add((double) y.x);
		values.add((double) y.y);
		values.add((double) y.x + 20);
		values.add((double) y.y + 20);
		tri.getPoints().addAll(values);
		setShape(tri);

		setEverything(color, border, root, AllShapes, undoStack);

	}

	public TriangleShape(Point x, Point y, Point z, Color color, Color border, Group root,
			LinkedList<TheShape> AllShapes, Stack<Action> undoStack) {
		List<Double> values = new ArrayList<Double>();
		values.add((double) x.x);
		values.add((double) x.y);
		values.add((double) y.x);
		values.add((double) y.y);
		values.add((double) z.x);
		values.add((double) z.y);
		tri.getPoints().addAll(values);
		setShape(tri);

		setEverything(color, border, root, AllShapes, undoStack);

		setCorners();

	}

	@Override
	public void dragIt(Point x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createControlAnchorsFor(Group root, Stack<Action> undoStack) {

		setShapeAnchors(new Anchor[3]);
		getShapeAnchors()[0] = first = new Anchor(getPoints()[0], getPoints()[1], this, root, undoStack);
		getShapeAnchors()[1] = second = new Anchor(getPoints()[2], getPoints()[3], this, root, undoStack);

		getShapeAnchors()[2] = third = new Anchor(getPoints()[4], getPoints()[5], this, root, undoStack);

		root.getChildren().addAll(first.circle, second.circle, third.circle);
	}

	@Override
	public void moveWithAnchors(Anchor anchor, double endX, double endY, Group root) {

		if (anchor.equals(first)) {
			tri.getPoints().set(0, endX);
			tri.getPoints().set(1, endY);

		} else if (anchor.equals(second)) {
			tri.getPoints().set(2, endX);
			tri.getPoints().set(3, endY);

		} else if (anchor.equals(third)) {
			tri.getPoints().set(4, endX);
			tri.getPoints().set(5, endY);
		}
		setCorners();
		updateAnchors(root);
	}

	@Override
	public void setCorners() {
		setPoints(new double[6]);
		for (int i = 0; i < this.getPoints().length; i++) {
			getPoints()[i] = tri.getPoints().get(i);
		}

	}

	@Override
	public void updateAnchors(Group root) {

		first.move(getPoints()[0], getPoints()[1]);
		second.move(getPoints()[2], getPoints()[3]);
		third.move(getPoints()[4], getPoints()[5]);
	}

	@Override
	public void MoveParameters() {
		// TODO Auto-generated method stub
		for (int i = 0; i < getPoints().length; i++) {
			tri.getPoints().set(i, getPoints()[i]);
		}
	}

}