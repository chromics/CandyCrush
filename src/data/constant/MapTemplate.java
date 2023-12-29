package data.constant;

import java.io.*;

import model.Cell;

public enum MapTemplate implements Serializable{
    RECTANGULAR{
        @Override
        public void initiate_Unplayable_Cell(Cell[][] grid, int board_Row_Size, int board_Col_Size){
            // Do Nothing
            // Every Cell is Playable
            // grid[2][2].setNotPlayable();
            // grid[3][2].setNotPlayable();
            // grid[5][2].setNotPlayable();
            // grid[2][4].setNotPlayable();
            // grid[3][4].setNotPlayable();
            // grid[5][4].setNotPlayable();
        }
    }, 
    I{
        @Override
        public void initiate_Unplayable_Cell(Cell[][] grid, int board_Row_Size, int board_Col_Size){
            
        }
    }, 
    HEART{
        @Override
        public void initiate_Unplayable_Cell(Cell[][] grid, int board_Row_Size, int board_Col_Size){
            
        }
    }, 
    CIRCLE{
        @Override
        public void initiate_Unplayable_Cell(Cell[][] grid, int board_Row_Size, int board_Col_Size){
            
        }
    };

    public abstract void initiate_Unplayable_Cell(Cell[][] grid, int board_Row_Size, int board_Col_Size);
}
