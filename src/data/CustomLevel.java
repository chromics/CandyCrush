package data;

import data.constant.MapTemplate;

public class CustomLevel {
    private int board_Row_Size;
    private int board_Col_Size;
    private MapTemplate mapTemplate;
    private int targetScore;
    private int initShuffle;
    private int initStep;

    public CustomLevel (MapTemplate mapTemplate, int board_Row_Size, int board_Col_Size,
                        int targetScore, int initStep, int initShuffle) {

        this.mapTemplate = mapTemplate;
        this.board_Row_Size = board_Row_Size;
        this.board_Col_Size = board_Col_Size;
        this.targetScore = targetScore;
        this.initStep = initStep;
        this.initShuffle = initShuffle;
    }

    public MapTemplate getMapTemplate () { return mapTemplate; }
    public int getBoard_Row_Size () { return board_Row_Size; }
    public int getBoard_Col_Size () { return board_Col_Size; }
    public int getTargetScore () { return targetScore; }
    public int getInitStep () { return initStep; }
    public int getInitShuffle () { return initShuffle; }
}
