package model.piece;

import data.GameData;
import data.constant.Constant;
import data.constant.Orientation;
import data.constant.Status;
import model.BoardPoint;

public class VerticalBomb extends Piece {
    private final static String NAME = "sackCat";
    private final static String IMAGEPATH = Constant.specialPropsHashMap.get(NAME);
    private final static Status TYPE = Status.SPECIALPIECE;

    public VerticalBomb () {}

    @Override
    public String getName () { return NAME; }

    @Override
    public String getImagePath () { return IMAGEPATH; }

    @Override
    public Status getType () { return TYPE; }

    @Override
    public void getEffect (GameData gameData, BoardPoint point) { 
        gameData.saveMatchData (new BoardPoint(0, point.getCol()), gameData.getBoard_Row_Size(), Orientation.DOWN); 
    }
    
    @Override
    public String toString () {
        return "Vertical Bomb";
    }
}
