package etu.upmc.ethique.model.menu.context;

import etu.upmc.ethique.model.component.Carriage;
import etu.upmc.ethique.model.component.Generator;
import etu.upmc.ethique.model.component.Group;
import etu.upmc.ethique.model.menu.Updater;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class GroupMenu extends Menu {
    private Carriage carriage;
    private Generator generator;
    private Updater menu;
    private MenuItem addGroup;
    private MenuItem changeGroup;
    private MenuItem deleteGroup;

    public GroupMenu(Carriage carriage, Generator generator, Updater menu) {
        super("GroupMenu");
        this.carriage = carriage;
        this.generator = generator;
        this.menu = menu;
        addGroup = new MenuItem("addGroup");
        changeGroup = new MenuItem("changeGroup");
        deleteGroup = new MenuItem("deleteGroup");
        getItems().addAll(addGroup, changeGroup, deleteGroup);
        addGroup.setOnAction(actionEvent -> {
            if (carriage.getGroup() != null) {
                if (deleteGroupDialog()) {
                    createGroupDialog();
                }
            } else {
                createGroupDialog();
            }
        });
        deleteGroup.setOnAction(actionEvent -> {
            deleteGroupDialog();
        });
        changeGroup.setOnAction(actionEvent -> {
            Group oldGroup=carriage.getGroup();
            if(createGroupDialog()){
                generator.groups.remove(oldGroup);
            }
        });
    }

    public boolean deleteGroupDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + carriage.getGroup() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            generator.groups.remove(carriage.getGroup());
            carriage.deleteGroup();
            menu.update();
            return true;
        }
        return false;
    }

    public boolean createGroupDialog() {
        AtomicBoolean createGroup = new AtomicBoolean(false);
        List<String> choices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            choices.add(String.format("%d", i));
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("number", choices);
        dialog.setTitle("Add Group");
        dialog.setHeaderText("Add Group at" + carriage.toString());
        dialog.setContentText("Type number of people:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(letter -> {
            Group group = new Group(Integer.valueOf(letter), carriage);
            createGroup.set(true);
            carriage.add(group);
            generator.groups.add(group);
            menu.update();
        });
        return createGroup.get();
    }
}
