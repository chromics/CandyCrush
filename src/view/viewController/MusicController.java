package view.viewController;

import javafx.fxml.Initializable;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MusicController implements Initializable {
    private Clip music;
    private double currentVolume;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMusicVolume();
    }

    public MusicController(double volume) {
        currentVolume = volume;
    }
    public void initMusicController(String path) throws Exception {
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(Objects.requireNonNull(MusicController.class.getResource(path)));
        music = AudioSystem.getClip();
        music.open(audioInput);

        FloatControl volume = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(20f * (float) Math.log10(currentVolume/100));
    }
    public void playMusic() throws Exception {
        if (music == null || !music.isRunning()) {
            music.loop(Clip.LOOP_CONTINUOUSLY);
            music.start();
        }
    }
    public void stopMusic() {
        music.stop();
    }
    public void setVolume(double value) {
        currentVolume = value;
    }
    public double getVolume() {
        return currentVolume;
    }
    public void setMusicVolume() {
        FloatControl volume = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(20f * (float) Math.log10(currentVolume/100));
    }



}
