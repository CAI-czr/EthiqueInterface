package etu.upmc.ethique.model.graph;

import etu.upmc.ethique.model.component.ComponentTrolley;

import java.awt.Graphics;

public class CShape implements Comparable {
    private int x;
    private int y;

    public static final int size = 50;
    private ComponentTrolley object;

    public enum Type {Track, Bridge, Switch, Group, Train, Circle}

    private Type type;

    public int getType() {
        return switch (object.getClass().getName()) {
            case "domain.Carriage" -> 1;
            case "domain.Bridge" -> 2;
            case "domain.Switch" -> 3;
            case "domain.Group" -> 4;
            case "domain.Train" -> 5;
            default -> 0;
        };
    }

    public CShape(int x, int y, ComponentTrolley object) {
        this.x = x;
        this.y = y;
        this.object = object;
        this.type = Type.values()[getType() - 1];
    }

    public ComponentTrolley getComponentTrolley() {
        return this.object;
    }

    public void setComponentTrolley(ComponentTrolley object) {
        this.object = object;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void draw(Graphics g) {
        switch (type) {
            case Track -> drawTrack(g);
            case Train -> drawTrain(g);
            case Switch -> drawSwitch(g);
            case Circle -> drawCircle(g);
            case Group -> drawGroup(g);
        }
        ;
    }

    public void drawTrack(Graphics g) {
    }

    public void drawTrain(Graphics g) {
    }

    public void drawSwitch(Graphics g) {
    }

    public void drawCircle(Graphics g) {
    }

    public void drawGroup(Graphics g) {
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }
}
