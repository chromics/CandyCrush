package model;

import java.io.*;

import mechanism.CreateBoard;
import mechanism.CreatePiece;
import model.piece.*;
import data.constant.Orientation;
import data.constant.MapTemplate;


public class Board implements Serializable{
    private Cell[][] grid;
    private int board_Row_Size;
    private int board_Col_Size;
    private MapTemplate mapTemplate;

    
    
    //-----------------------------------------------------------------------------------------------
    // Constructor & Initiator
    //-----------------------------------------------------------------------------------------------
    public Board(int board_Row_Size, int board_Col_Size, MapTemplate mapTemplate) {
        this.board_Row_Size = board_Row_Size;
        this.board_Col_Size = board_Col_Size;
        this.mapTemplate = mapTemplate;
    }
    
    public void initBoard(){
        this.grid = new Cell[board_Row_Size][board_Col_Size];
        
        initGrid();
        mapTemplate.initiate_Unplayable_Cell(grid, board_Row_Size, board_Col_Size);
        
        initPieces();
    }
    
    public void initGrid() {
        for (int i = 0; i < board_Row_Size; i++) {
            for (int j = 0; j < board_Col_Size; j++) {
                grid[i][j] = new Cell();
            }
        }
    }
    
    public void initPieces() {
        CreateBoard.initiateBoard(this);
    }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Pieces Methods
    //-----------------------------------------------------------------------------------------------
    public void generatePieceAt(BoardPoint point){
        Piece newPiece = new Piece(CreatePiece.createPiece());
        setPiece(point, newPiece);
    }
    
    public Piece getPieceAt(BoardPoint point) {
        return getGridAt(point).getPiece();
    }

    public Piece removePiece(BoardPoint point) {
        Piece piece = getPieceAt(point);
        getGridAt(point).removePiece();
        return piece;
    }

    public void setPiece(BoardPoint point, Piece piece) {
        getGridAt(point).setPiece(piece);
    }

    public void swapPiece(BoardPoint point1, BoardPoint point2) {
        var p1 = getPieceAt(point1);
        var p2 = getPieceAt(point2);
        setPiece(point1, p2);
        setPiece(point2, p1);
    }

    public boolean equalPieceAt(BoardPoint point1, BoardPoint point2){
        Cell c1 = getGridAt(point1);
        Cell c2 = getGridAt(point2);

        return (c1.equalPiece(c2));
    }

    public Piece get_Adjacent_Piece(BoardPoint point, Orientation orientation){
        return getPieceAt(point.getAdjacentPoint(orientation));
    }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Grid Methods
    //-----------------------------------------------------------------------------------------------
    public Cell getGridAt(BoardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }
    
    public Cell[][] getGrid() {
        return grid;
    }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // BoardPoint Methods
    //-----------------------------------------------------------------------------------------------
    public int calculateDistance (BoardPoint src, BoardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    public int getDistanceX (BoardPoint src, BoardPoint dest) {
        return (src.getCol() - dest.getCol());
    }

    public int getDistanceY (BoardPoint src, BoardPoint dest) {
        return (src.getRow() - dest.getRow());
    }
    
    public boolean is_Within_Boundary (BoardPoint point) {
        return (point.getRow() >= 0 
        && point.getRow() < board_Row_Size
        && point.getCol() >= 0
        && point.getCol() < board_Col_Size);
    }
    
    public boolean is_Equal_To_Adjacent (BoardPoint currentPoint, Orientation orientation) {
        BoardPoint nextPoint = currentPoint.getAdjacentPoint( orientation );
        return equalPieceAt( currentPoint, nextPoint );
    }
    
    public Cell get_Adjacent_Cell(BoardPoint point, Orientation orientation){
        return getGridAt(point.getAdjacentPoint(orientation));
    }

    public boolean is_Cell_Playable(BoardPoint point){
        return getGridAt(point).isPlayable();
    }

    public boolean any_piece(BoardPoint point){
        return getGridAt(point).containPiece();
    }
    //===============================================================================================
    
    
    //-----------------------------------------------------------------------------------------------
    // Getter
    //-----------------------------------------------------------------------------------------------
    public int get_Board_Row_Size(){ return board_Row_Size; }
    public int get_Board_Col_Size(){ return board_Col_Size; }
    //===============================================================================================
    

    @Override
    public String toString(){
        String res = "Board :";
        if(grid == null){
            res += "null\n";
        }
        else{
            for(int row = 0; row < board_Row_Size; row++){
                res += "\n";
                for(int col = 0; col < board_Row_Size; col++){
                    res += grid[row][col].getPiece().getImagePath() + " ";
                }
            }
            res += "\n";
        }
        return res;
    }
}
