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
    private static int currentLevelNum;

    public static void setStage(Stage dialogStage) {
        stage = dialogStage;
    }

    public static int getCurrentLevelNum() {
        return currentLevelNum;
    }

    public void selectLevel(ActionEvent event) throws Exception {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        Button sourceButton = (Button)(event.getSource());
        String buttonID = sourceButton.getId();
        int levelIndex = Integer.parseInt(buttonID.replaceAll("[^0-9]", "")) - 1;
        System.out.println("Level Index : " + levelIndex);


        StartSceneController.newGame(levelIndex, event, getClass().getResource("/view/fxml/BoardScene.fxml"), getClass().getResource("/view/fxml/ObjectiveDialogBox.fxml"));
        // insert level to the constructor?
    }
    public void customizeLevel() throws Exception {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.stage);
        dialog.setResizable(false);
//        dialog.setX(Main.stage.getX() + 430);
//        dialog.setY(Main.stage.getY() + 160);

        Image dialogIcon = new Image(Constant.catHashMap.get("defaultCat"));
        dialog.getIcons().add(dialogIcon);

        Parent boardScene = FXMLLoader.load(getClass().getResource(""));
        Scene scene = new Scene(boardScene);
        dialog.setScene(scene);
        dialog.show();

        LevelMenuSceneController.setStage(dialog);
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
