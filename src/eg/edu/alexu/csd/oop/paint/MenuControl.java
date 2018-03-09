package eg.edu.alexu.csd.oop.paint;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

import javafx.scene.Group;

public class MenuControl {

    @SuppressWarnings("rawtypes")
    public Group fileOpen(File file, Group root, LinkedList<TheShape> AllShapes, Stack<Action> undoStack, Class c,
            Class s) throws Exception {
        String extension = "";
        int i = file.getAbsolutePath().lastIndexOf('.');
        if (i > 0) {
            extension = file.getAbsolutePath().substring(i + 1);
        }
        if (extension.equals("xml")) {
            XML xmlFile = new XML();
            try {
                return xmlFile.loadXML(file, root, AllShapes, undoStack, c, s);
            } catch (Exception e) {
                throw e;
            }
        } else {
            JSON jsonFile = new JSON();
            try {
                return jsonFile.loadJSON(file, root, AllShapes, undoStack, c, s);
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public void fileSave(final LinkedList<TheShape> allShapes, final File file) throws IOException {
        String extension = "";
        int i = file.getAbsolutePath().lastIndexOf('.');
        if (i > 0) {
            extension = file.getAbsolutePath().substring(i + 1);
        }
        if (extension.equals("xml")) {
            XML xmlFile = new XML();
            xmlFile.saveXML(allShapes, file);
        } else if (extension.equals("json")) {
            JSON jsonFile = new JSON();
            jsonFile.saveJSON(allShapes, file);
        }
    }

}
