package etu.upmc.ethique.model.component;

public abstract class Position implements ComponentTrolley {
    protected String name;
    protected Group  group;
    public Position(String name) {
        this.group = null;
        this.name  = name;
    }
    public String getName() { return name; }
    public void   setName(String name) { this.name = name; }
    public Group  getGroup() { return group; }
    public void   setGroup(Group group) { this.group = group; }
}
