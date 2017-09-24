package com.game2048.View;

import com.game2048.Action.Tile;
import com.game2048.Controller.DrawUtils;
import com.game2048.Controller.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Render {
    public BufferedImage tileImage;
    private Color background;
    private Color text;
    public int value;
    private Font font;



    private static final int ROWS = 4;
    private static final int COLS = 4;
    private static int SPACING = 10;
    private static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    private static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;
    private BufferedImage gameBoard = new BufferedImage(BOARD_WIDTH,BOARD_HEIGHT,BufferedImage.TYPE_INT_RGB);
    private BufferedImage finalBoard = new BufferedImage(BOARD_WIDTH,BOARD_HEIGHT,BufferedImage.TYPE_INT_RGB);


    public void createBoardImage(BufferedImage gameBoard){
        Graphics2D g = (Graphics2D) gameBoard.getGraphics();
        g.setColor(Color.darkGray);
        g.fillRect(0,0,BOARD_WIDTH,BOARD_HEIGHT);
        g.setColor(Color.lightGray);

        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLS; col++){
                int x = SPACING + SPACING * col + Tile.WIDTH * col;
                int y = SPACING + SPACING * row + Tile.HEIGHT * row;
                g.fillRoundRect(x,y,Tile.WIDTH,Tile.HEIGHT,Tile.ARC_WIDTH,Tile.ARC_HEIGHT);
            }
        }
    }

    public void GraphicRender(Graphics2D g, BufferedImage finalBoard, Tile[][] board, BufferedImage gameBoard, int x, int y){
//        Render create = new Render();
//        create.GraphicRender(g,finalBoard,board,gameBoard,x,y);
        Graphics2D g2d = (Graphics2D)finalBoard.getGraphics();
        g2d.drawImage(gameBoard,0,0,null);

        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLS; col++){
                Tile current = board[row][col];
                if(current == null) continue;
                current.render.render(g2d, current);
            }
        }

        g.drawImage(finalBoard,x,y,null);
        g2d.dispose();
    }


    public void drawImage(){
        Graphics2D g = (Graphics2D)tileImage.getGraphics();
        if(value == 2){
            background = new Color(0xe9e9e9);
            text = new Color(0x000000);
        }else if(value == 4){
            background = new Color(0xe6daab);
            text = new Color(0x000000);
        }else if(value == 8){
            background = new Color(0xf79d3d);
            text = new Color(0xffffff);
        }else if(value == 16){
            background = new Color(0xf28007);
            text = new Color(0xffffff);
        }else if(value == 32){
            background = new Color(0xf55e3b);
            text = new Color(0xffffff);
        }else if(value == 64){
            background = new Color(0xff0000);
            text = new Color(0xffffff);
        }else if(value == 128){
            background = new Color(0xe9de84);
            text = new Color(0xffffff);
        }else if(value == 256){
            background = new Color(0xf6e873);
            text = new Color(0xffffff);
        }else if(value == 512){
            background = new Color(0xf5e555);
            text = new Color(0xffffff);
        }else if(value == 1024){
            background = new Color(0xf7e12c);
            text = new Color(0xffffff);
        }else if(value == 2048){
            background = new Color(0xffe400);
            text = new Color(0xffffff);
        }else{
            background = Color.black;
            text = Color.white;
        }

        g.setColor(new Color(0,0,0,0));
        g.fillRect(0,0,Tile.WIDTH,Tile.HEIGHT);

        g.setColor(background);
        g.fillRoundRect(0,0,Tile.WIDTH,Tile.HEIGHT,Tile.ARC_WIDTH,Tile.ARC_HEIGHT);

        g.setColor(text);

        if(value <= 64){
            font = Game.main.deriveFont(36f);
        }else{
            font = Game.main;
        }
        g.setFont(font);

        int drawX = Tile.WIDTH / 2 - DrawUtils.getMessageWidth(""+value,font,g)/2;
        int drawY = Tile.HEIGHT / 2 + DrawUtils.getMessageHeight(""+value,font,g)/2;
        g.drawString(""+value,drawX,drawY);
        g.dispose();
    }

    public void render(Graphics2D g, Tile tile){
        g.drawImage(tileImage, tile.x, tile.y, null);
    }
}
