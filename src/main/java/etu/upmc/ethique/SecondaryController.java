package etu.upmc.ethique;

import java.io.IOException;

import etu.upmc.ethique.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("mainScreen");
    }
}