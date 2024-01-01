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
    OCTAGON{
        @Override
        public void initiate_Unplayable_Cell(Cell[][] grid, int board_Row_Size, int board_Col_Size){
                grid[0][0].setNotPlayable();
                grid[0][1].setNotPlayable();
                grid[1][0].setNotPlayable();

                grid[board_Row_Size - 1][0].setNotPlayable();
                grid[board_Row_Size - 2][0].setNotPlayable();
                grid[board_Row_Size - 1][1].setNotPlayable();

                grid[0][board_Col_Size - 1].setNotPlayable();
                grid[0][board_Col_Size - 2].setNotPlayable();
                grid[1][board_Col_Size - 1].setNotPlayable();

                grid[board_Row_Size - 1][board_Col_Size - 1].setNotPlayable();
                grid[board_Row_Size - 2][board_Col_Size - 1].setNotPlayable();
                grid[board_Row_Size - 1][board_Col_Size - 2].setNotPlayable();
        }
    }, 
    M{
        @Override
        public void initiate_Unplayable_Cell(Cell[][] grid, int board_Row_Size, int board_Col_Size){
            // grid[]
        }
    }, 
    T{
        @Override
        public void initiate_Unplayable_Cell(Cell[][] grid, int board_Row_Size, int board_Col_Size){
            
        }
    };

    public abstract void initiate_Unplayable_Cell(Cell[][] grid, int board_Row_Size, int board_Col_Size);
}
