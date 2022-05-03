package etu.upmc.ethique.model.menu.context;

import etu.upmc.ethique.model.component.Carriage;
import etu.upmc.ethique.model.component.Generator;
import etu.upmc.ethique.model.component.Track;
import etu.upmc.ethique.model.graph.Vertex;
import etu.upmc.ethique.model.menu.Updater;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;

public class CarriageMenu extends ContextMenu {
    private Carriage carriage;
    private Generator generator;

    private SwitchMenu switchMenu;
    private BridgeMenu bridgeMenu;
    private GroupMenu groupMenu;
    private MenuItem deleteCarriage;
    private CheckMenuItem originalPosition;

    public CarriageMenu(Carriage carriage, Generator generator, Updater menu) {
        this.generator = generator;
        this.carriage = carriage;
        switchMenu = new SwitchMenu(carriage, generator, menu);
        bridgeMenu = new BridgeMenu(carriage, generator, menu);
        groupMenu = new GroupMenu(carriage, generator, menu);
        deleteCarriage = new MenuItem("delete Carriage");
        originalPosition = new CheckMenuItem("Original Position of Train");
        getItems().addAll(groupMenu, switchMenu, bridgeMenu, deleteCarriage, originalPosition);
        deleteCarriage.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + carriage + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                generator.removeCarriage(carriage);
                menu.update();
            }
        });
        originalPosition.setSelected(generator.train.getOriginPosition().equals(carriage));
        originalPosition.setOnAction(actionEvent -> {
            generator.train.setOriginPosition(carriage);
        });
    }
}
