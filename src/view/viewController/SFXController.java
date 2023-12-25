package view.viewController;

import javafx.scene.media.AudioClip;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class SFXController {
    private static Clip selectClip;
    public static void initializePlay(String path) {
//        AudioClip selectFX = new AudioClip(SFXController.class.getResource(path).toString());

        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(Objects.requireNonNull(SFXController.class.getResource("selectSFX.wav")));
            selectClip = AudioSystem.getClip();
            selectClip.open(audioInput);
            FloatControl gainControl2 = (FloatControl) selectClip.getControl(FloatControl.Type.MASTER_GAIN);
//            float gain = (float) (Math.log(Settings.getInitialSliderLocation() / 100.0) / Math.log(10.0) * 20.0);
//            gainControl2.setValue(gain+2);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing pop sound effect.");
        }

    }

    public static void play() {
        if (selectClip != null) {
            selectClip.setFramePosition(0);  // Rewind the sound
            selectClip.start();
        }
    }
}
