package controller;

import java.util.ArrayList;

import mechanism.CheckMatch;
import mechanism.Remove;
import mechanism.Fall;
import model.Board;
import model.BoardPoint;
import data.GameData;
import data.MatchData;
import data.constant.Level;
import data.constant.Orientation;
import view.viewController.BoardSceneController;

public class GameController {
    private BoardSceneController view;
    private Board board;
    private GameData gameData; 
    
    //-----------------------------------------------------------------------------------------------
    // Constructor & Initiator
    //-----------------------------------------------------------------------------------------------
    public GameController(BoardSceneController view){
        initiate_Game_Data(Level.LEVEL_1, false, false);
        //!Still dont know how to get specialMode input
        view.set_Game_Info(this);

        this.view = view;
        this.board = gameData.getBoard();
        initBoard();
    }

    public GameController(BoardSceneController view, GameData gameData){
        this.view = view;
        this.gameData = gameData;
        this.board = gameData.getBoard();
    }
    
    public void initiate_Game_Data(Level level, boolean automaticMode, boolean specialMode){
        gameData = new GameData(level, automaticMode, specialMode);
    }
    
    public void initBoard(){
        do{
            board.initBoard();
            scanMatches();
        }while(gameData.anyMatch());
        gameData.resetMatchData();

        view.initiateBoard(gameData.getBoard().getGrid());
    }
    //===============================================================================================

    
    //-----------------------------------------------------------------------------------------------
    // Basic Game Mechanism
    //-----------------------------------------------------------------------------------------------
    public void swapPieceOnBoard(BoardPoint point1, BoardPoint point2){
        board.swapPiece(point1, point2);
        view.swapImage(point1, point2);
        scanMatches();
        
        System.out.println(gameData.stringMatchData());
        if(! gameData.anyMatch()){
            board.swapPiece(point1, point2);
            view.swapImage(point1, point2);

            view.generateAlert("No Effect", "No Match Found");
        }
        else{
            gameData.decreaseStepLeft();
            view.deductMovesLeft();
            System.out.println("Moves Left : " + gameData.getStepLeft());

            removeMatches();
            
            if(gameData.getAutomaticMode()){
                automaticModeAction();
            }
        }
    }
    public void scanMatches(){
        CheckMatch.scan_and_save_matches(board, gameData);
    }
    public void removeMatches(){
        int scoreGained = gameData.getScoreGained();
        
        Remove.removePieces(board, gameData, view);
        
        gameData.updateScore(scoreGained);
        view.addScore();
        System.out.println("Score : " + gameData.getScore());
    }
    public void fall(){
        Fall.fall(board, gameData, view);
        scanMatches();
    }
    //===============================================================================================

    
    //-----------------------------------------------------------------------------------------------
    // Automatic Mode
    //-----------------------------------------------------------------------------------------------
    public void changeAutomaticMode(){
        gameData.setAutomaticMode(gameData.getAutomaticMode() & false); //Turn On if Off, Turn Off if On
        System.out.println("Set Automatic Mode : " + gameData.getAutomaticMode());
    }
    public void automaticModeAction(){
        while(gameData.anyMatch()){
            removeMatches();
            fall();
        }
    }
    //===============================================================================================

    
    //-----------------------------------------------------------------------------------------------
    // Special Mode
    //-----------------------------------------------------------------------------------------------
    public void generate_Special_Pieces(){
        // ArrayList<MatchData> matchDatas = gameData.getMatchDatas();
        
        // for(MatchData matchData : matchDatas){
            //     if(matchData.getLength() >= 5){
                
                //     }
                // }
    }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Win Methods
    //-----------------------------------------------------------------------------------------------
    public void checkWin(){
        if(gameData.getScore() >= gameData.getTargetScore()){
            win();
        }
    }
    public void win(){
        // try (view.winAlert()){

        // }  catch (Exception e) {
        //     e.printStackTrace();
        // }
    }
    public void nextLevel(){
        int thisLevelIndex = gameData.getLevelIndex();
        Level[] levelList = Level.values();
        Level nextLevel = levelList[thisLevelIndex + 1];
        initiate_Game_Data(nextLevel, gameData.getAutomaticMode(), gameData.getSpecialMode());
    }
    //===============================================================================================
    
    
    public GameData getGameData(){
        return gameData;
    }
}
