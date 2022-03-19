package game;

import game.actor.score.ScoreBoard;
import game.board.Board;

import javax.swing.*;
import java.awt.*;

public class chessGame extends JFrame {
    public static final int WINDOW_HEIGHT = 650;
    public static final int WINDOW_WIDTH = 650;

    public chessGame() {
        this.setLayout(new BorderLayout());
        ScoreBoard whiteScoreBoard = new ScoreBoard();
        ScoreBoard blackScoreBoard = new ScoreBoard();
        this.add(new Board(whiteScoreBoard, blackScoreBoard), BorderLayout.CENTER);

//        this.add(whiteScoreBoard, BorderLayout.NORTH);
//        this.add(blackScoreBoard, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setTitle("ChessGame");
        this.setResizable(false);
        this.setVisible(true);


    }
}
