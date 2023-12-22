package mechanism;

import model.BoardPoint;
import model.Board;
import model.Cell;
import data.constant.Constant;
import data.constant.Orientation;
import data.GameData;

import java.util.ArrayList;
import java.lang.Math;

public class CheckMatch {

    public static void scan_and_save_matches(Board board, GameData gameData){
        
        int board_Row_Size = gameData.getBoard_Row_Size();
        int board_Col_Size = gameData.getBoard_Col_Size();

        BoardPoint startPoint;
        BoardPoint currentPoint;

        startPoint = new BoardPoint(1, 1);
        currentPoint = new BoardPoint(2, 2);

        //Horizontal Check
        for(int row = 0; row < board_Row_Size; row++){
            startPoint = new BoardPoint(row, 0);
            for(int col = 0; col < board_Col_Size; col++){
                currentPoint = new BoardPoint(row, col);

                if(col == board_Col_Size - 1 || ! board.is_Equal_To_Adjacent(currentPoint, Orientation.RIGHT)){
                    save_Match_If_Valid(board, gameData, startPoint, currentPoint, Orientation.RIGHT);
                    startPoint = currentPoint.getAdjacentPoint(Orientation.RIGHT);
                }
            }
        }

        //Vertical Check
        for(int col = 0; col < board_Col_Size; col++){
            startPoint = new BoardPoint(0, col);
            for(int row = 0; row < board_Row_Size; row++){
                currentPoint = new BoardPoint(row, col);

                if(row == board_Row_Size - 1 || ! board.is_Equal_To_Adjacent(currentPoint, Orientation.DOWN)){
                    // System.out.printf("%s | Start Point : (%d,%d),\tCurrent Point : (%d,%d)\n", board.getPieceAt(currentPoint).getName(), startPoint.getRow(), startPoint.getCol(), currentPoint.getRow(), currentPoint.getCol());
                    save_Match_If_Valid(board, gameData, startPoint, currentPoint, Orientation.DOWN);
                    startPoint = currentPoint.getAdjacentPoint(Orientation.DOWN);
                }
            }
        }
    }

    public static void save_Match_If_Valid( Board board, GameData gameData, BoardPoint startPoint, BoardPoint endPoint, Orientation orientation ){
        
        int matchLength = board.calculateDistance( startPoint, endPoint ) + 1;
        
        if(matchLength >= 3){
            gameData.saveMatchData( startPoint, matchLength, orientation );
        }
    }    
}

