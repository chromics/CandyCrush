package mechanism;

import model.Cell;
import model.Board;
import model.BoardPoint;
import model.piece.Piece;
import data.constant.Constant;
import data.constant.Orientation;

import java.util.Random;
import java.util.HashSet;

public class CreateBoard {
    /*
     * 1. Create First Valid Move from the pattern to guarantee there is valid
     *    move at the start of the game (The explanation of the potential
     *    match pattern is in the data.Constant)
     * 
     * 2. Fill the rest of the board while checking its surrouding
     *    Check 1 : 
     *    . . A . .
     *    . . A . .
     *    D D x B B
     *    . . C . . 
     *    . . C . .
     * 
     *    x : current coordinate
     *    A, B, C, D : the surrounding coordinate it checks
     *    
     *    if both of the pieces at ,for example, A are the same, then that type of pieces is exlucded
     * 
     *    Check 2 :
     *    . . . . .
     *    . . A . .
     *    . B X B .
     *    . . A . .
     *    . . . . .
     * 
     *    x : current coordinate
     *    A, B : the surrounding coordinate it checks
     */

    public static void initiateBoard(Board board){
        initiate_First_Valid_Move(board);
        fill_The_Rest_Of_The_Board(board);
    }

    public static void initiate_First_Valid_Move(Board board){
        Orientation[] potentialMatchPattern = Util.RandomPick(Constant.potential_Match_Patterns);

        /*
         * 1. Randomize a coordinate (1 <= Row <= RowSize) && (1 <= Col <= ColSize)
         * 2. Check whether every coordinate of the pattern land on a playable Cell
         */
        Random rand = new Random();
        BoardPoint point1;
        BoardPoint point2;
        BoardPoint point3;

        do{
            int row = rand.nextInt( board.get_Board_Row_Size() - 2 ) + 1;
            int col = rand.nextInt( board.get_Board_Col_Size() - 2 ) + 1;

            point1 = new BoardPoint( row, col );
            point2 = point1.getAdjacentPoint( potentialMatchPattern[0] );
            point3 = point1.getAdjacentPoint( potentialMatchPattern[1] );

        } while( ! board.is_Cell_Playable( point1 )
                || ! board.is_Cell_Playable( point2 )
                || ! board.is_Cell_Playable( point3 ) );

        /*
         * Randomize a piece and assign it to the pattern coordinate
         */
        String randomPieceName = CreatePiece.createPiece();

        board.setPiece( point1, new Piece( randomPieceName ) );
        board.setPiece( point2, new Piece( randomPieceName ) );
        board.setPiece( point3, new Piece( randomPieceName ) );
    }


    public static void fill_The_Rest_Of_The_Board(Board board){
        Cell[][] grid = board.getGrid();

        for(int row = 0; row < board.get_Board_Row_Size(); row++){
            for(int col = 0; col < board.get_Board_Col_Size(); col++){

                if(grid[row][col].isPlayable() && grid[row][col].getPiece() == null){
                    HashSet<String> excludeList = new HashSet<>();

                    //Check 1 : 
                    if( row > 1
                        && grid[row-2][col].containPiece()
                        && grid[row-1][col].containPiece()
                        && grid[row-1][col].equalPiece(grid[row-2][col])){

                        excludeList.add(grid[row-1][col].getPiece().getName());
                    }
                    if( col > 1
                        && grid[row][col-2].containPiece()
                        && grid[row][col-1].containPiece()
                        && grid[row][col-1].equalPiece(grid[row][col-2])){

                        excludeList.add(grid[row][col-1].getPiece().getName());
                    }
                    if( row < board.get_Board_Row_Size() - 2
                        && grid[row+1][col].containPiece()
                        && grid[row+2][col].containPiece()
                        && grid[row+1][col].equalPiece(grid[row+2][col])){
                    
                        excludeList.add(grid[row+1][col].getPiece().getName());
                    }
                    if( col < board.get_Board_Col_Size() - 2
                        && grid[row][col+1].getPiece() != null
                        && grid[row][col+2].getPiece() != null
                        && grid[row][col+1].equalPiece(grid[row][col+2])){

                        excludeList.add(grid[row][col+1].getPiece().getName());
                    }

                    //Check 2 : 
                    if( row > 0 && row < board.get_Board_Row_Size() - 1
                        && grid[row-1][col].containPiece()
                        && grid[row+1][col].containPiece() 
                        && grid[row+1][col].equalPiece(grid[row-1][col])){
                        
                        excludeList.add(grid[row-1][col].getPiece().getName());
                    }
                    if( col > 0 && col < board.get_Board_Col_Size() - 1
                        && grid[row][col-1].containPiece()
                        && grid[row][col+1].containPiece()
                        && grid[row][col+1].equalPiece(grid[row][col-1])){

                        excludeList.add(grid[row][col-1].getPiece().getName());
                    }

                    String randomPieceName;
                    do{
                        randomPieceName = CreatePiece.createPiece();
                    }while(excludeList.contains(randomPieceName));

                    grid[row][col].setPiece(new Piece(randomPieceName));
                    
                }
            }
        }
    }
}
