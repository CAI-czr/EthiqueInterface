package etu.upmc.ethique;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import etu.upmc.ethique.model.component.Carriage;
import etu.upmc.ethique.model.component.Generator;
import etu.upmc.ethique.model.graph.*;
import etu.upmc.ethique.model.menu.context.CarriageMenu;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class MainScreen implements Initializable {

    private Graph graph;
    private Drawing drawing;
    private GraphConfig graphConfig;
    private Generator generator;

    private Circle train = null;

    private Path path;

    @FXML
    private Pane drawPane = new Pane();


    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    public void runTrain() {
        int i = 0;
        generator.reset();
        while ((i++ < generator.getTime()) && (generator.train.getPresent() != null)) {
            runOneStepTrain();
        }
    }

    @FXML
    public void resetTrain() {
        generator.reset();
        paint();
    }

    @FXML
    public void runOneStepTrain() {
        generator.train.run();
        paintTrain();
    }

    public void paint() {
        drawing.positionVertex(graph.vertices, graphConfig);
        drawing.positionEdges(graph.edges);

        for (Vertex vertex : graph.vertices) {
            Circle circle = new Circle();
            circle.setCenterX(drawing.getVertexPoint2D(vertex).getX());
            circle.setCenterY(drawing.getVertexPoint2D(vertex).getY());
            circle.setRadius(5);
            drawPane.getChildren().add(circle);
            CarriageMenu carriageMenu = new CarriageMenu((Carriage) vertex.getContent());
            circle.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent event) {
                    carriageMenu.show(circle, event.getScreenX(), event.getScreenY());
                }
            });
        }
        for (Edge edge : graph.edges) {
            Line line = new Line();
            line.setStartX(drawing.getEdgePoint2DIn(edge).getX());
            line.setStartY(drawing.getEdgePoint2DIn(edge).getY());
            line.setEndX(drawing.getEdgePoint2DOut(edge).getX());
            line.setEndY(drawing.getEdgePoint2DOut(edge).getY());
            drawPane.getChildren().add(line);
        }
        paintTrain();
    }

    public void paintTrain() {
        Vertex vertex = graph.find_vertex(generator.train.getPresent());
        if (vertex == null) {
            return;
        }
        double x=drawing.getVertexPoint2D(vertex).getX();
        double y=drawing.getVertexPoint2D(vertex).getY();
        if (train == null) {
            train = new Circle();
            train.setFill(Color.WHITE);
            drawPane.getChildren().add(train);
            train.setRadius(5);
            train.setCenterX(x);
            train.setCenterY(y);
        } else {
            drawPane.getChildren().remove(train);
            drawPane.getChildren().add(train);
            path.getElements().add(new LineTo(x,y));
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(500));
            pathTransition.setPath(path);
            pathTransition.setNode(train);
            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            pathTransition.setCycleCount(1);
            pathTransition.setAutoReverse(false);
            pathTransition.play();
        }
        path = new Path();
        path.getElements().add(new MoveTo(x,y));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generator = new Generator("./data/testsw.lp");
        graph = new Graph(generator);
        drawing = new Drawing();
        graphConfig = new GraphConfig();
        paint();
    }
}
