package data;

import model.BoardPoint;
import data.constant.Orientation;

public class MatchData {
    private BoardPoint point;
    private int length;
    private Orientation orientation;

    public MatchData(BoardPoint point, int length, Orientation orientation){
        this.point = point;
        this.length = length;
        this.orientation = orientation;
    }

    public int getRow() { return point.getRow(); }
    public int getCol() { return point.getCol(); }
    public int getLength() { return length; }
    public int getRowChange() { return orientation.getRowChange(); }
    public int getColChange() { return orientation.getColChange(); }
}
