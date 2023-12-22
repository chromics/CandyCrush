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
import view.Main;
import view.viewController.BoardSceneController;

public class StartSceneController implements Initializable {

    private Stage stage;
    private Scene scene;
    private MediaPlayer mediaPlayer;

    // new File(this.mediaPath).toURI().toString()

    public void initialize(URL location, ResourceBundle resourceBundle) {
        //!
        // playMusic();
    }

    public void playMusic() {
        // Constant.audio.get("springDay")
        System.out.println("PlayMusic");
        try{
            String resourcePath = "src\\data\\constant\\audio\\springDay.mp3"; // Update this with the actual relative path
            
            System.out.println(Constant.audioHashMap.get("springDay"));
            String filePath = "src\\data\\constant\\audio\\springDay.mp3"; // Update this with the actual relative path

            File file = new File(filePath);
        
            if (file.exists() && !file.isDirectory()) {
                System.out.println("File exists and is not a directory!");

                // Check if the file is readable
                if (file.canRead()) {
                    System.out.println("File is readable.");
                } else {
                    System.out.println("File is not readable.");
                }
            } else {
                System.out.println("File does not exist or is a directory!");
            }

            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(filePath);
            if (resource != null) {
                System.out.println("Resource found: " + resource.getFile());
            } else {
                System.out.println("Resource not found.");
            }


            // Media menuMusic = new Media(getClass().getResource(resourcePath).toExternalForm());
            // mediaPlayer = new MediaPlayer(menuMusic);
            // mediaPlayer.setVolume(0.5);
            // mediaPlayer.setAutoPlay(true);
            // Media menuMusic = new Media(getClass().getResource(Constant.audioHashMap.get("springDay")).toExternalForm());
            
            // mediaPlayer = new MediaPlayer(menuMusic);
            // mediaPlayer.setVolume(0.5);
            // mediaPlayer.setAutoPlay(true);
         } catch (NullPointerException e){
             e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    
        // Media menuMusic = new Media(new File(mediaPath).toURI().toString());
    
    }

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
        dialog.setX(Main.stage.getX() + 700);
        dialog.setY(Main.stage.getY() + 320);

        Image dialogIcon = new Image(Constant.fruitsHashMap.get("apple"));
        dialog.getIcons().add(dialogIcon);
        System.out.println("Exe");
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
        dialog.setX(Main.stage.getX() + 430);
        dialog.setY(Main.stage.getY() + 160);

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