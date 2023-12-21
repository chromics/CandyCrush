package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

import model.Board;
import model.BoardPoint;
import data.constant.MapTemplate;
import data.constant.Orientation;
import data.constant.Level;

public class GameData implements Serializable{
    private ArrayList<MatchData> matchDatas;
    private int[] fallData;
    private Board board;
    private Level level;
    private int levelIndex;
    private final int BOARD_ROW_SIZE;
    private final int BOARD_COL_SIZE;
    private int score;
    private int targetScore;
    private int stepLeft;
    private int hintsLeft;
    private int shuffleLeft;
    private MapTemplate mapTemplate;
    private boolean automaticMode;
    private boolean specialMode;
    
    //-----------------------------------------------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------------------------------------------
    public GameData(Level level, boolean automaticMode, boolean specialMode){
        //Level Settings
        this.automaticMode = automaticMode;
        this.specialMode = specialMode;
        this.level = level;
        this.levelIndex = level.getLevelIndex();
        this.BOARD_ROW_SIZE = level.getBoard_Row_Size();
        this.BOARD_COL_SIZE = level.getBoard_Col_Size();
        this.targetScore = level.getTargetScore();
        this.stepLeft = level.getInitStep();
        this.hintsLeft = level.getInitHints();
        this.shuffleLeft = level.getInitShuffle();
        this.mapTemplate = level.getMapTemplate();
        score = 0;

        //Initiate Board
        board = new Board(BOARD_ROW_SIZE, BOARD_COL_SIZE, mapTemplate);

        //Additional Data
        matchDatas = new ArrayList<>();
        fallData = new int[BOARD_COL_SIZE];
        resetFallData();
    }
    //===============================================================================================

    
    //-----------------------------------------------------------------------------------------------
    // Getter
    //-----------------------------------------------------------------------------------------------
    public ArrayList<MatchData> getMatchDatas() { return matchDatas; }
    public Board getBoard() { return board; }
    public int getScore() { return score; }
    public int getBoard_Row_Size() { return BOARD_ROW_SIZE; }
    public int getBoard_Col_Size() { return BOARD_COL_SIZE; }
    public int getTargetScore() { return targetScore; }
    public int getStepLeft() { return stepLeft; }
    public int getHintsLeft() { return hintsLeft; }
    public int getShuffleLeft() { return shuffleLeft; }
    public MapTemplate getMapTemplate() { return mapTemplate; }
    public boolean getAutomaticMode() { return automaticMode; }
    public boolean getSpecialMode() { return specialMode; }
    public Level getLevel() { return level; }
    public int getLevelIndex() { return levelIndex; }
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
    public void setShuffleLeft(int shuffleLeft){
        this.shuffleLeft = shuffleLeft;
    }
    public void setStepLeft(int stepLeft){
        this.stepLeft = stepLeft;
    }
    public void setScore(int score){
        this.score = score;
    }
    //===============================================================================================
    

    //-----------------------------------------------------------------------------------------------
    // Update Data Methods
    //-----------------------------------------------------------------------------------------------
    public void decreaseStepLeft(){
        this.stepLeft--;
    }
    public void updateScore(int scoreGained){
        this.score += scoreGained;
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
