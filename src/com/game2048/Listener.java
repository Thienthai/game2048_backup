package com.game2048;

import com.game2048.Action.Direction;
import com.game2048.View.Render;
import com.game2048.Action.Logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * Created by thienthai on 18/9/2560.
 */
public class Listener extends JPanel implements KeyListener{

    private static final long serialVersionUID = 1L;
    private Logic board;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 630;
    private boolean hasStarted;
    private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    private Render render;


    public Listener(){
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        addKeyListener(this);

        board = new Logic(WIDTH / 2 - Logic.BOARD_WIDTH / 2, HEIGHT - Logic.BOARD_HEIGHT - 10);

        //render = new Render();
        //render.createBoardImage();
    }

    public void boardUpdate() {
        //board.update();
        checkKeys();
        board.update();
        Keyboard.update();
    }

    private void checkKeys(){ // ใช้ฟังชั่นนี้เชื่อมระหว่าง listener กับ logic
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

    public void parseRender(){
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0,0,WIDTH,HEIGHT);
        board.render(g);
        //RENDER BOARD
        g.dispose();

        Graphics2D g2d = (Graphics2D) getGraphics();
        //System.out.println(g2d);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
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
