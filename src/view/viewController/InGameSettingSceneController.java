package view.viewController;

import controller.GameController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class InGameSettingSceneController implements Initializable {
    private GameController gameController;

    @FXML
    private Slider sliderMusic;
    @FXML
    private Slider sliderSFX;
    @FXML
    private CheckBox autoModeCheckBox;

    public void initialize(URL location, ResourceBundle resourceBundle) {
        autoModeCheckBox.setSelected(gameController.getGameData().getAutomaticMode());
    }
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
