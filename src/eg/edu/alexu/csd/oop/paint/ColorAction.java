package eg.edu.alexu.csd.oop.paint;

import java.util.LinkedList;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class ColorAction extends Action {

	private Color lastColor;
	private LinkedList<Color> firstColor;
	private LinkedList<TheShape> shapes;

	public ColorAction(LinkedList<TheShape> shapes, LinkedList<Color> firstColor, Color lastColor) {
		this.shapes = shapes;
		this.firstColor = firstColor;
		this.lastColor = lastColor;
	}

	@Override
	public void undo(Group root, LinkedList<TheShape> AllShapes) {
		for (int i = 0; i < shapes.size(); i++) {
			TheShape shape = shapes.get(i);
			shape.addColor(firstColor.get(i));
		}
	}

	@Override
	public void redo(Group root, LinkedList<TheShape> AllShapes) {
		// TODO Auto-generated method stub
		for (int i = 0; i < shapes.size(); i++) {
			TheShape shape = shapes.get(i);
			shape.addColor(lastColor);
		}
	}


}
