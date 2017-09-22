package com.game2048.View;

import com.game2048.Action.Direction;
import com.game2048.Action.Logic;

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

    public RenderHelper(){
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        addKeyListener(this);
    }

    public void parseRender(Logic board){
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
