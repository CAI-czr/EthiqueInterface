package etu.upmc.ethique.model.component;

import etu.upmc.ethique.model.graph.CShape;

/*
    Le class Bridge.
*/
public class Bridge extends Position {
    private Position position;
    private boolean  push;
    private CShape cshape;
    public CShape    getCShape() { return cshape; }
    public void      setCShape(CShape cshape) { this.cshape = cshape; }
    public Bridge(String name, Position position) {
        super(name);
        this.position = position;
        if (this.position instanceof Carriage) {
            Carriage tmp = (Carriage) this.position;
            tmp.add(this);
        }
        push = false;
    }

    public void delPosition() {
        if (this.position instanceof Carriage) {
            Carriage tmp = (Carriage) this.position;
            tmp.delBridge();
        }
        position = null;
    }
    public void setPosition(Position p) {
        position = p;
        if (this.position instanceof Carriage) {
            Carriage tmp = (Carriage) this.position;
            tmp.add(this);
        }
    }
    public Position getPosition() { return position; }
    public String   getInformation() { return name + "," + position.getName(); }
    public boolean  getPush() { return push; }
    public void     setPush(boolean b) { push = b; }
    @Override
    public String toString() {
        return "Bridge: " + name + "-" + position;
    }
}
