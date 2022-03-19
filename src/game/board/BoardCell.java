package game.board;

import game.actor.pieces.Piece;
import utilities.Coordinates;

import javax.swing.*;
import java.awt.*;

public class BoardCell extends JButton {

    // styling of cell
    public static final Color BORDER_COLOR = new Color(161, 234, 177);
    public static final Color TILE_ONE_BG = Color.GRAY;
    public static final Color TILE_TWO_BG = Color.DARK_GRAY;
    public static final Color ACTIVE_BG = new Color(171, 206, 171);



    private final Coordinates coords;
    private Piece piece;

    public BoardCell(Coordinates coords){
        //color handling for alternate tiling color
        this.coords = coords;
        this.paint();

        this.setBorderPainted(false);
        this.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        //General cell settings
        this.setVisible(true);
        this.setFocusPainted(false);

    }

    private void paint(){
        if ((this.getCoords().getX() + this.getCoords().getY()) % 2 == 0){
            this.setBackground(TILE_ONE_BG);
        }else{
            this.setBackground(TILE_TWO_BG);
        }

    }

    public void addPiece(Piece piece){
        this.piece = piece;
        this.piece.changePosition(this.getCoords());
        this.setIcon(this.piece.getSprite());

    }


    public Piece getPiece(){
        return this.piece;
    }

    public Coordinates getCoords() {
        return coords;
    }

    public boolean isOccupied(){
        return this.piece != null;
    }

    public boolean isOccupiedByEnemy(Piece piece){
        return this.piece != null && piece.getSide() != this.piece.getSide();
    }

    public void removePiece(){
        this.piece = null;
        this.setIcon(null);

    }


    public void activate(){
        this.setBackground(ACTIVE_BG);
        this.setBorderPainted(true);
    }
    public void unactivate(){
        this.setBorderPainted(false);
        this.paint();
    }
}
