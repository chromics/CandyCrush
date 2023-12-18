package view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
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
        gameObjective(event);
    }
    
    public void loadGame(ActionEvent event) throws Exception {
        Parent boardScene = FXMLLoader.load(getClass().getResource("/view/fxml/LoadScene.fxml"));
        this.stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        this.scene = new Scene(boardScene);
        stage.setScene(this.scene);
        stage.show();
    }

    public void settings(ActionEvent event) {
        System.out.println("settings");
    }

    public void gameObjective(ActionEvent event) throws Exception {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this.stage);
        dialog.setResizable(false);
        dialog.setX(545);
        dialog.setY(250);

        Image dialogIcon = new Image("data/constant/image/apple.png");
        dialog.getIcons().add(dialogIcon);

        Parent boardScene = FXMLLoader.load(getClass().getResource("/view/fxml/ObjectiveDialogBox.fxml"));
        Scene scene = new Scene(boardScene);
        dialog.setScene(scene);
        dialog.show();
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