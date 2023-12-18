package view;

import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.image.*;

public class Main extends Application {
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
        // SCENE ROOT NODES
        Parent startScene = FXMLLoader.load(getClass().getResource("fxml/StartScene.fxml"));

        // STAGE
        // a) Icon
        Image icon = new Image("data/constant/image/apple.png");
        this.stage.getIcons().add(icon);

        // b) Title
        this.stage.setTitle("Happy Match");
        this.stage.setHeight(500);
        this.stage.setWidth(800);
        this.stage.setX(100);
        this.stage.setY(100);
        this.stage.setResizable(false);

        // Set Scene
        this.stage.setScene(new Scene(startScene));
        // Show Stage
        this.stage.show();

        // Exit Stage
        this.stage.setOnCloseRequest(event -> {
            event.consume();
            exit(this.stage);
        });

    }

    public void exit(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit the game!");
        alert.setContentText("Do you want to leave?");
        alert.setX(725);
        alert.setY(45);

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

    public static void main(String[] args) {
        launch(args);
    }
}