package game.actor.pieces;

import game.board.Board;
import utilities.Coordinates;

import java.util.ArrayList;

public class Rook extends Piece {

    public static final String ROOK_NAME = "rook";

    public Rook(Side side, Coordinates coords, Board board) {
        super(side, ROOK_NAME, coords, board);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public ArrayList<Coordinates> getMovement(boolean attackRange) {
        return this.verticalAxesMovement();
    }

}
