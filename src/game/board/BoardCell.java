package game.board;

import game.actor.pieces.Piece;
import utilities.Coordinates;

import javax.swing.*;
import java.awt.*;

public class BoardCell extends JButton {
    public static final Color BORDER_COLOR = new Color(0, 0, 0);
    public static final Color TILE_ONE_BG = Color.GRAY;
    public static final Color TILE_TWO_BG = Color.DARK_GRAY;


    private Coordinates coords;
    private Piece piece;

    public BoardCell(Coordinates coords){
        //color handling for alternate tiling color
        if ((coords.getX() + coords.getY()) % 2 == 0){
            this.setBackground(TILE_ONE_BG);
        }else{
            this.setBackground(TILE_TWO_BG);
        }

        this.coords = coords;
        this.setBorderPainted(false);
        //General cell settings
        this.setVisible(true);
        this.setFocusPainted(false);

    }

    public void addPiece(Piece piece){
        this.piece = piece;
        this.setIcon(this.piece.getSprite());

    }


    public Piece getPiece(){
        return this.piece;
    }

    public Coordinates getCoords() {
        return coords;
    }
}
