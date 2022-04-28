module etu.upmc.exmple {
	requires javafx.controls;
	requires javafx.base;
	requires javafx.graphics;
	requires javafx.fxml;
	requires java.desktop;

	opens etu.upmc.ethique to javafx.fxml;

	exports etu.upmc.ethique;
    exports etu.upmc.ethique.model.menu.context;
    opens etu.upmc.ethique.model.menu.context to javafx.fxml;
//    exports etu.upmc.ethique.model.menus;
//    opens etu.upmc.ethique.model.menus to javafx.fxml;
}
