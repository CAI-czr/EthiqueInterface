package etu.upmc.ethique.model.graph;

import java.awt.*;

public class GraphConfig {
    public final int xDistance;
    public final int yDistance;
    public final int yMaxValue;
    public final int yMinValue;
    public final int xMaxValue;
    public final int xMinValue;
    public final int xSize;
    public final int ySize;

    public GraphConfig() {
        xDistance = 80;
        yDistance = 80;
        yMaxValue = 700;
        yMinValue = 100;
        xMaxValue = 700;
        xMinValue = 100;
        xSize=20;
        ySize=20;
    }
}
