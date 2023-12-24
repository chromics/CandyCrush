package view.viewController;

import controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class InGameSettingSceneController{
    private static GameController gameController;

    @FXML
    private Slider sliderMusic;
    @FXML
    private Slider sliderSFX;
    @FXML
    private CheckBox autoModeCheckBox;

    public void gameMusicAdjust() {
        
    }
    public void SFXAdjust() {
        
    }
    public void toggleAutoMode() {
        gameController.changeAutomaticMode();
    }
    
    public void setGameController(GameController gameController){
        InGameSettingSceneController.gameController = gameController;
        setCheckBoxStatus();
    }
    
    public void setCheckBoxStatus(){
        autoModeCheckBox.setSelected(gameController.getGameData().getAutomaticMode());
    }
}
