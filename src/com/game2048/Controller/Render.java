package com.game2048.Controller;

import com.game2048.Model.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Render {

    private static final int ROWS = 4;
    private static final int COLS = 4;
    private static int SPACING = 10;
    private static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    private static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;
    //private BufferedImage gameBoard;

    public void createBoardImage(BufferedImage gameBoard){
//        Render create = new Render();
//        create.createBoardImage(gameBoard);
//        gameBoard = new BufferedImage(BOARD_WIDTH,BOARD_HEIGHT,BufferedImage.TYPE_INT_RGB);
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
                current.render(g2d);
            }
        }

        g.drawImage(finalBoard,x,y,null);
        g2d.dispose();
    }
}
