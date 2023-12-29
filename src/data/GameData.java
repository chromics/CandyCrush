package data;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

import model.Board;
import model.piece.HorizontalBomb;
import model.piece.Piece;
import model.piece.VerticalBomb;
import model.BoardPoint;
import data.constant.MapTemplate;
import data.constant.Orientation;
import data.constant.GameMode;
import data.constant.Level;

public class GameData implements Serializable{
    private static Duration animation_Duration = Duration.ofMillis(500);
    private List<MatchData> matchDatas;
    private int[] fallData; //Number of Empty Cell per Column
    private BoardPoint[] hint;
    private Map<BoardPoint, Piece> newSpecialPieces;
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

        newSpecialPieces = new HashMap<>();

        printGameData();
    }
    public GameData (int board_Row_Size, int board_Col_Size, MapTemplate mapTemplate){

        this.BOARD_ROW_SIZE = board_Row_Size;
        this.BOARD_COL_SIZE = board_Col_Size;
        this.mapTemplate = mapTemplate;

        this.board = new Board(board_Row_Size, board_Col_Size, mapTemplate);
        this.board.initGrid();

        matchDatas = new ArrayList<>();
        fallData = new int[BOARD_COL_SIZE];
        resetFallData();

        newSpecialPieces = new HashMap<>();
    }
    //===============================================================================================

    
    //-----------------------------------------------------------------------------------------------
    // Getter
    //-----------------------------------------------------------------------------------------------
    public List<MatchData> getMatchDatas() { return matchDatas; }
    public int[] getFallData() { return fallData; }
    public BoardPoint[] getHint() { return hint; }
    public Map<BoardPoint, Piece> getnewSpecialPieces() { return newSpecialPieces; }
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
    public Level getLevel() { return level; }
    public int getLevelNum() { return (levelIndex + 1); }
    public int getLevelIndex() { return levelIndex; }
    public Level getNextLevel() { return gameMode.getNextLevel(level); }
    public GameMode getGameMode() { return gameMode; }
    public Duration get_Animation_Duration() { return animation_Duration; }
    public int get_Animation_Duration_In_Millis() { return (int)animation_Duration.toMillis(); }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Setter
    //-----------------------------------------------------------------------------------------------
    public void setGameMode (GameMode gameMode) {
        this.gameMode = gameMode;
    }
    public void setAutomaticMode (boolean automaticMode) {
        this.automaticMode = automaticMode;
    }
    public void setSpecialMode (boolean specialMode) {
        this.specialMode = specialMode;
    }
    public void setLevelIndex (int levelIndex) {
        this.levelIndex = levelIndex;
        setLevel(gameMode.getLevelList().get(levelIndex));
    }
    public void setLevel (Level level) {
        this.level = level;
    }
    public void setScore (int score) {
        this.score = score;
    }
    public void setTargetScore (int targetScore) {
        this.targetScore = targetScore;
    }
    public void setRemainingShuffle (int remainingShuffle) {
        this.remainingShuffle = remainingShuffle;
    }
    public void setRemainingStep (int remainingStep) {
        this.remainingStep = remainingStep;
    }
    public void setRemainingHints (int remainingHints) {
        this.remainingHints = remainingHints;
    }
    public void setAnimationDuration (int milliseconds) {
        animation_Duration = Duration.ofMillis(milliseconds);
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
    public void savenewSpecialPieces (BoardPoint point, Orientation orientation){

        if (orientation == Orientation.UP || orientation == Orientation.DOWN) {
            newSpecialPieces.put(point, new VerticalBomb());
        }
        else if (orientation == Orientation.LEFT || orientation == Orientation.RIGHT) {
            newSpecialPieces.put(point, new HorizontalBomb());
        }
    }
    public void resetnewSpecialPieces () {
        newSpecialPieces.clear();
    }
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
        return (hint != null && remainingHints != 0);
        /*
         * Infinity Hints : (<= -10), Initial Value = -10 
         * Finite Hints : (> 0), Initial Value customized
         */
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

    public String txtMatchDatas () {
        StringBuilder res = new StringBuilder();

        if (anyMatch()) {
            for(MatchData matchData : matchDatas){
                res.append(matchData.txtMatchData() + "\n");
            }
        }

        return res.toString();
    }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Fall Data (Number of Empty Cell per Column)
    //-----------------------------------------------------------------------------------------------
    public void updateFallData (int num_Of_Empty_Cell, int col) {
        fallData[col] = num_Of_Empty_Cell;
    }
    public void increaseFallDataAt (int col) {
        fallData[col]++;
    }
    public void deductFallDataAt (int col) {
        fallData[col]--;
    }
    public void resetFallData () {
        Arrays.fill(fallData, 0);
    }
    public int getFallDataAtCol(int col){
        return fallData[col];
    }
    public boolean hasnotFall(){
        for(int data : fallData){
            if(data > 0){
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
    public String txtFallData () {
        StringBuilder res = new StringBuilder();
        
        for(int num : fallData){
            res.append(num + " ");
        }
        
        return res.toString();
    }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Shuffle
    //-----------------------------------------------------------------------------------------------
    public boolean anyShuffle () {
        return (remainingShuffle != 0);
    }
    //===============================================================================================
    
    public void printGameData(){
        System.out.println("\nGame Data : ");
        System.out.println("Board Row Size : " + BOARD_ROW_SIZE);
        System.out.println("Board Col Size : " + BOARD_COL_SIZE);
        System.out.println("Map Template : " + mapTemplate.toString());
        System.out.println("GameMode : " + gameMode);
        System.out.println("Automatic Mode : " + automaticMode);
        System.out.println("Special Mode : " + specialMode);
        System.out.println("Level : " + level.toString());
        System.out.println("Score : " + score);
        System.out.println("Target Score : " + targetScore);
        System.out.println("Remaining Step : " + remainingStep);
        System.out.println("Remaining Shuffle : " + remainingShuffle);
        System.out.println("Remaining Hints : " + remainingHints);
        System.out.println("Fall Data : " + txtFallData());
        System.out.print("Match Data : " + stringMatchData());
        System.out.println("Board:\n" + board.txtBoard());
        System.out.println("\n");
    }
}
