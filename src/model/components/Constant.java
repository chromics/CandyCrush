package model.components;

import java.util.HashMap;

public enum Constant{
    CHESSBOARD_ROW_SIZE(8),CHESSBOARD_COL_SIZE(8), PICTURE_SIZE(35);

    private final int num;
    Constant(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public static HashMap<String,String> DECORATIONS = new HashMap<>(){{
        put("patch","model/static/image/patch.png");
        put("bigPatch","model/static/image/bigPatch.png");
        put("fence","model/static/image/fence.png");
    }};

    public static HashMap<String,String> FRUITS = new HashMap<>(){{
        put("apple","model/static/image/apple.png");
        put("banana","model/static/image/banana.png");
        put("orange","model/static/image/orange.png");
        put("pear","model/static/image/pear.png");
    }};
}