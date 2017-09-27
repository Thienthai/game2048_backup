package com.game2048.View;

import com.game2048.Model.Direction;
import com.game2048.Model.Logic;
import com.game2048.Model.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class RenderHelper extends JPanel implements KeyListener {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 630;
    private boolean hasStarted;
    private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    private BufferedImage gameBoard;
    private BufferedImage finalBoard;
    private static final int ROWS = 4;
    private static final int COLS = 4;
    private static int SPACING = 10;
    private static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    private static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;

    public RenderHelper(){
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        addKeyListener(this);
        gameBoard = new BufferedImage(BOARD_WIDTH,BOARD_HEIGHT,BufferedImage.TYPE_INT_RGB);
        finalBoard = new BufferedImage(BOARD_WIDTH,BOARD_HEIGHT,BufferedImage.TYPE_INT_RGB);
        createBoardImage(gameBoard);
    }

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

    public void parseRender(Logic board){
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0,0,WIDTH,HEIGHT);
        Render create = new Render();
        create.GraphicRender(g,finalBoard,board.getBoard(),gameBoard,board.getX(),board.getY());
        //RENDER BOARD
        g.dispose();
        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
    }

    public void checkKeys(Logic board){ // ใช้ฟังชั่นนี้เชื่อมระหว่าง listener กับ logic
        if(Keyboard.typed(KeyEvent.VK_LEFT)){
            //move tiles left
            board.moveTiles(Direction.LEFT);
            if(!hasStarted)hasStarted = true;
        }
        if(Keyboard.typed(KeyEvent.VK_RIGHT)){
            //move tiles right
            board.moveTiles(Direction.RIGHT);
            if(!hasStarted)hasStarted = true;
        }
        if(Keyboard.typed(KeyEvent.VK_UP)){
            //move tiles up
            board.moveTiles(Direction.UP);
            if(!hasStarted)hasStarted = true;
        }
        if(Keyboard.typed(KeyEvent.VK_DOWN)){
            //move tiles down
            board.moveTiles(Direction.DOWN);
            if(!hasStarted)hasStarted = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Keyboard.ketPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Keyboard.ketReleased(e);
    }
}
