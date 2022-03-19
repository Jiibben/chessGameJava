package game.actor.pieces;

import game.board.Board;
import utilities.Coordinates;

import java.util.ArrayList;

public class Queen extends Piece {
    public static final String QUEEN_NAME = "queen";

    public Queen(Side side, Coordinates coords, Board board) {
        super(side, QUEEN_NAME, coords, board);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public ArrayList<Coordinates> getMovement(boolean attackRange) {
        ArrayList<Coordinates> movement = diagonalAxesMovement();
        movement.addAll(verticalAxesMovement());
        return movement;
    }
}
