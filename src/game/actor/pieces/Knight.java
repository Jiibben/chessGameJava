package game.actor.pieces;

import game.board.Board;
import utilities.Coordinates;

import java.util.ArrayList;

public class Knight extends Piece{

    public static final String KNIGHT_NAME = "knight";

    public Knight(Side side, Coordinates coords, Board board) {
        super(side, KNIGHT_NAME, coords, board);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public ArrayList<Coordinates> getMovement() {
        ArrayList<Coordinates> movement = new ArrayList<>();
        Coordinates currentCoords = new Coordinates(getPosition());
        Coordinates[] coords = {currentCoords.incrementAndNew(1, 2), currentCoords.incrementAndNew(-1, 2), currentCoords.incrementAndNew(1,-2), currentCoords.incrementAndNew(-1,-2),
        currentCoords.incrementAndNew(2,1), currentCoords.incrementAndNew(2,-1), currentCoords.incrementAndNew(-2,-1), currentCoords.incrementAndNew(-2,1)};
        for(Coordinates coord : coords){
            if(this.getBoard().isOnBoard(coord) &&( !this.getBoard().getCellByCoords(coord).isOccupied() || this.getBoard().getCellByCoords(coord).isOccupiedByEnemy(this))){
                movement.add(coord);
            }
        }
        return movement;
    }
}
