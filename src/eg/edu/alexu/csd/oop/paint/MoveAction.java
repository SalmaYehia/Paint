package eg.edu.alexu.csd.oop.paint;

import java.awt.Point;
import java.util.LinkedList;
import javafx.scene.Group;

public class MoveAction extends Action {

	private Point delta = new Point();
	private TheShape shape;
	private double points[];

	MoveAction(TheShape shape, Point beforeMove, Point afterMove) {
		this.shape = shape;
		this.delta.setLocation(afterMove.x - beforeMove.x, afterMove.y - beforeMove.y);;
		points = shape.getPoints();
	}

	@Override
	public void undo(Group root, LinkedList<TheShape> AllShapes) {
		for(int i = 0; i < points.length - 1; i+=2) {
			points[i] -= delta.getX();
			points[i + 1] -= delta.getY();
			
		}
		shape.MoveParameters();
		shape.updateAnchors(root);
	}

	@Override
	public void redo(Group root, LinkedList<TheShape> AllShapes) {
		for(int i = 0; i < points.length - 1; i+=2) {
			points[i] += delta.getX();
			points[i + 1] += delta.getY();
			
		}
		shape.MoveParameters();
		shape.updateAnchors(root);
	}

}
