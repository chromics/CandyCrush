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
            grid[1][0].setNotPlayable();
            grid[2][0].setNotPlayable();
            grid[7][0].setNotPlayable();
            grid[8][0].setNotPlayable();
            grid[9][0].setNotPlayable();

            grid[0][1].setNotPlayable();
            grid[1][1].setNotPlayable();
            grid[8][1].setNotPlayable();
            grid[9][1].setNotPlayable();

            grid[0][2].setNotPlayable();
            grid[9][2].setNotPlayable();

            grid[0][7].setNotPlayable();
            grid[9][7].setNotPlayable();

            grid[0][8].setNotPlayable();
            grid[1][8].setNotPlayable();
            grid[8][8].setNotPlayable();
            grid[9][8].setNotPlayable();

            grid[0][9].setNotPlayable();
            grid[1][9].setNotPlayable();
            grid[2][9].setNotPlayable();
            grid[7][9].setNotPlayable();
            grid[8][9].setNotPlayable();
            grid[9][9].setNotPlayable();
        }
    }, 
    Y{
        @Override
        public void initiate_Unplayable_Cell(Cell[][] grid, int board_Row_Size, int board_Col_Size){
            grid[0][3].setNotPlayable();
            grid[0][4].setNotPlayable();
            grid[0][5].setNotPlayable();
            grid[0][6].setNotPlayable();

            grid[1][3].setNotPlayable();
            grid[1][4].setNotPlayable();
            grid[1][5].setNotPlayable();
            grid[1][6].setNotPlayable();

            grid[2][4].setNotPlayable();
            grid[2][5].setNotPlayable();

            grid[4][0].setNotPlayable();
            grid[4][9].setNotPlayable();

            grid[5][0].setNotPlayable();
            grid[5][1].setNotPlayable();
            grid[5][8].setNotPlayable();
            grid[5][9].setNotPlayable();

            grid[6][0].setNotPlayable();
            grid[6][1].setNotPlayable();
            grid[6][2].setNotPlayable();
            grid[6][7].setNotPlayable();
            grid[6][8].setNotPlayable();
            grid[6][9].setNotPlayable();

            grid[7][0].setNotPlayable();
            grid[7][1].setNotPlayable();
            grid[7][2].setNotPlayable();
            grid[7][7].setNotPlayable();
            grid[7][8].setNotPlayable();
            grid[7][9].setNotPlayable();

            grid[8][0].setNotPlayable();
            grid[8][1].setNotPlayable();
            grid[8][2].setNotPlayable();
            grid[8][7].setNotPlayable();
            grid[8][8].setNotPlayable();
            grid[8][9].setNotPlayable();

            grid[9][0].setNotPlayable();
            grid[9][1].setNotPlayable();
            grid[9][2].setNotPlayable();
            grid[9][7].setNotPlayable();
            grid[9][8].setNotPlayable();
            grid[9][9].setNotPlayable();
        }
    }, 
    T{
        @Override
        public void initiate_Unplayable_Cell(Cell[][] grid, int board_Row_Size, int board_Col_Size){
            grid[3][0].setNotPlayable();
            grid[3][1].setNotPlayable();
            grid[3][2].setNotPlayable();
            grid[3][7].setNotPlayable();
            grid[3][8].setNotPlayable();
            grid[3][9].setNotPlayable();

            grid[4][0].setNotPlayable();
            grid[4][1].setNotPlayable();
            grid[4][2].setNotPlayable();
            grid[4][7].setNotPlayable();
            grid[4][8].setNotPlayable();
            grid[4][9].setNotPlayable();

            grid[5][0].setNotPlayable();
            grid[5][1].setNotPlayable();
            grid[5][2].setNotPlayable();
            grid[5][7].setNotPlayable();
            grid[5][8].setNotPlayable();
            grid[5][9].setNotPlayable();

            grid[6][0].setNotPlayable();
            grid[6][1].setNotPlayable();
            grid[6][2].setNotPlayable();
            grid[6][7].setNotPlayable();
            grid[6][8].setNotPlayable();
            grid[6][9].setNotPlayable();

            grid[7][0].setNotPlayable();
            grid[7][1].setNotPlayable();
            grid[7][2].setNotPlayable();
            grid[7][7].setNotPlayable();
            grid[7][8].setNotPlayable();
            grid[7][9].setNotPlayable();

            grid[8][0].setNotPlayable();
            grid[8][1].setNotPlayable();
            grid[8][2].setNotPlayable();
            grid[8][7].setNotPlayable();
            grid[8][8].setNotPlayable();
            grid[8][9].setNotPlayable();

            grid[9][0].setNotPlayable();
            grid[9][1].setNotPlayable();
            grid[9][2].setNotPlayable();
            grid[9][7].setNotPlayable();
            grid[9][8].setNotPlayable();
            grid[9][9].setNotPlayable();
        }
    };

    public abstract void initiate_Unplayable_Cell(Cell[][] grid, int board_Row_Size, int board_Col_Size);
}
