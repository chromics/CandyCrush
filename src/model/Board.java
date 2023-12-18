package model;

import mechanism.CreateBoard;
import model.piece.*;
import data.Constant;


public class Board {
    private Cell[][] grid;

    public Board() {
        this.grid = new Cell[Constant.BOARD_ROW_SIZE.getNum()][Constant.BOARD_COL_SIZE.getNum()];
        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.BOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.BOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces() {
        CreateBoard.initiateBoard(grid);
    }

    public Piece getPieceAt(BoardPoint point) {
        return getGridAt(point).getPiece();
    }

    public Cell getGridAt(BoardPoint point) {
        return grid[point.getRow()][point.getCol()];
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

    public int calculateDistance(BoardPoint src, BoardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    public Cell[][] getGrid() {
        return grid;
    }
}
