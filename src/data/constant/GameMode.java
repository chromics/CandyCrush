package data.constant;

import java.util.List;
import java.util.ArrayList;

public enum GameMode {
    
    Normal_Game_Mode(
        false, 
        false,
        new ArrayList<>(){{
            add(Level.LEVEL_1);
            add(Level.LEVEL_2);
            add(Level.LEVEL_3);
            add(Level.LEVEL_4);
            add(Level.LEVEL_5);
            add(Level.LEVEL_6);
            add(Level.LEVEL_7);
            add(Level.LEVEL_8);
            add(Level.LEVEL_9);
            add(Level.LEVEL_10);
        }}
    ),
    Special_Game_Mode(
        true,
        true,
        new ArrayList<>(){{
            add(Level.LEVEL_C1);
            add(Level.LEVEL_C2);
            add(Level.LEVEL_C3);
            add(Level.LEVEL_C4);
            add(Level.LEVEL_C5);
            add(Level.LEVEL_C6);
            add(Level.LEVEL_C7);
            add(Level.LEVEL_C8);
            add(Level.LEVEL_C9);
            add(Level.LEVEL_C10);
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
