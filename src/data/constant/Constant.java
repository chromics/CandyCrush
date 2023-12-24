package data.constant;

import java.util.HashMap;

// import data.constant.Orientation;

public enum Constant{
    ;

    public static HashMap <Integer, Integer> pictureSizeList = new HashMap<>(){{
        put(7,40);
        put(8,35);
        put(9,31);
        put(10,28);
    }};

    public static HashMap <String, String> audioHashMap = new HashMap<>() {{
        put( "springDay","CandyCrush/data/constant/audio/springDay.mp3" );
    }};
    
    public static HashMap<String,String> decorations = new HashMap<>(){{
        put( "patchSelectMark","data/constant/image/patchSelectMark.png" );
        put( "patch","data/constant/image/patch.png" );
        put( "bigPatch","data/constant/image/bigPatch.png" );
        put( "fence","data/constant/image/fence.png" );
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
        put( "downSackCat","data/constant/image/downSackCat.png" );
        put( "iceBlock","data/constant/image/iceBlock.png" );
        put( "leftSleighCat","data/constant/image/leftSleighCat.png" );
    }};

    public static HashMap<String,String> catHashMap = new HashMap<>() {{
        put( "defaultCat","data/constant/image/defaultCat.png" );
        put( "happyCat","data/constant/image/happyCat.png" );
        put( "sadCat","data/constant/image/sadCat.png" );
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
