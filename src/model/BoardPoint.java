package model;

import java.io.*;

import data.constant.Orientation;

public class BoardPoint implements Serializable {
    private final int row;
    private final int col;

    public BoardPoint(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public int hashCode() {
        return row + col;
    }

    @Override
    @SuppressWarnings("ALL")
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        BoardPoint temp = (BoardPoint) obj;
        return (temp.getRow() == this.row) && (temp.getCol() == this.col);
    }

    @Override
    public String toString() {
        return "("+row + ","+col+")";
    }

    public BoardPoint getAdjacentPoint(Orientation orientation){
        int destRow = this.row + orientation.getRowChange();
        int destCol = this.col + orientation.getColChange();

        return new BoardPoint(destRow, destCol);
    }
}
