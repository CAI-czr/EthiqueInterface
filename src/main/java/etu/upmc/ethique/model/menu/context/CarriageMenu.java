package etu.upmc.ethique.model.menus.context;

import etu.upmc.ethique.model.component.Carriage;
import etu.upmc.ethique.model.graph.Vertex;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class CarriageMenu extends ContextMenu{
    private Vertex vertex;
    private MenuItem switchMenus;
    private MenuItem bridgeMenus;
    private MenuItem groupMenus;

    public CarriageMenu(Carriage carriage) {
        switchMenus = new MenuItem("switchMenus");
        bridgeMenus = new MenuItem("bridgeMenus");
        groupMenus = new MenuItem("groupMenus");
        getItems().addAll(groupMenus,switchMenus,bridgeMenus);
    }
}
