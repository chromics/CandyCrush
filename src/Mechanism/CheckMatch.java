package mechanism;

import model.Cell;
import model.Board;
import model.BoardPoint;
import data.GameData;
import data.constant.Orientation;

public class CheckMatch {

    public static void scan_and_save_matches(Board board, GameData gameData){
        /*
         * 0 1 2 3 4 5 6
         * A B B C C C A
         * 
         * CurrentCell compared with its right side Cell
         * 
         * StartPoint : A (0)
         * CurrentPoint : A(0)
         * 
         * A(0) != B(1) -> currentPoint-startPoint+1 = 0 - 0 + 1 = 1 (< 3) (Not a Match); StartPoint : B(1); 
         * B(1) == B(2) -> Do Nothing
         * B(2) != C(3) -> currentPoint-startPoint+1 = 2 - 1 + 1 = 2 (< 3) (Not a Match); StartPoint : C(3) ; 
         * C(3) == C(4) -> Do Nothing
         * C(4) == C(5) -> Do Nothing
         * C(5) != A(6) -> currentPoint-startPoint+1 = 5 - 3 + 1 = 3 (== 3) (a Match); StartPoint : A(6);
         * A(6) last index -> currentPoint-startPoint+1 = 6 - 6 + 1 = 1 (< 3) (Not a Match);
         * 
         */
        int board_Row_Size = gameData.getBoard_Row_Size();
        int board_Col_Size = gameData.getBoard_Col_Size();
        Cell[][] grid = board.getGrid();

        // BoardPoint startPoint;
        // BoardPoint currentPoint;

        // startPoint = new BoardPoint(1, 1);
        // currentPoint = new BoardPoint(2, 2);

        // for(int row = 0; row < board_Row_Size; row++){
            //     startPoint = new BoardPoint(row, 0);
            //     for(int col = 0; col < board_Col_Size; col++){
                //         currentPoint = new BoardPoint(row, col);
                
                //         if(col == board_Col_Size - 1 || ! board.is_Equal_To_Adjacent(currentPoint, Orientation.RIGHT)){
                    //             save_Match_If_Valid(board, gameData, startPoint, currentPoint, Orientation.RIGHT);
                    //             startPoint = currentPoint.getAdjacentPoint(Orientation.RIGHT);
                    //         }
                    //     }
                    // }
        //Horizontal Check
        for (int row = 0; row < board_Row_Size; row++) {
            int startCol = 0;
            for (int col = 0; col < board_Col_Size; col++) {

                if (grid[row][col].containSpecialPiece()) {
                    if (col > 0
                        && col < board_Col_Size - 1
                        && ! grid[row][col-1].equalPiece(grid[row][col+1])) {
                        
                        BoardPoint startPoint = new BoardPoint(row, startCol);
                        BoardPoint endPoint = new BoardPoint(row, col);

                        save_Match_If_Valid(board, gameData, startPoint, endPoint, Orientation.RIGHT);
                        startCol = col + 1;
                    }
                }
                else if(col == board_Col_Size - 1 || ! grid[row][col].equalPiece(grid[row][col+1])){
                    BoardPoint startPoint = new BoardPoint(row, startCol);
                    BoardPoint endPoint = new BoardPoint(row, col);

                    save_Match_If_Valid(board, gameData, startPoint, endPoint, Orientation.RIGHT);
                    startCol = col + 1;
                }
            }
        }
        
        //Vertical Check
        for(int col = 0; col < board_Col_Size; col++){
            int startRow = 0;
            for(int row = 0; row < board_Row_Size; row++){
                
                if (grid[row][col].containSpecialPiece()) {
                    if (row > 0
                        && row < board_Row_Size - 1
                        && ! grid[row-1][col].equalPiece(grid[row+1][col])) {
                        
                        BoardPoint startPoint = new BoardPoint(startRow, col);
                        BoardPoint endPoint = new BoardPoint(row, col);
                        
                        save_Match_If_Valid(board, gameData, startPoint, endPoint, Orientation.DOWN);
                        startRow = row + 1;
                    }
                }
                else if (row == board_Row_Size - 1 || ! grid[row][col].equalPiece(grid[row+1][col])) {
                    // System.out.printf("%s | Start Point : (%d,%d),\tCurrent Point : (%d,%d)\n", board.getPieceAt(currentPoint).getName(), startPoint.getRow(), startPoint.getCol(), currentPoint.getRow(), currentPoint.getCol());
                    BoardPoint startPoint = new BoardPoint(startRow, col);
                    BoardPoint endPoint = new BoardPoint(row, col);
                    
                    save_Match_If_Valid(board, gameData, startPoint, endPoint, Orientation.DOWN);
                    startRow = row + 1;
                }
            }
        }
        // for(int col = 0; col < board_Col_Size; col++){
        //     startPoint = new BoardPoint(0, col);
        //     for(int row = 0; row < board_Row_Size; row++){
        //         currentPoint = new BoardPoint(row, col);

        //         if(row == board_Row_Size - 1 || ! board.is_Equal_To_Adjacent(currentPoint, Orientation.DOWN)){
        //             // System.out.printf("%s | Start Point : (%d,%d),\tCurrent Point : (%d,%d)\n", board.getPieceAt(currentPoint).getName(), startPoint.getRow(), startPoint.getCol(), currentPoint.getRow(), currentPoint.getCol());
        //             save_Match_If_Valid(board, gameData, startPoint, currentPoint, Orientation.DOWN);
        //             startPoint = currentPoint.getAdjacentPoint(Orientation.DOWN);
        //         }
        //     }
        // }
    }

    public static void save_Match_If_Valid( Board board, GameData gameData, BoardPoint startPoint, BoardPoint endPoint, Orientation orientation ){
        
        int matchLength = board.calculateDistance(startPoint, endPoint) + 1;
        
        if (matchLength >= 3) {
            gameData.saveMatchData(startPoint, matchLength, orientation);
        }
    }    
}

