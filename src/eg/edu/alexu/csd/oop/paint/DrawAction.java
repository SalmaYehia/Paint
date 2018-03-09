package eg.edu.alexu.csd.oop.paint;

import java.util.LinkedList;

import javafx.scene.Group;

public class DrawAction extends Action {

	private TheShape shape;
	
	public  DrawAction (TheShape shape) {
		this.shape = shape;
	}

	public TheShape getTheShape () {
		return shape;
	}
	
	public void undo(Group root, LinkedList<TheShape> AllShapes) {
		root.getChildren().remove(shape.getShape());
		for (int i = 0; i < AllShapes.size(); i++) {
			if(AllShapes.get(i).equals(shape)) {
				AllShapes.remove(i);
				break;
			}
		}
		for (Anchor anchor : this.getTheShape().getShapeAnchors()) {
			root.getChildren().remove(anchor.circle);
		}
	}
	
	public void redo(Group root, LinkedList<TheShape> AllShapes) {
		root.getChildren().add(shape.getShape());
		AllShapes.add(shape);
		for (Anchor anchor : this.getTheShape().getShapeAnchors()) {
			root.getChildren().add(anchor.circle);
		}
	}
	
}
