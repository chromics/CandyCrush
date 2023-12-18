package mechanism;

import data.constant.Constant;

public class CreatePiece {
    public static String createPiece(){
        return Util.RandomPick(Constant.fruitsName);
    }
}
