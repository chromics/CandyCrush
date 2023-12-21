package view.viewController;

import controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class InGameSettingSceneController {
    private GameController gameController;

    @FXML
    private Slider sliderMusic;
    @FXML
    private Slider sliderSFX;

    public void gameMusicAdjust() {
        
    }
    public void SFXAdjust() {

    }
    public void toggleAutoMode() {
        gameController.changeAutomaticMode();
    }

    public void setGameController(GameController gameController){
        this.gameController = gameController;
    }
}
