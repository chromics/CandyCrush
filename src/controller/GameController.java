package controller;

import java.util.Map;
import java.util.List;
import javafx.application.Platform;

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
import data.constant.Constant;
import view.viewController.BoardSceneController;

public class GameController extends Thread {
    private BoardSceneController boardSceneController;
    private Board board;
    private GameData gameData;
    private boolean specialMode;
    private int extra_Duration = Constant.EXTRA_DURATION.getNum();
    private int animation_Duration = Constant.ANIMATION_DURATION.getNum();
    private int automatic_Break_Duration = Constant.AUTOMATIC_BREAK_DURATION.getNum();
    
    //-----------------------------------------------------------------------------------------------
    // Constructor & Game Initiator
    //-----------------------------------------------------------------------------------------------
    public GameController () {
        gameData = new GameData();
    }
        
    // Load Game
    public void setGameData (GameData gameData) {
        this.gameData = gameData;
        this.board = gameData.getBoard();
        
        boardSceneController.set_Game_Info(this);
        boardSceneController.initiateBoard();
        
        specialMode = gameData.getSpecialMode();

        if (specialMode && gameData.anyMatch()) {
            generate_Special_Pieces();
        }
        

        System.out.println("Load Game Controller : " + gameData.getGameMode().toString());
    }

    // New Game
    public void setBoardSceneController (BoardSceneController boardSceneController) {
        System.out.println("Set Board Scene Controller");
        this.boardSceneController = boardSceneController;
    }
    public void initNewGame () {
        System.out.println("Init New Game");

        gameData.init_Main_Data();
        gameData.init_Additional_Data();
        
        boardSceneController.set_Game_Info(this);

        board = gameData.getBoard();
        initBoard();

        boardSceneController.initiateBoard();

        specialMode = gameData.getSpecialMode();
    }

    //Board Initiator
    public void initBoard () {
        do{
            board.initBoard();
            scanMatches();
        }while(gameData.anyMatch());
        gameData.resetMatchData();
        HintFinder.findHint(gameData);

        System.out.println("\nInitiated Board : \n" + board.txtBoard());
    }
    //===============================================================================================

    
    //-----------------------------------------------------------------------------------------------
    // Basic Game Mechanism
    //-----------------------------------------------------------------------------------------------
    public void swapPieceOnBoard(BoardPoint point1, BoardPoint point2){
        System.out.println("\nSwap Action Start");
        Platform.runLater(() -> boardSceneController.deactivate_All_Buttons());
        Platform.runLater(() -> boardSceneController.resetSelectedPoint());

        board.swapPiece(point1, point2);
        Platform.runLater(() -> boardSceneController.swapImage(point1, point2));
        scanMatches();

        Util.threadSleep(extra_Duration + animation_Duration);
        System.out.println("\nSwap Action Finish");
        
        if(! gameData.anyMatch()){
            
            // boardSceneController.resetSelectedPoint();
            System.out.println("\nSwap Action Start");
            board.swapPiece(point1, point2);
            Platform.runLater(() -> boardSceneController.swapImage(point1, point2));
            Util.threadSleep(extra_Duration + animation_Duration);
            System.out.println("\nSwap Action Finish");

            // UtilboardSceneController.generateErrorAlert("No Effect", "No Match Found");
            Platform.runLater(() -> boardSceneController.catDialog("longBox","No Match Found", 505, 140));
            Platform.runLater(() -> boardSceneController.setCatTimer("longBox",2000));
        }
        else{
            gameData.resetHint();
            gameData.decreaseRemainingStep();
            Platform.runLater(() -> boardSceneController.deductMovesLeft());

            System.out.println("Moves Left : " + gameData.getRemainingStep());
            
            if(gameData.getAutomaticMode()){
                automaticModeAction();
            }
        }
        Platform.runLater(() -> boardSceneController.activate_All_Buttons());
    }
    public void scanMatches(){
        CheckMatch.scan_and_save_matches(board, gameData);

        if (specialMode) {
            generate_Special_Pieces();
        }

        System.out.println(gameData.stringMatchData());
    }
    public void removeMatches(){
        int scoreGained = gameData.getScoreGained();
        
        Remove.removePieces(board, gameData, boardSceneController);
        
        gameData.updateScore(scoreGained);
        Platform.runLater(() -> boardSceneController.addScore());
        
        System.out.println("\nRemove Done");
        System.out.println("Score : " + gameData.getScore());
        System.out.println();
        
        if (specialMode) {
            Platform.runLater(() -> boardSceneController.update_Ice_Block_Destroyed());
            System.out.println("Placing Special Pieces");
            place_Special_Pieces();
        }
    }
    public void fall(){
        Platform.runLater(() -> boardSceneController.deactivate_All_Buttons());

        System.out.println("Fall Start");
        Fall.fall(board, gameData, boardSceneController);

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
                    // UtilboardSceneController.generateErrorAlert("No Match Can be Formed Anymore", "Please use shuffle");
                    Platform.runLater(() -> boardSceneController.catDialog("longBox","Please use Shuffle !", 505, 140));
                    Platform.runLater(() -> boardSceneController.setCatTimer("longBox",5000));
                }
                else{
                    Platform.runLater(() -> {
                        try {
                            boardSceneController.catDialog("longBox","Dead End, No Shuffle Left !", 505, 140);
                            boardSceneController.setCatTimer("longBox",5000);
                            Util.threadSleep(2000);
                            boardSceneController.lose();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    
                }
            }
        }

        Platform.runLater(() -> boardSceneController.activate_All_Buttons());
    }
    //===============================================================================================

    
    //-----------------------------------------------------------------------------------------------
    // Automatic Mode
    //-----------------------------------------------------------------------------------------------
    public void changeAutomaticMode(){
        gameData.setAutomaticMode(gameData.getAutomaticMode() == false); //Turn On if Off, Turn Off if On
        
        if (gameData.getAutomaticMode()) {
            automaticModeAction();
        }

        System.out.println("Set Automatic Mode : " + gameData.getAutomaticMode());
    }
    public void automaticModeAction(){
        if (gameData.hasnotFall()) {
            fall();
        }
        while(gameData.anyMatch()){
            removeMatches();
            fall();
            Util.threadSleep(automatic_Break_Duration);
        }
        Platform.runLater(() -> boardSceneController.activate_All_Buttons());
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
                Platform.runLater(() -> boardSceneController.setPieceImageAt(newSpecialPiece.getName(), point));
                Util.threadSleep(extra_Duration);

                System.out.println("New " + newSpecialPieces.get(point) + " at " + point);
                System.out.println("Board at " + point + " : " + board.getPieceAt(point).getName());

                System.out.println("\nBoard :\n" + board.txtBoard());
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
        Platform.runLater(() -> {
            try {

                if (specialMode 
                    && gameData.getScore() >= gameData.getTargetScore() 
                    && gameData.getIceBlockDestroyed() == gameData.getTotalIceBlock()) {

                    if (gameData.getLevelNum() == gameData.getNumOfLevel()) {
                        boardSceneController.catDialog("longBox","You Win !", 505, 140);
                        boardSceneController.setCatTimer("longBox",5000);
                        Util.threadSleep(1000);
                        boardSceneController.specialFinalWin();
                    }
                    else {
                        boardSceneController.catDialog("longBox","You Win !", 505, 140);
                        boardSceneController.setCatTimer("longBox",5000);
                        Util.threadSleep(1000);
                        boardSceneController.specialWin();
                    }
                }
                if (! specialMode && gameData.getScore() >= gameData.getTargetScore()) {
                    
                    if (gameData.getLevelNum() == gameData.getNumOfLevel()) {
                        boardSceneController.catDialog("longBox","You Win !", 505, 140);
                        boardSceneController.setCatTimer("longBox",5000);
                        Util.threadSleep(1000);
                        boardSceneController.finalWin();
                    }
                    else {
                        boardSceneController.catDialog("longBox","You Win !", 505, 140);
                        boardSceneController.setCatTimer("longBox",5000);
                        Util.threadSleep(1000);
                        boardSceneController.win();
                    }
                }
                else if (gameData.getRemainingStep() <= 0) {
                    
                    if (specialMode) {
                        boardSceneController.catDialog("longBox","No Remaining Step T_T", 505, 140);
                        boardSceneController.setCatTimer("longBox",5000);
                        Util.threadSleep(2000);
                        boardSceneController.specialLose();
                    }
                    else {
                        boardSceneController.catDialog("longBox","No Remaining Step T_T", 505, 140);
                        boardSceneController.setCatTimer("longBox",5000);
                        Util.threadSleep(2000);
                        boardSceneController.lose();
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void nextLevel (BoardSceneController boardSceneController) {

        gameData.nextLevel();
        
        this.board = gameData.getBoard();
        initBoard();
        
        this.boardSceneController = boardSceneController;
        Platform.runLater(() -> boardSceneController.set_Game_Info(this));
    }
    public void restartLevel (BoardSceneController boardSceneController) {
        
        gameData.restartLevel();
        
        this.board = gameData.getBoard();
        initBoard();

        this.boardSceneController = boardSceneController;
        Platform.runLater(() -> boardSceneController.set_Game_Info(this));
    }
    //===============================================================================================
    
    
    public GameData getGameData(){
        return gameData;
    }
}
