package model;

import java.io.*;

import data.constant.Status;
import model.piece.*;

public class Cell implements Serializable {
    private Piece piece;
    private Status playable_Status = Status.PLAYABLE;
    private Status ice_Block_Status = Status.NO_ICE_BLOCK;

    //---------------------------------------------------------------
    // Status
    //---------------------------------------------------------------
    public void set_Playable_Status (Status playable_Status) {
        this.playable_Status = playable_Status;
    }

    public Status get_Playable_Status () {
        return playable_Status;
    }

    public void setNotPlayable () {
        this.playable_Status = Status.NOTPLAYABLE;
    }
    
    public boolean isPlayable () {
        return playable_Status == Status.PLAYABLE;
    }

    public boolean containPiece () {
        if(isPlayable() && this.piece != null){
            return true;
        }
        return false;
    }
    //===============================================================
    
    
    //---------------------------------------------------------------
    // Ice Block Status
    //---------------------------------------------------------------
    public boolean has_Ice_Block () {
        return (ice_Block_Status == Status.HAS_ICE_BLOCK);
    }
    public void add_Ice_Block () {
        ice_Block_Status = Status.HAS_ICE_BLOCK;
    }
    public void remove_Ice_Block () {
        ice_Block_Status = Status.NO_ICE_BLOCK;
    }
    public void set_Ice_Block_Status (Status ice_Block_Status) {
        this.ice_Block_Status = ice_Block_Status;
    }
    public Status get_Ice_Block_Status () {
        return ice_Block_Status;
    }
    //===============================================================
    
    
    //---------------------------------------------------------------
    // Piece
    //---------------------------------------------------------------
    public Piece getPiece () { 
        return piece;
    }
    
    public void setPiece (Piece piece) {
        this.piece = piece;
    }

    public void setPiece (String pieceName) {

        if (pieceName != null) {

            if (pieceName.equals("sleighCat")) {
                this.piece = new HorizontalBomb();
            }
            else if (pieceName.equals("sackCat")) {
                this.piece = new VerticalBomb();
            }
            else {
                this.piece = new Piece(pieceName);
            }
        }
    }

    public void removePiece () {
        this.piece = null;
    }

    public boolean equalPiece (Cell otherCell) {
        if (this.containPiece() && otherCell.containPiece()) {
            if (this.containSpecialPiece()|| otherCell.containSpecialPiece()) {
                return true;
            }
            return (this.piece.getName().equals(otherCell.getPiece().getName()));
        }
        return false;
    }

    public boolean containSpecialPiece () {
        return (this.containPiece()
                && this.getPiece().getType() == Status.SPECIALPIECE);
    }
    //===============================================================
    
}
