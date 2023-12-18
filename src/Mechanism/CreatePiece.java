package mechanism;

import data.Constant;

public class CreatePiece {
    public static String createPiece(){
        return Util.RandomPick(Constant.fruitsName);
    }
}
