package data.constant;

public enum Level {
    //Level, Board_Row_Size, Board_Col_Size, TargetScore, Init Step, init Hints, init Shuffle, Map)
    //Default Game Mode
    LEVEL_1(0, 8, 8, 1000, 30, 5, 3, MapTemplate.RECTANGULAR),
    LEVEL_2(1, 8,8, 1500, 30, 5, 3, MapTemplate.RECTANGULAR),
    
    //Christmas Game Mode
    LEVEL_C1(0, 8, 8, 2000, 40, 5, 4, MapTemplate.RECTANGULAR),
    LEVEL_C2(1, 8,8, 2500, 35, 5, 3, MapTemplate.RECTANGULAR);

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
