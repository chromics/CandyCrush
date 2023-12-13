package view;

import javafx.event.ActionEvent;

public class Controller {

    public void newGame(ActionEvent event) {
        System.out.println("newGame");
    }
    public void loadGame(ActionEvent event) {
        System.out.println("loadGame");
    }
    public void settings(ActionEvent event) {
        System.out.println("settings");
    }

}

// @FXML
// private Label label;

// public void initialize() {
//     String javaVersion = System.getProperty("java.version");
//     String javafxVersion = System.getProperty("javafx.version");
//     label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
// }

// import javafx.fxml.FXML;
// import javafx.scene.control.Label;