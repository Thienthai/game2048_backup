package com.game2048.Action;

import java.awt.image.BufferedImage;

import com.game2048.View.Render;

public class Tile {


    public static final int ROWS = 4;
    public static final int COLS = 4;
    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;
    public static final int SLIDE_SPEED = 20;
    public static final int ARC_WIDTH = 15;
    public static final int ARC_HEIGHT = 15;
    private static int SPACING = 10;
    public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;

    public Render render = new Render();
    private BufferedImage gameBoard;
    private com.game2048.Action.Point slideTo;
    public int x;
    public int y;

    private boolean canCombine = true; // ADDED

    public com.game2048.Action.Point getSlideTo() {
        return slideTo;
    }

    public void setSlideTo(com.game2048.Action.Point slideTo) {
        this.slideTo = slideTo;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Tile(int value, int x, int y){
        render.value = value;
        this.x = x;
        this.y = y;
        slideTo = new Point(x,y);
        render.tileImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        render.drawImage();
    }


    public void update(){

    }

    public int getValue(){
        return render.value;
    }

    public void setValue(int value){
        render.value = value;
        render.drawImage();
    }

    public boolean canCombine(){
        return canCombine;
    }

    public void setCanCombine(boolean canCombine){
        this.canCombine = canCombine;
    }

}
