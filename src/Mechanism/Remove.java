package mechanism;

import model.Board;
import model.BoardPoint;
import view.viewController.BoardSceneController;
import data.GameData;
import data.MatchData;
import data.constant.Orientation;

public class Remove {
    public static void removePieces(Board board, GameData gameData, BoardSceneController view){
        MatchData to_be_removed = gameData.popMatchData();
        boolean specialMode = gameData.getSpecialMode();

        while(to_be_removed != null){
            
            int matchLength = to_be_removed.getLength();
            BoardPoint currentPoint = to_be_removed.getPoint();
            Orientation orientation = to_be_removed.getOrientation();

            for(; matchLength > 0; matchLength--){
                
                if(specialMode && board.getGridAt(currentPoint).containSpecialPiece()){
                    board.getPieceAt(currentPoint).getEffect(gameData, currentPoint);
                }
                    
                if (board.is_Cell_Playable(currentPoint)) {
                    gameData.increaseFallDataAt(currentPoint.getCol()); // For Fall Mechanism
                        
                    board.removePiece(currentPoint);
                    view.removeImage(currentPoint);
                }

                currentPoint = currentPoint.getAdjacentPoint(orientation);
            }
            
            to_be_removed = gameData.popMatchData();
        }
    }
}
