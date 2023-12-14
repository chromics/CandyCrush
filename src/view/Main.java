package view;

import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.image.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // SCENE ROOT NODES
        Parent startScene = FXMLLoader.load(getClass().getResource("fxml/StartScene.fxml"));

        // STAGE
        // a) Icon
        Image icon = new Image("static/testicon.png");
        primaryStage.getIcons().add(icon);

        // b) Title
        primaryStage.setTitle("Happy Match");
        primaryStage.setResizable(false);

        // Set Scene
        primaryStage.setScene(new Scene(startScene));
        // Show Stage
        primaryStage.show();

        // Exit Stage
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exit(primaryStage);
        });

    }

    public void exit(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit the game!");
        alert.setContentText("Do you want to leave?");

        ButtonType YES = new ButtonType("Yes");
        ButtonType CANCEL = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(YES, CANCEL);
        Optional <ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == YES) {
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}