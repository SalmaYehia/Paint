package eg.edu.alexu.csd.oop.paint;


import java.util.LinkedList;

import javafx.scene.Group;

public abstract class Action {

	// draw move resize color delete
	public abstract void undo(Group root, LinkedList<TheShape> AllShapes);

	public abstract void redo(Group root, LinkedList<TheShape> AllShapes);



}
