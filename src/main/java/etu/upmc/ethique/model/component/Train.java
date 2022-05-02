package etu.upmc.ethique.model.component;


import etu.upmc.ethique.model.graph.CShape;

public class Train implements ComponentTrolley {
    public static final boolean STOPAFTERCOLLISION = true;
    public Carriage             originPosition;
    private Carriage            present;
    private CShape cshape;
    public CShape               getCShape() { return cshape; }
    public void                 setCShape(CShape cshape) { this.cshape = cshape; }
    public Train(Carriage originPosition) {
        this.originPosition = originPosition;
        present             = originPosition;
    }
    public Train() {}

    @Override
    public String toString() {
        if (present == null) {
            return "Train: null";
        }
        return "Train: " + present.getName();
    }

    public int run() {
        if (present == null) {
            return -1;
        }
        if (present.getSwitch() != null) {
            System.out.println("Le train s'est deposition de la track " + present.getTrack() +
                               " a la track " + present.getSwitch().next().getTrack());
            present = present.getSwitch().next();
        } else {
            present = present.next();
        }
        if (present != null) {
            System.out.println("Le train roule sur " + present.getName());
            if (present.getGroup() != null || present.getBridge() != null) {
                if (present.getGroup() != null) {
                    present.getGroup().roule();
                    return -1;
                } else if (present.getBridge() != null && present.getBridge().getGroup() != null &&
                           present.getBridge().getPush() == true) {
                    System.out.println(present.getBridge().getGroup());
                    present.getBridge().getGroup().roule();
                    return -1;
                }
            }
        } else {
            System.out.println("Pas de sortie du train\n");
        }
        return 0;
    }

    public Carriage getPresent() { return this.present; }
    public Carriage getOriginPosition() { return this.originPosition; }
    public void     setPresent(Carriage present) { this.present = present; }
    public void     setOriginPosition(Carriage originPosition) {
        this.originPosition = originPosition;
        this.present        = originPosition;
    }

    public String getName() { return "train"; }
    public String getInformation() { return "Train," + present.getInformation(); }
    public void   simulation() {
        while (present != null)
            run();
    }
}
