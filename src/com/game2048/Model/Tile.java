package com.game2048.Model;

import java.awt.image.BufferedImage;

import com.game2048.View.Render;

public class Tile {

    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;
    public static final int SLIDE_SPEED = 20;
    public static final int ARC_WIDTH = 15;
    public static final int ARC_HEIGHT = 15;

    public Render render = new Render();
    private com.game2048.Model.Point slideTo;
    public int x;
    public int y;

    private boolean canCombine = true; // ADDED

    public void setSlideTo(com.game2048.Model.Point slideTo) {
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
