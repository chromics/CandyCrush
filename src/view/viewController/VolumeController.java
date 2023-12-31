package view.viewController;

public class VolumeController {
    private static double startSceneMusicVolume;
    private static double startSceneWindVolume;
    private static double boardSceneMusicVolume;

    public static void setStartSceneMusicVolume(double volume) {
        startSceneMusicVolume = volume;
    }
    public static void setStartSceneWindVolume(double volume) {
        startSceneWindVolume = volume;
    }

    public static double getStartSceneMusicVolume() {
        return startSceneMusicVolume;
    }
    public static double getStartSceneWindVolume() {
        return startSceneWindVolume;
    }

    public static void setBoardSceneMusicVolume(double volume) {
        boardSceneMusicVolume = volume;
    }
    public static double getBoardSceneMusicVolume() {
        return boardSceneMusicVolume;
    }

}
