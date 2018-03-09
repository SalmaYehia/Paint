
package eg.edu.alexu.csd.oop.paint;

import java.awt.Point;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class Anchor {

    public TheShape shape;
    public Circle circle;

    public Anchor(double x, double y, TheShape shape, Group root, Stack<Action> undoStack) {

        circle = new Circle(x, y, 10);
        circle.setFill(Color.CYAN.deriveColor(1, 1, 1, 0.5));
        circle.setStroke(Color.CYAN);
        circle.setStrokeWidth(2);
        circle.setStrokeType(StrokeType.OUTSIDE);

        this.shape = shape;

        enableDrag(root, undoStack);

    }

    public void move(double x, double y) {
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    // make a node movable by dragging it around with the mouse.

    public void enableDrag(final Group root, final Stack<Action> undoStack) {

        final Point dragDelta = new Point();
        final Point start = new Point();
        final Point end = new Point();

        circle.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override

            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                start.setLocation(mouseEvent.getX(), mouseEvent.getY());
                dragDelta.setLocation(circle.getCenterX() - mouseEvent.getX(), circle.getCenterY() - mouseEvent.getY());
                root.setCursor(javafx.scene.Cursor.MOVE);

            }

        });

        circle.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override

            public void handle(MouseEvent mouseEvent) {
                root.setCursor(Cursor.HAND);
                end.setLocation(mouseEvent.getX(), mouseEvent.getY());
                Action action = new ResizeAction(shape, Anchor.this, start, end);
                undoStack.add(action);
            }

        });

        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override

            public void handle(MouseEvent mouseEvent) {
                double newX = mouseEvent.getX() + dragDelta.x;
                double newY = mouseEvent.getY() + dragDelta.y;

                if (newX > 0 && newX < circle.getScene().getWidth() && newY > 0
                        && newY < circle.getScene().getHeight()) {
                    try {
                        shape.moveWithAnchors(Anchor.this, mouseEvent.getX(), mouseEvent.getY(), root);

                    } catch (Exception e) {
                        ///
                    }
                }
            }

        });

        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override

            public void handle(MouseEvent mouseEvent) {

                if (!mouseEvent.isPrimaryButtonDown()) {

                    root.setCursor(javafx.scene.Cursor.HAND);

                }

            }

        });

        circle.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override

            public void handle(MouseEvent mouseEvent) {

                if (!mouseEvent.isPrimaryButtonDown()) {

                    root.setCursor(javafx.scene.Cursor.DEFAULT);

                }

            }

        });

    }

}