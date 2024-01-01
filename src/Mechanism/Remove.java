package mechanism;

import javafx.application.Platform;

import model.Board;
import model.BoardPoint;
import view.viewController.BoardSceneController;
import data.GameData;
import data.MatchData;
import data.constant.Constant;
import data.constant.Orientation;

public class Remove {
    private static int remove_Break_Duration = Constant.REMOVE_BREAK_DURATION.getNum();

    public static void removePieces(Board board, GameData gameData, BoardSceneController boardSceneController){
        MatchData to_be_removed = gameData.popMatchData();
        boolean specialMode = gameData.getSpecialMode();

        while(to_be_removed != null){
            
            int matchLength = to_be_removed.getLength();
            BoardPoint currentPoint = to_be_removed.getPoint();
            Orientation orientation = to_be_removed.getOrientation();

            for(; matchLength > 0; matchLength--){
                BoardPoint finalCurrentPoint = currentPoint;
                
                if(specialMode){

                    if (board.getGridAt(currentPoint).containSpecialPiece()) {
                        board.getPieceAt(currentPoint).getEffect(gameData, currentPoint);
                    }
                    else if (board.getGridAt(currentPoint).has_Ice_Block()) {
                        board.getGridAt(currentPoint).remove_Ice_Block();
                        gameData.increase_Ice_Block_Destroyed();

                        Platform.runLater(() -> boardSceneController.removeIceBlockAt(finalCurrentPoint));
                    }
                }
                    
                if (board.is_Cell_Playable(currentPoint)) {
                    gameData.updateFallData(currentPoint.getRow(), currentPoint.getCol());; // For Fall Mechanism
                        
                    board.removePiece(currentPoint);

                    
                    Platform.runLater(() -> boardSceneController.removeImage(finalCurrentPoint));
                }

                currentPoint = currentPoint.getAdjacentPoint(orientation);
            }
            
            to_be_removed = gameData.popMatchData();
        }
        Util.threadSleep(remove_Break_Duration);
    }
}
