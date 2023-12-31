package view.viewController;

import controller.GameController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class InGameSettingSceneController implements Initializable {
    private static GameController gameController;

    @FXML
    private Slider sliderMusic;
    @FXML
    private CheckBox autoModeCheckBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSliderValue(VolumeController.getBoardSceneMusicVolume());
        setMusicVolume();
    }
    public void setSliderValue(double value) {
        sliderMusic.setValue(value);
    }
    public void setMusicVolume() {
        sliderMusic.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                VolumeController.setBoardSceneMusicVolume(sliderMusic.getValue());
                BoardSceneController.musicController.setVolume(sliderMusic.getValue());
                BoardSceneController.musicController.setMusicVolume();
            }
        });
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
