package game.actor.players;

import game.actor.pieces.Piece;
import game.board.Board;
import game.board.BoardCell;
import utilities.Coordinates;

public class Player {
    private final Piece.Side side;
    private Piece selectedPiece;

    public Player(Piece.Side side) {
        this.side = side;
    }


    public void hasPlayed() {

    }

    private void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }



    public void unselectPiece() {
        this.selectedPiece = null;
    }

    public void selectPiece(Piece piece) {
        this.setSelectedPiece(piece);
        piece.select();

    }

    public void movePiece(BoardCell targetCell) {
        this.selectedPiece.move(targetCell);
        this.unselectPiece();

    }


    public Piece.Side getSide() {
        return side;
    }
}

