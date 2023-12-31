package data;

import java.io.*;

import model.BoardPoint;
import data.constant.Orientation;

public class MatchData implements Serializable{
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
    public BoardPoint getPoint() { return point; }
    public Orientation getOrientation() { return orientation; }

    public String txtMatchData() {
        return String.format("%d %d %d %s", getRow(), getCol(), getLength(), orientation.toString());
    }

    @Override
    public String toString(){
        return String.format("Point : (%d,%d), Length : %d, Orientation : %s", getRow(), getCol(), getLength(), orientation.toString());
    }
}
