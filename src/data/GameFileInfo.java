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
    String level;

    //-----------------------------------------------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------------------------------------------
    public GameFileInfo(GameData gameData, String saveName){
        this.gameData = gameData;
        this.saveName = saveName;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDate = new Date();
        saveDate = sdf.format(currentDate);

        level = gameData.getLevel().getLevelString();

        this.fileName = (this.saveName + "_" + level + "_" + this.saveDate + ".ser");
    }
    //===============================================================================================
    

    //-----------------------------------------------------------------------------------------------
    // Getter
    //-----------------------------------------------------------------------------------------------
    public GameData getGameData(){ return gameData; }
    public String getFileName(){ return fileName; }
    public String getSaveName(){ return saveName; }
    public String getSaveDate(){ return saveDate; }
    public String getLevel(){ return level; }
    //===============================================================================================

}
