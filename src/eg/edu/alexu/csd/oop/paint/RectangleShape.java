package eg.edu.alexu.csd.oop.paint;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Stack;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleShape extends ClosedShape {

	protected Rectangle rectangle = new Rectangle();
	protected Point start;
	protected Point end;
	protected Anchor upleft;
	protected Anchor upright;
	protected Anchor downleft;
	protected Anchor downright;
	// points where anchors

	public RectangleShape(Point corner, Double width, Double hight, Color color, Color border, Group root,
			LinkedList<TheShape> AllShapes, Stack<Action> undoStack) {
		
		rectangle.setX(corner.getX());
		rectangle.setY(corner.getY());

		rectangle.setWidth(width);
		rectangle.setHeight(hight);

		start = end = corner;

		setShape(rectangle);

		setEverything(color, border, root, AllShapes, undoStack);

	}

	/// used to set anchors in drawing
	@Override
	public void setCorners() {

		setPoints(new double[8]);

		// top left
		getPoints()[0] = rectangle.getX();
		getPoints()[1] = rectangle.getY();
		// top right
		getPoints()[2] = rectangle.getX() + rectangle.getWidth();
		getPoints()[3] = rectangle.getY();
		// bottom left
		getPoints()[4] = rectangle.getX();
		getPoints()[5] = rectangle.getY() + rectangle.getHeight();
		// bottom right
		getPoints()[6] = rectangle.getX() + rectangle.getWidth();
		getPoints()[7] = rectangle.getY() + rectangle.getHeight();
	}
	public double getX() {
		return rectangle.getX();
	}
	public double getY() {
		return rectangle.getY();
	}
	public double getwidth() {
		return rectangle.getWidth();
	}
	public double gethight() {
		return rectangle.getHeight();
	}

	@Override
	public void dragIt(Point x) {

		end = x;

		rectangle.setWidth(Math.abs((end.getX() - start.getX())));
		rectangle.setHeight(Math.abs((end.getY() - start.getY())));

		rectangle.setWidth(Math.abs((end.getX() - start.getX())));
		rectangle.setHeight(Math.abs((end.getY() - start.getY())));

		double centerX = (start.getX() + 0.5 * (end.getX() - start.getX()));
		double centerY = (start.getY() + 0.5 * (end.getY() - start.getY()));

		rectangle.setX(centerX - 0.5 * rectangle.getWidth());
		rectangle.setY(centerY - 0.5 * rectangle.getHeight());
		
		setCorners();

	}

	@Override
	public void moveWithAnchors(Anchor anchor, double endX, double endY, Group root) {

		double startX = anchor.circle.getCenterX();
		double startY = anchor.circle.getCenterY();
		/// upper left
		if (anchor.equals(upleft)) {
			rectangle.setX(endX);
			rectangle.setY(endY);
			rectangle.setWidth(Math.abs(rectangle.getWidth() + startX - endX));
			rectangle.setHeight(Math.abs(rectangle.getHeight() + startY - endY));

		} else if (anchor.equals(upright)) {
			rectangle.setY(rectangle.getY() + endY - startY);
			rectangle.setWidth(Math.max(rectangle.getWidth() - startX + endX, 0.0));
			rectangle.setHeight(Math.max(rectangle.getHeight() + startY - endY, 0.0));

		} else if (anchor.equals(downleft)) {
			rectangle.setX(rectangle.getX() + endX - startX);
			rectangle.setWidth(Math.max(rectangle.getWidth() + startX - endX, 0.0));
			rectangle.setHeight(Math.max(rectangle.getHeight() - startY + endY, 0.0));
		} else if (anchor.equals(downright)) {
			rectangle.setWidth(Math.abs(rectangle.getWidth() - startX + endX));
			rectangle.setHeight(Math.abs(rectangle.getHeight() - startY + endY));
		}
		setCorners();
		updateAnchors(root);
	}
	
	
	@Override
	public void createControlAnchorsFor(Group root,Stack<Action> undoStack) {

		setShapeAnchors(new Anchor[4]);
		getShapeAnchors()[0] = upleft = new Anchor(getPoints()[0], getPoints()[1], this, root, undoStack);
		getShapeAnchors()[1] = upright = new Anchor(getPoints()[2], getPoints()[3], this, root, undoStack);

		getShapeAnchors()[2] = downleft = new Anchor(getPoints()[4], getPoints()[5], this, root, undoStack);
		getShapeAnchors()[3] = downright = new Anchor(getPoints()[6], getPoints()[7], this, root, undoStack);

		root.getChildren().addAll(upleft.circle, upright.circle, downleft.circle, downright.circle);

	}

	@Override
	public void updateAnchors(Group root) {

		upleft.move(getPoints()[0], getPoints()[1]);
		upright.move(getPoints()[2], getPoints()[3]);
		downleft.move(getPoints()[4], getPoints()[5]);
		downright.move(getPoints()[6], getPoints()[7]);

	}

	@Override
	public void MoveParameters() {
		rectangle.setX(getPoints()[0]);
		rectangle.setY(getPoints()[1]);
		
	}
}
