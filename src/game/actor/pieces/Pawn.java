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
        //check how many times that pawn was played
        int number = (this.getNumberOfPlay() == 0 ? 2 : 1);

        //check if it's an attack range mode
        if (!attackRange){

        for (int i = 1; i <= number; i++) {
            Coordinates possibility = new Coordinates(this.getPosition());
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

        Coordinates eatOne = this.getPosition().incrementAndNew(1, 1);
        Coordinates eatTwo = this.getPosition().incrementAndNew(-1, 1);

        if (this.getSide() == Side.WHITE) {
            eatOne = this.getPosition().incrementAndNew(-1, -1);
            eatTwo = this.getPosition().incrementAndNew(1, -1);

        }

        if (this.getBoard().isOnBoard(eatOne) && this.getBoard().getCellByCoords(eatOne).isOccupied() &&this.getBoard().getCellByCoords(eatOne).isOccupiedByEnemy(this)) {
            movement.add(eatOne);
        }
        if (this.getBoard().isOnBoard(eatTwo) && this.getBoard().getCellByCoords(eatTwo).isOccupied() && this.getBoard().getCellByCoords(eatTwo).isOccupiedByEnemy(this)) {
            movement.add(eatTwo);
        }
        return movement;
    }

}
