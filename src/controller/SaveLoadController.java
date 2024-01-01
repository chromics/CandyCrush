package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import data.GameData;
import data.constant.GameMode;
import data.constant.Constant;
import data.constant.Orientation;
import data.constant.MapTemplate;
import model.Cell;
import model.BoardPoint;

public class SaveLoadController {
    private final static String DIRECTORY_PATH = "src\\save\\gamefile\\";
    private final static String FILE_NAME_LIST_PATH = "src\\save\\FileName.txt";

    private final static String SAVE_NAME_LABEL = "File_Name: ";
    private final static String BOARD_ROW_SIZE_LABEL = "\nBoard_Row_Size: ";
    private final static String BOARD_COL_SIZE_LABEL = "\nBoard_Col_Size: ";
    private final static String MAP_TEMPLATE_LABEL = "\nMap_Template: ";
    private final static String GAME_MODE_LABEL = "\nGame_Mode: ";
    private final static String AUTOMATIC_MODE_LABEL = "\nAutomatic_Mode: ";
    private final static String SPECIAL_MODE_LABEL = "\nSpecial_Mode: ";
    private final static String LEVEL_INDEX_LABEL = "\nLevel_Index: ";
    private final static String SCORE_LABEL = "\nScore: ";
    private final static String TARGET_SCORE_LABEL = "\nTarget_Score: ";
    private final static String ICE_BLOCK_DESTROYED_LABEL = "\nIce_Block_Destroyed: ";
    private final static String TOTAL_ICE_BLOCK_LABEL = "\nTotal_Ice_Block: ";
    private final static String REMAINING_STEP_LABEL = "\nRemaining_Step: ";
    private final static String REMAINING_SHUFFLE_LABEL = "\nRemaining_Shuffle: ";
    private final static String REMAINING_HINTS_LABEL = "\nRemaining_Hints: ";
    private final static String FALL_DATA_LABEL = "\nFall_Data: ";
    private final static String MATCH_DATA_LABEL = "\nMatch_Data:\n";
    private final static String BOARD_LABEL = "Board:\n";
    private final static String ICE_BLOCK_BOARD_LABEL = "Ice_Block_Board:\n";

    private final static String BOARD_INFORMATION = """
        Board Information : 
        0 : null
        1 : apple
        2 : orange
        3 : banana
        4 : pear
        5 : grape
        6 : blueberry
        7 : Horizontal Bomb
        8 : Vertical Bomb 
        """;
      
    //-----------------------------------------------------------------------------------------------
    // Save
    //-----------------------------------------------------------------------------------------------
    public static void overwrite_File_Name_List (Map<String, String> fileNameList) {

        try (PrintWriter writer = new PrintWriter(FILE_NAME_LIST_PATH)) {
            for (String fileName : fileNameList.keySet()) {
                writer.println(fileName);
                writer.println(fileNameList.get(fileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void append_File_Name (String fileName, String displayName) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME_LIST_PATH, true))) {
            writer.write(fileName);
            writer.newLine();
            writer.write(displayName);
            writer.newLine();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void save_Game_File (GameData gameData, String fileName) {
        
        String filePath = DIRECTORY_PATH + fileName;

        try (BufferedWriter writer = new BufferedWriter (new FileWriter (filePath))) {

            writer.write(SAVE_NAME_LABEL); 
            writer.write(gameData.getSaveName() + "\n");

            writer.write(BOARD_ROW_SIZE_LABEL); 
            writer.write(String.valueOf(gameData.getBoard_Row_Size()));
            
            writer.write(BOARD_COL_SIZE_LABEL);
            writer.write(String.valueOf(gameData.getBoard_Col_Size()));
            
            writer.write(MAP_TEMPLATE_LABEL);
            writer.write(gameData.getMapTemplate().toString());
            
            writer.write(GAME_MODE_LABEL);
            writer.write(gameData.getGameMode().toString());
            
            writer.write(AUTOMATIC_MODE_LABEL);
            writer.write(String.valueOf(gameData.getAutomaticMode()));
            
            writer.write(SPECIAL_MODE_LABEL);
            writer.write(String.valueOf(gameData.getSpecialMode()));
            
            writer.write(LEVEL_INDEX_LABEL);
            writer.write(String.valueOf(gameData.getLevelIndex()));
            
            writer.write(SCORE_LABEL);
            writer.write(String.valueOf(gameData.getScore()));
            
            writer.write(TARGET_SCORE_LABEL);
            writer.write(String.valueOf(gameData.getTargetScore()));
            
            writer.write(ICE_BLOCK_DESTROYED_LABEL);
            writer.write(String.valueOf(gameData.getIceBlockDestroyed()));

            writer.write(TOTAL_ICE_BLOCK_LABEL);
            writer.write(String.valueOf(gameData.getTotalIceBlock()));

            writer.write(REMAINING_STEP_LABEL);
            writer.write(String.valueOf(gameData.getRemainingStep()));
            
            writer.write(REMAINING_SHUFFLE_LABEL);
            writer.write(String.valueOf(gameData.getRemainingShuffle()));
            
            writer.write(REMAINING_HINTS_LABEL);
            writer.write(String.valueOf(gameData.getRemainingHints()));
            
            writer.write(FALL_DATA_LABEL);
            writer.write(gameData.txtFallData());
            
            writer.write(MATCH_DATA_LABEL);
            writer.write(gameData.txtMatchDatas());

            writer.write(BOARD_LABEL);
            writer.write(gameData.getBoard().txtBoard());

            writer.write(ICE_BLOCK_BOARD_LABEL);
            writer.write(gameData.getBoard().txtIceBlockBoard());

            writer.write(BOARD_INFORMATION);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    public static void save_Game (GameData gameData, String inputName) {
        LocalDateTime currentTime = LocalDateTime.now();

        String fileDate = format_File_Date(currentTime);
        String displayDate = format_Display_Date(currentTime);

        String fileName = generate_File_Name(gameData, inputName, fileDate);
        String displayName = generate_Display_Name(gameData, inputName, displayDate);

        gameData.setSaveName(inputName);

        append_File_Name(fileName, displayName);
        save_Game_File(gameData, fileName);
    }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Load
    //-----------------------------------------------------------------------------------------------
    public static Map<String, String> load_File_Name_List () {

        Map<String, String> fileNameList = new HashMap<>();
        
        try{
            File file = new File(FILE_NAME_LIST_PATH);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                fileNameList.put(scanner.nextLine(), scanner.nextLine());
            }   
            
            scanner.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return fileNameList;
    }
    public static GameData load_Game_Data (String filePath) {
        System.out.println("Enter Load_Game_Data");
        GameData loadedGameData = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            Scanner scanner = new Scanner(reader);
            scanner.useDelimiter("[\\n\\s]+");

            loadedGameData = new GameData();

            scanner.next(); //Skip Label
            loadedGameData.setSaveName(scanner.next()); // Save Name
            scanner.next(); //Skip Label
            loadedGameData.setBoard_Row_Size(scanner.nextInt()); // Board Row Size
            scanner.next(); //Skip Label
            loadedGameData.setBoard_Col_Size(scanner.nextInt()); // Board Col Size
            scanner.next(); //Skip Label
            loadedGameData.setMapTemplate(MapTemplate.valueOf(scanner.next())); // Map Template
            scanner.next(); //Skip Label
            loadedGameData.setGameMode(GameMode.valueOf(scanner.next())); // Game Mode
            scanner.next(); //Skip Label
            loadedGameData.setAutomaticMode(scanner.nextBoolean()); // Automatic Mode
            scanner.next(); //Skip Label
            loadedGameData.setSpecialMode(scanner.nextBoolean()); // Special Mode
            scanner.next(); //Skip Label
            loadedGameData.setLevelIndex(scanner.nextInt()); // Level Index
            scanner.next(); //Skip Label
            loadedGameData.setScore(scanner.nextInt()); // Score
            scanner.next(); //Skip Label
            loadedGameData.setTargetScore(scanner.nextInt()); // Target Score
            scanner.next(); //Skip Label
            loadedGameData.setIceBlockDestroyed(scanner.nextInt()); // Remaining Step
            scanner.next(); //Skip Label
            loadedGameData.setTotalIceBlock(scanner.nextInt()); // Remaining Step
            scanner.next(); //Skip Label
            loadedGameData.setRemainingStep(scanner.nextInt()); // Remaining Step
            scanner.next(); //Skip Label
            loadedGameData.setRemainingShuffle(scanner.nextInt()); // Remaining Shuffle
            scanner.next(); //Skip Label
            loadedGameData.setRemainingHints(scanner.nextInt()); // Remaining Hints
            scanner.next(); //Skip Label
            
            System.out.println("Save-File-Name : " + loadedGameData.getSaveName());
            System.out.println("Map-Template : " + loadedGameData.getMapTemplate());
            System.out.println("Board-Row-Size : " + loadedGameData.getBoard_Row_Size());
            System.out.println("Board-Col-Size : " + loadedGameData.getBoard_Col_Size());
            System.out.println("Game-Mode : " + loadedGameData.getGameMode());
            System.out.println("Auto-Mode : " + loadedGameData.getAutomaticMode());
            System.out.println("Special-Mode : " + loadedGameData.getSpecialMode());
            System.out.println("Level-Index : " + loadedGameData.getLevelIndex());
            System.out.println("Level : " + loadedGameData.getLevel().toString());
            System.out.println("Score : " + loadedGameData.getScore());
            System.out.println("Target-Score : " + loadedGameData.getTargetScore());
            System.out.println("Ice-Block-Destroyed : " + loadedGameData.getIceBlockDestroyed());
            System.out.println("Total-Ice-Block : " + loadedGameData.getTotalIceBlock());
            System.out.println("Remaining-Step : " + loadedGameData.getRemainingStep());
            System.out.println("Remaining-Shuffle : " + loadedGameData.getRemainingShuffle());
            System.out.println("Remaining-Hint : " + loadedGameData.getRemainingHints());

            loadedGameData.init_Additional_Data();
            //Load Additional Data
            //Fall Data
            for(int col = 0; col < loadedGameData.getBoard_Col_Size(); col++){
                int lowest_Empty_Cell_At_Col = Integer.parseInt(scanner.next());
                
                loadedGameData.updateFallData(lowest_Empty_Cell_At_Col, col);
            }
            
            System.out.println("Fall-Data : " + loadedGameData.txtFallData());

            scanner.next(); //Skip Label

            //Match Data
            String temp = scanner.next();
            while (! temp.equals("Board:")) {
                int row = Integer.parseInt(temp);
                int col = scanner.nextInt();
                int length = scanner.nextInt();
                String orientation = scanner.next();

                BoardPoint point = new BoardPoint(row, col);

                loadedGameData.saveMatchData(point, length, Orientation.valueOf(orientation));

                temp = scanner.next();
            }

            System.out.println("Match Data : \n" + loadedGameData.stringMatchData());

            //Board
            Cell[][] grid = loadedGameData.getBoard().getGrid();

            for(int row = 0; row < loadedGameData.getBoard_Row_Size(); row++){
                for(int col = 0; col < loadedGameData.getBoard_Col_Size(); col++){

                    grid[row][col].setPiece(Constant.numberToPiece.get(scanner.next()));
                }
            }
            
            //Ice Block at the Board
            scanner.next(); //Skip Label
            for(int row = 0; row < loadedGameData.getBoard_Row_Size(); row++){
                for(int col = 0; col < loadedGameData.getBoard_Col_Size(); col++){

                    if (scanner.nextInt() == 1) {
                        grid[row][col].add_Ice_Block();
                    }
                }
            }
            

            System.out.println("Board : \n" + loadedGameData.getBoard().txtBoard());
            System.out.println("Ice Block at Board : \n" + loadedGameData.getBoard().txtIceBlockBoard());
            scanner.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loadedGameData;
    }
    public static GameData loadGame (String fileName) {
        System.out.println("Enter Load Game Method");
        String filePath = DIRECTORY_PATH + fileName;
        GameData loadedGameData = load_Game_Data(filePath);
        
        // remove_Game_File(filePath);

        //Load Game
        // loadedGameData.printGameData();

        return loadedGameData;
    }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Remove
    //-----------------------------------------------------------------------------------------------
    public static void remove_Game_File (String filePath) {
        
        File file = new File(DIRECTORY_PATH + filePath);
        file.delete();
    }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // File Name Formatter
    //-----------------------------------------------------------------------------------------------
    public static String generate_File_Name (GameData gameData, String saveName, String saveDate) {
        return String.format("%s_%s_Level-%d_%s.txt", saveName, gameData.getGameMode().toString(), gameData.getLevelIndex()+1, saveDate);
    }
    public static String generate_Display_Name (GameData gameData, String saveName, String saveDate) {
        String gameModeDisplay;
        if(gameData.getGameMode() == GameMode.Normal_Game_Mode){
            gameModeDisplay = "Normal Mode";
        }
        else {
            gameModeDisplay = "Special Mode";
        }
        return String.format("%-10s | %-13s | Level-%d | %s", saveName, gameModeDisplay, gameData.getLevelIndex()+1, saveDate);
    }
    public static String format_File_Date (LocalDateTime currentTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return currentTime.format(formatter);
    }
    public static String format_Display_Date (LocalDateTime currentTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentTime.format(formatter);
    }
    //===============================================================================================

    // public static void main(String[] args) {
    //     String fileName = "SaveName_Special_Game_Mode_Level-1_2023-12-28_00-02-21.txt";
    //     GameData loadedGameData = loadGame(fileName);
    // }
}