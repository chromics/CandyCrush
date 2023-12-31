package view.viewController;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class MusicController {
    public static void playMusic(Clip music) {
        if (music == null || !music.isRunning()) {
            music.start();
        }
    }
    public static void stopMusic(Clip music) {
        music.stop();
    }

}
