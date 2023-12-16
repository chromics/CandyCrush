package model.components;

public class ChessPiece {
    private String name;
    private String imagePath;

    public ChessPiece(String name){
        this.name = name;
        this.imagePath = Constant.FRUITS.get(name);
    }

    public String getName(){ return name; }
    public String getImagePath(){ return imagePath; }
}
