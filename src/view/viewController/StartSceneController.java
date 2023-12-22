package view.viewController;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import data.constant.Constant;
import controller.GameController;
import view.viewController.BoardSceneController;

public class StartSceneController {

    private Stage stage;
    private Scene scene;
    private MediaPlayer mediaPlayer;

    // new File(this.mediaPath).toURI().toString()

    public void initialize(URL location, ResourceBundle resourceBundle) {
        //!
        // playMusic(this.mediaPlayer);
    }

    // public void playMusic(MediaPlayer mediaPlayer) {
    //     String mediaPath = "springDay.wav";
    //     // Constant.audio.get("springDay")
    //     Media menuMusic = new Media(getClass().getResource(mediaPath).toExternalForm());
    //     // Media menuMusic = new Media(new File(mediaPath).toURI().toString());
    //     mediaPlayer = new MediaPlayer(menuMusic);
    //     mediaPlayer.setVolume(0.5);
    //     mediaPlayer.setAutoPlay(true);
    // }

    public void newGame(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/BoardScene.fxml"));
        Parent boardScene = loader.load();
        
        BoardSceneController view = loader.getController();
        GameController gameController = new GameController(view);

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
        //! The boardScene and game will be loaded from loadScene, just like backToStartScene
    }

    public void settings(ActionEvent event) throws Exception {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this.stage);
        dialog.setResizable(false);
        dialog.setX(775);
        dialog.setY(365);

        Image dialogIcon = new Image(Constant.fruitsHashMap.get("apple"));
        dialog.getIcons().add(dialogIcon);

        Parent boardScene = FXMLLoader.load(getClass().getResource("/view/fxml/MenuSetting.fxml"));
        Scene scene = new Scene(boardScene);
        dialog.setScene(scene);
        dialog.show();
    }

    public void gameObjective(ActionEvent event) throws Exception {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this.stage);
        dialog.setResizable(false);
        dialog.setX(545);
        dialog.setY(250);

        Image dialogIcon = new Image(Constant.fruitsHashMap.get("apple"));
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