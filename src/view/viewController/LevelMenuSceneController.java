package view.viewController;

import data.constant.Constant;
import data.constant.GameMode;
import data.constant.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.Main;

public class LevelMenuSceneController {
    private static Stage stage;
    private Scene scene;

    public static void setStage(Stage dialogStage) {
        stage = dialogStage;
    }

    public void selectLevel(ActionEvent event) throws Exception {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        Button sourceButton = (Button)(event.getSource());
        String buttonID = sourceButton.getId();
        int levelIndex = Integer.parseInt(buttonID.replaceAll("[^0-9]", "")) - 1;
        System.out.println("Level Index : " + levelIndex);

        Main.setLevelIndex(levelIndex);

        StartSceneController.newGame(event, getClass().getResource("/view/fxml/BoardScene.fxml"), getClass().getResource("/view/fxml/ObjectiveDialogBox.fxml"));
    }

    public void home() throws Exception {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/StartScene.fxml"));
        this.scene = new Scene(startScene);
        Main.stage.setScene(this.scene);
        Main.stage.show();
    }
}
