package etu.upmc.ethique.model.menu.context;

import etu.upmc.ethique.model.component.Carriage;
import etu.upmc.ethique.model.component.Generator;
import etu.upmc.ethique.model.component.Switch;
import etu.upmc.ethique.model.menu.Updater;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

import java.util.Calendar;

public class SwitchMenu extends Menu {
    private Carriage carriage;
    private Generator generator;
    private MenuItem addSwitch;
    private MenuItem changeSwitch;
    private MenuItem deleteSwitch;

    public SwitchMenu(Carriage carriage, Generator generator, Updater menu) {
        super("SwitchMenu");
        this.carriage = carriage;
        this.generator = generator;
        addSwitch = new MenuItem("addSwitch");
        changeSwitch = new MenuItem("changeSwitch");
        deleteSwitch = new MenuItem("deleteSwitch");
        getItems().addAll(addSwitch, changeSwitch, deleteSwitch);

    }
}
