package Mechanism;

import model.components.ChessPiece;
import model.components.Cell;
import model.components.Constant;
import model.components.Util;
import java.util.HashSet;
import java.util.Random;

public class CreateBoard {
    private static Random rand = new Random();
    private static final int[][] potential_match_pattern = {
        {-1,-1,0,1}, {-1,0,1,-1}, {0,-1,1,1}, {-1,1,1,0},
        {1,-1,0,1}, {-1,0,1,1}, {0,-1,-1,1}, {-1,-1,1,0},
        {-1,-1,-1,1}, {-1,-1,1,-1}, {1,-1,1,1}, {-1,1,1,1}
    };
    /*
     * r = y axis, c = x axis
     * The Match Pattern : {r1, c1, r2, c2}
     * 
     * So, a coordinate (r,c) where (1 <= r <= 6 && 1 <= c <= 6) will be randomized
     * r1,c1 and r2,c2 added to r,c to get r,c two neighbours which will form a valid
     * move to guarantee that the board always has valid moves at the start of the game.
     * 
     * First row (the last three of the first row is just the rotation of the same pattern):
     * @ . .
     * . @ @
     * . . .
     * 
     * second row (the last three of the second row is just the rotation of the same pattern):
     * . . .
     * . @ @
     * @ . .
     * 
     * Third row (the last three of the third row is just the rotation of the same pattern):
     * @ . @
     * . @ .
     * . . .
     */

    public static void initiateBoard(Cell[][] grid){
        initiateFirstValidMove(grid);
        fillTheRestOfTheBoard(grid);
    }

    public static void initiateFirstValidMove(Cell[][] grid){
        int[] matchPattern = Util.RandomPick(potential_match_pattern);
        int first_Pattern_Row, first_Pattern_Col;
        int second_Pattern_Row, second_Pattern_Col;
        int third_Pattern_Row, third_Pattern_Col;

        do{
            first_Pattern_Row = rand.nextInt(Constant.CHESSBOARD_ROW_SIZE.getNum() - 2) + 1;
            first_Pattern_Col = rand.nextInt(Constant.CHESSBOARD_COL_SIZE.getNum() - 2) + 1;
            second_Pattern_Row = first_Pattern_Row + matchPattern[0];
            second_Pattern_Col = first_Pattern_Col + matchPattern[1];
            third_Pattern_Row = first_Pattern_Row + matchPattern[2];
            third_Pattern_Col = first_Pattern_Col + matchPattern[3];
        }while( !grid[first_Pattern_Row][first_Pattern_Col].isPlayable() ||
                !grid[second_Pattern_Row][second_Pattern_Col].isPlayable() ||
                !grid[third_Pattern_Row][third_Pattern_Col].isPlayable());

        String icon = CreatePieces.createPieces();

        grid[first_Pattern_Row][first_Pattern_Col].setPiece(new ChessPiece(icon));
        grid[second_Pattern_Row][second_Pattern_Col].setPiece(new ChessPiece(icon));
        grid[third_Pattern_Row][third_Pattern_Col].setPiece(new ChessPiece(icon));
    }

    public static void fillTheRestOfTheBoard(Cell[][] grid){
        for(int row = 0; row < Constant.CHESSBOARD_ROW_SIZE.getNum(); row++){
            for(int col = 0; col < Constant.CHESSBOARD_COL_SIZE.getNum(); col++){
                if(grid[row][col].getPiece() == null){
                    HashSet<String> exclude_List = new HashSet<>();

                    /*
                     * These two If statements below check two pieces up and left
                     * . . @
                     * . . @
                     * # # X
                     * 
                     * X : current coordinate
                     * 
                     * If two same pieces found like the illustration above, 
                     * that piece will be excluded
                     */
                    if(row > 1 && grid[row-1][col].equal(grid[row-2][col])){
                        exclude_List.add(grid[row-1][col].getPiece().getName());
                    }
                    if(col > 1 && grid[row][col-1].equal(grid[row][col-2])){
                        exclude_List.add(grid[row][col-1].getPiece().getName());
                    }

                    /*
                     * These two If statements below check two pieces down and right
                     * used when the current coordinate surrounded by the pieces generated
                     * earlier in fillTheRestOfTheBoard and the pieces from initiateFirstValidMove
                     * 
                     * @ # @ # @ # @ @
                     * # # @ X . . . .
                     * . . . # . . . .
                     * . . . @ . . . .
                     * . . . . @ . . .
                     * 
                     * In order to avoid creating any match at the start of the game
                     * 
                     */
                    if(row > 0 && row < 7 && grid[row+1][col].getPiece() != null){
                        if(grid[row-1][col].equal(grid[row+1][col])){
                            exclude_List.add(grid[row+1][col].getPiece().getName());
                        }
                        else if(row < 6 && grid[row+2][col].getPiece() != null && grid[row+1][col].equal(grid[row+2][col])){
                            exclude_List.add(grid[row+1][col].getPiece().getName());
                        }
                    }
                    if(col > 0 && col < 7 && grid[row][col+1].getPiece() != null){
                        if(grid[row][col-1].equal(grid[row][col+1])){
                            exclude_List.add(grid[row][col+1].getPiece().getName());
                        } 
                        else if(col < 6 && grid[row][col+2].getPiece() != null && grid[row][col+1].equal(grid[row][col+2])){
                            exclude_List.add(grid[row][col+1].getPiece().getName());
                        }
                    }

                    /*
                     * Regenerate Random Icon if it supposed to be excluded
                     */
                    String randomIcon;
                    do{
                        randomIcon = CreatePieces.createPieces();
                    }while(exclude_List.contains(randomIcon));                    

                    grid[row][col].setPiece(new ChessPiece(randomIcon));
                }
            }
        }
    }
}
