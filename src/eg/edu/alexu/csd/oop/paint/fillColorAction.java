package eg.edu.alexu.csd.oop.paint;

import java.util.LinkedList;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class fillColorAction extends Action {
	private Color lastColor;
	private LinkedList<Color> firstColor;
	private LinkedList<TheShape> shapes;

	public fillColorAction(LinkedList<TheShape> shapes, LinkedList<Color> firstColor, Color lastColor) {
		this.shapes = shapes;
		this.firstColor = firstColor;
		this.lastColor = lastColor;
	}

	@Override
	public void undo(Group root, LinkedList<TheShape> AllShapes) {
		for (int i = 0; i < shapes.size(); i++) {
			TheShape shape = shapes.get(i);
			try {
				((ClosedShape) shape).setStyle(firstColor.get(i));
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void redo(Group root, LinkedList<TheShape> AllShapes) {
		// TODO Auto-generated method stub
		for (int i = 0; i < shapes.size(); i++) {
			TheShape shape = shapes.get(i);
			try {
				((ClosedShape) shape).setStyle(lastColor);
			} catch (Exception e) {
			}
		}
	}
}
