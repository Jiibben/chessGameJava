package game.actor.pieces;

import game.board.Board;
import utilities.Coordinates;

import java.util.ArrayList;

public class King extends Piece {

    public static final String KING_NAME = "king";

    public King(Side side, Coordinates coords, Board board) {

        super(side, KING_NAME,  coords, board);
    }

    @Override
    public boolean isKing() {
        return true;
    }

    @Override
    public ArrayList<Coordinates> getMovement() {
        return null;
    }
}