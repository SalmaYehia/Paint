package eg.edu.alexu.csd.oop.paint;

import java.awt.Point;
import java.util.LinkedList;

import javafx.scene.Group;

public class ResizeAction extends Action {

	private Anchor anchor;
	private Point start = new Point();
	private Point end = new Point();
	private TheShape shape;

	public ResizeAction(TheShape shape, Anchor anchor, Point start, Point end) {
		this.shape = shape;
		this.anchor = anchor;
		this.start = start;
		this.end = end;
	}

	@Override
	public void undo(Group root, LinkedList<TheShape> AllShapes) {
		shape.moveWithAnchors(anchor, start.getX(), start.getY(), root);
	}

	@Override
	public void redo(Group root, LinkedList<TheShape> AllShapes) {
		shape.moveWithAnchors(anchor, end.getX(), end.getY(), root);

	}

}
