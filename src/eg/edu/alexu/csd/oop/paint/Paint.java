package eg.edu.alexu.csd.oop.paint;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.Stack;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Paint extends Application {

    Stage window;
    MenuControl MControl = new MenuControl();
    Group root = new Group();
    String state = "";
    TheShape current;
    Class<?> c =  null, s = null;
    int counter = 0;
    File circleJar, squareJar;
    private boolean fillColor = false;
    boolean found = false;
    private LinkedList<TheShape> AllShapes = new LinkedList<TheShape>();
    private ColorPicker colorPicker = new ColorPicker();
    private Stack<Action> undoStack = new Stack<Action>();
    private Stack<Action> redoStack = new Stack<Action>();

    @Override
    public void start(Stage arg0) throws Exception {

        final Stage window;
        window = arg0;
        window.setTitle("Flying Colors");
        BorderPane layout = new BorderPane();
        Scene scene = new Scene(layout, 1000, 600);

        // --- Menu bar
        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu menuFile = new Menu("File");

        MenuItem New = new MenuItem("New");
        New.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {

                CheckBeforeOpen();
            }
        });

        MenuItem Open = new MenuItem("Open File...");
        Open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                //CheckBeforeOpen();
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML Files", "*.xml"),
                        new ExtensionFilter("JSON Files", "*.json"));
                File file = fileChooser.showOpenDialog(window);
                try {
                    root = MControl.fileOpen(file, root, AllShapes, undoStack, c, s);
                } catch (Exception e) {
                    if (file.getAbsolutePath() != "") {
                        final Stage dialog = new Stage();
                        dialog.setTitle("Error");
                        dialog.setMinWidth(200);
                        final Label msg = new Label("Please, load the required jars and try again");

                        final Button Ok = new Button("Ok");
                        Ok.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent arg0) {
                                dialog.close();
                            }
                        });
                        VBox sP = new VBox(20);
                        sP.setAlignment(Pos.CENTER);
                        sP.getChildren().addAll(msg, Ok);
                        Scene s = new Scene(sP, 400, 100);
                        dialog.setScene(s);
                        dialog.show();
                    }
                }
            }
        });

        MenuItem Save = new MenuItem("Save");
        Save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                save();
            }
        });

        menuFile.getItems().addAll(New, Open, Save);
        menuBar.getMenus().addAll(menuFile);
        layout.setTop(menuBar);

        // --- Tools Area
        VBox toolsArea = new VBox(10);
        toolsArea.setPrefWidth(60);
        toolsArea.setId("toolsArea");

        final Canvas drawingArea = new Canvas(scene.getWidth(), scene.getHeight());

        drawingArea.setOnMouseClicked(click);
        scene.setOnKeyPressed(pressKey);
        drawingArea.setOnMouseDragged(drag);
        drawingArea.setOnMouseReleased(leave);

        ImageView line = new ImageView(new Image(getClass().getResource("/resources/seg.jpg").toURI().toString()));
        line.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                // TODO Auto-generated method stub
                if (state != "line") {
                    state = "line";
                }
                counter = 0;
            }
        });

        final ImageView square = new ImageView(
                new Image(getClass().getResource("/resources/square.jpg").toURI().toString()));
        square.setVisible(false);
        square.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                // TODO Auto-generated method stub
                if (state != "square") {
                    state = "square";
                }
                counter = 0;
            }
        });

        ImageView triangle = new ImageView(new Image(getClass().getResource("/resources/tri.jpg").toURI().toString()));
        triangle.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                // TODO Auto-generated method stub
                if (state != "triangle") {
                    state = "triangle";
                }
                counter = 0;
            }
        });
        final ImageView circle = new ImageView(
                new Image(getClass().getResource("/resources/circle.jpg").toURI().toString()));
        circle.setVisible(false);
        circle.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                // TODO Auto-generated method stub
                if (state != "circle") {
                    state = "circle";
                }
                counter = 0;
            }
        });
        ImageView ellipse = new ImageView(new Image(getClass().getResource("/resources/elli.jpg").toURI().toString()));
        ellipse.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                // TODO Auto-generated method stub
                if (state != "ellipse") {
                    state = "ellipse";
                }
                counter = 0;
            }
        });
        ImageView rectangular = new ImageView(
                new Image(getClass().getResource("/resources/rec.jpg").toURI().toString()));
        rectangular.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                // TODO Auto-generated method stub
                if (state != "rectangle") {
                    state = "rectangle";
                }
                counter = 0;
            }
        });
        ImageView fill = new ImageView(new Image(getClass().getResource("/resources/fill.jpg").toURI().toString()));
        fill.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                // TODO Auto-generated method stub
                fillColor = !fillColor;
            }
        });
        ImageView select = new ImageView(new Image(getClass().getResource("/resources/select.jpg").toURI().toString()));
        select.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                // TODO Auto-generated method stub
                state = "";
                for (TheShape crnt : AllShapes) {
                    crnt.notSelected(root);
                }
            }
        });

        Button loading = new Button("Load");
        loading.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Jar files", "*.jar"));
                File file = fileChooser.showOpenDialog(window);
                String path = file.getAbsolutePath();
                URL url;
                ClassLoader cl;
                if (path.endsWith("CircleShape.jar")) {
                    circle.setVisible(true);
                    try {
                        circleJar = file;
                        url = file.toURI().toURL();
                        URL[] urls = new URL[] { url };
                        cl = new URLClassLoader(urls);
                        c = cl.loadClass("eg.edu.alexu.csd.oop.paint.CircleShape");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (path.endsWith("SquareShape.jar")) {
                    square.setVisible(true);
                    try {
                        squareJar = file;
                        url = file.toURI().toURL();
                        URL[] urls = new URL[] { url };
                        cl = new URLClassLoader(urls);
                        s = cl.loadClass("eg.edu.alexu.csd.oop.paint.SquareShape");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    final Stage dialog = new Stage();
                    dialog.setTitle("Error");
                    dialog.setMinWidth(200);
                    final Label x = new Label("Not supported jar");

                    final Button ok = new Button("Ok");
                    ok.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                            dialog.close();
                        }
                    });

                    VBox sP = new VBox(20);
                    sP.setAlignment(Pos.CENTER);
                    sP.getChildren().addAll(x, ok);
                    Scene s = new Scene(sP, 400, 100);
                    dialog.setScene(s);
                    dialog.show();
                }
            }
        });

        toolsArea.getChildren().addAll(line, ellipse, rectangular, triangle, square, circle, fill, select, loading);
        layout.setLeft(toolsArea);

        // --- Drawing Area

        final GraphicsContext gc = drawingArea.getGraphicsContext2D();
        gc.setStroke(Color.BISQUE);
        gc.setLineWidth(5);
        drawingArea.setId("drawingArea");
        root.getChildren().add(drawingArea);
        layout.setCenter(root);

        // --- Color Picker
        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                Color c = colorPicker.getValue();
                LinkedList<TheShape> selectedShapes = new LinkedList<TheShape>();
                LinkedList<Color> selectedcolors = new LinkedList<Color>();

                for (TheShape current : AllShapes) {
                    if (current.isSelected()) {
                        selectedShapes.add(current);

                        if (fillColor && current instanceof ClosedShape) {
                            selectedcolors.add(((ClosedShape) current).getColor());
                            ((ClosedShape) current).setStyle(c);
                        } else {
                            selectedcolors.add(current.getBoarderColor());
                            current.addColor(c);
                        }
                    }
                }
                if(!selectedShapes.isEmpty()){

                    if (!fillColor) {
                        Action action = new ColorAction(selectedShapes, selectedcolors, c);
                        undoStack.push(action);
                    } else {
                        Action action = new fillColorAction(selectedShapes, selectedcolors, c);
                        undoStack.push(action);
                    }
                }
            }
        });

        toolsArea.getChildren().add(colorPicker);

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        window.setScene(scene);
        window.show();

    }

    LinkedList<Point> clickpoint = new LinkedList<Point>();

    EventHandler<MouseEvent> click = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (state == "triangle") {
                Point point = new Point();
                point.setLocation(event.getX(), event.getY());
                if (counter % 2 == 0) {
                    clickpoint.add(point);
                } else if (counter % 2 == 1) {
                    TriangleShape triangle = new TriangleShape(clickpoint.getLast(), point, colorPicker.getValue(), colorPicker.getValue(),
                            root, AllShapes, undoStack);
                    Action action = new DrawAction(triangle);
                    undoStack.push(action);
                    triangle.setCorners();
                    triangle.createControlAnchorsFor(root, undoStack);

                }
                counter++;

            } else {
                for (TheShape crnt : AllShapes) {
                    crnt.notSelected(root);
                }
            }
        }
    };

    EventHandler<KeyEvent> pressKey = new EventHandler<KeyEvent>() {
        @Override
        public void handle(final KeyEvent keyEvent) {

            if (keyEvent.getCode() == KeyCode.DELETE) {
                LinkedList<TheShape> selectedShapes = new LinkedList<TheShape>();
                for (TheShape current : AllShapes) {
                    if (current.isSelected()) {
                        selectedShapes.add(current);
                    }
                }
                for (TheShape sel : selectedShapes) {
                    sel.delete(root, AllShapes);

                }
                Action action = new DeleteAction(selectedShapes);
                undoStack.push(action);
            } else if (keyEvent.getCode() == KeyCode.U) {
                // undo
                if (!undoStack.isEmpty()) {
                    Action action = undoStack.pop();
                    // System.out.println(action);
                    action.undo(root, AllShapes);
                    redoStack.push(action);
                }

            } else if (keyEvent.getCode() == KeyCode.R) {
                // redo
                if (!redoStack.isEmpty()) {
                    Action action = redoStack.pop();
                    action.redo(root, AllShapes);
                    undoStack.push(action);
                }

            }
        }
    };

    EventHandler<MouseEvent> drag = (new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            Point now = new Point();
            now.setLocation(event.getSceneX(), event.getSceneY());

            // TODO Auto-generated method stub
            if (state == "line") {
                if (current == null) {
                    LineSegment line = new LineSegment(now, now, colorPicker.getValue(), root, AllShapes, undoStack);
                    current = line;
                } else {
                    current.dragIt(now);
                }
            } else if (state == "ellipse") {
                if (current == null) {
                    Oval oval = new Oval(now, 0.0, 0.0, colorPicker.getValue(), colorPicker.getValue(), root, AllShapes, undoStack);
                    current = oval;
                } else {
                    current.dragIt(now);
                }

            } else if (state == "circle") {
                if (current == null) {

                    try {
                        Constructor<?>[] con = c.getConstructors();
                        TheShape obj = (TheShape) con[0].newInstance(now, 0.0, 0.0, colorPicker.getValue(),colorPicker.getValue(), root,
                                AllShapes, undoStack);
                        current = obj;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    current.dragIt(now);
                }

            } else if (state == "rectangle") {
                if (current == null) {
                    RectangleShape rectangle = new RectangleShape(now, 0.0, 0.0, colorPicker.getValue(), colorPicker.getValue(), root,
                            AllShapes, undoStack);
                    current = rectangle;
                } else {
                    current.dragIt(now);
                }
            } else if (state == "square") {
                if (current == null) {

                    try {
                        Constructor<?>[] con = s.getConstructors();
                        TheShape obj = (TheShape) con[0].newInstance(now, 0.0, 0.0, colorPicker.getValue(),colorPicker.getValue(), root,
                                AllShapes, undoStack);
                        current = obj;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    current.dragIt(now);
                }
            }
        }
    });

    EventHandler<MouseEvent> leave = (new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            // TODO Auto-generated method stub
            if (current != null) {
                Action action = new DrawAction(current);
                undoStack.push(action);
                current.createControlAnchorsFor(root, undoStack);
                current = null;
            }
        }
    });

    public void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("FlyingColors.xml");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML files (*.xml)", "*.xml"),
                new ExtensionFilter("JSON files (*.json)", "*.json"));

        // Show save file dialog
        File file = fileChooser.showSaveDialog(window);
        try {
            MControl.fileSave(AllShapes, file);
        } catch (IOException e) {

        }
    }

    private void CheckBeforeOpen() {
        final Stage dialog = new Stage();
        dialog.setTitle("To save or not to save");
        dialog.setMinWidth(200);
        final Label x = new Label("Do you want to save your current masterpiece?");

        final Button Yes = new Button("Yes");
        Yes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // save and open new one
                save();
                root.getChildren().clear();
                AllShapes.clear();
                undoStack.empty();
                redoStack.empty();
                counter = 0;
                fillColor = false;
                found = false;
                dialog.close();
            }
        });

        final Button No = new Button("No");
        No.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // open new one
                root.getChildren().clear();
                AllShapes.clear();
                undoStack.empty();
                redoStack.empty();
                counter = 0;
                fillColor = false;
                found = false;
                dialog.close();
            }
        });

        VBox sP = new VBox(20);
        sP.setAlignment(Pos.CENTER);
        sP.getChildren().addAll(x, Yes, No);
        Scene s = new Scene(sP, 400, 100);
        dialog.setScene(s);
        dialog.show();
    }

}