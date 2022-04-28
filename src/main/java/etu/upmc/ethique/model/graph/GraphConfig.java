package etu.upmc.ethique.model.graph;

import java.awt.*;

public class GraphConfig {
    public final int xDistance;
    public final int yDistance;
    public final int yMaxValue;
    public final int yMinValue;
    public final int xMaxValue;
    public final int xMinValue;

    public GraphConfig() {
        xDistance = 50;
        yDistance = 50;
        yMaxValue = 400;
        yMinValue = 0;
        xMaxValue = 400;
        xMinValue = 0;
    }
}
