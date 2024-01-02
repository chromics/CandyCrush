package data;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

import model.Cell;
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
    private String saveName = "null";
    private int[] highest_Playable_Cell_At_Col;
    private List<MatchData> matchDatas;
    private int[] fallData; //Lowest Empty Cell at each column
    private BoardPoint[] hint;
    private Map<BoardPoint, Piece> newSpecialPieces;
    private Board board;
    private Level level;
    private int numOfLevel;
    private int levelIndex;
    private GameMode gameMode;
    private int board_Row_Size;
    private int board_Col_Size;
    private int score;
    private int targetScore;
    private int ice_Block_Destroyed;
    private int total_Ice_Block;
    private int remainingStep;
    private int remainingHints;
    private int remainingShuffle;
    private MapTemplate mapTemplate;
    private boolean automaticMode;
    private boolean specialMode;
    
    //-----------------------------------------------------------------------------------------------
    // Constructor & Initiator
    //-----------------------------------------------------------------------------------------------
    public GameData () {
        //Empty Constructor
    }

    public void init_Main_Data () {
        //Level Settings
        automaticMode = gameMode.getAutomaticMode();
        specialMode = gameMode.getSpecialMode();
        numOfLevel = gameMode.getLevelList().size();
        level = gameMode.getLevelList().get(levelIndex);
        levelIndex = level.getLevelIndex();
        board_Row_Size = level.getBoard_Row_Size();
        board_Col_Size = level.getBoard_Col_Size();
        targetScore = level.getTargetScore();
        remainingStep = level.getInitStep();
        remainingHints = level.getInitHints();
        remainingShuffle = level.getInitShuffle();
        mapTemplate = level.getMapTemplate();
        score = 0;
        ice_Block_Destroyed = 0;
        total_Ice_Block = 0;
    }
    public void init_Additional_Data () {
        //Initiate Board
        board = new Board(board_Row_Size, board_Col_Size, mapTemplate);

        update_Highest_Playable_Row_Data();

        /*
         * Only initiate the ice block if its a new game not a loaded one
         */
        if (specialMode && total_Ice_Block == 0) {
            board.initiate_Ice_Block(this);
        }

        //Additional Data
        newSpecialPieces = new HashMap<>();
        matchDatas = new ArrayList<>();
        fallData = new int[board_Col_Size];
        resetFallData();
    }
    //===============================================================================================

    
    //-----------------------------------------------------------------------------------------------
    // Getter
    //-----------------------------------------------------------------------------------------------
    public String getSaveName () { return saveName; }
    public List<MatchData> getMatchDatas () { return matchDatas; }
    public int[] getFallData () { return fallData; }
    public BoardPoint[] getHint () { return hint; }
    public Map<BoardPoint, Piece> getnewSpecialPieces () { return newSpecialPieces; }
    public Board getBoard () { return board; }
    public int getScore () { return score; }
    public int getBoard_Row_Size () { return board_Row_Size; }
    public int getBoard_Col_Size () { return board_Col_Size; }
    public int getTargetScore () { return targetScore; }
    public int getIceBlockDestroyed () { return ice_Block_Destroyed; }
    public int getTotalIceBlock () { return total_Ice_Block; }
    public int getRemainingStep () { return remainingStep; }
    public int getRemainingHints () { return remainingHints; }
    public int getRemainingShuffle () { return remainingShuffle; }
    public MapTemplate getMapTemplate () { return mapTemplate; }
    public boolean getAutomaticMode () { return automaticMode; }
    public boolean getSpecialMode () { return specialMode; }
    public Level getLevel () { return level; }
    public int getNumOfLevel () { return numOfLevel; }
    public int getLevelNum () { return (levelIndex + 1); }
    public int getLevelIndex () { return levelIndex; }
    public Level getNextLevel () { return gameMode.getNextLevel(level); }
    public GameMode getGameMode () { return gameMode; }
    public int get_Highest_Playable_Cell_At_Col (int col) { return highest_Playable_Cell_At_Col[col]; }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Setter
    //-----------------------------------------------------------------------------------------------
    public void setBoard_Row_Size (int board_Row_Size) {
        this.board_Row_Size = board_Row_Size;
    }
    public void setBoard_Col_Size (int board_Col_Size) {
        this.board_Col_Size = board_Col_Size;
    }
    public void setGameMode (GameMode gameMode) {
        System.out.println("Set Game Mode");
        this.gameMode = gameMode;
    }
    public void setMapTemplate (MapTemplate mapTemplate) {
        this.mapTemplate = mapTemplate;
    }
    public void setAutomaticMode (boolean automaticMode) {
        this.automaticMode = automaticMode;
    }
    public void setSpecialMode (boolean specialMode) {
        this.specialMode = specialMode;
    }
    public void setLevelIndex (int levelIndex) {
        System.out.println("Set Level Index");
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
    public void setIceBlockDestroyed (int ice_Block_Destroyed) {
        this.ice_Block_Destroyed = ice_Block_Destroyed;
    }
    public void setTotalIceBlock (int total_Ice_Block) {
        this.total_Ice_Block = total_Ice_Block;
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
    public void setSaveName (String saveName) {
        this.saveName = saveName;
    }
    //===============================================================================================
    

    //-----------------------------------------------------------------------------------------------
    // Update Data Methods
    //-----------------------------------------------------------------------------------------------
    public void decreaseRemainingStep () {
        this.remainingStep--;
    }
    public void updateScore (int scoreGained) {
        this.score += scoreGained;
    }
    public void increase_Ice_Block_Destroyed () {
        this.ice_Block_Destroyed++;
    }
    public void nextLevel () {
        level = gameMode.getNextLevel(level);
        levelIndex = level.getLevelIndex();
    }
    public void restartLevel () {
        //Do Nothing
    }
    public void update_Highest_Playable_Row_Data() {
        highest_Playable_Cell_At_Col = new int[board_Col_Size];

        Cell[][] grid = board.getGrid();

        for (int col = 0; col < board_Col_Size; col++) {
            for (int row = 0; row < board_Row_Size; row++) {
                if (grid[row][col].isPlayable()) {
                    highest_Playable_Cell_At_Col[col] = row;
                    break;
                }
            }
        }
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
    public void updateFallData (int lowest_Empty_Cell_At_Col, int col) {

        if (lowest_Empty_Cell_At_Col > fallData[col]) {
            fallData[col] = lowest_Empty_Cell_At_Col;
        }
    }
    public void increaseFallDataAt (int col) {
        fallData[col]++;
    }
    public void deductFallDataAt (int col) {
        fallData[col]--;
    }
    public void resetFallData () {
        Arrays.fill(fallData, -1);
    }
    public int getFallDataAtCol(int col){
        return fallData[col];
    }
    public boolean hasnotFall(){
        for(int data : fallData){
            if(data > -1){
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
        System.out.println("Save Name : " + saveName);
        System.out.println("Board Row Size : " + board_Row_Size);
        System.out.println("Board Col Size : " + board_Col_Size);
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
        System.out.println("\nBoard:\n" + board.txtBoard());
        System.out.println("\n");
    }
}
