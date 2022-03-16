package game.actor.pieces;

import game.board.Board;
import utilities.Coordinates;

import java.util.ArrayList;

public class Pawn extends Piece {

    public static final String PAWN_NAME = "pawn";

    public Pawn(Side side, Coordinates coords, Board board) {
        super(side, PAWN_NAME, coords, board);
    }


    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public ArrayList<Coordinates> getMovement(boolean attackRange) {
        ArrayList<Coordinates> movement = new ArrayList<>();
        int number = (this.getNumberOfPlay() == 0 ? 2 : 1);
        if (!attackRange){
        for (int i = 1; i <= number; i++) {
            Coordinates possibility = new Coordinates(getPosition());
            if (this.getSide() == Side.WHITE) {
                possibility.decrementY(i);
            } else {
                possibility.incrementY(i);
            }
            if (this.getBoard().isOnBoard(possibility) && !this.getBoard().getCellByCoords(possibility).isOccupied()) {
                movement.add(possibility);
            } else {
                break;
            }
        }
        }

        Coordinates eatOne = getPosition().incrementAndNew(1, 1);
        Coordinates eatTwo = getPosition().incrementAndNew(-1, 1);

        if (this.getSide() == Side.WHITE) {
            eatOne = getPosition().incrementAndNew(-1, -1);
            eatTwo = getPosition().incrementAndNew(1, -1);

        }


        if (this.getBoard().isOnBoard(eatOne) && this.getBoard().getCellByCoords(eatOne).isOccupied() &&this.getBoard().getCellByCoords(eatOne).isOccupiedByEnemy(this)) {
            movement.add(eatOne);
        }
        if (this.getBoard().isOnBoard(eatTwo) && this.getBoard().getCellByCoords(eatOne).isOccupied() && this.getBoard().getCellByCoords(eatTwo).isOccupiedByEnemy(this)) {
            movement.add(eatTwo);
        }
        return movement;
    }

}
