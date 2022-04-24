module etu.upmc.exmple {
	requires javafx.controls;
	requires javafx.base;
	requires javafx.graphics;
	requires javafx.fxml;

	opens etu.upmc.ethique to javafx.fxml;

	exports etu.upmc.ethique;
}
