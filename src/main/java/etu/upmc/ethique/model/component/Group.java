package etu.upmc.ethique.model.component;



/*
Le class Group.Les informations pour les groupes de personne.
*/
public class Group implements ComponentTrolley {
    private int      nb;
    private Position position;
    private boolean  alive;
    private String   name;
    private CShape   cshape;
    public CShape    getCShape() { return cshape; }
    public void      setCShape(CShape cshape) { this.cshape = cshape; }
    public Group(int nb, Position position, String name) {
        this.name     = name;
        this.position = position;
        this.nb       = nb;
        this.alive    = true;
        position.setGroup(this);
    }
    public Group(int nb, Position position) { this(nb, position, position.getName()); }
    public Group(String name) { this.name = name; }
    public String toString() {
        String out = "null";
        if (position != null) {
            out = position.getName();
        }
        return "group: " + name + "-" + nb + "-" + out;
    }

    public int      getNb() { return this.nb; }
    public Position getPosition() { return this.position; }
    public boolean  isAlive() { return this.alive; }
    public void     setAlive(boolean alive) { this.alive = alive; }
    public void     setNb(int nb) { this.nb = nb; }
    public void     setPosition(Position position) {
        this.position = position;
        position.setGroup(this);
    }
    public String getName() { return name; }
    public String getInformation() { return name + "," + nb; }

    /*la mort du groupe lorsque le train détecte la présence d'un groupe.*/
    public void roule() {
        if (position instanceof Bridge) {
            System.out.println(nb + " hommes sont tombés du pont et écrasés à mort par le train.");
        }
        System.out.println(
            nb + " hommes qui se trouvaient sur les tracks ont été écrasés à mort par le train.");
        if (alive) alive = false;
    }
    public void delPosition() {
        if (position != null) {
            position.setGroup(null);
            ;
            position = null;
        }
    }
}
