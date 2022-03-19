package game.actor.players;

import game.actor.pieces.Piece;

import game.actor.score.ScoreBoard;
import game.board.BoardCell;

public class Player {
    private final Piece.Side side;
    private Piece selectedPiece;
    private final ScoreBoard sb;

    public Player(Piece.Side side, ScoreBoard sb) {
        this.side = side;
        this.sb = sb;
    }

    public ScoreBoard getSb() {
        return sb;
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

