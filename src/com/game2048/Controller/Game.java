package com.game2048.Controller;

import com.game2048.View.RenderHelper;

import javax.swing.*;
import java.awt.*;

// แยก เป็น 2 ส่วน Controller กับ Models

public class Game implements Runnable {

    public static final Font main = new Font("Bebas Neue Regular", Font.PLAIN, 28);
    private Thread game;
    private boolean running;
    private Listener listener;
    private RenderHelper renhelp;

    public Game(){
        renhelp = new RenderHelper();
        listener = new Listener();
        JFrame window = new JFrame("2048");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(renhelp);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

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
                listener.boardUpdate(renhelp);
                //update();
                unprocessed--;
                shouldRender = true;
            }

            // parseRender ควรเอาไปใส้ใน observer
            if(shouldRender){
                fps++;
                renhelp.parseRender(listener.getBoard());
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
}
