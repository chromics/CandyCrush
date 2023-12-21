package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;

public class MenuSettingController {

    @FXML
    Slider musicSlider;

    public void musicAdjust(MediaPlayer mediaPlayer) {
        mediaPlayer.setVolume(musicSlider.getValue());
    }
}
