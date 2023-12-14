package view.controller;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class BoardSceneController {
    @FXML
    private Stage stage;
    private Scene scene;

    public void saveExit(ActionEvent event) throws Exception {

        // Alert
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Save & Exit");
        alert.setHeaderText("You're about to exit the current game!");
        alert.setContentText("Do you want to save?");

        ButtonType YES = new ButtonType("Yes");
        ButtonType NO = new ButtonType("No");
        ButtonType CANCEL = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(YES, NO, CANCEL);

        // Save & Exit
        Optional <ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == YES) {
            // Save
            // *insert save sequence*
            // Exit
            backToStartScene(event);
        }
        // Exit
        else if (result.isPresent() && result.get() == NO) {
            backToStartScene(event);
        }

    }
    public void backToStartScene(ActionEvent event) throws Exception{
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/StartScene.fxml"));
        this.stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        this.scene = new Scene(startScene);
        stage.setScene(this.scene);
        stage.show();
    }

    public void swap(ActionEvent event) {
        System.out.println("swap");
    }

    public void replant(ActionEvent event) {
        System.out.println("replant");
    }

    public void shuffle(ActionEvent event) {
        System.out.println("shuffle");
    }

}
