package etu.upmc.ethique;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import etu.upmc.ethique.model.component.Carriage;
import etu.upmc.ethique.model.component.Generator;
import etu.upmc.ethique.model.component.Group;
import etu.upmc.ethique.model.graph.*;
import etu.upmc.ethique.model.menu.Updater;
import etu.upmc.ethique.model.menu.context.CarriageMenu;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainScreen implements Initializable, Updater {

    private Graph graph;
    private Drawing drawing;
    private GraphConfig graphConfig;
    private Generator generator;

    private Circle train = null;
    @FXML
    private MenuItem openFile;
    @FXML
    private MenuItem saveFile;

    private Path path;

    @FXML
    private Pane drawPane = new Pane();

    @FXML
    public void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showSaveDialog(saveFile.getParentPopup().getScene().getWindow());
        if (file != null) {
            generator.saveFile(file);
        }
    }

    @FXML
    public void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(openFile.getParentPopup().getScene().getWindow());
        if (file != null) {
            generator = new Generator(file);
            update();
        }
    }


    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    public void runTrain() {
        int i = 0;
        Point2D pos = null;
        resetTrain();
        paintTrain();
        drawPane.getChildren().remove(train);
        drawPane.getChildren().add(train);
        int colision = 0;
        while ((i++ < generator.getTime()) && (generator.train.getPresent() != null)) {
            pos = drawing.getVertexPoint2D(graph.find_vertex(generator.train.getPresent()));
            path.getElements().add(new LineTo(pos.getX(), pos.getY()));
            if (colision == -1) {
                break;
            }
            colision = generator.train.run();
        }
        PathTransition end = new PathTransition();
        end.setOnFinished(actionEvent -> {
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(5000));
            pathTransition.setPath(path);
            pathTransition.setNode(train);
            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            pathTransition.setCycleCount(1);
            pathTransition.setAutoReverse(false);
            pathTransition.play();
        });
        end.play();
        if (pos != null) {
            path = new Path();
            path.getElements().add(new MoveTo(pos.getX(), pos.getY()));
        }
    }

    @FXML
    public void resetTrain() {
        generator.reset();
        update();
    }

    @FXML
    public void runOneStepTrain() {
        generator.train.run();
        paintTrain();
        paint();
    }

    public void paintGroup(Point2D pos, Group group) {
        int nb = group.getNb();
        Canvas canvas = new Canvas();
        canvas.setLayoutX(pos.getX() - graphConfig.xSize / 2);
        canvas.setLayoutY(pos.getY() - graphConfig.ySize / 2);
        int nbLine = (int) Math.sqrt(nb) + 1;
        double hx = graphConfig.xSize * 1.3 / nbLine;
        double hy = graphConfig.ySize * 1.3 / nbLine;
        canvas.setHeight(hx * nbLine);
        canvas.setWidth(hy * nbLine);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (group.isAlive()) {
            gc.setFill(Color.LIGHTGREEN);
        } else {
            gc.setFill(Color.RED);
        }
        for (int i = 0; i < nbLine; i++) {
            for (int j = 0; j < nbLine; j++) {
                gc.fillOval(hx * i, hy * j, hx, hy);
                --nb;
                if (nb <= 0) break;
            }
            if (nb <= 0) break;
        }
        drawPane.getChildren().add(canvas);
    }

    public void paint() {
        for (Vertex vertex : graph.vertices) {
            if (vertex.getContent() instanceof Carriage) {
                Carriage carriage = (Carriage) vertex.getContent();
                Point2D pos = drawing.getVertexPoint2D(vertex);
                Circle circle = new Circle();
                circle.setCenterX(pos.getX());
                circle.setCenterY(pos.getY());
                circle.setRadius(graphConfig.xSize / 2);
                drawPane.getChildren().add(circle);
                if (carriage.getGroup() != null) {
                    paintGroup(pos, carriage.getGroup());
                }
                CarriageMenu carriageMenu = new CarriageMenu(carriage, generator, this);
                circle.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                    @Override
                    public void handle(ContextMenuEvent event) {
                        carriageMenu.show(circle, event.getScreenX(), event.getScreenY());
                    }
                });

                final Tooltip tooltip = new Tooltip();
                tooltip.setShowDelay(new Duration(100));
                tooltip.setText(carriage.toString());
                Tooltip.install(circle, tooltip);
            }
        }

        for (Edge edge : graph.edges) {
            Line line = new Line();
            line.setStartX(drawing.getEdgePoint2DIn(edge).getX());
            line.setStartY(drawing.getEdgePoint2DIn(edge).getY());
            line.setEndX(drawing.getEdgePoint2DOut(edge).getX());
            line.setEndY(drawing.getEdgePoint2DOut(edge).getY());
            drawPane.getChildren().add(line);
        }
        if (train != null) {
            drawPane.getChildren().remove(train);
            drawPane.getChildren().add(train);
        }
    }

    public void paintRightArrow(int x1, int x2, int y1, int y2) {
        Line line = new Line();
        line.setStartX(x1);
        line.setStartY(y1);
        line.setEndX(x2);
        line.setEndY(y2);
        drawPane.getChildren().add(line);
    }

    public void paintTrain() {
        Vertex vertex = graph.find_vertex(generator.train.getPresent());
        if (vertex == null) {
            return;
        }
        double x = drawing.getVertexPoint2D(vertex).getX();
        double y = drawing.getVertexPoint2D(vertex).getY();
        if (train == null) {
            train = new Circle();
            train.setFill(Color.WHITE);
            drawPane.getChildren().add(train);
            train.setRadius(graphConfig.xSize / 2);
            train.setCenterX(x);
            train.setCenterY(y);
        } else {
            drawPane.getChildren().remove(train);
            drawPane.getChildren().add(train);
            path.getElements().add(new LineTo(x, y));
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
        path.getElements().add(new MoveTo(x, y));
    }

    public void update() {
        if (generator != null) {
            drawPane.getChildren().clear();
            graph = new Graph(generator);
            graphConfig = new GraphConfig();
            drawing = new Drawing(graph, graphConfig);
            BackgroundFill bf = new BackgroundFill(Color.WHITE,
                    CornerRadii.EMPTY, Insets.EMPTY);
            Background background = new Background(bf);
            drawPane.setBackground(background);
            paint();
            paintTrain();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generator = new Generator("./data/testsw.lp");
        update();
    }
}
