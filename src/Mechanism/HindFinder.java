package mechanism;

import model.Cell;
import model.BoardPoint;
import data.GameData;

public class HindFinder {
    public static void findHind(GameData gameData){
        Cell[][] grid = gameData.getBoard().getGrid();

        for (int row = 0; row < gameData.getBoard_Row_Size(); row++){
            for(int col = 0; col < gameData.getBoard_Col_Size(); col++){

                Cell currentCell = grid[row][col];

                if( currentCell.equalPiece(grid[row][col+1]) ){
                    if( currentCell.equalPiece(grid[row-1][col+2]) ){
                        /*
                        * . . C
                        * A B .
                        * . . .
                        */
                        saveHint(gameData, row, col, row, col+1, row-1, col+2);
                        return;
                    }
                    if( currentCell.equalPiece(grid[row+1][col+2]) ){
                        /*
                        * . . .
                        * A B .
                        * . . C
                        */
                        saveHint(gameData, row, col, row, col+1, row+1, col+2);
                        return;
                    }
                    if( currentCell.equalPiece(grid[row][col+3]) ){
                        /*
                        * . . . .
                        * A B . C
                        * . . . .
                        */
                        saveHint(gameData, row, col, row, col+1, row, col+3);
                        return;
                    }
                }
                
                if( currentCell.equalPiece(grid[row-1][col+1]) ){
                    if( currentCell.equalPiece(grid[row-1][col+2]) ){
                        /*
                        * . B C
                        * A . .
                        * . . .
                        */
                        saveHint(gameData, row, col, row-1, col+1, row-1, col+2);
                        return;
                    }
                    if( currentCell.equalPiece(grid[row][col+2]) ){
                        /*
                        * . B .
                        * A . C
                        * . . .
                        */
                        saveHint(gameData, row, col, row-1, col+1, row, col+2);
                        return;
                    }
                }
                
                if( currentCell.equalPiece(grid[row+1][col+1]) ){
                    if( currentCell.equalPiece(grid[row+1][col+2]) ){
                        /*
                        * . . .
                        * A . .
                        * . B C
                        */
                        saveHint(gameData, row, col, row+1, col+1, row+1, col+2);
                        return;
                    }
                    if( currentCell.equalPiece(grid[row][col+2]) ){
                        /*
                        * . . .
                        * A . C
                        * . B .
                        */
                        saveHint(gameData, row, col, row+1, col+1, row, col+2);
                        return;
                    }
                }
                
                if( currentCell.equalPiece(grid[row][col+2])
                     && currentCell.equalPiece(grid[row][col+3]) ){
                    
                    /*
                    * . . . .
                    * A . B C
                    * . . . .
                    */
                    saveHint(gameData, row, col, row, col+2, row, col+3);
                    return;
                }
                
                if( currentCell.equalPiece(grid[row+1][col]) ){
                    if( currentCell.equalPiece(grid[row+2][col-1]) ){
                        /*
                        * . A .
                        * . B .
                        * C . .
                        */
                        saveHint(gameData, row, col, row+1, col, row+2, col-1);
                        return;
                    }
                    if( currentCell.equalPiece(grid[row+2][col+1]) ){
                        /*
                        * . A .
                        * . B .
                        * . . C
                        */
                        saveHint(gameData, row, col, row+1, col, row+2, col+1);
                        return;
                    }
                    if( currentCell.equalPiece(grid[row+3][col]) ){
                        /*
                        * . A .
                        * . B .
                        * . . .
                        * . C .
                        */
                        saveHint(gameData, row, col, row+1, col, row, col);
                        return;
                    }
                }
                
                if( currentCell.equalPiece(grid[row+1][col-1]) ){
                    if( currentCell.equalPiece(grid[row+2][col-1]) ){
                        /*
                        * . A .
                        * B . .
                        * C . .
                        */
                        saveHint(gameData, row, col, row+1, col-1, row+2, col-1);
                        return;
                    }
                    if( currentCell.equalPiece(grid[row+2][col]) ){
                        /*
                        * . A .
                        * B . .
                        * . C .
                        */
                        saveHint(gameData, row, col, row+1, col-1, row+2, col);
                        return;
                    }
                }

                if( currentCell.equalPiece(grid[row+1][col+1]) ){
                    if( currentCell.equalPiece(grid[row+2][col+1]) ){
                        /*
                        * . A .
                        * . . B
                        * . . C
                        */
                        saveHint(gameData, row, col, row+1, col+1, row+2, col+1);
                        return;
                    }
                    if( currentCell.equalPiece(grid[row+2][col]) ){
                        /*
                        * . A .
                        * . . B
                        * . C .
                        */
                        saveHint(gameData, row, col, row+1, col+1, row+2, col);
                        return;
                    }
                }
                
                if( currentCell.equalPiece(grid[row+2][col])
                && currentCell.equalPiece(grid[row+3][col]) ){
                    
                    /*
                    * . A .
                    * . . .
                    * . B .
                    * . C .
                    */
                    saveHint(gameData, row, col, row+2, col, row+3, col);
                    return;
                }
                
            }
        }
    }

    public static void saveHint(GameData gameData, int row1, int col1, int row2, int col2, int row3, int col3){
        BoardPoint point1 = new BoardPoint(row1, col1);
        BoardPoint point2 = new BoardPoint(row2, col2);
        BoardPoint point3 = new BoardPoint(row3, col3);

        gameData.saveHint(point1, point2, point3);
    }
}
