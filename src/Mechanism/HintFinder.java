package mechanism;

import model.Cell;
import model.BoardPoint;
import data.GameData;

public class HintFinder {
    public static void findHint(GameData gameData){
        
        Cell[][] grid = gameData.getBoard().getGrid();
        int board_Row_Size = gameData.getBoard_Row_Size();
        int board_Col_Size = gameData.getBoard_Col_Size();

        for (int row = 0; row < board_Row_Size; row++){
            for(int col = 0; col < board_Col_Size; col++){

                Cell currentCell = grid[row][col];

                if(currentCell.containPiece()){

                    if (col < board_Col_Size - 2){
                        if (currentCell.equalPiece(grid[row][col+1])) {
                            
                            if (row > 0
                                && currentCell.equalPiece(grid[row-1][col+2])) {
                                /*
                                * . . C
                                * A B .
                                * . . .
                                */
                                saveHint(gameData, row-1, col+2, row, col+2);
                                return;
                            }
                            if (row < board_Row_Size
                                && currentCell.equalPiece(grid[row+1][col+2])) {
                                /*
                                * . . .
                                * A B .
                                * . . C
                                */
                                saveHint(gameData, row+1, col+2, row, col+2);
                                return;
                            }
                            if (col < board_Col_Size - 3
                                && currentCell.equalPiece(grid[row][col+3])){
                                /*
                                * . . . .
                                * A B . C
                                * . . . .
                                */
                                saveHint(gameData, row, col+2, row, col+3);
                                return;
                            }
                        }
                        
                        if (row > 0
                            && currentCell.equalPiece(grid[row-1][col+1])) {
                                
                            if (currentCell.equalPiece(grid[row-1][col+2])) {
                                /*
                                * . B C
                                * A . .
                                * . . .
                                */
                                saveHint(gameData, row, col, row-1, col);
                                return;
                            }
                            if (currentCell.equalPiece(grid[row][col+2])) {
                                /*
                                * . B .
                                * A . C
                                * . . .
                                */
                                saveHint(gameData, row-1, col+1, row, col+1);
                                return;
                            }
                        }
                        
                        if (row < board_Row_Size
                            && currentCell.equalPiece(grid[row+1][col+1])) {
    
                            if (currentCell.equalPiece(grid[row+1][col+2])) {
                                /*
                                * . . .
                                * A . .
                                * . B C
                                */
                                saveHint(gameData, row, col, row+1, col);
                                return;
                            }
                            if (currentCell.equalPiece(grid[row][col+2])) {
                                /*
                                * . . .
                                * A . C
                                * . B .
                                */
                                saveHint(gameData, row+1, col+1, row, col+1);
                                return;
                            }
                        }
    
                        if (col < board_Col_Size - 3
                            && currentCell.equalPiece(grid[row][col+2])
                            && currentCell.equalPiece(grid[row][col+3])){
                            
                            /*
                            * . . . .
                            * A . B C
                            * . . . .
                            */
                            saveHint(gameData, row, col, row, col+1);
                            return;
                        }
                    }
    
                    
                    
                    if (row < board_Row_Size - 2) {
    
                        if (currentCell.equalPiece(grid[row+1][col])) {
    
                            if (col > 0
                                && currentCell.equalPiece(grid[row+2][col-1])) {
                                /*
                                * . A .
                                * . B .
                                * C . .
                                */
                                saveHint(gameData, row+2, col-1, row+2, col);
                                return;
                            }
                            if (col < board_Col_Size - 1
                                && currentCell.equalPiece(grid[row+2][col+1])) {
                                /*
                                * . A .
                                * . B .
                                * . . C
                                */
                                saveHint(gameData, row+2, col+1, row+2, col);
                                return;
                            }
                            if (row < board_Row_Size - 3
                                && currentCell.equalPiece(grid[row+3][col])) {
                                /*
                                * . A .
                                * . B .
                                * . . .
                                * . C .
                                */
                                saveHint(gameData, row+2,col, row+3, col);
                                return;
                            }
                        }
                        
                        if (col > 0
                            && currentCell.equalPiece(grid[row+1][col-1])) {
    
                            if (currentCell.equalPiece(grid[row+2][col-1])) {
                                /*
                                * . A .
                                * B . .
                                * C . .
                                */
                                saveHint(gameData, row, col-1, row, col);
                                return;
                            }
                            if (currentCell.equalPiece(grid[row+2][col])) {
                                /*
                                * . A .
                                * B . .
                                * . C .
                                */
                                saveHint(gameData, row+1, col-1, row+1, col);
                                return;
                            }
                        }
        
                        if (col < board_Col_Size - 1
                            && currentCell.equalPiece(grid[row+1][col+1])) {
    
                            if (currentCell.equalPiece(grid[row+2][col+1])) {
                                /*
                                * . A .
                                * . . B
                                * . . C
                                */
                                saveHint(gameData, row, col+1, row, col);
                                return;
                            }
                            if (currentCell.equalPiece(grid[row+2][col])) {
                                /*
                                * . A .
                                * . . B
                                * . C .
                                */
                                saveHint(gameData, row+1, col+1, row+1, col);
                                return;
                            }
                        }
                        
                        if (row < board_Row_Size - 3
                            && currentCell.equalPiece(grid[row+2][col])
                            && currentCell.equalPiece(grid[row+3][col])){
                            
                            /*
                            * . A .
                            * . . .
                            * . B .
                            * . C .
                            */
                            saveHint(gameData, row+1, col, row, col);
                            return;
                        }
                    }
                }

            }
        }
    }

    public static void saveHint(GameData gameData, int row1, int col1, int row2, int col2){
        BoardPoint point1 = new BoardPoint(row1, col1);
        BoardPoint point2 = new BoardPoint(row2, col2);

        gameData.saveHint(point1, point2);
    }
}
