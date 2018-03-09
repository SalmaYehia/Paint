package eg.edu.alexu.csd.oop.paint;

import java.util.LinkedList;
import java.util.Stack;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public abstract class ClosedShape extends TheShape {
	
	protected Color color ; 

	public void setStyle(Color color) {
		getShape().setFill(color);
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	@Override
	public void setEverything(Color color,Color border, Group Root, LinkedList<TheShape> AllShapes, Stack<Action> undoStack) {
		getShape().setStrokeWidth(3);
		AllShapes.add(this);
		Root.getChildren().add(getShape());
		SetActions(Root, AllShapes, undoStack);
		for (int i = 0; i < AllShapes.size() - 1; i++) {
			TheShape crnt = AllShapes.get(i);
			crnt.notSelected(Root);
		}
		addColor(border);
		setStyle(color);
	}

}
