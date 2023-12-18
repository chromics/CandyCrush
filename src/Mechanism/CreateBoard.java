package mechanism;

import model.Cell;
import model.piece.Piece;
import data.constant.Constant;

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

    public static void initiateBoard(Cell[][] grid){
        initiate_First_Valid_Move(grid);
        fill_The_Rest_Of_The_Board(grid);
    }

    public static void initiate_First_Valid_Move(Cell[][] grid){
        int[] potentialMatchPattern = Util.RandomPick(Constant.potentialMatchPatterns);

        /*
         * 1. Randomize a coordinate (1 <= Row <= RowSize) && (1 <= Col <= ColSize)
         * 2. Check whether every coordinate of the pattern land on a playable Cell
         */
        Random rand = new Random();
        int first_row, first_col;
        int second_row, second_col;
        int third_row, third_col;

        do{
            first_row = rand.nextInt(Constant.BOARD_ROW_SIZE.getNum() - 2) + 1;
            first_col = rand.nextInt(Constant.BOARD_COL_SIZE.getNum() - 2) + 1;
            
            second_row = first_row + potentialMatchPattern[0];
            second_col = first_col + potentialMatchPattern[1];

            third_row = first_row + potentialMatchPattern[2];
            third_col = first_col + potentialMatchPattern[3];

        } while(!grid[first_row][first_col].isPlayable() ||
                !grid[second_row][second_col].isPlayable() ||
                !grid[third_row][third_col].isPlayable());

        /*
         * Randomize a piece and assign it to the pattern coordinate
         */
        String randomPieceName = CreatePiece.createPiece();

        grid[first_row][first_col].setPiece(new Piece(randomPieceName));
        grid[second_row][second_col].setPiece(new Piece(randomPieceName));
        grid[third_row][third_col].setPiece(new Piece(randomPieceName));
    }


    public static void fill_The_Rest_Of_The_Board(Cell[][] grid){

        for(int row = 0; row < Constant.BOARD_ROW_SIZE.getNum(); row++){
            for(int col = 0; col < Constant.BOARD_COL_SIZE.getNum(); col++){

                if(grid[row][col].isPlayable() && grid[row][col].getPiece() == null){
                    HashSet<String> excludeList = new HashSet<>();

                    //Check 1 : 
                    if( row > 1 
                        && grid[row-2][col].containPiece()
                        && grid[row-1][col].containPiece()
                        && grid[row-1][col].equal(grid[row-2][col])){

                        excludeList.add(grid[row-1][col].getPiece().getName());
                    }
                    if( col > 1
                        && grid[row][col-2].containPiece()
                        && grid[row][col-1].containPiece()
                        && grid[row][col-1].equal(grid[row][col-2])){

                        excludeList.add(grid[row][col-1].getPiece().getName());
                    }
                    if( row < Constant.BOARD_ROW_SIZE.getNum() - 2
                        && grid[row+1][col].containPiece()
                        && grid[row+2][col].containPiece()
                        && grid[row+1][col].equal(grid[row+2][col])){
                    
                        excludeList.add(grid[row+1][col].getPiece().getName());
                    }
                    if( col < Constant.BOARD_COL_SIZE.getNum() - 2
                        && grid[row][col+1].getPiece() != null
                        && grid[row][col+2].getPiece() != null
                        && grid[row][col+1].equal(grid[row][col+2])){

                        excludeList.add(grid[row][col+1].getPiece().getName());
                    }

                    //Check 2 : 
                    if( row > 0 && row < Constant.BOARD_ROW_SIZE.getNum() - 1
                        && grid[row-1][col].containPiece()
                        && grid[row+1][col].containPiece() 
                        && grid[row+1][col].equal(grid[row-1][col])){
                        
                        excludeList.add(grid[row-1][col].getPiece().getName());
                    }
                    if( col > 0 && col < Constant.BOARD_COL_SIZE.getNum() - 1
                        && grid[row][col-1].containPiece()
                        && grid[row][col+1].containPiece()
                        && grid[row][col+1].equal(grid[row][col-1])){

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
