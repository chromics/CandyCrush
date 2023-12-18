package data.constant;

public enum Orientation {
    UP(-1,0), DOWN(1,0), 
    LEFT(0,-1), RIGHT(0,1),
    UPLEFT(-1,-1), UPRIGHT(-1,1),
    DOWNLEFT(1,-1), DOWNRIGHT(1,1);


    private int rowChange;
    private int colChange;

    Orientation(int rowChange, int colChange){
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    public int getRowChange(){ return rowChange; }
    public int getColChange(){ return colChange; }
}
