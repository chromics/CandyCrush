package mechanism;

import data.GameData;
import data.constant.Constant;
import data.constant.Orientation;
import model.Cell;
import model.Board;
import model.BoardPoint;
import model.piece.Piece;
import view.viewController.BoardSceneController;

public class Fall {
    public static void fall(Board board, GameData gameData, BoardSceneController view){
        System.out.println("Fall Start");

        Cell[][] grid = board.getGrid();
        int[] fallData = gameData.getFallData(); //Number of Empty Cell per Column

        int board_Col_Size = gameData.getBoard_Col_Size();
        int startRow = (gameData.getBoard_Row_Size() - 1) - 1; // Start at 1 row away from the lowest row, walk upward

        while (gameData.hasnotFall()) {

            for (int col = 0; col < board_Col_Size; col++) {

                if (fallData[col] > 0) {

                    for (int row = startRow; row >= 0; row--) {
    
                        if (grid[row][col].containPiece() 
                            && grid[row+1][col].isPlayable()
                            && ! grid[row + 1][col].containPiece()) {

                            System.out.println("Fall at (" + row + ", " + col + ")");
    
                            /*
                            * If the current Cell has piece &
                            * The Cell below is empty, then
                            * the piece fall by one cell
                            */
    
                            BoardPoint src = new BoardPoint(row, col);
                            BoardPoint dest = new BoardPoint(row + 1, col);
    
                            board.swapPiece(src, dest);
                            view.swapImage(src, dest);
                        }
                    }

                    BoardPoint currentPoint = new BoardPoint(0, col);
                    if (! board.any_piece(currentPoint)) {
                        System.out.println("Generate New Piece");
                        Piece newPiece = new Piece (Util.generateRandomPieceName());
                        
                        board.setPiece(currentPoint, newPiece);
                        view.setPieceImageAt(newPiece.getImagePath(), currentPoint);
                    }
                    
                    fallData[col]--;
                }
            }

            //!The Images only changed after the sleep finish
            //Wait for the drop animation to finish
            // Util.pauseExecution(gameData.get_Animation_Duration_In_Millis());
        }

            // int lowest_Empty_Row = gameData.getFallDataAtCol(col);

            // BoardPoint dest = new BoardPoint(lowest_Empty_Row, col);
            // BoardPoint src = dest.getAdjacentPoint(Orientation.UP);

            // while(board.is_Within_Boundary(src)){
            //     if(board.getPieceAt(src) != null){
            //         board.swapPiece(src, dest);
            //         view.swapImage(src, dest);

            //         dest = dest.getAdjacentPoint(Orientation.UP);
            //     }
            //     src = src.getAdjacentPoint(Orientation.UP);
            // }

            // while (board.is_Within_Boundary(dest)) {
            //     if ( ! board.any_piece(dest)) {
            //         board.generatePieceAt(dest);
            //         view.generatePieceViewAt(board.getPieceAt(dest).getImagePath(), dest);
            //     }
            //     dest = dest.getAdjacentPoint(Orientation.UP);
            // }
        
        gameData.resetFallData();
    }


}
