package etu.upmc.ethique.model.component;
import etu.upmc.ethique.model.graph.CShape;

import java.util.ArrayList;

/*Le class Track, qui est une liste de troncon*/
public class Track extends ArrayList<Carriage> implements ComponentTrolley {
	private static final long serialVersionUID = 1L;
	private String name;
    private CShape cshape;
    public CShape  getCShape() { return cshape; }
    public void    setCShape(CShape cshape) { this.cshape = cshape; }
    public Track(String name, int length) {
        this.name = name;
        for (int i = 0; i < length; i++) {
            addNewCarriage();
        }
    }
    
    @Override
    public String toString() {
        return "Track: " + name + "(" + Generator.generateNumber(size()) + ")";
    }
    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public Track(String name) { this.name = name; }
    public void addNewCarriage() {
        add(new Carriage(name + "(" + size() + ")", name, size() - 1));
        if (size() >= 2) {
            get(size() - 2).suivant = get(size() - 1);
        }
        if (size() != 1) {
            get(size() - 1).before = get(size() - 2);
        }
    }
    public String getInformation() { return name + "(" + Generator.generateNumber(size()) + ")"; }

    public int nbSwitch() {
        int nb = 0;
        for (int i = 0; i < size(); i++) {
            if (get(i).getSwitch() != null) {
                nb++;
            }
        }
        return nb;
    }
}
