package view.viewController;

import controller.GameController;
import data.constant.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class NewGameInputDialogController implements Initializable {
    private static Stage stage;
    private ToggleGroup toggleGroup = new ToggleGroup();
    @FXML
    private ToggleButton normalModeToggle;
    @FXML
    private ToggleButton specialModeToggle;
    @FXML
    private CheckBox autoModeCheckbox;
    public void initialize(URL location, ResourceBundle resourceBundle) {
        normalModeToggle.setToggleGroup(toggleGroup);
        specialModeToggle.setToggleGroup(toggleGroup);
    }

    public static void setStage(Stage dialogStage) {
        stage = dialogStage;
    }

    public void normalGameMode(ActionEvent event) throws Exception {
        StartSceneController.newGame(event, getClass().getResource("/view/fxml/BoardScene.fxml"), getClass().getResource("/view/fxml/ObjectiveDialogBox.fxml"));
    }
    public void specialGameMode(ActionEvent event) {

    }

    public void startNewGame(ActionEvent event) throws Exception{
        if (normalModeToggle.isSelected()) {
            normalGameMode(event);
            stage.close();
        } else if (specialModeToggle.isSelected()) {
            specialGameMode(event);
            stage.close();
        }
        else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error!");
            errorAlert.setContentText("Please select a game mode ;)");
            errorAlert.show();
//            errorAlert.setX(this.stage.getX() + 625);
//            errorAlert.setY(this.stage.getY() - 55);
        }
    }
}