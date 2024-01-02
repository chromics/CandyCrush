package mechanism;

import javafx.application.Platform;

import view.viewController.BoardSceneController;
import data.constant.Orientation;
import data.constant.Constant;
import data.GameData;
import model.Board;
import model.BoardPoint;

public class Fall {
    private static int extra_Duration = Constant.EXTRA_DURATION.getNum();
    private static int animation_Duration = Constant.ANIMATION_DURATION.getNum();

    public static void fall (Board board, GameData gameData, BoardSceneController boardSceneController) {
        int max_Distance = 0;

        for (int col = 0; col < gameData.getBoard_Col_Size(); col++) {
            int lowest_Empty_Cell_At_Col = gameData.getFallDataAtCol(col);

            BoardPoint dest = new BoardPoint(lowest_Empty_Cell_At_Col, col);
            BoardPoint src = dest.getAdjacentPoint(Orientation.UP);

            while (board.is_Within_Boundary(src)) {

                if (board.any_piece(dest)) {
                    dest = dest.getAdjacentPoint(Orientation.UP);
                }
                else if (board.any_piece(src)) {
                    board.swapPiece(src, dest);
                    System.out.println("Piece "  + board.getPieceAt(dest).getName() + " Fall from " + src + " to " + dest);
                    
                    final BoardPoint finalSrc = src;
                    final BoardPoint finalDest = dest;

                    Platform.runLater(() -> boardSceneController.fallImage(finalSrc, finalDest));

                    dest = dest.getAdjacentPoint(Orientation.UP);
                }

                src = src.getAdjacentPoint(Orientation.UP);
            }

            while (board.is_Within_Boundary(dest)) {

                if (board.is_Cell_Playable(dest) && ! board.any_piece(dest)) {

                    final BoardPoint finalSrc = src;
                    final BoardPoint finalDest = dest;
                    
                    board.generatePieceAt(dest);
                    System.out.println("Piece " + board.getPieceAt(dest).getName() + " Generated at " + dest);
                    Platform.runLater(() -> boardSceneController.fallImage(finalSrc, finalDest));
                }

                src = src.getAdjacentPoint(Orientation.UP);
                dest = dest.getAdjacentPoint(Orientation.UP);
            }


            int current_Distance = board.calculateDistance(src, dest);

            if (current_Distance > max_Distance) {
                max_Distance = current_Distance;
            }

        }
        
        Util.threadSleep(max_Distance * animation_Duration + extra_Duration);
        gameData.resetFallData();
    }
}
// public static void fall(Board board, GameData gameData, BoardSceneController boardSceneController){
//     System.out.println("Fall Start");

//     Cell[][] grid = board.getGrid();
//     int[] fallData = gameData.getFallData(); //Number of Empty Cell per Column

//     int board_Col_Size = gameData.getBoard_Col_Size();
//     int startRow = (gameData.getBoard_Row_Size() - 1) - 1; // Start at 1 row away from the lowest row, walk upward

//     while (gameData.hasnotFall()) {

//         for (int col = 0; col < board_Col_Size; col++) {

//             if (fallData[col] > 0) {

//                 for (int row = startRow; row >= 0; row--) {

//                     if (grid[row][col].containPiece() 
//                         && grid[row+1][col].isPlayable()
//                         && ! grid[row + 1][col].containPiece()) {

//                         System.out.println("Fall at (" + row + ", " + col + ")");

//                         /*
//                         * If the current Cell has piece &
//                         * The Cell below is empty, then
//                         * the piece fall by one cell
//                         */

//                         BoardPoint src = new BoardPoint(row, col);
//                         BoardPoint dest = new BoardPoint(row + 1, col);

//                         board.swapPiece(src, dest);
//                         // boardSceneController.swapImage(src, dest);
//                         Platform.runLater(() -> boardSceneController.fallImage(src, dest));
//                     }
//                 }

//                 BoardPoint currentPoint = new BoardPoint(0, col);
//                 if (! board.any_piece(currentPoint)) {
//                     System.out.println("Generate New Piece");
//                     Piece newPiece = new Piece (Util.generateRandomPieceName());
                    
//                     board.setPiece(currentPoint, newPiece);
//                     // boardSceneController.setPieceImageAt(newPiece.getImagePath(), currentPoint);
//                     // Platform.runLater(() -> boardSceneController.setPieceImageAt(newPiece.getImagePath(), currentPoint));
//                     Platform.runLater(() -> boardSceneController.generatePieceViewAt(newPiece.getImagePath(), currentPoint));
//                 }
                
//                 fallData[col]--;
//             }
//         }

//         //!The Images only changed after the sleep finish
//         //Wait for the drop animation to finish
//         Util.threadSleep(extra_Duration);
//     }
//     gameData.resetFallData();
// }



    // public static Task<Void> fall(Board board, GameData gameData, BoardSceneController view){
    //     return new Task<>() {

    //         @Override
    //         protected Void call() throws Exception {

    //             System.out.println("Fall Start");
        
    //             Cell[][] grid = board.getGrid();
    //             int[] fallData = gameData.getFallData(); //Number of Empty Cell per Column
        
    //             int board_Col_Size = gameData.getBoard_Col_Size();
    //             int startRow = (gameData.getBoard_Row_Size() - 1) - 1; // Start at 1 row away from the lowest row, walk upward
        
    //             while (gameData.hasnotFall()) {
        
    //                 for (int col = 0; col < board_Col_Size; col++) {
        
    //                     if (fallData[col] > 0) {
        
    //                         for (int row = startRow; row >= 0; row--) {
            
    //                             if (grid[row][col].containPiece() 
    //                                 && grid[row+1][col].isPlayable()
    //                                 && ! grid[row + 1][col].containPiece()) {
        
    //                                 System.out.println("Fall at (" + row + ", " + col + ")");
            
    //                                 /*
    //                                 * If the current Cell has piece &
    //                                 * The Cell below is empty, then
    //                                 * the piece fall by one cell
    //                                 */
            
    //                                 BoardPoint src = new BoardPoint(row, col);
    //                                 BoardPoint dest = new BoardPoint(row + 1, col);
            
    //                                 board.swapPiece(src, dest);
    //                                 // view.swapImage(src, dest);
    //                                 Platform.runLater(() -> view.swapImage(src, dest));
    //                             }
    //                         }
        
    //                         // Generate New Piece
    //                         BoardPoint currentPoint = new BoardPoint(0, col);
    //                         if (! board.any_piece(currentPoint)) {
    //                             System.out.println("Generate New Piece");
    //                             Piece newPiece = new Piece (Util.generateRandomPieceName());
                                
    //                             board.setPiece(currentPoint, newPiece);
    //                             // view.setPieceImageAt(newPiece.getImagePath(), currentPoint);
    //                             Platform.runLater(() -> view.setPieceImageAt(newPiece.getName(), currentPoint));
    //                         }
                            
    //                         fallData[col]--;
    //                     }
    //                 }
        
    //                 //!The Images only changed after the sleep finish
    //                 //Wait for the drop animation to finish
    //                 // System.out.println(board.txtBoard());
    //                 // System.out.println();
    //                 // Util.pauseExecution(gameData.get_Animation_Duration_In_Millis());
    //             }
    //             gameData.resetFallData();
    //             return null;
    //         }

    //     };
    // }

    // public static void fall(Board board, GameData gameData, BoardSceneController view){
    //     System.out.println("Fall Start");

    //     Cell[][] grid = board.getGrid();
    //     int[] fallData = gameData.getFallData(); //Number of Empty Cell per Column

    //     int board_Col_Size = gameData.getBoard_Col_Size();
    //     int startRow = (gameData.getBoard_Row_Size() - 1) - 1; // Start at 1 row away from the lowest row, walk upward

    //     while (gameData.hasnotFall()) {

    //         for (int col = 0; col < board_Col_Size; col++) {

    //             if (fallData[col] > 0) {

    //                 for (int row = startRow; row >= 0; row--) {
    
    //                     if (grid[row][col].containPiece() 
    //                         && grid[row+1][col].isPlayable()
    //                         && ! grid[row + 1][col].containPiece()) {

    //                         System.out.println("Fall at (" + row + ", " + col + ")");
    
    //                         /*
    //                         * If the current Cell has piece &
    //                         * The Cell below is empty, then
    //                         * the piece fall by one cell
    //                         */
    
    //                         BoardPoint src = new BoardPoint(row, col);
    //                         BoardPoint dest = new BoardPoint(row + 1, col);
    
    //                         board.swapPiece(src, dest);
    //                         // view.swapImage(src, dest);
    //                         Platform.runLater(() -> view.swapImage(src, dest));
    //                     }
    //                 }

    //                 BoardPoint currentPoint = new BoardPoint(0, col);
    //                 if (! board.any_piece(currentPoint)) {
    //                     System.out.println("Generate New Piece");
    //                     Piece newPiece = new Piece (Util.generateRandomPieceName());
                        
    //                     board.setPiece(currentPoint, newPiece);
    //                     // view.setPieceImageAt(newPiece.getImagePath(), currentPoint);
    //                     Platform.runLater(() -> view.setPieceImageAt(newPiece.getImagePath(), currentPoint));
    //                 }
                    
    //                 fallData[col]--;
    //             }
    //         }

    //         //!The Images only changed after the sleep finish
    //         //Wait for the drop animation to finish
    //         Util.pauseExecution(gameData.get_Animation_Duration_In_Millis());
    //     }

    //     gameData.resetFallData();
    // }
    

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