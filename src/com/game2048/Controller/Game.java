package com.game2048.Controller;

import com.game2048.Listener;
import com.game2048.Logic.Logic;
import com.game2048.Logic.Keyboard;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import static java.lang.Thread.sleep;

// แยก เป็น 2 ส่วน Controller กับ Models

public class Game extends JPanel implements KeyListener, Runnable{

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 630;
    public static final Font main = new Font("Bebas Neue Regular", Font.PLAIN, 28);
    private Thread game;
    private boolean running;
    private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    private Logic board;
    private Listener listener;

    public Game(){
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        addKeyListener(this);

        board = new Logic(WIDTH / 2 - Logic.BOARD_WIDTH / 2, HEIGHT - Logic.BOARD_HEIGHT - 10);
        listener = new Listener();
    }

    private void update() throws InterruptedException {
        board.update();
        Keyboard.update();
//        System.out.println("key board update");
//        sleep(100);
    } // ตรงนี้เป็นตัว controller ระหว่าง board กับ model

//    private void render(){
//        Graphics2D  g = (Graphics2D) image.getGraphics();
//        g.setColor(Color.white);
//        g.fillRect(0,0,WIDTH,HEIGHT);
//        board.render(g);
//        //RENDER BOARD
//        System.out.println(g);
//        g.dispose();
//
//        Graphics2D g2d = (Graphics2D) getGraphics();
//        System.out.println(g2d);
//        g2d.drawImage(image, 0, 0, null);
//        g2d.dispose();
//    }

    // add this line
    // and this line
    // add this two libe

    @Override
    public void run(){
        int fps = 0, update = 0;
        long fpsTimer = System.currentTimeMillis();
        double nsPerUpdate = 1000000000.0 / 60;

        // last update time in nanoseconds
        double then = System.nanoTime();
        double unprocessed = 0;

        while(running){

            boolean shouldRender = false;
            double now = System.nanoTime();
            unprocessed += (now - then) / nsPerUpdate;
            then = now;

            while(unprocessed >= 1){
                update++;
                //listener.boardUpdate();
                try {
                    //update();
                    listener.boardUpdate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                unprocessed--;
                shouldRender = true;
            }

            // parseRender ควรเอาไปใส้ใน observer
            if(shouldRender){
                fps++;
                //render();
                //parseRender();
                listener.parseRender();
                shouldRender = false;
            }

            else{
                try{
                    Thread.sleep(1);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        // FPS Timer
        if(System.currentTimeMillis() - fpsTimer > 1000){
            System.out.printf("%d fps %d updates",fps, update);
            System.out.println();
            fps = 0;
            update = 0;
            fpsTimer += 1000;
        }
    }

    public synchronized void start(){
        if(running){
            return;
        }
        running = true;
        game = new Thread(this, "gamex");
        game.start();
    }

    public synchronized void stop(){
        if(!running) return;
        running = false;
        System.exit(0);
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
