package data.constant;

import java.util.HashMap;
import javafx.scene.image.Image;

// import data.constant.Orientation;

public enum Constant{
    ANIMATION_DURATION(200),
    AUTOMATIC_BREAK_DURATION(250),
    REMOVE_BREAK_DURATION(50),
    EXTRA_DURATION(30);

    private int duration;

    Constant (int duration) {
        this.duration = duration;
    }

    public int getNum() {
        return duration;
    }

    public static HashMap <Integer, Integer> pictureSizeList = new HashMap<>(){{
        put(7,48);
        put(8,43);
        put(9,37);
        put(10,32);
    }};

    public static HashMap <String, String> audioHashMap = new HashMap<>() {{
        put( "springDay","CandyCrush/data/constant/audio/springDay.mp3" );
    }};
    
    public static HashMap<String,String> decorations = new HashMap<>(){{
        put( "patchSelectMark","data/constant/image/patchSelectMark.png" );
        put( "patch","data/constant/image/patch.png" );
        put( "bigPatch","data/constant/image/bigPatch.png" );
        put( "fence","data/constant/image/fence.png" );
        put( "iceBlock","data/constant/image/iceBlock.png" );
    }};

    public static HashMap<String, String> pieceToNumber = new HashMap<>(){{
        put(null, "0");
        put("apple", "1");
        put("orange", "2");
        put("banana", "3");
        put("pear", "4");
        put("grape", "5");
        put("blueberry", "6");
        put("sleighCat", "7");
        put("sackCat", "8");
    }};

    public static HashMap<String, String> numberToPiece = new HashMap<>(){{
        put("0", null);
        put("1", "apple");
        put("2", "orange");
        put("3", "banana");
        put("4", "pear");
        put("5", "grape");
        put("6", "blueberry");
        put("7", "sleighCat");
        put("8", "sackCat");
    }}; 

    public static String[] fruitsName = { "apple", "banana", "orange", "pear", "blueberry", "grape" };

    public static HashMap<String,String> fruitsHashMap = new HashMap<>(){{
        put( "apple","data/constant/image/apple.png" );
        put( "banana","data/constant/image/banana.png" );
        put( "blueberry","data/constant/image/blueberry.png" );
        put( "grape","data/constant/image/grape.png" );
        put( "orange","data/constant/image/orange.png" );
        put( "pear","data/constant/image/pear.png" );
    }};

    public static HashMap<String,String> specialPropsHashMap = new HashMap<>() {{
        put( "sleighCat","data/constant/image/sleighCat.png" );
        put( "sackCat","data/constant/image/sackCat.png" );
    }};

    public static HashMap <String, Image> piecesImageHashMap = new HashMap<>();

    static {
        for (String fruitName : fruitsHashMap.keySet()) {
            Image fruitImage = new Image(fruitsHashMap.get(fruitName));
            piecesImageHashMap.put(fruitName, fruitImage);
        }

        for (String specialPropName : specialPropsHashMap.keySet()) {
            Image specialPropImage = new Image(specialPropsHashMap.get(specialPropName));
            piecesImageHashMap.put(specialPropName, specialPropImage);
        }
    }

    public static HashMap<String,String> catHashMap = new HashMap<>() {{
        put( "defaultCat","data/constant/image/defaultCat.png" );
        put( "happyCat","data/constant/image/happyCat.png" );
        put( "sadCat","data/constant/image/sadCat.png" );
        put( "meowCat", "data/constant/image/meowCat.png");
    }};
    public static Orientation[][] potential_Match_Patterns = {

        // {A, C}
        // B at center
    
        { Orientation.RIGHT, Orientation.UPLEFT }, { Orientation.DOWNLEFT, Orientation.UP },
        // A . .                                   . C .        
        // . B C                                   . B .        
        // . . .                                   A . .  

        { Orientation.DOWNRIGHT, Orientation.LEFT }, { Orientation.UPRIGHT, Orientation.DOWN },
        // . . .                                     . . A                                      
        // C B .                                     . B .                                      
        // . . A                                     . C .                                            
        
        { Orientation.DOWNLEFT, Orientation.RIGHT }, { Orientation.DOWNRIGHT, Orientation.UP },
        // . . .                                     . C .
        // . B C                                     . B .
        // A . .                                     . . A      
        
        { Orientation.UPRIGHT, Orientation.LEFT }, { Orientation.UPLEFT, Orientation.DOWN },
        // . . A                                   A . .
        // C B .                                   . B .
        // . . .                                   . C .      
        
        
        { Orientation.UPLEFT, Orientation.UPRIGHT }, { Orientation.DOWNLEFT, Orientation.UPLEFT },
        // A . C                                     C . .
        // . B .                                     . B .
        // . . .                                     A . .      
        
        { Orientation.DOWNRIGHT, Orientation.DOWNLEFT }, { Orientation.UPRIGHT, Orientation.DOWNRIGHT },
        // . . .                                         . . A
        // . B .                                         . B .
        // C . A                                         . . C      
    };
}
