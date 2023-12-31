package view.viewController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuSettingController implements Initializable {

    @FXML
    private Slider musicSlider;
    @FXML
    private Slider windSlider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSliderValue("musicSlider", VolumeController.getStartSceneMusicVolume());
        setSliderValue("windSlider", VolumeController.getStartSceneWindVolume());
        setMusicVolume();
        setWindVolume();
    }

//    public MenuSettingController(MusicController musicController, MusicController windController) {
//        this.musicController = musicController;
//        this.windController = windController;
//    }

//    public void setInitVolume() {
//        musicSlider.setValue(VolumeController.getStartSceneMusicVolume());
//        windSlider.setValue(VolumeController.getStartSceneWindVolume());
//    }

//    public float getSliderValue(String slider) {
//        if (slider.equals("musicSlider")) {
//            return (float) musicSlider.getValue();
//        }
//        else {
//            return (float) windSlider.getValue();
//        }
//    }
    public void setSliderValue(String slider, double value) {
        if (slider.equals("musicSlider")) {
            musicSlider.setValue((double) value);
        }
        else {
            windSlider.setValue((double) value);
        }
    }
    public void setMusicVolume() {
        musicSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                VolumeController.setStartSceneMusicVolume(musicSlider.getValue());
                StartSceneController.musicController.setVolume(musicSlider.getValue());
                StartSceneController.musicController.setMusicVolume();
                System.out.println(StartSceneController.musicController.getVolume());
            }
        });
    }
    public void setWindVolume() {
        windSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                VolumeController.setStartSceneWindVolume(windSlider.getValue());
                StartSceneController.windController.setVolume(windSlider.getValue());
                StartSceneController.windController.setMusicVolume();
                System.out.println(StartSceneController.windController.getVolume());
            }
        });
    }
}
