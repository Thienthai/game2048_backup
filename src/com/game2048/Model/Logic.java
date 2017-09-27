package com.game2048.Model;

import com.game2048.Controller.Listener;
import com.game2048.View.Keyboard;

import java.awt.event.KeyEvent;

public class Logic {

    private static final int ROWS = 4;
    private static final int COLS = 4;
    public Tile[][] getBoard() {
        return board;
    }
    private Tile[][] board;
    private boolean dead;
    private boolean won;
    private int x;
    private int y;
    private static int SPACING = 10;
    public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;
    private boolean hasStarted;

    public Logic(int x, int y){
        this.x = x;
        this.y = y;
        board = new Tile[ROWS][COLS];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private boolean move(int row, int col, int horizontalDirection, int verticalDirection, Direction dir){
        boolean canMove = false;
        Tile current = board[row][col];
        if(current == null) return false;
        boolean move = true;
        int newCol = col;
        int newRow = row;
        while(move){
            newCol += horizontalDirection;
            newRow += verticalDirection;
            if(checkOutOfBounds(dir,newRow,newCol)) break;
            if(board[newRow][newCol] == null){
                board[newRow][newCol] = current;
                board[newRow - verticalDirection][newCol - horizontalDirection] = null;
                board[newRow][newCol].setSlideTo(new Point (newRow,newCol));
                canMove = true;
            }
            else if(board[newRow][newCol].getValue() == current.getValue() && board[newRow][newCol].canCombine()){
                board[newRow][newCol].setCanCombine(false);
                board[newRow][newCol].setValue(board[newRow][newCol].getValue() * 2);
                canMove = true;
                board[newRow - verticalDirection][newCol - horizontalDirection] = null;
                board[newRow][newCol].setSlideTo(new Point (newRow,newCol));

            }else{
                move = false;
            }
        }

        return canMove;
    }

    private boolean checkOutOfBounds(Direction dir, int row, int col){
        if(dir == Direction.LEFT){
            return col < 0;
        }else if(dir == Direction.RIGHT){
            return col > COLS - 1;
        }else if(dir == Direction.UP){
            return row < 0;
        }else if(dir == Direction.DOWN){
            return row > ROWS - 1;
        }
        return false;
    }

    public void moveTiles(Direction dir){
        boolean canMove = false;
        int horizontalDirection = 0;
        int verticalDirection = 0;

        if(dir == Direction.LEFT){
            horizontalDirection = -1;
            for(int row = 0; row < ROWS; row++){
                for(int col = 0; col < COLS; col++){
                    if(!canMove){
                        canMove = move(row,col,horizontalDirection,verticalDirection,dir);
                    }else{
                        move(row,col,horizontalDirection,verticalDirection,dir);
                    }
                }
            }
        }

        else if(dir == Direction.RIGHT){
            horizontalDirection = 1;
            for(int row = 0; row < ROWS; row++){
                for(int col = COLS - 1; col >= 0; col--){
                    if(!canMove){
                        canMove = move(row,col,horizontalDirection,verticalDirection,dir);
                    }else{
                        move(row,col,horizontalDirection,verticalDirection,dir);
                    }
                }
            }
        }

        else if(dir == Direction.UP){
            verticalDirection = -1;
            for(int row = 0; row < ROWS; row++){
                for(int col = 0; col < COLS; col++){
                    if(!canMove){
                        canMove = move(row,col,horizontalDirection,verticalDirection,dir);
                    }else{
                        move(row,col,horizontalDirection,verticalDirection,dir);
                    }
                }
            }
        }

        else if(dir == Direction.DOWN){
            verticalDirection = 1;
            for(int row = ROWS - 1; row >= 0; row--){
                for(int col = 0; col < COLS; col++){
                    if(!canMove){
                        canMove = move(row,col,horizontalDirection,verticalDirection,dir);
                    }else{
                        move(row,col,horizontalDirection,verticalDirection,dir);
                    }
                }
            }
        }

        else{

            System.out.println(dir + " is not a valid direction.");

        }

        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLS; col++){
                Tile current = board[row][col];
                if(current == null) continue;
                current.setCanCombine(true);
            }
        }

        if(canMove){
            Listener listener = new Listener();
            listener.spawn_listener(board);
            // check dead
            checkDead();
        }

    }

    private void checkDead(){
        for(int row = 0; row < ROWS; row++){
            for(int col = 0;col < COLS; col++){
                if(board[row][col] == null) return;
                if(checkSurroundingTiles(row,col,board[row][col])){
                    return;
                }
            }
        }
    }

    private boolean checkSurroundingTiles(int row,int col, Tile current){
        if(row > 0){
            Tile check = board[row - 1][col];
            if(check == null) return  true;
            if(current.getValue() == check.getValue()) return true;
        }
        if(row < ROWS - 1){
            Tile check = board[row+1][col];
            if(check == null)return  true;
            if(current.getValue() == check.getValue()) return  true;
        }
        if(col > 0){
            Tile check = board[row][col - 1];
            if(check == null)return  true;
            if(current.getValue() == check.getValue()) return  true;
        }
        if(col < COLS - 1){
            Tile check = board[row][col + 1];
            if(check == null)return  true;
            if(current.getValue() == check.getValue()) return  true;
        }
        return false;
    }

}
