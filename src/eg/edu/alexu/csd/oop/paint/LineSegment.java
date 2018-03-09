package eg.edu.alexu.csd.oop.paint;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class LineSegment extends TheShape {

	private Polygon line = new Polygon();
	private Point first = new Point();
	private Point second = new Point();

	private Anchor start, end;

	public LineSegment(Point x, Point y, Color color, Group root, LinkedList<TheShape> AllShapes,
			Stack<Action> undoStack) {

		getFirst().setLocation(x.x, x.y);
		getSecond().setLocation(y.x, y.y);
		List<Double> values = new ArrayList<Double>();
		values.add((double) x.x);
		values.add((double) x.y);
		values.add((double) y.x);
		values.add((double) y.y);
		line.getPoints().addAll(values);
		setShape(getLine());

		getLine().setStrokeWidth(8);

		setEverything(color, color, root, AllShapes, undoStack);

	}

	@Override
	public void dragIt(Point x) {

		getLine().getPoints().add(2, (double) x.x);
		getLine().getPoints().add(3, (double) x.y);
		getLine().getPoints().remove(4);
		getLine().getPoints().remove(4);

		setCorners();
	}

	@Override
	public void moveWithAnchors(Anchor anchor, double endX, double endY, Group root) {
		if (start.equals(anchor)) {
			getLine().getPoints().set(0, endX);
			getLine().getPoints().set(1, endY);

		} else if (end.equals(anchor)) {
			getLine().getPoints().set(2, endX);
			getLine().getPoints().set(3, endY);

		}
		setCorners();
		updateAnchors(root);
	}

	@Override
	public void createControlAnchorsFor(Group root, Stack<Action> undoStack) {
		setShapeAnchors(new Anchor[2]);

		getShapeAnchors()[0] = start = new Anchor(getPoints()[0], getPoints()[1], this, root, undoStack);
		getShapeAnchors()[1] = end = new Anchor(getPoints()[2], getPoints()[3], this, root, undoStack);

		root.getChildren().addAll(start.circle, end.circle);

	}

	@Override
	public void setCorners() {
		setPoints(new double[4]);

		// start
		getPoints()[0] = getLine().getPoints().get(0);
		getPoints()[1] = getLine().getPoints().get(1);
		// end
		getPoints()[2] = getLine().getPoints().get(2);
		getPoints()[3] = getLine().getPoints().get(3);

	}

	@Override
	public void updateAnchors(Group root) {

		start.move(getPoints()[0], getPoints()[1]);
		end.move(getPoints()[2], getPoints()[3]);

	}

	public Point getFirst() {
		return first;
	}

	public void setFirst(Point first) {
		this.first = first;
	}

	public Point getSecond() {
		return second;
	}

	public void setSecond(Point second) {
		this.second = second;
	}

	public Polygon getLine() {
		return line;
	}

	public void setLine(Polygon line) {
		this.line = line;
	}

	@Override
	public void MoveParameters() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 4; i++) {
			line.getPoints().set(i, getPoints()[i]);
		}
	}

}