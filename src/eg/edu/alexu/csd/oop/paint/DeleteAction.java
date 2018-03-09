package eg.edu.alexu.csd.oop.paint;

import java.util.LinkedList;

import javafx.scene.Group;

public class DeleteAction extends Action {

	private LinkedList<TheShape> shapes = new LinkedList<TheShape>();

	public  DeleteAction (LinkedList<TheShape> shapes) {
		this.shapes = shapes;
	}

	public void redo(Group root, LinkedList<TheShape> AllShapes) {
		for (int i = 0; i < shapes.size(); i++) {
			root.getChildren().remove(shapes.get(i).getShape());
			for (int j = 0; j < AllShapes.size(); j++) {
				if (AllShapes.get(j).equals(shapes.get(i))) {
					AllShapes.remove(j);
					break;
				}
			}
			for (int j = 0; j < shapes.get(i).getShapeAnchors().length; j++) {
				root.getChildren().remove(shapes.get(i).getShapeAnchors()[j].circle);
			}
		}
		
	}

	public void undo(Group root, LinkedList<TheShape> AllShapes) {
		for (int i = 0; i < shapes.size(); i++) {
			root.getChildren().add(shapes.get(i).getShape());
			AllShapes.add(shapes.get(i));
			for (int j = 0; j < shapes.get(i).getShapeAnchors().length; j++) {
				root.getChildren().add(shapes.get(i).getShapeAnchors()[j].circle);
			}
		}
		
	}

}
