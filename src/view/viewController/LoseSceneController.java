package view.viewController;

import controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import view.Main;

public class LoseSceneController {
    private Scene scene;
    private GameController gameController;

    public void retry() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/BoardScene.fxml"));
        Parent boardScene = loader.load();
        
        BoardSceneController boardSceneController = loader.getController();
        gameController.restartLevel(boardSceneController);
    
        scene = new Scene(boardScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }

    public void returnHome() throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/StartScene.fxml"));
        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
