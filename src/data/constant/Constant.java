package data.constant;

import java.util.HashMap;

// import data.constant.Orientation;

public enum Constant{
    PICTURE_SIZE( 35 );

    private final int num;
    Constant( int num ){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public static HashMap<String,String> decorations = new HashMap<>(){{
        put( "patch","data/constant/image/patch.png" );
        put( "bigPatch","data/constant/image/bigPatch.png" );
        put( "fence","data/constant/image/fence.png" );
    }};

    public static String[] fruitsName = { "apple", "banana", "orange", "pear" };

    public static HashMap<String,String> fruitsHashMap = new HashMap<>(){{
        put( "apple","data/constant/image/apple.png" );
        put( "banana","data/constant/image/banana.png" );
        put( "orange","data/constant/image/orange.png" );
        put( "pear","data/constant/image/pear.png" );
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
