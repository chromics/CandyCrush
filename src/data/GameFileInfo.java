package data;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import data.GameData;

public class GameFileInfo implements Serializable {
    GameData gameData;
    String saveDate;
    String saveName;
    String fileName;
    String levelDisplay;
    String saveDateDisplay;
    String fileNameDisplay;

    //-----------------------------------------------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------------------------------------------
    public GameFileInfo(GameData gameData, String saveName){
        this.gameData = gameData;
        this.saveName = saveName;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        SimpleDateFormat sdfDisplay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        saveDate = sdf.format(currentDate);
        saveDateDisplay = sdfDisplay.format(currentDate);

        levelDisplay = "Level-" + String.valueOf(gameData.getLevel());

        fileName = (this.saveName + "_" + levelDisplay + "_" + this.saveDate + ".ser");
        fileNameDisplay = String.format("%-15s   %s    %s", this.saveName, levelDisplay, saveDateDisplay);
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
