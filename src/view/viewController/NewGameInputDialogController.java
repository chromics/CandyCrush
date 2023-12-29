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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import view.Main;
import data.constant.GameMode;

public class NewGameInputDialogController {
    private static Stage stage;
    private static Scene scene;
    @FXML
    private Button normalModeToggle;
    @FXML
    private Button specialModeToggle;

    public static void setStage(Stage dialogStage) {
        stage = dialogStage;
    }

    public void normalGameMode() {
        StartSceneController.setCurrentGameMode(GameMode.Normal_Game_Mode);
    }
    public void specialGameMode() {
        StartSceneController.setCurrentGameMode(GameMode.Special_Game_Mode);
    }

    public void setGameMode(ActionEvent event) throws Exception{
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        Button sourceButton = (Button)(event.getSource());
        String buttonID = sourceButton.getId();
        if (buttonID.equals("normalModeToggle")) {
            System.out.println("Start Normal Game Mode");
            normalGameMode();

            Parent boardScene = FXMLLoader.load(getClass().getResource("/view/fxml/LevelMenuScene.fxml"));
            scene = new Scene(boardScene);
            Main.stage.setScene(scene);
            Main.stage.show();

            stage.close();
        } else if (buttonID.equals("specialModeToggle")) {
            System.out.println("Start Special Game Mode");
            specialGameMode();

            Parent boardScene = FXMLLoader.load(getClass().getResource("/view/fxml/LevelMenuScene.fxml"));
            scene = new Scene(boardScene);
            Main.stage.setScene(scene);
            Main.stage.show();

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
