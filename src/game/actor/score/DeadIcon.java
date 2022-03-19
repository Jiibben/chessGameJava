package game.actor.score;

import game.actor.pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class DeadIcon extends JButton {

    private Piece piece;

    public DeadIcon() {
        this.setVisible(true);
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setSize(100, 100);
        this.setEnabled(false);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setIcon(new ImageIcon("res/deadPiece/placeHolder.png"));
        this.setDisabledIcon(new ImageIcon("res/deadPiece/placeHolder.png"));
    }


    public void pieceDead(Piece piece) {
        this.piece = piece;

        this.setText("");
        //set
        this.setIcons(piece);

        this.setEnabled(true);


    }

    private void setIcons(Piece piece) {
        this.setIcon(piece.getDeadSprite());
        this.setDisabledIcon(piece.getDeadSprite());
    }


    public void restart() {
        this.setSize(100, 100);
        this.setEnabled(false);
        this.setIcon(new ImageIcon("res/deadPiece/placeHolder.png"));
        this.setDisabledIcon(new ImageIcon("res/deadPiece/placeHolder.png"));
        this.piece = null;
    }
}
