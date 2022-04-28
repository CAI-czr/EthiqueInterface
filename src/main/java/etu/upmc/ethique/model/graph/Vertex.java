package etu.upmc.ethique.model.graph;


import java.awt.Dimension;

/**
 * A class which implements the Vertex interface
 */
public class Vertex {
    private Dimension size;
    private Object    content;

    public Vertex() {}

    public Vertex(Dimension size, Object content) {
        this.size    = size;
        this.content = content;
    }

    public Vertex(Dimension size) {
        this.size = size;
        content   = "";
    }

    public Vertex(Object content) { this.content = content; }

    public Dimension getSize() { return size; }
    public Object    getContent() { return content; }
    public void      setSize(Dimension size) { this.size = size; }
    public void      setContent(Object content) { this.content = content; }
}
