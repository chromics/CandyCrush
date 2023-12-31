package view.viewController;

import java.awt.*;
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
    public static MusicController musicController;
    public static MusicController windController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public static void initMusic() throws Exception {
        musicController = new MusicController(VolumeController.getStartSceneMusicVolume());
        musicController.initMusicController("/data/constant/audio/springDay.wav");
        musicController.playMusic();

        windController = new MusicController(VolumeController.getStartSceneWindVolume());
        windController.initMusicController("/data/constant/audio/windSFX.wav");
        windController.playMusic();
    }
    public static void stopMusic() {
        musicController.stopMusic();
    }
    public static void stopWind() {
        windController.stopMusic();
    }

    public static void newGame(ActionEvent event, URL mainURL, URL dialogURL) throws Exception {
        FXMLLoader loader = new FXMLLoader(mainURL);
        Parent boardScene = loader.load();
        
        Main.setBoardSceneController(loader.getController());
        Main.initiateNewGame();
    
        scene = new Scene(boardScene);
        Main.stage.setScene(scene);
        Main.stage.show();
        // gameObjective(event, dialogURL);

        musicController.stopMusic();
        windController.stopMusic();

        VolumeController.setBoardSceneMusicVolume(70);
        if (Main.getGameMode() == GameMode.Special_Game_Mode) {
            BoardSceneController.initSpecialMusic();
        }
        else if (Main.getGameMode() == GameMode.Normal_Game_Mode) {
            BoardSceneController.initMusic();
        }
    }
//    public static void newCustomGame(ActionEvent event, URL mainURL, URL dialogURL, int gridWidth, int gridHeight, int movesCount, int shuffleCount, int targetScore, Clip BGM, GameMode gamemode) throws Exception {
//        FXMLLoader loader = new FXMLLoader(mainURL);
//        Parent boardScene = loader.load();
//
//        boardSceneController = loader.getController();
//        GameController gameController = new GameController(currentGameMode, levelIndex, boardSceneController);
//
//        scene = new Scene(boardScene);
//        Main.stage.setScene(scene);
//        Main.stage.show();
//        gameObjective(event, dialogURL);
//
//        MusicController.stopMusic(music);
//        MusicController.stopMusic(wind);
//    }

//    public static void newTemplatedGame(ActionEvent event, URL mainURL, URL dialogURL, int movesCount, int shuffleCount, int targetScore, Clip BGM, Template template) throws Exception {
//        FXMLLoader loader = new FXMLLoader(mainURL);
//        Parent boardScene = loader.load();
//
//        boardSceneController = loader.getController();
//        GameController gameController = new GameController(currentGameMode, levelIndex, boardSceneController);
//
//        scene = new Scene(boardScene);
//        Main.stage.setScene(scene);
//        Main.stage.show();
//        gameObjective(event, dialogURL);
//
//        MusicController.stopMusic(music);
//        MusicController.stopMusic(wind);
//    }

    public static void gameObjective(ActionEvent event, URL url) throws Exception {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.stage);
        dialog.setResizable(false);
        dialog.setX(Main.stage.getX() + 465);
        dialog.setY(Main.stage.getY() + 180);

        dialog.setTitle("Game Objective");
        Image dialogIcon = new Image(Constant.fruitsHashMap.get("apple"));
        dialog.getIcons().add(dialogIcon);

        Parent boardScene = FXMLLoader.load(url);
        Scene scene = new Scene(boardScene);
        dialog.setScene(scene);
        dialog.show();
    }
    
    public void loadGame(ActionEvent event) throws Exception {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        Parent boardScene = FXMLLoader.load(getClass().getResource("/view/fxml/LoadScene.fxml"));
        Main.stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        scene = new Scene(boardScene);
        Main.stage.setScene(scene);
        boardScene.requestFocus();
        Main.stage.show();
    }

    public void settings(ActionEvent event) throws Exception {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.stage);
        dialog.setResizable(false);
        dialog.setX(Main.stage.getX() + 620);
        dialog.setY(Main.stage.getY() + 320);

        Image dialogIcon = new Image("data/constant/image/highVolumeIcon.png");
        dialog.getIcons().add(dialogIcon);
        dialog.setTitle("Volume Settings");


        Parent boardScene = FXMLLoader.load(getClass().getResource("/view/fxml/MenuSetting.fxml"));

        // MenuSettingController settingsView = loader.getController();
        // settingsView.setInitVolume();

        Scene scene = new Scene(boardScene);
        dialog.setScene(scene);
        dialog.show();
    }

    public void setGameMode(ActionEvent event) throws Exception {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.stage);
        dialog.setResizable(false);
        dialog.setX(Main.stage.getX() + 120);
        dialog.setY(Main.stage.getY() + 160);

        Image dialogIcon = new Image("data/constant/image/settingsIcon.png");
        dialog.getIcons().add(dialogIcon);
        dialog.setTitle("Select Game Mode");

        Parent boardScene = FXMLLoader.load(getClass().getResource("/view/fxml/NewGameInputDialog.fxml"));
        Scene scene = new Scene(boardScene);
        dialog.setScene(scene);
        dialog.show();

        boardScene.requestFocus();
        NewGameInputDialogController.setStage(dialog);
    }
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