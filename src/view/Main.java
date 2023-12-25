package view;

import java.util.Optional;

import controller.SaveLoadController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.*;
import view.viewController.BoardSceneController;
import view.viewController.InGameSettingSceneController;
import view.viewController.SaveFileInputDialogController;
import view.viewController.StartSceneController;
import data.GameData;
import data.GameFileInfo;

public class Main extends Application {
    public static Stage stage;
    private static GameData gameData;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        // SCENE ROOT NODES
        Parent startScene = FXMLLoader.load(getClass().getResource("fxml/StartScene.fxml"));
        // STAGE
        // a) Icon
        Image icon = new Image("data/constant/image/apple.png");
        stage.getIcons().add(icon);

        // b) Title
        stage.setTitle("Happy Match");
        stage.setHeight(500);
        stage.setWidth(800);
        stage.setResizable(false);

        // Set Scene
        stage.setScene(new Scene(startScene));
        // Show Stage
        stage.show();

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
        // Alert
        Alert alert = new Alert(AlertType.CONFIRMATION);

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
//            SaveFileInputDialogController.generateSaveFileNameTextField("mainStage");
//            SaveLoadController.saveGame(BoardSceneController.getGameData(), SaveFileInputDialogController.getText());

            Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION);
            saveAlert.setTitle("Success!");
            saveAlert.setHeaderText("Save successful!");
            saveAlert.setContentText("Your file has been successfully saved.");
            saveAlert.setX(Main.stage.getX() + 625);
            saveAlert.setY(Main.stage.getY() - 55);
            saveAlert.showAndWait();

            stage.close();
        }
        // Exit
        else if (result.isPresent() && result.get() == ButtonType.NO) {
            stage.close();
        }
    }
    
    public void exitStage() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
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
        }
    }

    public static void saveGame(String fileName){
//        startSceneController.getBoardSceneController().saveGame(fileName);
    }
    public static void main(String[] args) {
        launch(args);
    }
}