package game.actor.pieces;

import game.board.Board;
import utilities.Coordinates;

import java.util.ArrayList;

public class Bishop extends Piece{

    public static final String BISHOP_NAME = "bishop";

    public Bishop(Side side, Coordinates coords, Board board) {
        super(side, BISHOP_NAME, coords, board);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public ArrayList<Coordinates> getMovement() {
        return diagonalAxesMovement();
    }
}
