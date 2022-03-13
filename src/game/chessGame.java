package game;

import game.board.Board;

import javax.swing.*;

public class chessGame extends JFrame {
    public static final int WINDOW_HEIGHT = 620;
    public static final int WINDOW_WIDTH = 620;

    public chessGame(){
        this.add(new Board());

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setTitle("ChessGame");
        this.setResizable(false);
        this.setVisible(true);
    }
}
