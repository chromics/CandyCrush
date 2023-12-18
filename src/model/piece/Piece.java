package model.piece;

import data.constant.Constant;

public class Piece {
    protected String name;
    protected String imagePath;

    public Piece(String name){
        this.name = name;
        this.imagePath = Constant.fruitsHashMap.get(name);
    }

    public String getName() { return name; }
    public String getImagePath() { return imagePath; }
}
