package data.constant;

import java.io.*;

import model.Cell;

public enum MapTemplate implements Serializable{
    RECTANGULAR{
        @Override
        public void initiate_Unplayable_Cell(Cell[][] grid, int row, int col){
            // Do Nothing
            // Every Cell is Playable
        }
    }, 
    I{
        @Override
        public void initiate_Unplayable_Cell(Cell[][] grid, int row, int col){
            
        }
    }, 
    HEART{
        @Override
        public void initiate_Unplayable_Cell(Cell[][] grid, int row, int col){
            
        }
    }, 
    CIRCLE{
        @Override
        public void initiate_Unplayable_Cell(Cell[][] grid, int row, int col){
            
        }
    };

    public abstract void initiate_Unplayable_Cell(Cell[][] grid, int row, int col);
}
