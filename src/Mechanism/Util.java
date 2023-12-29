package mechanism;

import data.constant.Constant;

public class Util {
    public static <T> T RandomPick(T[] arr){
        int randomIndex = (int) (Math.random() * arr.length);
        return arr[randomIndex];
    }

    public static void pauseExecution (int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String generateRandomPieceName(){
        return Util.RandomPick(Constant.fruitsName);
    }
}
