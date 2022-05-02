package etu.upmc.ethique.model.menu.context;

import etu.upmc.ethique.model.component.Carriage;
import etu.upmc.ethique.model.component.Generator;
import etu.upmc.ethique.model.component.Track;
import etu.upmc.ethique.model.graph.Vertex;
import etu.upmc.ethique.model.menu.Updater;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

public class CarriageMenu extends ContextMenu {
    private Carriage carriage;
    private Generator generator;

    private SwitchMenu switchMenu;
    private BridgeMenu bridgeMenu;
    private GroupMenu groupMenu;
    private MenuItem deleteCarriage;

    public CarriageMenu(Carriage carriage, Generator generator, Updater menu) {
        this.generator = generator;
        this.carriage = carriage;
        switchMenu = new SwitchMenu(carriage.getSwitch());
        bridgeMenu = new BridgeMenu(carriage.getBridge());
        groupMenu = new GroupMenu(carriage.getGroup());
        deleteCarriage = new MenuItem("delete Carriage");
        getItems().addAll(groupMenu, switchMenu, bridgeMenu, deleteCarriage);
        deleteCarriage.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + carriage + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                generator.removeCarriage(carriage);
                menu.update();
            }
        });
    }
}
