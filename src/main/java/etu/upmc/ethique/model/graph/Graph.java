package etu.upmc.ethique.model.graph;

import etu.upmc.ethique.model.component.Carriage;
import etu.upmc.ethique.model.component.Generator;
import etu.upmc.ethique.model.component.Track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    public List<Vertex> vertices;
    public List<Edge> edges;
    public Map<Vertex, List<Edge>> adjacentLists;
    public Map<Vertex, List<Edge>> outgoingEdges;
    public Map<Vertex, List<Edge>> incomingEdges;
    public Map<Object, Vertex> vertexByContentMap;

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Vertex find_vertex(Object object) {
        for (Vertex vertex : vertices) {
            if (vertex.getContent().equals(object)) {
                return vertex;
            }
        }
        return null;
    }

    public Graph() {
        vertices = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        adjacentLists = new HashMap<Vertex, List<Edge>>();
        vertexByContentMap = new HashMap<Object, Vertex>();
        outgoingEdges = new HashMap<Vertex, List<Edge>>();
        incomingEdges = new HashMap<Vertex, List<Edge>>();
    }

    public Graph(Generator generator) {
        this();
        for (Track track : generator.tracks) {
            for (Carriage carriage : track) {
                vertices.add(new Vertex(carriage));
            }
        }
        for (Vertex v : vertices) {
            if (v.getContent() instanceof Carriage) {
                Carriage carriage = (Carriage) v.getContent();
                if (carriage.suivant != null) {
                    Vertex vertex = find_vertex(carriage.suivant);
                    edges.add(new Edge(v, vertex));
                }
                if (carriage.getSwitch() != null) {
                    Carriage other = carriage.getSwitch().getOtherCarriage(carriage);
                    Vertex vertex = find_vertex(other.next());
                    edges.add(new Edge(v, vertex));
                }
            }

        }
    }
}
