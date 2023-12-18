package data;

import java.util.ArrayList;

import model.BoardPoint;
import data.constant.Orientation;

public class GameData {
    private ArrayList<MatchData> matchDatas;
    private int score;
    private int hintsLeft;
    private int shuffleLeft;
    private int targetScore;

    public GameData(){
        matchDatas = new ArrayList<>();
        score = 0;
    }

    
    
    //---------------------------------------------------------------
    // Getter
    //---------------------------------------------------------------
    public int getScore(){ return score; }
    public ArrayList<MatchData> getMatchDatas(){ return matchDatas; }
    //===============================================================
    
    
    //---------------------------------------------------------------
    // Match Data
    //---------------------------------------------------------------
    public MatchData popMatchData(){
        if(matchDatas == null){
            return null;
        }
        
        MatchData lastElement = matchDatas.remove(matchDatas.size() - 1);
        return lastElement;
    }
    
    public void saveMatchData(BoardPoint point, int length, Orientation orientation){
        matchDatas.add(new MatchData(point, length, orientation));
    }
    
    public int getScoreGained(){
        int scoreGained = 0;
        for(MatchData matchData : matchDatas){
            scoreGained += matchData.getLength() * 10;
        }
        return scoreGained;
    }
    //===============================================================


}
