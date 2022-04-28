package etu.upmc.ethique.model.graph;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Drawing {
    private Map<Vertex, Point2D> vertexMappings;
    private Map<Edge, List<Point2D>> edgeMappings;

    public Drawing() {
        vertexMappings = new HashMap<Vertex, Point2D>();
        edgeMappings = new HashMap<Edge, List<Point2D>>();
    }

    public void positionEdges(List<Edge> edges) {
        for (Edge e : edges) {
            List<Point2D> list = new ArrayList<Point2D>();
            list.add(vertexMappings.get(e.getOrigin()));
            list.add(vertexMappings.get(e.getDestination()));
            edgeMappings.put(e, list);
        }
    }


    public void positionVertex(List<Vertex> vertexs, GraphConfig graphConfig) {
        int xPosition = graphConfig.xMinValue;
        int yPosition = graphConfig.yMinValue;
        for (Vertex v : vertexs) {
            vertexMappings.put(v, new Point2D(xPosition, yPosition));
            xPosition = xPosition + graphConfig.xDistance;
            if (xPosition >= graphConfig.xMaxValue) {
                yPosition += graphConfig.yDistance;
                xPosition = graphConfig.xMinValue;
            }
        }
    }

    public Point2D getVertexPoint2D(Vertex vertex){
        return vertexMappings.get(vertex);
    }

    public Point2D getEdgePoint2DOut(Edge edge){
        return edgeMappings.get(edge).get(1);
    }

    public Point2D getEdgePoint2DIn(Edge edge){
        return edgeMappings.get(edge).get(0);
    }
}
