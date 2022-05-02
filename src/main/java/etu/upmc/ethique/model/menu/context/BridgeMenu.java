package etu.upmc.ethique.model.menu.context;

import etu.upmc.ethique.model.component.Carriage;
import etu.upmc.ethique.model.component.Bridge;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

public class BridgeMenu extends Menu {
    private Bridge switche;
    private MenuItem addBridge;
    private MenuItem changeBridge;
    private MenuItem deleteBridge;

    public BridgeMenu(Bridge bridge) {
        super("BridgeMenu");
        addBridge = new MenuItem("addBridge");
        changeBridge = new MenuItem("changeBridge");
        deleteBridge = new MenuItem("deleteBridge");
        getItems().addAll(addBridge, changeBridge, deleteBridge);

    }
}
