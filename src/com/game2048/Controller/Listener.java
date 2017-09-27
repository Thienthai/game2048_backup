package com.game2048.Controller;

import com.game2048.Model.Tile;
import com.game2048.View.Keyboard;
import com.game2048.View.Render;
import com.game2048.Model.Logic;
import com.game2048.View.RenderHelper;

import javax.swing.*;

/**
 * Created by thienthai on 18/9/2560.
 */
public class Listener extends JPanel{

    private static final long serialVersionUID = 1L;

    private Logic board;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 630;


    public Listener(){
        board = new Logic(WIDTH / 2 - Logic.BOARD_WIDTH / 2, HEIGHT - Logic.BOARD_HEIGHT - 10);
        spawn_listener(board.getBoard());
    }

    public void boardUpdate(RenderHelper renhelp) {
        renhelp.checkKeys(board);
        updateHelper();
        Keyboard.update();
    }

    public Logic getBoard() {
        return board;
    }

    public void updateHelper(){
        Render ren = new Render();
        ren.updateBoard(board.getBoard());
    }

    public void spawn_listener(Tile[][] brd){
        Render ren = new Render();
        ren.start(brd);
    }

}
