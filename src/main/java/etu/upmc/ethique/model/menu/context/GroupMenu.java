package etu.upmc.ethique.model.menu.context;

import etu.upmc.ethique.model.component.Carriage;
import etu.upmc.ethique.model.component.Group;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

public class GroupMenu extends Menu {
    private Group switche;
    private MenuItem addGroup;
    private MenuItem changeGroup;
    private MenuItem deleteGroup;

    public GroupMenu(Group group) {
        super("GroupMenu");
        addGroup = new MenuItem("addGroup");
        changeGroup = new MenuItem("changeGroup");
        deleteGroup = new MenuItem("deleteGroup");
        getItems().addAll(addGroup, changeGroup, deleteGroup);
    }
}
