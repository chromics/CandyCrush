package controller;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

import data.GameData;
import data.GameFileInfo;

public class SaveLoadController {
    private static final String nameListPath = "src\\save\\FileName.txt";
    private static final String directory = "src\\save\\gamefile";

    private static final File nameListFile = new File(nameListPath);

    //-----------------------------------------------------------------------------------------------
    // Save
    //-----------------------------------------------------------------------------------------------
    public static void saveGame(GameFileInfo gameFileInfo){
        String fileName = gameFileInfo.getFileName();
        appendFileName(fileName);
        saveGame(gameFileInfo);
    }

    public static void appendFileName(String fileName){
    
        try(PrintWriter writer = new PrintWriter(new FileWriter(nameListFile, true))){
            writer.println(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void overwriteFileNameList(List<String> fileNameList){
    
        try (PrintWriter writer = new PrintWriter(nameListFile)) {
            for (String name : fileNameList) {
                writer.println(name);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveGameFile (GameFileInfo gameFileInfo) {
        String gameFileName = directory + gameFileInfo.getFileName();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(gameFileName))) {
            oos.writeObject(gameFileInfo); 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //===============================================================================================
    

    //-----------------------------------------------------------------------------------------------
    // Load
    //-----------------------------------------------------------------------------------------------
    public static List<String> load_File_Name_List(){
        List<String> fileNameList = new ArrayList<>();
        try (Scanner scanner = new Scanner(nameListFile)) {
            while (scanner.hasNextLine()) {
                fileNameList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileNameList;
    }

    public static List<GameFileInfo> load_Game_File_List (){
        List<String> fileNameList = load_File_Name_List();
        List<GameFileInfo> gameFileList = new ArrayList<>();

        for(String fileName : fileNameList){
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
                    gameFileList.add((GameFileInfo) ois.readObject());

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
        }

        return gameFileList;
    }
    
    public static GameData loadGame(GameFileInfo to_Be_Loaded_GameFile){
        List<String> fileNameList = load_File_Name_List();
        fileNameList.remove(to_Be_Loaded_GameFile.getFileName());

        removeFile(to_Be_Loaded_GameFile.getFileName());

        overwriteFileNameList(fileNameList);

        return to_Be_Loaded_GameFile.getGameData();
    }
    //===============================================================================================   
    
    
    //-----------------------------------------------------------------------------------------------
    // Remove
    //-----------------------------------------------------------------------------------------------
    public static void removeFile(String fileName){
        File file = new File(fileName);
        file.delete();
    }
    //===============================================================================================       
}
