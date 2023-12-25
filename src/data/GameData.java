package data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.Arrays;
import java.io.*;

import model.Board;
import model.BoardPoint;
import data.constant.MapTemplate;
import data.constant.Orientation;
import javafx.scene.control.Cell;
import data.constant.GameMode;
import data.constant.Level;

public class GameData implements Serializable{
    private List<MatchData> matchDatas;
    private List<BoardPoint> newSpecialPiecesPosition;
    private int[] fallData;
    private BoardPoint[] hint;
    private Board board;
    private Level level;
    private int levelIndex;
    private GameMode gameMode;
    private final int BOARD_ROW_SIZE;
    private final int BOARD_COL_SIZE;
    private int score;
    private int targetScore;
    private int remainingStep;
    private int remainingHints;
    private int remainingShuffle;
    private MapTemplate mapTemplate;
    private boolean automaticMode;
    private boolean specialMode;
    
    //-----------------------------------------------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------------------------------------------
    public GameData(GameMode gameMode, int levelIndex){
        //Level Settings
        this.gameMode = gameMode;
        this.automaticMode = gameMode.getAutomaticMode();
        this.specialMode = gameMode.getSpecialMode();
        this.level = gameMode.getLevelList().get(levelIndex);
        this.levelIndex = level.getLevelIndex();
        this.BOARD_ROW_SIZE = level.getBoard_Row_Size();
        this.BOARD_COL_SIZE = level.getBoard_Col_Size();
        this.targetScore = level.getTargetScore();
        this.remainingStep = level.getInitStep();
        this.remainingHints = level.getInitHints();
        this.remainingShuffle = level.getInitShuffle();
        this.mapTemplate = level.getMapTemplate();
        score = 0;

        //Initiate Board
        board = new Board(BOARD_ROW_SIZE, BOARD_COL_SIZE, mapTemplate);

        //Additional Data
        matchDatas = new ArrayList<>();
        fallData = new int[BOARD_COL_SIZE];
        resetFallData();

        System.out.println("\nInitiate Game Data : ");
        System.out.println("GameMode : " + gameMode);
        System.out.println("Automatic Mode : " + automaticMode);
        System.out.println("Special Mode : " + specialMode);
        System.out.println("Level : " + level.toString());
        System.out.println("Board Row Size : " + BOARD_ROW_SIZE);
        System.out.println("Board Col Size : " + BOARD_COL_SIZE);
        System.out.println("Target Score : " + targetScore);
        System.out.println("Remaining Step : " + remainingStep);
        System.out.println("Remaining Hints : " + remainingHints);
        System.out.println("Remaining Shuffle : " + remainingShuffle);
        System.out.println("Map Template : " + mapTemplate.toString());
        System.out.println();
    }
    //===============================================================================================

    
    //-----------------------------------------------------------------------------------------------
    // Getter
    //-----------------------------------------------------------------------------------------------
    public List<MatchData> getMatchDatas() { return matchDatas; }
    public BoardPoint[] getHint() { return hint; }
    public List<BoardPoint> getNewSpecialPiecesPosition() { return newSpecialPiecesPosition; }
    public Board getBoard() { return board; }
    public int getScore() { return score; }
    public int getBoard_Row_Size() { return BOARD_ROW_SIZE; }
    public int getBoard_Col_Size() { return BOARD_COL_SIZE; }
    public int getTargetScore() { return targetScore; }
    public int getRemainingStep() { return remainingStep; }
    public int getRemainingHints() { return remainingHints; }
    public int getRemainingShuffle() { return remainingShuffle; }
    public MapTemplate getMapTemplate() { return mapTemplate; }
    public boolean getAutomaticMode() { return automaticMode; }
    public boolean getSpecialMode() { return specialMode; }
    public int getLevel() { return (levelIndex + 1); }
    public int getLevelIndex() { return levelIndex; }
    public Level getNextLevel() { return gameMode.getNextLevel(level); }
    public GameMode getGameMode() { return gameMode; }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Setter
    //-----------------------------------------------------------------------------------------------
    public void setAutomaticMode(boolean automaticMode){
        this.automaticMode = automaticMode;
    }
    public void setSpecialMode(boolean specialMode){
        this.specialMode = specialMode;
    }
    public void setRemainingShuffle(int remainingShuffle){
        this.remainingShuffle = remainingShuffle;
    }
    public void setRemainingStep(int remainingStep){
        this.remainingStep = remainingStep;
    }
    public void setScore(int score){
        this.score = score;
    }
    //===============================================================================================
    

    //-----------------------------------------------------------------------------------------------
    // Update Data Methods
    //-----------------------------------------------------------------------------------------------
    public void decreaseStepLeft(){
        this.remainingStep--;
    }
    public void updateScore(int scoreGained){
        this.score += scoreGained;
    }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Special Mode
    //-----------------------------------------------------------------------------------------------
    // public void saveNewSpecialPiecesPosition (BoardPoint)
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Hint Data
    //-----------------------------------------------------------------------------------------------
    public void saveHint (BoardPoint point1, BoardPoint point2) {
        hint = new BoardPoint[]{point1, point2};
    }
    public void resetHint(){
        hint = null;
    }
    public boolean anyHint(){
        return (hint != null);
    }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Match Data
    //-----------------------------------------------------------------------------------------------
    public MatchData popMatchData(){
        if(matchDatas.size() == 0){
            return null;
        }
        
        MatchData lastElement = matchDatas.remove(matchDatas.size() - 1);
        return lastElement;
    }
    
    public void saveMatchData(BoardPoint point, int length, Orientation orientation){
        matchDatas.add(new MatchData(point, length, orientation));
    }
    
    public int getScoreGained(){
        int scoreGained = 0;
        for(MatchData matchData : matchDatas){
            scoreGained += matchData.getLength() * 10;
        }
        return scoreGained;
    }
    
    public boolean anyMatch(){
        return (matchDatas.size() != 0);
    }

    public void resetMatchData(){
        matchDatas = new ArrayList<>();
    }
    
    public String stringMatchData(){
        if(! anyMatch()){
            return "No Match";
        }
        else{
            String res = "";
            for(MatchData matchData : matchDatas){
                res += matchData + "\n";
            }
            return res;
        }
    }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Fall Data
    //-----------------------------------------------------------------------------------------------
    public void updateFallData(BoardPoint point){
        int row = point.getRow();
        int col = point.getCol();
        
        if(fallData[col] < row){
            fallData[col] = row;
        }
    }
    public void resetFallData(){
        Arrays.fill(fallData, -1);
    }
    public int getFallDataAtCol(int col){
        return fallData[col];
    }
    public boolean hasnotFall(){
        for(int data : fallData){
            if(data != -1){
                return true;
            }
        }
        return false;
    }
    public String stringFallData(){
        String res = "[ ";
        for(int num : fallData){
            res += num + ", ";
        }
        res += "]\n";
        return res;
    }
    //===============================================================================================
}
