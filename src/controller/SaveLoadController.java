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

    private final static String BOARD_ROW_SIZE_LABEL = "Board_Row_Size: ";
    private final static String BOARD_COL_SIZE_LABEL = "\nBoard_Col_Size: ";
    private final static String MAP_TEMPLATE_LABEL = "\nMap_Template: ";
    private final static String GAME_MODE_LABEL = "\nGame_Mode: ";
    private final static String AUTOMATIC_MODE_LABEL = "\nAutomatic_Mode: ";
    private final static String SPECIAL_MODE_LABEL = "\nSpecial_Mode: ";
    private final static String LEVEL_INDEX_LABEL = "\nLevel_Index: ";
    private final static String SCORE_LABEL = "\nScore: ";
    private final static String TARGET_SCORE_LABEL = "\nTarget_Score: ";
    private final static String REMAINING_STEP_LABEL = "\nRemaining_Step: ";
    private final static String REMAINING_SHUFFLE_LABEL = "\nRemaining_Shuffle: ";
    private final static String REMAINING_HINTS_LABEL = "\nRemaining_Hints: ";
    private final static String FALL_DATA_LABEL = "\nFall_Data: ";
    private final static String MATCH_DATA_LABEL = "\nMatch_Data:\n";
    private final static String BOARD_LABEL = "Board:\n";

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

    private static String currentFilePath;
    public static String getCurrentFilePath() {
        return currentFilePath;
    }
      
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
        currentFilePath = filePath;

        try (BufferedWriter writer = new BufferedWriter (new FileWriter (filePath))) {

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

            scanner.next(); //Skip Label
            int board_Row_Size = scanner.nextInt();
            scanner.next(); //Skip Label
            int board_Col_Size = scanner.nextInt();
            scanner.next(); //Skip Label
            String mapTemplate = scanner.next();
            
            loadedGameData = new GameData(board_Row_Size, board_Col_Size, MapTemplate.valueOf(mapTemplate));
            System.out.println("BRS : " + loadedGameData.getBoard_Row_Size());
            System.out.println("BCS : " + loadedGameData.getBoard_Col_Size());

            scanner.next(); //Skip Label
            loadedGameData.setGameMode(GameMode.valueOf(scanner.next()));
            scanner.next(); //Skip Label
            loadedGameData.setAutomaticMode(scanner.nextBoolean());
            scanner.next(); //Skip Label
            loadedGameData.setSpecialMode(scanner.nextBoolean());
            scanner.next(); //Skip Label
            loadedGameData.setLevelIndex(scanner.nextInt());
            scanner.next(); //Skip Label
            loadedGameData.setScore(scanner.nextInt());
            scanner.next(); //Skip Label
            loadedGameData.setTargetScore(scanner.nextInt());
            scanner.next(); //Skip Label
            loadedGameData.setRemainingStep(scanner.nextInt());
            scanner.next(); //Skip Label
            loadedGameData.setRemainingShuffle(scanner.nextInt());
            scanner.next(); //Skip Label
            loadedGameData.setRemainingHints(scanner.nextInt());
            scanner.next(); //Skip Label
            
            System.out.println("Game-Mode : " + loadedGameData.getGameMode());
            System.out.println("Auto-Mode : " + loadedGameData.getAutomaticMode());
            System.out.println("Special-Mode : " + loadedGameData.getSpecialMode());
            System.out.println("Level-Index : " + loadedGameData.getLevelIndex());
            System.out.println("Level : " + loadedGameData.getLevel().toString());
            System.out.println("Score : " + loadedGameData.getScore());
            System.out.println("Target-Score : " + loadedGameData.getTargetScore());
            System.out.println("Remaining-Step : " + loadedGameData.getRemainingStep());
            System.out.println("Remaining-Shuffle : " + loadedGameData.getRemainingShuffle());
            System.out.println("Remaining-Hint : " + loadedGameData.getRemainingHints());

            //Fall Data
            for(int col = 0; col < loadedGameData.getBoard_Col_Size(); col++){
                int num_Of_Empty_Cell_At = Integer.parseInt(scanner.next());
                
                loadedGameData.updateFallData(num_Of_Empty_Cell_At, col);
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

            System.out.println("Board : \n" + loadedGameData.getBoard().txtBoard());
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