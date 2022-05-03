package etu.upmc.ethique.model.component;


import etu.upmc.ethique.model.graph.CShape;

public class Carriage extends Position {
    private String track;
    public Carriage suivant;
    public Carriage before;
    private Switch sw;
    private Bridge bridge;
    private int index;
    private CShape cshape;

    public CShape getCShape() {
        return cshape;
    }

    public void setCShape(CShape cshape) {
        this.cshape = cshape;
    }

    public Carriage(String name, String track, int index) {
        super(name);
        this.track = track;
        this.index = index;
        suivant = null;
        before = null;
        sw = null;
        bridge = null;
    }

    public String getName() {
        return name;
    }

    public String getTrack() {
        return this.track;
    }

    public Carriage getSuivant() {
        return this.suivant;
    }

    public Switch getSwitch() {
        return this.sw;
    }

    public Bridge getBridge() {
        return bridge;
    }

    public int getIndex() {
        return index;
    }

    public void addBefore(Carriage before) {
        this.before = before;
    }

    public void add(Carriage suivant) {
        this.suivant = suivant;
    }

    public void add(Group group) {
        this.group = group;
    }

    public void add(Bridge bridge) {
        this.bridge = bridge;
    }

    public void add(Switch sw) {
        this.sw = sw;
    }

    public void deleteBridge() {
        bridge = null;
    }

    public void deleteGroup() {
        group = null;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getInformation() {
        return name;
    }

    public Carriage next() {
        if (sw != null) {
            return sw.next();
        }
        return suivant;
    }
}
