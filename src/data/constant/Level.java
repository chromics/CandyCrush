package data.constant;

public enum Level {
    //Level, Board_Row_Size, Board_Col_Size, TargetScore, Init Step, init Hints, init Shuffle, Map)
    //Default Game Mode
    Level_Custom(-1,10,10,300,20,-1,-1,MapTemplate.RECTANGULAR),
    LEVEL_1(0, 8, 8, 500, 30, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_2(1, 8,8, 750, 30, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_3(2, 8,8, 750, 30, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_4(3, 8,8, 1000, 5, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_5(4, 8,8, 1000, 5, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_6(5, 8,8, 150, 5, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_7(6, 8,8, 4000, 4, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_8(7, 8,8, 4500, 40, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_9(8, 8,8, 5000, 40, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_10(9, 8,8, 50, 40, -1, 3, MapTemplate.RECTANGULAR),
    
    //Christmas Game Mode
    Level_C_Custom(-1,10,10,200,40,-1,-1,MapTemplate.RECTANGULAR),
    LEVEL_C1(0, 10, 10, 200, 40, -1, -1, MapTemplate.OCTAGON),
    LEVEL_C2(1, 7,7, 200, 35, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C3(2, 9,9, 1500, 5, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C4(3, 7,9, 25000, 5, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C5(4, 9,7, 2500, 5, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C6(5, 10,10, 2750, 40, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C7(6, 8,8, 2750, 40, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C8(7, 8,8, 150, 45, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C9(8, 8,8, 3000, 4, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C10(9, 10,10, 3000, 50, -1, 3, MapTemplate.T);

    private int levelIndex;
    private int board_Row_Size;
    private int board_Col_Size;
    private int targetScore;
    private int initStep;
    private int initHints;
    private int initShuffle;
    private MapTemplate mapTemplate;

    Level ( int levelIndex, int board_Row_Size, int board_Col_Size, int targetScore, 
            int initStep, int initHints, int initShuffle, MapTemplate mapTemplate){
            
        this.levelIndex = levelIndex;
        this.board_Row_Size = board_Row_Size;
        this.board_Col_Size = board_Col_Size;
        this.targetScore = targetScore;
        this.initStep = initStep;
        this.initHints = initHints;
        this.initShuffle = initShuffle;
        this.mapTemplate = mapTemplate;
    }

    public int getLevel() { return levelIndex + 1; }
    public int getLevelIndex() { return levelIndex; }
    public int getBoard_Row_Size() { return board_Row_Size; }
    public int getBoard_Col_Size() { return board_Col_Size; }
    public int getTargetScore() { return targetScore; }
    public int getInitStep() { return initStep; }
    public int getInitHints() { return initHints; }
    public int getInitShuffle() { return initShuffle; }
    public MapTemplate getMapTemplate() { return mapTemplate; }
}
