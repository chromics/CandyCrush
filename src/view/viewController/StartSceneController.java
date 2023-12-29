package view.viewController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import view.Main;
import data.constant.Constant;
import data.constant.GameMode;
import data.constant.Level;
import controller.GameController;
import view.viewController.BoardSceneController;

import javax.sound.sampled.*;

public class StartSceneController implements Initializable {

    private static Scene scene;
    private static BoardSceneController boardSceneController;
    private MediaPlayer mediaPlayer;
    private Clip music;
    private Clip wind;

    private static GameMode currentGameMode;
    private static int currentLevel;

//    private String buttonClickPath = Objects.requireNonNull(getClass().getResource("/data/constant/audio/springDay.wav")).toString();
//    private static AudioClip buttonClick = new AudioClip(buttonClickPathPath);

    // new File(this.mediaPath).toURI().toString()

    public void initialize(URL location, ResourceBundle resourceBundle) {
        if (music == null || !music.isRunning()) {
            try {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/data/constant/audio/springDay.wav")));
                music = AudioSystem.getClip();
                music.open(audioInput);
                music.loop(Clip.LOOP_CONTINUOUSLY);
                music.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
                System.out.println("Error initializing background music.");
            }
        }
        if (wind == null || !wind.isRunning()) {
            try {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/data/constant/audio/springDay.wav")));
                wind = AudioSystem.getClip();
                wind.open(audioInput);
                wind.loop(Clip.LOOP_CONTINUOUSLY);
                wind.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
                System.out.println("Error initializing background music.");
            }
        }
        Font.loadFont(getClass().getResourceAsStream("/data/constant/font/Minecraft.ttf"), 65);
        Font.loadFont(getClass().getResourceAsStream("/data/constant/font/Minecraftia-Regular.ttf"), 20);
    }

    public static GameMode getCurrentGameMode() {
        return currentGameMode;
    }
    public static void setCurrentGameMode(GameMode gameMode) {
        currentGameMode = gameMode;
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }
    public static void setCurrentLevel(int level) {
        currentLevel = level;
    }

//    public void playMusic() {
//        // Constant.audio.get("springDay")
//        System.out.println("PlayMusic");
//        try{
//            String resourcePath = "src\\data\\constant\\audio\\springDay.mp3"; // Update this with the actual relative path
//
//            System.out.println(Constant.audioHashMap.get("springDay"));
//            String filePath = "src\\data\\constant\\audio\\springDay.mp3"; // Update this with the actual relative path
//
//            File file = new File(filePath);
//
//            if (file.exists() && !file.isDirectory()) {
//                System.out.println("File exists and is not a directory!");
//
//                // Check if the file is readable
//                if (file.canRead()) {
//                    System.out.println("File is readable.");
//                } else {
//                    System.out.println("File is not readable.");
//                }
//            } else {
//                System.out.println("File does not exist or is a directory!");
//            }
//
//            ClassLoader classLoader = getClass().getClassLoader();
//            URL resource = classLoader.getResource(filePath);
//            if (resource != null) {
//                System.out.println("Resource found: " + resource.getFile());
//            } else {
//                System.out.println("Resource not found.");
//            }
//
//
//            // Media menuMusic = new Media(getClass().getResource(resourcePath).toExternalForm());
//            // mediaPlayer = new MediaPlayer(menuMusic);
//            // mediaPlayer.setVolume(0.5);
//            // mediaPlayer.setAutoPlay(true);
//            // Media menuMusic = new Media(getClass().getResource(Constant.audioHashMap.get("springDay")).toExternalForm());
//
//            // mediaPlayer = new MediaPlayer(menuMusic);
//            // mediaPlayer.setVolume(0.5);
//            // mediaPlayer.setAutoPlay(true);
//         } catch (NullPointerException e){
//             e.printStackTrace();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        // Media menuMusic = new Media(new File(mediaPath).toURI().toString());
//
//    }
    public static void newGame(GameMode gameMode, ActionEvent event, URL mainURL, URL dialogURL) throws Exception {
//        buttonClick.play();
        FXMLLoader loader = new FXMLLoader(mainURL);
        Parent boardScene = loader.load();
        
        boardSceneController = loader.getController();
        GameController gameController = new GameController(gameMode, 0, boardSceneController);
    
        scene = new Scene(boardScene);
        Main.stage.setScene(scene);
        Main.stage.show();
        gameObjective(event, dialogURL);
    }

    public static void gameObjective(ActionEvent event, URL url) throws Exception {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.stage);
        dialog.setResizable(false);
        dialog.setX(Main.stage.getX() + 465);
        dialog.setY(Main.stage.getY() + 175);

        Image dialogIcon = new Image(Constant.catHashMap.get("defaultCat"));
        dialog.getIcons().add(dialogIcon);

        Parent boardScene = FXMLLoader.load(url);
        Scene scene = new Scene(boardScene);
        dialog.setScene(scene);
        dialog.show();
    }
    
    public void loadGame(ActionEvent event) throws Exception {
//        buttonClick.play();
        Parent boardScene = FXMLLoader.load(getClass().getResource("/view/fxml/LoadScene.fxml"));
        Main.stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        scene = new Scene(boardScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }

    public void settings(ActionEvent event) throws Exception {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.stage);
        dialog.setResizable(false);
        dialog.setX(Main.stage.getX() + 700);
        dialog.setY(Main.stage.getY() + 320);

        Image dialogIcon = new Image(Constant.fruitsHashMap.get("apple"));
        dialog.getIcons().add(dialogIcon);

        Parent boardScene = FXMLLoader.load(getClass().getResource("/view/fxml/MenuSetting.fxml"));
        Scene scene = new Scene(boardScene);
        dialog.setScene(scene);
        dialog.show();
    }

    public void setGameMode(ActionEvent event) throws Exception {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.stage);
        dialog.setResizable(false);
//        dialog.setX(Main.stage.getX() + 430);
//        dialog.setY(Main.stage.getY() + 160);

        Image dialogIcon = new Image(Constant.catHashMap.get("defaultCat"));
        dialog.getIcons().add(dialogIcon);

        Parent boardScene = FXMLLoader.load(getClass().getResource("/view/fxml/NewGameInputDialog.fxml"));
        Scene scene = new Scene(boardScene);
        dialog.setScene(scene);
        dialog.show();

        boardScene.requestFocus();
        NewGameInputDialogController.setStage(dialog);
    }

    public void setLevel() {

    }

    public static BoardSceneController getBoardSceneController(){
        return boardSceneController;
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