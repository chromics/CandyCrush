package model.piece;

import data.GameData;
import data.constant.Constant;
import data.constant.Orientation;
import data.constant.Status;
import model.BoardPoint;

public class HorizontalBomb extends Piece {
    private final static String NAME = "sleighCat";
    private final static String IMAGEPATH = Constant.specialPropsHashMap.get(NAME);
    private final static Status TYPE = Status.SPECIALPIECE;

    public HorizontalBomb () {}

    @Override
    public String getName () { return NAME; }

    @Override
    public String getImagePath () { return IMAGEPATH; }

    @Override
    public Status getType () { return TYPE; }

    @Override
    public void getEffect (GameData gameData, BoardPoint point) { 
        gameData.saveMatchData(new BoardPoint(point.getRow(), 0), gameData.getBoard_Row_Size(), Orientation.RIGHT); 
    }

    @Override
    public String toString () {
        return "Horizontal Bomb";
    }
}
