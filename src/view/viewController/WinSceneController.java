package view.viewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import view.Main;
import controller.GameController;

public class WinSceneController {
    private Scene scene;

    public void nextLevel() throws Exception {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/BoardScene.fxml"));
        Parent boardScene = loader.load();
        
        Main.setBoardSceneController(loader.getController());
        Main.nextLevel();
    
        scene = new Scene(boardScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }

    public void returnHome() throws Exception {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/StartScene.fxml"));
        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }
}
