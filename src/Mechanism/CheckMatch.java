package mechanism;

import model.BoardPoint;
import model.Cell;
import data.constant.Constant;
import data.constant.Orientation;
import data.GameData;
import data.GameData;

import java.util.ArrayList;


public class CheckMatch {
    public static int match_length_at_direction(Orientation orientation, Cell[][] grid, int startRow, int startCol ){
        
        int curRow = startRow + orientation.getRowChange();
        int curCol = startCol + orientation.getColChange();
        int length = 1;

        while ( 0 <= curRow  && curRow < Constant.BOARD_ROW_SIZE.getNum()
                && 0 <= curCol && curCol < Constant.BOARD_COL_SIZE.getNum()
                && grid[curRow][curCol].containPiece()
                && grid[startRow][startCol].equal(grid[curRow][curCol]) ){

                length++;
                curRow += orientation.getRowChange();
                curCol += orientation.getColChange();
            }
        
        return length;
    }

    public static void scan_and_save_matches(Cell[][] grid, GameData gameData){

        //Loop from Bottom Left of the Board to Top Right
        for(int row = Constant.BOARD_ROW_SIZE.getNum() - 1; row >= 0; row--){
            for(int col = 0; col < Constant.BOARD_COL_SIZE.getNum(); col++){
                if(grid[row][col].containPiece()){
                    
                    int tempLength = match_length_at_direction(Orientation.UP, grid, row, col);
                    if(tempLength >= 3){
                        gameData.saveMatchData(new BoardPoint(row, col), tempLength, Orientation.UP);
                    }

                    tempLength = match_length_at_direction(Orientation.RIGHT, grid, row, col);
                    if(tempLength >= 3){
                        gameData.saveMatchData(new BoardPoint(row, col), tempLength, Orientation.RIGHT);
                    }                 
                }
            }
        }
    }
}
