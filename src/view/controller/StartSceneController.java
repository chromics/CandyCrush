package view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartSceneController {
    @FXML
    private Stage stage;
    private Scene scene;

    public void newGame(ActionEvent event) throws Exception {
        Parent boardScene = FXMLLoader.load(getClass().getResource("/view/fxml/BoardScene.fxml"));
        this.stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        this.scene = new Scene(boardScene);
        stage.setScene(this.scene);
        stage.show();
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