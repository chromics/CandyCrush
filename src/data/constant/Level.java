package data.constant;

public enum Level {
    //Level, Board_Row_Size, Board_Col_Size, TargetScore, Init Step, init Hints, init Shuffle, Map)
    //Default Game Mode
    LEVEL_1(0, 8, 8, 1000, 30, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_2(1, 8,8, 1500, 30, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_3(2, 8,8, 2000, 30, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_4(3, 8,8, 2500, 30, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_5(4, 8,8, 3000, 30, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_6(5, 8,8, 30, 35, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_7(6, 8,8, 4000, 1, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_8(7, 8,8, 4500, 40, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_9(8, 8,8, 5000, 40, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_10(9, 8,8, 5500, 40, -1, 3, MapTemplate.RECTANGULAR),
    
    //Christmas Game Mode
    LEVEL_C1(0, 8, 8, 2000, 40, -1, -1, MapTemplate.OCTAGON),
    LEVEL_C2(1, 7,7, 2250, 35, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C3(2, 9,9, 2500, 35, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C4(3, 7,9, 2750, 35, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C5(4, 9,7, 3000, 35, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C6(5, 10,10, 3250, 40, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C7(6, 8,8, 3500, 40, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C8(7, 8,8, 3750, 45, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C9(8, 8,8, 4000, 45, -1, 3, MapTemplate.RECTANGULAR),
    LEVEL_C10(9, 8,8, 4250, 50, -1, 3, MapTemplate.RECTANGULAR);

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
