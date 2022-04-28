package etu.upmc.ethique.model.component;


import etu.upmc.ethique.model.graph.CShape;

public interface ComponentTrolley {
    public String getName();
    /**Get the standard written form of the object for easy reading*/
    public String getInformation();
    public CShape getCShape();
}
