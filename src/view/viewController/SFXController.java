package view.viewController;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class SFXController {
    private static Clip sfxClip;
    public static void initializePlay(String path) {
//        AudioClip selectFX = new AudioClip(SFXController.class.getResource(path).toString());

        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(Objects.requireNonNull(SFXController.class.getResource(path)));
            sfxClip = AudioSystem.getClip();
            sfxClip.open(audioInput);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing pop sound effect.");
        }

    }

    public static void setVolume(float volume) {
        FloatControl gainControl = (FloatControl) sfxClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    public static void play() {
        if (sfxClip != null) {
            sfxClip.setFramePosition(0);  // Rewind the sound
            sfxClip.start();
        }
    }
}

//    FloatControl gainControl2 = (FloatControl) sfxClip.getControl(FloatControl.Type.MASTER_GAIN);
//    float gain = (float) (Math.log(Settings.getInitialSliderLocation() / 100.0) / Math.log(10.0) * 20.0);
//    gainControl2.setValue(gain+2);