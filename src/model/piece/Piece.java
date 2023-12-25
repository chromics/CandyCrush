package model.piece;

import java.io.*;

import data.GameData;
import data.MatchData;
import data.constant.Status;
import model.BoardPoint;
import data.constant.Constant;

public class Piece implements Serializable {
    private String name;
    private String imagePath;
    private final static Status TYPE = Status.NORMALPIECE;

    public Piece(String name){
        this.name = name;
        this.imagePath = Constant.fruitsHashMap.get(name);
    }

    public Piece(){}

    public String getName() { return name; }
    public String getImagePath() { return imagePath; }
    public Status getType() { return TYPE; }

    public void getEffect(GameData gameData, BoardPoint point) {}

}
