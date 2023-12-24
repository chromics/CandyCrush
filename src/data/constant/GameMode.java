package data.constant;

import java.util.List;
import java.util.ArrayList;

import data.constant.Level;

public enum GameMode {
    
    NORMALGAMEMODE(
        false, 
        false,
        new ArrayList<>(){{
            add(Level.LEVEL_1);
            add(Level.LEVEL_2);
        }}
    ),
    CHRISTMASGAMEMODE(
        true,
        true,
        new ArrayList<>(){{
            add(Level.LEVEL_C1);
            add(Level.LEVEL_C2);
        }}
    );
    
    //-----------------------------------------------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------------------------------------------
    private boolean specialMode;
    private boolean automaticMode;
    private List<Level> levelList;
    
    GameMode(boolean specialMode, boolean automaticMode, List<Level> levelList){
        this.specialMode = specialMode;
        this.automaticMode = automaticMode;
        this.levelList = levelList;
    }
    //===============================================================================================

    
    //-----------------------------------------------------------------------------------------------
    // Getter
    //-----------------------------------------------------------------------------------------------
    public boolean getAutomaticMode(){
        return automaticMode;
    }
    public boolean getSpecialMode(){
        return specialMode;
    }
    public List<Level> getLevelList(){
        return levelList;
    }
    public Level getNextLevel(Level currentLevel){
        int currentLevelIndex = currentLevel.getLevelIndex();
        return levelList.get(currentLevelIndex + 1);
    }
    //===============================================================================================
}
