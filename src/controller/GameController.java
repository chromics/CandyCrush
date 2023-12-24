package controller;

import mechanism.CheckMatch;
import mechanism.Remove;
import mechanism.Fall;
import model.Board;
import model.BoardPoint;
import data.GameData;
import data.constant.Level;
import data.constant.GameMode;
import view.viewController.BoardSceneController;
import view.viewController.UtilView;

public class GameController {
    private BoardSceneController view;
    private Board board;
    private GameData gameData; 
    private GameMode gameMode;
    
    //-----------------------------------------------------------------------------------------------
    // Constructor & Initiator
    //-----------------------------------------------------------------------------------------------
    public GameController(GameMode gameMode, int levelIndex, BoardSceneController view){
        initiate_Game_Data(gameMode, levelIndex);
        view.set_Game_Info(this);
        
        this.view = view;
        this.board = gameData.getBoard();
        initBoard();
        System.out.println("Initiate Game Controller : " + gameData.getGameMode().toString());
    }

    public GameController(BoardSceneController view, GameData gameData){
        this.view = view;
        this.gameData = gameData;
        this.board = gameData.getBoard();
        System.out.println("Load Game Controller : " + gameData.getGameMode().toString());
    }
    
    public void initiate_Game_Data(GameMode gameMode, int levelIndex){
        gameData = new GameData(gameMode, levelIndex);
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
        
        if(! gameData.anyMatch()){
            board.swapPiece(point1, point2);
            view.swapImage(point1, point2);

            UtilView.generateAlert("No Effect", "No Match Found");
        }
        else{
            gameData.decreaseStepLeft();
            view.deductMovesLeft();
            System.out.println("Moves Left : " + gameData.getRemainingStep());

            removeMatches();
            
            if(gameData.getAutomaticMode()){
                automaticModeAction();
            }
        }
    }
    public void scanMatches(){
        CheckMatch.scan_and_save_matches(board, gameData);
        System.out.println(gameData.stringMatchData());
    }
    public void removeMatches(){
        int scoreGained = gameData.getScoreGained();
        
        Remove.removePieces(board, gameData, view);
        
        gameData.updateScore(scoreGained);
        view.addScore();
        System.out.println("\nRemove Done");
        System.out.println("Score : " + gameData.getScore());
        System.out.println();
    }
    public void fall(){
        Fall.fall(board, gameData, view);
        System.out.println("\nFall Done\n");
        scanMatches();
    }
    //===============================================================================================

    
    //-----------------------------------------------------------------------------------------------
    // Automatic Mode
    //-----------------------------------------------------------------------------------------------
    public void changeAutomaticMode(){
        gameData.setAutomaticMode(gameData.getAutomaticMode() == false); //Turn On if Off, Turn Off if On
        System.out.println("Set Automatic Mode : " + gameData.getAutomaticMode());
    }
    public void automaticModeAction(){
        fall();
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
        int nextLevelIndex = gameData.getLevelIndex() + 1;
        initiate_Game_Data(gameMode, nextLevelIndex);
    }
    //===============================================================================================
    
    
    public GameData getGameData(){
        return gameData;
    }
}
