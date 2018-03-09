package eg.edu.alexu.csd.oop.paint;

import java.awt.Point;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class XML {

    public void saveXML(LinkedList<TheShape> allShapes, File file) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // --- root Element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("allShapes");
            doc.appendChild(rootElement);

            for (TheShape current : allShapes) {
                if (current instanceof LineSegment) {
                    Element shape = doc.createElement("shape");
                    Attr type = doc.createAttribute("type");
                    type.setValue("line");
                    shape.setAttributeNode(type);
                    rootElement.appendChild(shape);

                    Element x1 = doc.createElement("x1");
                    Element y1 = doc.createElement("y1");
                    Element x2 = doc.createElement("x2");
                    Element y2 = doc.createElement("y2");

                    double[] Points = current.getPoints();
                    x1.appendChild(doc.createTextNode(String.valueOf(Points[0])));
                    y1.appendChild(doc.createTextNode(String.valueOf(Points[1])));
                    x2.appendChild(doc.createTextNode(String.valueOf(Points[2])));
                    y2.appendChild(doc.createTextNode(String.valueOf(Points[3])));

                    Element Bcolor = doc.createElement("Bcolor");
                    Bcolor.appendChild(doc.createTextNode((current.getBoarderColor()).toString()));

                    shape.appendChild(x1);
                    shape.appendChild(y1);
                    shape.appendChild(x2);
                    shape.appendChild(y2);
                    shape.appendChild(Bcolor);
                    rootElement.appendChild(shape);

                } else if (current.getType() == "circle") {
                    Element shape = doc.createElement("shape");
                    Attr type = doc.createAttribute("type");
                    type.setValue("circle");
                    shape.setAttributeNode(type);
                    rootElement.appendChild(shape);

                    Element x = doc.createElement("x");
                    Element y = doc.createElement("y");
                    Element rad = doc.createElement("rad");
                    Element Bcolor = doc.createElement("Bcolor");
                    Element fcolor = doc.createElement("fcolor");
                    fcolor.appendChild(doc.createTextNode(String.valueOf(((ClosedShape) current).getColor())));
                    shape.appendChild(fcolor);
                    x.appendChild(doc.createTextNode(String.valueOf(((Oval) current).getCenterX())));
                    y.appendChild(doc.createTextNode(String.valueOf(((Oval) current).getCenterY())));
                    rad.appendChild(doc.createTextNode(String.valueOf(((Oval) current).getRadiusX())));
                    Bcolor.appendChild(doc.createTextNode(current.getBoarderColor().toString()));
                    shape.appendChild(x);
                    shape.appendChild(y);
                    shape.appendChild(rad);
                    shape.appendChild(Bcolor);
                    rootElement.appendChild(shape);

                } else if (current instanceof Oval) {
                    Element shape = doc.createElement("shape");
                    Attr type = doc.createAttribute("type");
                    type.setValue("oval");
                    shape.setAttributeNode(type);
                    rootElement.appendChild(shape);

                    Element x = doc.createElement("x");
                    Element y = doc.createElement("y");
                    Element rad1 = doc.createElement("rad1");
                    Element rad2 = doc.createElement("rad2");

                    x.appendChild(doc.createTextNode(String.valueOf(((Oval) current).getCenterX())));
                    y.appendChild(doc.createTextNode(String.valueOf(((Oval) current).getCenterY())));
                    rad1.appendChild(doc.createTextNode(String.valueOf(((Oval) current).getRadiusX())));
                    rad2.appendChild(doc.createTextNode(String.valueOf(((Oval) current).getRadiusY())));

                    Element Bcolor = doc.createElement("Bcolor");
                    Bcolor.appendChild(doc.createTextNode(current.getBoarderColor().toString()));

                    Element fcolor = doc.createElement("fcolor");
                    fcolor.appendChild(doc.createTextNode(String.valueOf(((ClosedShape) current).getColor())));
                    shape.appendChild(fcolor);

                    shape.appendChild(x);
                    shape.appendChild(y);
                    shape.appendChild(rad1);
                    shape.appendChild(rad2);
                    shape.appendChild(Bcolor);
                    rootElement.appendChild(shape);

                } else if (current instanceof TriangleShape) {
                    Element shape = doc.createElement("shape");
                    Attr type = doc.createAttribute("type");
                    type.setValue("triangle");
                    shape.setAttributeNode(type);
                    rootElement.appendChild(shape);

                    double[] points = current.getPoints();

                    Element x1 = doc.createElement("x1");
                    Element y1 = doc.createElement("y1");
                    Element x2 = doc.createElement("x2");
                    Element y2 = doc.createElement("y2");
                    Element x3 = doc.createElement("x3");
                    Element y3 = doc.createElement("y3");
                    Element Bcolor = doc.createElement("Bcolor");

                    x1.appendChild(doc.createTextNode(String.valueOf(points[0])));
                    y1.appendChild(doc.createTextNode((String.valueOf(points[1]))));
                    x2.appendChild(doc.createTextNode((String.valueOf(points[2]))));
                    y2.appendChild(doc.createTextNode((String.valueOf(points[3]))));
                    x3.appendChild(doc.createTextNode((String.valueOf(points[4]))));
                    y3.appendChild(doc.createTextNode((String.valueOf(points[5]))));
                    Bcolor.appendChild(doc.createTextNode(current.getBoarderColor().toString()));

                    Element fcolor = doc.createElement("fcolor");
                    fcolor.appendChild(doc.createTextNode(String.valueOf(((ClosedShape) current).getColor())));
                    shape.appendChild(fcolor);

                    shape.appendChild(x1);
                    shape.appendChild(y1);
                    shape.appendChild(x2);
                    shape.appendChild(y2);
                    shape.appendChild(x3);
                    shape.appendChild(y3);
                    shape.appendChild(Bcolor);
                    rootElement.appendChild(shape);

                } else if (current.getType() == "square") {
                    Element shape = doc.createElement("shape");
                    Attr type = doc.createAttribute("type");
                    type.setValue("square");
                    shape.setAttributeNode(type);
                    rootElement.appendChild(shape);
                    Element x = doc.createElement("x");
                    Element y = doc.createElement("y");
                    Element l = doc.createElement("l");
                    Element Bcolor = doc.createElement("Bcolor");

                    Element fcolor = doc.createElement("fcolor");
                    fcolor.appendChild(doc.createTextNode(String.valueOf(((ClosedShape) current).getColor())));
                    shape.appendChild(fcolor);

                    x.appendChild(doc.createTextNode(String.valueOf(((RectangleShape) current).getX())));
                    y.appendChild(doc.createTextNode(String.valueOf(((RectangleShape) current).getY())));
                    l.appendChild(doc.createTextNode(String.valueOf(((RectangleShape) current).getwidth())));
                    Bcolor.appendChild(doc.createTextNode(current.getBoarderColor().toString()));

                    shape.appendChild(x);
                    shape.appendChild(y);
                    shape.appendChild(l);
                    shape.appendChild(Bcolor);
                    rootElement.appendChild(shape);
                }
                else if (current instanceof RectangleShape) {
                    Element shape = doc.createElement("shape");
                    Attr type = doc.createAttribute("type");
                    type.setValue("rect");
                    shape.setAttributeNode(type);
                    rootElement.appendChild(shape);

                    Element h = doc.createElement("h");
                    Element w = doc.createElement("w");
                    Element Bcolor = doc.createElement("Bcolor");

                    Element x = doc.createElement("x");
                    Element y = doc.createElement("y");

                    h.appendChild(doc.createTextNode(String.valueOf(((RectangleShape) current).getwidth())));
                    w.appendChild(doc.createTextNode(String.valueOf(((RectangleShape) current).gethight())));
                    x.appendChild(doc.createTextNode(String.valueOf(((RectangleShape) current).getX())));
                    y.appendChild(doc.createTextNode(String.valueOf(((RectangleShape) current).getY())));

                    Element fcolor = doc.createElement("fcolor");
                    fcolor.appendChild(doc.createTextNode(String.valueOf(((ClosedShape) current).getColor())));
                    shape.appendChild(fcolor);

                    Bcolor.appendChild(doc.createTextNode(current.getBoarderColor().toString()));
                    shape.appendChild(h);
                    shape.appendChild(w);
                    shape.appendChild(x);
                    shape.appendChild(y);
                    shape.appendChild(Bcolor);
                    rootElement.appendChild(shape);

                }


            }

            // --- write the content to xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(file.getAbsolutePath()));
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    public Group loadXML(File file, Group Root, LinkedList<TheShape> AllShapes, Stack<Action> undoStack, Class cfile, Class sfile) throws Exception {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file.getAbsolutePath());
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("shape");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                String key = nNode.getAttributes().getNamedItem("type").getNodeValue();
                Element eElement = (Element) nNode;
                switch (key) {
                case "line":
                    Point p1 = new Point();
                    Point p2 = new Point();
                    p1.setLocation(Double.parseDouble(eElement.getElementsByTagName("x1").item(0).getTextContent()),
                            Double.parseDouble(eElement.getElementsByTagName("y1").item(0).getTextContent()));
                    p2.setLocation(Double.parseDouble(eElement.getElementsByTagName("x2").item(0).getTextContent()),
                            Double.parseDouble(eElement.getElementsByTagName("y2").item(0).getTextContent()));
                    String c = eElement.getElementsByTagName("Bcolor").item(0).getTextContent();
                    LineSegment l = new LineSegment(p1, p2, Color.web(c), Root, AllShapes, new Stack<Action>());
                    l.setCorners();
                    l.createControlAnchorsFor(Root, undoStack);
                    break;
                case "oval":
                    Point cen = new Point();
                    Double rad1, rad2;
                    cen.setLocation(Double.parseDouble(eElement.getElementsByTagName("x").item(0).getTextContent()),
                            Double.parseDouble(eElement.getElementsByTagName("y").item(0).getTextContent()));
                    rad1 = Double.parseDouble(eElement.getElementsByTagName("rad1").item(0).getTextContent());
                    rad2 = Double.parseDouble(eElement.getElementsByTagName("rad2").item(0).getTextContent());
                    String color = eElement.getElementsByTagName("Bcolor").item(0).getTextContent();
                    String fcolor = eElement.getElementsByTagName("fcolor").item(0).getTextContent();
                    Oval o = new Oval(cen, rad1, rad2, Color.web(fcolor), Color.web(color), Root, AllShapes, new Stack<Action>());
                    o.setCorners();
                    o.createControlAnchorsFor(Root, undoStack);
                    break;
                case "triangle":
                    Point one = new Point();
                    Point two = new Point();
                    Point three = new Point();
                    one.setLocation(Double.parseDouble(eElement.getElementsByTagName("x1").item(0).getTextContent()),
                            Double.parseDouble(eElement.getElementsByTagName("y1").item(0).getTextContent()));
                    two.setLocation(Double.parseDouble(eElement.getElementsByTagName("x2").item(0).getTextContent()),
                            Double.parseDouble(eElement.getElementsByTagName("y2").item(0).getTextContent()));
                    three.setLocation(Double.parseDouble(eElement.getElementsByTagName("x3").item(0).getTextContent()),
                            Double.parseDouble(eElement.getElementsByTagName("y3").item(0).getTextContent()));

                    String Bcolor = eElement.getElementsByTagName("Bcolor").item(0).getTextContent();
                    String fcol = eElement.getElementsByTagName("fcolor").item(0).getTextContent();
                    TriangleShape t = new TriangleShape(one, two, three,Color.web(fcol), Color.web(Bcolor), Root, AllShapes,
                            new Stack<Action>());
                    t.setCorners();
                    t.createControlAnchorsFor(Root, undoStack);
                    break;
                case "rect":
                    Double h, w, x, y;
                    h = Double.parseDouble(eElement.getElementsByTagName("h").item(0).getTextContent());
                    w = Double.parseDouble(eElement.getElementsByTagName("w").item(0).getTextContent());

                    x = Double.parseDouble(eElement.getElementsByTagName("x").item(0).getTextContent());
                    y = Double.parseDouble(eElement.getElementsByTagName("y").item(0).getTextContent());
                    Point corner = new Point();
                    corner.setLocation(x, y);

                    String co = eElement.getElementsByTagName("Bcolor").item(0).getTextContent();
                    String fc = eElement.getElementsByTagName("fcolor").item(0).getTextContent();
                    RectangleShape rec = new RectangleShape(corner, h, w,Color.web(fc), Color.web(co), Root, AllShapes,
                            new Stack<Action>());
                    rec.setCorners();
                    rec.createControlAnchorsFor(Root, undoStack);
                    break;
                case "circle":
                        Point center = new Point();
                        center.setLocation(
                                Double.parseDouble(eElement.getElementsByTagName("x").item(0).getTextContent()),
                                Double.parseDouble(eElement.getElementsByTagName("y").item(0).getTextContent()));
                        Double rad = Double.parseDouble(eElement.getElementsByTagName("rad").item(0).getTextContent());
                        String col = eElement.getElementsByTagName("Bcolor").item(0).getTextContent();
                        String fC = eElement.getElementsByTagName("fcolor").item(0).getTextContent();
                        Constructor<?>[] con = cfile.getConstructors();
                        TheShape obj = (TheShape) con[0].newInstance(center, rad, rad,Color.web(fC), Color.web(col), Root, AllShapes,
                                undoStack);
                        obj.setCorners();
                        obj.createControlAnchorsFor(Root, undoStack);
                    break;
                case "square":
                    try {
                        Point corn = new Point();
                        corn.setLocation(
                                Double.parseDouble(eElement.getElementsByTagName("x").item(0).getTextContent()),
                                Double.parseDouble(eElement.getElementsByTagName("y").item(0).getTextContent()));
                        Double len = Double.parseDouble(eElement.getElementsByTagName("l").item(0).getTextContent());
                        String col1 = eElement.getElementsByTagName("Bcolor").item(0).getTextContent();
                        String fcc = eElement.getElementsByTagName("fcolor").item(0).getTextContent();
                        Constructor<?>[] con1 = sfile.getConstructors();
                        TheShape obj1 = (TheShape) con1[0].newInstance(corn, len, len,Color.web(fcc), Color.web(col1), Root, AllShapes,
                                undoStack);
                        obj1.setCorners();
                        obj1.createControlAnchorsFor(Root, undoStack);
                    } catch (NullPointerException e) {
                        throw e;
                    }
                    break;
                default:
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return Root;
    }
}