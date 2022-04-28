package etu.upmc.ethique.model.menus;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import etu.upmc.ethique.App;
import etu.upmc.ethique.model.component.Generator;
import etu.upmc.ethique.model.graph.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class MainScreen implements Initializable {

    private Group group;
    @FXML
    private Group drawPane = new Group();

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Generator generator = new Generator("./data/testsw.lp");
        Graph graph = new Graph(generator);
        Drawing drawing = new Drawing();
        GraphConfig graphConfig = new GraphConfig();
        drawing.positionVertex(graph.vertices, graphConfig);
        drawing.positionEdges(graph.edges);

        for (Vertex vertex : graph.vertices) {
            Circle circle = new Circle();
            circle.setCenterX(drawing.getVertexPoint2D(vertex).getX());
            circle.setCenterY(drawing.getVertexPoint2D(vertex).getY());
            circle.setRadius(3);
            drawPane.getChildren().add(circle);
        }

        for (Edge edge : graph.edges) {
            Line line = new Line();
            line.setStartX(drawing.getEdgePoint2DIn(edge).getX());
            line.setStartY(drawing.getEdgePoint2DIn(edge).getY());
            line.setEndX(drawing.getEdgePoint2DOut(edge).getX());
            line.setEndY(drawing.getEdgePoint2DOut(edge).getY());
            drawPane.getChildren().add(line);
        }
    }
}
