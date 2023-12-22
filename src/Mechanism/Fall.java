package mechanism;

import data.GameData;
import data.constant.Orientation;
import model.Board;
import model.BoardPoint;
import view.viewController.BoardSceneController;

public class Fall {
    public static void fall(Board board, GameData gameData, BoardSceneController view){
        
        for(int col = 0; col < gameData.getBoard_Col_Size(); col++){

            int lowest_Empty_Row = gameData.getFallDataAtCol(col);

            BoardPoint dest = new BoardPoint(lowest_Empty_Row, col);
            BoardPoint src = dest.getAdjacentPoint(Orientation.UP);

            while(board.is_Within_Boundary(src)){
                if(board.getPieceAt(src) != null){
                    board.swapPiece(src, dest);
                    view.swapImage(src, dest);
                    dest = dest.getAdjacentPoint(Orientation.UP);
                }
                src = src.getAdjacentPoint(Orientation.UP);
            }

            while(board.is_Within_Boundary(dest)){
                board.generatePieceAt(dest);
                view.setPieceImageAt(board.getPieceAt(dest).getImagePath(), dest);
                dest = dest.getAdjacentPoint(Orientation.UP);
            }
        }
        gameData.resetFallData();
    }
}
