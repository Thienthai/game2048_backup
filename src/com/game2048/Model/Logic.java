package com.game2048.Model;

import com.game2048.View.Keyboard;

import java.awt.event.KeyEvent;
import java.util.Random;

public class Logic {

    private static final int ROWS = 4;
    private static final int COLS = 4;

    private final int startingTiles = 2;

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
        start();
    }

    private void start(){
        for(int i = 0; i < startingTiles; i++){
            spawnRandom();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void spawnRandom(){
        Random random = new Random();
        boolean notValid = true;

        while(notValid){
            int location = random.nextInt(ROWS * COLS);
            int row = location / ROWS;
            int col = location % COLS;
            Tile current = board[row][col];
            if(current == null){
                int value = random.nextInt(10) < 9 ? 2 : 4;
                Tile tile = new Tile(value, getTileX(col),getTileY(row));
                board[row][col] = tile;
                notValid = false;
            }
        }
    }

    public int getTileX(int col){
        return SPACING + col * Tile.WIDTH + col * SPACING;
    }

    public int getTileY(int row){
        return SPACING + row * Tile.HEIGHT + row * SPACING;
    }


    public void update(){
        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLS; col++){
                Tile current = board[row][col];
                if(current == null) continue;
                resetPosition(current, row,col);
                if(current.getValue() == 2048){
                    won = true;
                }
            }
        }
    }

    private void resetPosition(Tile current, int row, int col){
        if(current == null) return;

        int x = getTileX(col);
        int y = getTileY(row);

        int disX = current.getX() - x;
        int disY = current.getY() - y;

        if(Math.abs(disX) < Tile.SLIDE_SPEED){
            current.setX(current.getX() - disX);
        }

        if(Math.abs(disY) < Tile.SLIDE_SPEED){
            current.setY(current.getY() - disY);
        }

        if(disX < 0){
            current.setX(current.getX() + Tile.SLIDE_SPEED);
        }

        if(disY < 0){
            current.setY(current.getY() + Tile.SLIDE_SPEED);
        }
        if(disX > 0){
            current.setX(current.getX() - Tile.SLIDE_SPEED);
        }
        if(disY > 0){
            current.setY(current.getY() - Tile.SLIDE_SPEED);
        }

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
            spawnRandom();
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

    private void checkKeys(){ // ใช้ฟังชั่นนี้เชื่อมระหว่าง listener กับ logic
        if(Keyboard.typed(KeyEvent.VK_LEFT)){
            //move tiles left
            moveTiles(Direction.LEFT);
            if(!hasStarted)hasStarted = true;
        }
        if(Keyboard.typed(KeyEvent.VK_RIGHT)){
            //move tiles right
            moveTiles(Direction.RIGHT);
            if(!hasStarted)hasStarted = true;
        }
        if(Keyboard.typed(KeyEvent.VK_UP)){
            //move tiles up
            moveTiles(Direction.UP);
            if(!hasStarted)hasStarted = true;
        }
        if(Keyboard.typed(KeyEvent.VK_DOWN)){
            //move tiles down
            moveTiles(Direction.DOWN);
            if(!hasStarted)hasStarted = true;
        }
    }

}
