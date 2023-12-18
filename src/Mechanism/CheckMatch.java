package mechanism;

import model.Cell;
import data.constant.Constant;

public class CheckMatch {
    // public static 
    public static void findMatches(Cell[][] grid){

        //Loop from Bottom Left of the Board to Top Right
        for(int row = Constant.BOARD_ROW_SIZE.getNum() - 1; row >= 0; row--){
            for(int col = 0; col < Constant.BOARD_COL_SIZE.getNum(); col++){
                if(grid[row][col].containPiece()){
                    
                }
            }
        }
    }
}
