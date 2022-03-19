package game.actor.score;

import game.actor.pieces.Piece;
import game.board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ScoreBoard extends JPanel{
    public static final Color BG_SCOREBOARD = new Color(168, 159, 158);


    public ArrayList<Piece> deadPiece = new ArrayList<>();
    public ArrayList<DeadIcon> buttonsPiece = new ArrayList<>();


    public ScoreBoard() {
        super();


        this.setLayout(new GridLayout(1, 16));
        this.setVisible(true);
        this.setBackground(BG_SCOREBOARD);
        for (int i = 0; i < 16; i++) {
            DeadIcon deadIcon = new DeadIcon();
            this.buttonsPiece.add(deadIcon);
            this.add(deadIcon);
        }

    }

    public void addPiece(Piece piece) {
        this.buttonsPiece.get(this.deadPiece.size()).pieceDead(piece);
        this.deadPiece.add(piece);

    }





}
