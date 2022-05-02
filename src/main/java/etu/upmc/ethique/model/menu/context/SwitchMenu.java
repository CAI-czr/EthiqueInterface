package etu.upmc.ethique.model.menu.context;

import etu.upmc.ethique.model.component.Carriage;
import etu.upmc.ethique.model.component.Switch;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

public class SwitchMenu extends Menu {
    private Switch switche;
    private MenuItem addSwitch;
    private MenuItem changeSwitch;
    private MenuItem deleteSwitch;

    public SwitchMenu(Switch switche) {
        super("SwitchMenu");
        addSwitch = new MenuItem("addSwitch");
        changeSwitch = new MenuItem("changeSwitch");
        deleteSwitch = new MenuItem("deleteSwitch");
        getItems().addAll(addSwitch, changeSwitch, deleteSwitch);

    }
}
