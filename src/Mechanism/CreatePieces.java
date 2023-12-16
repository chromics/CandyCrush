package Mechanism;

import model.components.Util;

public class CreatePieces {
    public static String createPieces(){
        return Util.RandomPick(new String[]{"apple", "banana", "pear", "orange"});
    }
}
