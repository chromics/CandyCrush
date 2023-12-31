package controller;

import java.util.Map;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mechanism.CheckMatch;
import mechanism.Remove;
import mechanism.Util;
import mechanism.Fall;
import mechanism.HintFinder;
import model.Board;
import model.piece.Piece;
import model.BoardPoint;
import data.GameData;
import data.MatchData;
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
        this.gameMode = gameMode;
        this.board = gameData.getBoard();
        initBoard();

        // for(int row = 0; row < gameData.getBoard_Row_Size(); row++){
        //     for (int col = 0; col < gameData.getBoard_Col_Size(); col++){
        //         view.setIceBlockAt(new BoardPoint(row, col));
        //     }
        // }
        // view.removeIceBlockAt(new BoardPoint(1, 1));
        System.out.println("Initiate Game Controller : " + gameData.getGameMode().toString());
    }

    public GameController(BoardSceneController view, GameData gameData){
        this.view = view;
        this.gameData = gameData;
        this.board = gameData.getBoard();
        
        view.set_Game_Info(this);
        view.initiateBoard();

        if (gameData.getSpecialMode() && gameData.anyMatch()) {
            generate_Special_Pieces();
        }

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
        HintFinder.findHint(gameData);
        view.initiateBoard();
    }
    //===============================================================================================

    
    //-----------------------------------------------------------------------------------------------
    // Basic Game Mechanism
    //-----------------------------------------------------------------------------------------------
    public void swapPieceOnBoard(BoardPoint point1, BoardPoint point2){
        view.resetSelectedPoint();

        board.swapPiece(point1, point2);
        view.swapImage(point1, point2);
        scanMatches();

        // Util.pauseExecution(1000);
        
        if(! gameData.anyMatch()){
            // view.resetSelectedPoint();
            board.swapPiece(point1, point2);
            view.swapImage(point1, point2);
        }
        else{
            gameData.resetHint();
            gameData.decreaseStepLeft();
            view.deductMovesLeft();
            System.out.println("Moves Left : " + gameData.getRemainingStep());
            
            if(gameData.getAutomaticMode()){
                automaticModeAction();
            }
        }
    }
    public void scanMatches(){
        CheckMatch.scan_and_save_matches(board, gameData);

        if (gameData.getSpecialMode()) {
            generate_Special_Pieces();
        }

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

        if (gameData.getSpecialMode()) {
            System.out.println("Placing Special Pieces");
            place_Special_Pieces();
        }
    }
    public void fall(){
        Fall.fall(board, gameData, view);
        System.out.println("\nFall Done\n");
        scanMatches();

        if (! gameData.anyMatch()) {
            try{
                checkWin();
            } catch (Exception e) {
                e.printStackTrace();
            }
            HintFinder.findHint(gameData);

            if(! gameData.anyHint()){
                if(gameData.anyShuffle()){
                    UtilView.generateErrorAlert("No Match Can be Formed Anymore", "Please use shuffle");
                }
                else{
                    try{
                        view.lose();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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
        System.out.println("Generate Special Piece If Condition Met");
        List<MatchData> matchDatas = gameData.getMatchDatas();
        
        for (MatchData matchData : matchDatas) {

            if (matchData.getLength() >= 4) {
                System.out.println("New Special Piece : " + matchData.getPoint() + ", " + matchData.getOrientation().toString());
                gameData.savenewSpecialPieces(matchData.getPoint(), matchData.getOrientation());
            }
        }
    }
    public void place_Special_Pieces(){
        Map<BoardPoint, Piece> newSpecialPieces = gameData.getnewSpecialPieces();

        if (newSpecialPieces != null) {

            for (BoardPoint point : newSpecialPieces.keySet()) {
                Piece newSpecialPiece = newSpecialPieces.get(point);
                board.setPiece(point, newSpecialPiece);
                view.setPieceImageAt(newSpecialPiece.getImagePath(), point);

                gameData.deductFallDataAt(point.getCol());
                System.out.println("New " + newSpecialPieces.get(point) + " at " + point);
                System.out.println("Board at " + point + " : " + board.getPieceAt(point).getName());
                System.out.println("Board at " + point + " : " + board.getPieceAt(point).getImagePath());
            }

            gameData.resetnewSpecialPieces();
        }
        else {
            System.out.println("No Special Piece");
        }
    }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Win Methods
    //-----------------------------------------------------------------------------------------------
    public void checkWin() throws Exception {
        if(gameData.getScore() >= gameData.getTargetScore()){
            view.win();
        }
        else if(gameData.getRemainingStep() <= 0){
            view.lose();
        }
    }
    public void nextLevel (BoardSceneController view) {
        this.view = view;

        int nextLevelIndex = gameData.getLevelIndex() + 1;
        initiate_Game_Data(gameMode, nextLevelIndex);
        view.set_Game_Info(this);

        this.board = gameData.getBoard();
        initBoard();
    }
    public void restartLevel (BoardSceneController view) {
        this.view = view;

        int currentLevelIndex = gameData.getLevelIndex();
        initiate_Game_Data(gameMode, currentLevelIndex);
        view.set_Game_Info(this);

        this.board = gameData.getBoard();
        initBoard();
    }
    //===============================================================================================
    
    
    public GameData getGameData(){
        return gameData;
    }
}
