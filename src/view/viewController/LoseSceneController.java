package view.viewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import view.Main;

public class LoseSceneController {
    private Scene scene;
    public void retry() throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/BoardScene.fxml"));
        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }

    public void returnHome() throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/StartScene.fxml"));
        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }
}
