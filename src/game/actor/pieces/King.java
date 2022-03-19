package game.actor.pieces;

import game.board.Board;
import utilities.Coordinates;

import java.util.ArrayList;

public class King extends Piece {

    public static final String KING_NAME = "king";

    public King(Side side, Coordinates coords, Board board) {

        super(side, KING_NAME, coords, board);
    }

    @Override
    public boolean isKing() {
        return true;
    }


    public boolean isSafe() {
        return !Coordinates.contains(this.getBoard().getDangerPos(this.getSide()), this.getPosition());
    }

    @Override
    public ArrayList<Coordinates> getMovement(boolean attackRange) {
        ArrayList<Coordinates> movement = new ArrayList<>();
        Coordinates currPos = this.getPosition();
        Coordinates[] possibleCoords = {currPos.incrementAndNew(0, 1), currPos.incrementAndNew(0, -1), currPos.incrementAndNew(1, 1), currPos.incrementAndNew(-1, 1), currPos.incrementAndNew(1, -1), currPos.incrementAndNew(-1, -1), currPos.incrementAndNew(1, 0), currPos.incrementAndNew(-1, 0)};
        for (Coordinates coord : possibleCoords) {
            if (this.getBoard().isOnBoard(coord) && (this.getBoard().getCellByCoords(coord).isOccupiedByEnemy(this) || !this.getBoard().getCellByCoords(coord).isOccupied())) {
                movement.add(coord);
            }
        }
        return movement;
    }

}