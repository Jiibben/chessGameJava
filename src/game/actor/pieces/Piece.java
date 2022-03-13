package game.actor.pieces;


import javax.swing.*;

public abstract class Piece {
    private final Side side;
    private final String name;

    public static enum Side {
        BLACK, WHITE;
    }

    public Piece(Side side, String name) {
        this.side = side;
        this.name = name;
    }

    public ImageIcon getSprite() {
        return new ImageIcon("res/"+this.name  + ((Side.BLACK == this.side)  ? "Black" : "White") + ".png");
    }

    public Side getSide() {
        return side;
    }

}
