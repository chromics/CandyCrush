package model.piece;

import java.io.*;

import data.constant.Constant;
import data.MatchData;

public class Piece implements Serializable {
    protected String name;
    protected String imagePath;

    public Piece(String name){
        this.name = name;
        this.imagePath = Constant.fruitsHashMap.get(name);
    }

    public String getName() { return name; }
    public String getImagePath() { return imagePath; }

    // public abstract MatchData 
}
