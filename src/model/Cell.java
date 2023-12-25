package model;

import java.io.*;

import data.constant.Status;
import model.piece.*;

public class Cell implements Serializable{
    private Piece piece;
    private Status status = Status.PLAYABLE;

    //---------------------------------------------------------------
    // Status
    //---------------------------------------------------------------
    public void setStatus(Status status){
        this.status = status;
    }
    
    public boolean isPlayable(){
        return status == Status.PLAYABLE;
    }

    public boolean containPiece(){
        if(isPlayable() && this.piece != null){
            return true;
        }
        return false;
    }

    //===============================================================
    
    
    //---------------------------------------------------------------
    // Piece
    //---------------------------------------------------------------
    public Piece getPiece(){ 
        return piece;
    }
    
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    
    public void removePiece() {
        this.piece = null;
    }
    
    public boolean equalPiece (Cell otherCell) {
        if (this.containPiece() && otherCell.containPiece()) {
//            if (this.containSpecialPiece()|| otherCell.containSpecialPiece()) {
//                return true;
//            }
            return (this.piece.getName().equals(otherCell.getPiece().getName()));
        }
        return false;
    }

//    public boolean containSpecialPiece(){
//        return (this.getPiece().getType() == Status.SPECIALPIECE);
//    }
    //===============================================================
    
}
