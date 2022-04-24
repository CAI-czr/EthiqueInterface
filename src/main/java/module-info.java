module etu.upmc.project.ethique {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.desktop;

    opens etu.upmc.ethique to javafx.fxml;
    exports etu.upmc.ethique;
}
