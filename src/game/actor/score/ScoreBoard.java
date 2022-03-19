package game.actor.score;

import game.actor.pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class ScoreBoard extends JPanel {
    public static final Color BG_SCOREBOARD= new Color(168, 159, 158);

    public ScoreBoard() {
        super();
        this.setLayout(new GridLayout(1, 16));
        this.setVisible(true);
        this.setBackground(BG_SCOREBOARD);

    }

    public void addPiece(Piece piece) {
        DeadIcon icon = new DeadIcon();
        icon.setIcon(piece.getSprite());
        this.add(icon);
    }
}
