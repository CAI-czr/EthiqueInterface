package etu.upmc.ethique.model.graph;

public class Edge {

    private Vertex origin;
    private Vertex destination;
    private int weight = 0;

    public Edge() {
    }

    public Edge(Vertex origin, Vertex destination, int weight) {
        super();
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
    }

    public Edge(Vertex origin, Vertex destination) {
        super();
        this.origin = origin;
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setOrigin(Vertex origin) {
        this.origin = origin;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    public Vertex getOrigin() {
        return origin;
    }

    public Vertex getDestination() {
        return destination;
    }
}
