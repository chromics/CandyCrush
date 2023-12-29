package data;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameFileInfo implements Serializable {
    GameData gameData;
    String saveDate;
    String saveName;
    String fileName;
    String levelDisplay;
    String saveDateDisplay;
    String fileNameDisplay;
    String gameMode;

    //-----------------------------------------------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------------------------------------------
    public GameFileInfo(GameData gameData, String saveName){
        this.gameData = gameData;
        this.saveName = saveName;
        this.gameMode = gameData.getGameMode().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        SimpleDateFormat sdfDisplay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        saveDate = sdf.format(currentDate);
        saveDateDisplay = sdfDisplay.format(currentDate);

        levelDisplay = "Level-" + String.valueOf(gameData.getLevelNum());

        fileName = (this.saveName + "_" + gameMode +  "_" + levelDisplay + "_" + this.saveDate + ".ser");
        fileNameDisplay = String.format("%-15s   %-20s %s    %s", this.saveName, gameMode, levelDisplay, saveDateDisplay);
    }
    public GameFileInfo(GameData gameData){
        this.gameData = gameData;
        this.saveName = "SaveFile";
        this.gameMode = gameData.getGameMode().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        SimpleDateFormat sdfDisplay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        saveDate = sdf.format(currentDate);
        saveDateDisplay = sdfDisplay.format(currentDate);

        levelDisplay = "Level-" + String.valueOf(gameData.getLevelNum());

        fileName = (this.saveName + "_" + gameMode +  "_" + levelDisplay + "_" + this.saveDate + ".ser");
        fileNameDisplay = String.format("%-15s   %-20s %s    %s", this.saveName, gameMode, levelDisplay, saveDateDisplay);
    }
    //===============================================================================================
    

    //-----------------------------------------------------------------------------------------------
    // Getter
    //-----------------------------------------------------------------------------------------------
    public GameData getGameData() { return gameData; }
    public String getFileName() { return fileName; }
    public String getSaveName() { return saveName; }
    public String getSaveDate() { return saveDate; }
    public String getLevelDisplay() { return levelDisplay; }
    public String getFileNameDisplay() { return fileNameDisplay; }
    //===============================================================================================

}


/*
* Board_Row_Size: 8
* Board_Col_Size: 7
* Map_Template: RECTANGULAR
* GameMode: NORMAL_GAME_MODE
* Automatic_Mode: true
* Special_Mode: true
* Level_Index: 1
* Score: 10
* Target_Score: 2000
* Remaining_Step: 5
* Remaining_Shuffle: 3
* Remaining_Hints: -10
* Fall_Data: 0 1 2 3 4 5 6 7
* Match_Data:
* row col length orientation
* Board:
* 1 2 3 4 5 6 0
* 0 0 0 2 3 2 4
* 0 2 3 4 5 2 1
* Board Information : 
* 0 : null
* 1 : apple
* 2 : orange
* 3 : banana
* 4 : pear
* 5 : grape
* 6 : blueberry
* 7 : Horizontal Bomb
* 8 : Vertical Bomb 
*/