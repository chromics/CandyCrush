package model.components;

import java.io.Serializable;
/**
 * This class describe the slot for Chess in Chessboard
 * */
public class Cell implements Serializable {
    // the position for chess
    private ChessPiece piece;
    private Status status = Status.PLAYABLE;

    public void setPlayable(Status status){
        this.status = status;
    }

    public boolean isPlayable(){
        if(status == Status.PLAYABLE){
            return true;
        }
        return false;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }

    public boolean equal(Cell otherPiece){
        return (this.piece.getName().equals(otherPiece.getPiece().getName()));
    }
}