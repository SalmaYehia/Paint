package eg.edu.alexu.csd.oop.paint;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class TheShape {

	private Shape shape;
	private boolean selected = true;
	private Color boarderColor;
	private double points[];
	private Anchor shapeAnchors[];
	private Point beforeMove = new Point();
	private Point afterMove = new Point();
	private Point now = new Point();
	private Point then = new Point();
	private Point dragDelta = new Point();
	private String type = "";
	public abstract void dragIt(Point x);

	void addColor(Color c) {
		getShape().setStroke(c);
		setBoarderColor(c);
	}

	private void MousePressed() {

		getShape().setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				then.setLocation(mouseEvent.getX(), mouseEvent.getY());
				beforeMove.setLocation(mouseEvent.getX(), mouseEvent.getY());

			}
		});

	}

	private void MouseDrag(final Group root) {
		getShape().setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				now.setLocation(mouseEvent.getX(), mouseEvent.getY());
				dragDelta.setLocation(now.x - then.x , now.y - then.y);

				for(int i = 0; i < points.length - 1; i+=2) {
					points[i] += dragDelta.getX();
					points[i + 1] += dragDelta.getY();

				}
				MoveParameters();
				updateAnchors(root);
				then.setLocation(now);
			}
		});

	}

	private void MouseClick(final Group root, final LinkedList<TheShape> AllShapes) {
		getShape().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				TheShape.this.Selected(root, AllShapes);

			}
		});
	}

	private void MouseEntered(final Group root) {
		getShape().setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(final MouseEvent arg0) {
				root.setCursor(Cursor.CLOSED_HAND);
			}
		});

	}

	private void MouseExited (final Group root) {
		getShape().setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(final MouseEvent arg0) {
				root.setCursor(Cursor.DEFAULT);
			}
		});

	}

	private void MouseReleased(final Group root, final Stack<Action> undoStack) {
		getShape().setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				root.setCursor(Cursor.DEFAULT);
				afterMove.setLocation(arg0.getX(), arg0.getY());
				Action action = new MoveAction(TheShape.this, beforeMove, afterMove);
				undoStack.push(action);

			}
		});
	}

	public void SetActions(Group root, LinkedList<TheShape> AllShapes, Stack<Action> undoStack) {

		MousePressed();
		MouseDrag(root);
		MouseClick(root, AllShapes);
		MouseEntered(root);
		MouseExited(root);
		MouseReleased(root, undoStack);

	}

	public void delete(Group Root, LinkedList<TheShape> AllShapes) {
		Root.getChildren().remove(getShape());
		for (int i = 0; i < AllShapes.size() ; i++) {
			if (AllShapes.get(i).equals(this)) {
				AllShapes.remove(i);
			}
		}
		for (Anchor anchor : getShapeAnchors()) {
			Root.getChildren().remove(anchor.circle);
		}
	}

	public boolean isSelected() {
		return selected;
	}

	public void setEverything(Color color, Color border, Group Root, LinkedList<TheShape> AllShapes, Stack<Action> undoStack) {
		shape.setStrokeWidth(3);
		AllShapes.add(this);
		Root.getChildren().add(this.getShape());
		SetActions(Root, AllShapes, undoStack);
		for (int i = 0; i < AllShapes.size() - 1; i++) {
			TheShape crnt = AllShapes.get(i);
			crnt.notSelected(Root);
		}
		addColor(color);
	}

	public void notSelected(Group root) {
		selected = false;
		for (Anchor anchor : getShapeAnchors()) {
			root.getChildren().remove(anchor.circle);
		}
	}

	public void Selected(Group root, LinkedList<TheShape> AllShapes) {
		selected = true;
		for (Anchor anchor : getShapeAnchors()) {
			if (root.getChildren().contains(anchor.circle)) {
				break;
			}
			root.getChildren().add(anchor.circle);
		}

	}

	public abstract void createControlAnchorsFor(Group root,Stack<Action> undoStack);

	public abstract void moveWithAnchors(Anchor anchor, double endX, double endY, Group root);

	public abstract void setCorners();

	public abstract void MoveParameters();

	public abstract void updateAnchors(Group root);

	public double[] getPoints() {
		return points;
	}

	public void setPoints(double points[]) {
		this.points = points;
	}

	public Color getBoarderColor() {
		return boarderColor;
	}

	public void setBoarderColor(Color boarderColor) {
		this.boarderColor = boarderColor;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public Anchor[] getShapeAnchors() {
		return shapeAnchors;
	}

	public void setShapeAnchors(Anchor shapeAnchors[]) {
		this.shapeAnchors = shapeAnchors;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}