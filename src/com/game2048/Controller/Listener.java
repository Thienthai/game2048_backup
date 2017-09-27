package com.game2048.Controller;

import com.game2048.View.Keyboard;
import com.game2048.View.Render;
import com.game2048.Model.Logic;
import com.game2048.View.RenderHelper;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Created by thienthai on 18/9/2560.
 */
public class Listener extends JPanel{

    private static final long serialVersionUID = 1L;

    private Logic board;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 630;
    private boolean hasStarted;
    private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    private Render render;


    public Listener(){
        board = new Logic(WIDTH / 2 - Logic.BOARD_WIDTH / 2, HEIGHT - Logic.BOARD_HEIGHT - 10);
    }

    public void boardUpdate(RenderHelper renhelp) {
        //board.update();
        renhelp.checkKeys(board);
        board.update();
        Keyboard.update();
    }

    public Logic getBoard() {
        return board;
    }

}
