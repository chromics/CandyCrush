package view;

import java.util.Optional;

import controller.SaveLoadController;
import data.constant.Constant;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.stage.Window;
import view.viewController.*;
import data.GameData;

public class Main extends Application {
    public static Stage stage;
    private static GameData gameData;

    @Override
    public void start(Stage primaryStage) throws Exception{
        VolumeController.setStartSceneMusicVolume(70);
        VolumeController.setStartSceneWindVolume(70);
        StartSceneController.initMusic();

        Font.loadFont(getClass().getResourceAsStream("/data/constant/font/Minecraft.ttf"), 65);
        Font.loadFont(getClass().getResourceAsStream("/data/constant/font/Minecraftia-Regular.ttf"), 20);

        stage = primaryStage;
        // SCENE ROOT NODES
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/StartScene.fxml"));
        Parent startScene = loader.load();
        // STAGE
        // a) Icon
        Image icon = new Image(Constant.catHashMap.get("defaultCat"));
        stage.getIcons().add(icon);

        // b) Title
        stage.setTitle("Harvest Match");
        stage.setHeight(500);
        stage.setWidth(800);
        stage.setResizable(false);

        // Set Scene
        stage.setScene(new Scene(startScene));
        // Show Stage
        stage.show();
        startScene.requestFocus();

        // Exit Stage
        stage.setOnCloseRequest(event -> {
            event.consume();
            try {
                exit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void exit() throws Exception {
        System.out.println("Save & Exit from Main");
        // System.out.println("attempt to close");
        // if scene is boardScene add save file notice before exiting
        Parent root = stage.getScene().getRoot();
        String rootID = "boardPane";
        if (rootID.equals(root.getId())) {
            exitBoardScene();
            // System.out.println("current scene is boardscene");
        }
        else {
            // else just confirm exit
            exitStage();
        }
        // System.out.println("attempt to close 2");
    }
    public void exitBoardScene() throws Exception {
        Alert alert = new Alert(AlertType.CONFIRMATION);

        Image dialogIcon = new Image(Constant.catHashMap.get("sadCat"));
        ImageView dialogView = new ImageView(dialogIcon);
        dialogView.setFitHeight(80);
        dialogView.setFitWidth(100);
        alert.getDialogPane().setGraphic(dialogView);

        alert.setTitle("Save & Exit");
        alert.setHeaderText("You're about to exit the current game!");
        alert.setContentText("Do you want to save?");
        alert.setX(Main.stage.getX() + 625);
        alert.setY(Main.stage.getY() - 55);

        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.YES)).setDefaultButton(true);

        // Save & Exit
        Optional <ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            SaveFileInputDialogController.generateSaveFileNameTextField("mainStage");
            stage.close();
            System.exit(0);
        }
        // Exit
        else if (result.isPresent() && result.get() == ButtonType.NO) {
            stage.close();
            System.exit(0);
        }
    }
    
    public void exitStage() {
        Alert alert = new Alert(AlertType.CONFIRMATION);

        Image dialogIcon = new Image(Constant.catHashMap.get("sadCat"));
        ImageView dialogView = new ImageView(dialogIcon);
        dialogView.setFitHeight(80);
        dialogView.setFitWidth(100);
        alert.getDialogPane().setGraphic(dialogView);

        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit the game!");
        alert.setContentText("Do you want to leave?");
        alert.setX(stage.getX() + 625);
        alert.setY(stage.getY() - 55);

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setDefaultButton(false);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setDefaultButton(true);

        Optional <ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage.close();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}