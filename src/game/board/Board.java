package game.board;

import game.actor.pieces.*;
import game.actor.players.Player;
import game.actor.score.ScoreBoard;
import game.chessGame;
import utilities.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Board extends JPanel implements ActionListener {
    //settings
    public static int NUMBEROFROW = 8;
    public static int NUMBEROFCOL = 8;
    //cell of board
    private final ArrayList<ArrayList<BoardCell>> cells = new ArrayList<>(NUMBEROFROW);
    //pieces on board
    private ArrayList<Piece> pieces;

    //players
    private final Player whitePlayer;
    private final Player blackPlayer;
    private Player activePlayer;
    private Player inactivePlayer;

    //state handling
    public enum GameState {

        SELECTION, MOVEMENT, END
    }


    private GameState currentGameState = GameState.SELECTION;

    public Board(ScoreBoard whiteScoreBoard, ScoreBoard blackScoreBoard) {

        this.whitePlayer = new Player(Piece.Side.WHITE, whiteScoreBoard);
        this.blackPlayer = new Player(Piece.Side.BLACK, blackScoreBoard);
        this.activePlayer = this.whitePlayer;
        this.inactivePlayer = blackPlayer;


        //layout handler
        this.setLayout(new GridLayout(NUMBEROFROW, NUMBEROFCOL));
        //adding every cell
        this.setBackground(ScoreBoard.BG_SCOREBOARD);
        for (int y = 0; y < NUMBEROFROW; y++) {
            ArrayList<BoardCell> line = new ArrayList<>();
            for (int x = 0; x < NUMBEROFCOL; x++) {
                BoardCell cell = new BoardCell(new Coordinates(x, y));
                cell.addActionListener(this);
                line.add(cell);
                this.add(cell);
            }
            this.cells.add(line);
        }

        //placing pieces
        this.placePieces();
        //set board visible
        this.setVisible(true);
    }


    public ArrayList<Coordinates> getDangerPos(Piece.Side allySide) {
        ArrayList<Coordinates> dangerousPositions = new ArrayList<>();
        for (Piece i : pieces) {
            if (i.getSide() != allySide && i.isActive()) {
                dangerousPositions.addAll(i.getMovement(false));
            }
        }
        return dangerousPositions;
    }

    public void addToScoreBoard(Piece piece) {
        if (piece.getSide() == Piece.Side.WHITE) {
            this.blackPlayer.getSb().addPiece(piece);
        } else {
            this.whitePlayer.getSb().addPiece(piece);

        }
    }

    public void removePieceFromList(Piece piece) {
        pieces.remove(piece);
    }

    public King getKing(Piece.Side side) {
        for (Piece piece : pieces) {
            if (piece.isKing() && side == piece.getSide()) {
                return (King) piece;
            }
        }
        return null;
    }


    public void swap(Piece piece) {
        Object[] possibilities = {Queen.QUEEN_NAME, Bishop.BISHOP_NAME, Knight.KNIGHT_NAME, Rook.ROOK_NAME};
        Piece newPiece;
        String s = (String) JOptionPane.showInputDialog(
                this,
                "Select a piece to swap with your pawn\n",
                "Pick a piece",
                JOptionPane.INFORMATION_MESSAGE, this.getKing(piece.getSide()).getSprite(),
                possibilities,
                Queen.QUEEN_NAME);
        if (s == null) {
            this.swap(piece);
        } else {
            newPiece = switch (s) {
                case Queen.QUEEN_NAME -> new Queen(piece.getSide(), piece.getPosition(), this);
                case Bishop.BISHOP_NAME -> new Bishop(piece.getSide(), piece.getPosition(), this);
                case Knight.KNIGHT_NAME -> new Knight(piece.getSide(), piece.getPosition(), this);
                default -> new Rook(piece.getSide(), piece.getPosition(), this);
            };

            piece.deactivate();
            piece.removeFromCell();
            newPiece.activate();
            newPiece.addToCell();

            this.pieces.add(newPiece);
            this.pieces.remove(piece);
        }

    }

    //get cell from board
    public BoardCell getCell(int x, int y) {
        return this.cells.get(y).get(x);
    }

    //get cell from board
    public BoardCell getCellByCoords(Coordinates coords) {
        return this.getCell(coords.getX(), coords.getY());
    }

    private boolean canPlayerMakeMove(Player p) {
        for (Piece a : this.pieces) {
            if (a.getSide() == p.getSide()) {
                if (a.canMove()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkWin() {
        return !canPlayerMakeMove(inactivePlayer) && !getKing(inactivePlayer.getSide()).isSafe();
    }

    private boolean checkDraw() {
        return !canPlayerMakeMove(inactivePlayer) && getKing(inactivePlayer.getSide()).isSafe();

    }

    //check if coordinates of cases are on board
    public boolean isOnBoard(Coordinates coords) {
        return coords.getX() >= 0 && coords.getX() <= 7 && coords.getY() >= 0 && coords.getY() <= 7;
    }

    private void killAllCells() {
        for (ArrayList<BoardCell> lines : cells) {
            for (BoardCell cell : lines) {
                if (cell.isOccupied()) {
                    cell.getPiece().kill();
                }
                cell.setEnabled(false);

            }
        }
    }


    public void nextRound() {
        if (checkWin()) {
            this.currentGameState = GameState.END;
            JOptionPane.showMessageDialog(getParent(), activePlayer.getSide() + " wins");
            this.killAllCells();
            int input = JOptionPane.showConfirmDialog(this, "Do you want to play again ?", "Play Again.",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            switch (input) {
                case 0 -> this.restart();
                case 1, 2 -> System.exit(0);
            }
        } else if (checkDraw()) {
            this.killAllCells();
            this.currentGameState = GameState.END;

        } else {
            Player played = this.activePlayer;
            this.activePlayer = this.inactivePlayer;
            this.inactivePlayer = played;
            this.selectionPhase();
        }
    }

    public void selectionPhase() {
        this.currentGameState = GameState.SELECTION;
    }

    public void movementPhase() {
        this.currentGameState = GameState.MOVEMENT;
    }


    public void activateCells(ArrayList<Coordinates> coordinatesArrayList) {
        for (Coordinates coord : coordinatesArrayList) {
            this.getCellByCoords(coord).activate();
        }
    }

    public void disableAllCell() {
        for (ArrayList<BoardCell> lines : cells) {
            for (BoardCell cell : lines) {
                cell.setEnabled(false);

            }
        }
    }

    public void enableAllCell() {
        for (ArrayList<BoardCell> lines : cells) {
            for (BoardCell cell : lines) {
                cell.setEnabled(true);

            }
        }
    }


    /**
     * highlights all cells matching the coordinates given
     *
     * @param coordinatesArrayList coordinates list of cells to highlight
     */
    public void desactiveCells(ArrayList<Coordinates> coordinatesArrayList) {
        for (Coordinates coord : coordinatesArrayList) {
            this.getCellByCoords(coord).unactivate();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (ArrayList<BoardCell> line : this.cells) {
            for (BoardCell cell : line) {
                // cell interaction
                if (e.getSource() == cell) {
                    // check if the clicked cell is occupied and if it's in the same side of the current player
                    // also check if it's in game selection mode
                    if (cell.isOccupied() && this.activePlayer.getSide() == cell.getPiece().getSide() && currentGameState == GameState.SELECTION) {
                        this.activePlayer.selectPiece(cell.getPiece());

                    } else if (currentGameState == GameState.MOVEMENT) {
                        this.activePlayer.movePiece(cell);
                    }
                }
            }
        }
    }

    private void restart() {
        this.whitePlayer.getSb().reset();
        this.blackPlayer.getSb().reset();
        this.activePlayer = whitePlayer;
        this.inactivePlayer = blackPlayer;
        this.enableAllCell();
        this.placePieces();
    }


    private void placePieces() {
        this.pieces = new ArrayList<>(Arrays.asList(
                new King(Piece.Side.BLACK, new Coordinates(4, 0), this),
                new King(Piece.Side.WHITE, new Coordinates(4, 7), this),
                new Rook(Piece.Side.BLACK, new Coordinates(0, 0), this),
                new Rook(Piece.Side.BLACK, new Coordinates(7, 0), this),
                new Knight(Piece.Side.BLACK, new Coordinates(1, 0), this),
                new Knight(Piece.Side.BLACK, new Coordinates(6, 0), this),
                new Bishop(Piece.Side.BLACK, new Coordinates(2, 0), this),
                new Bishop(Piece.Side.BLACK, new Coordinates(5, 0), this),
                new Queen(Piece.Side.BLACK, new Coordinates(3, 0), this),
                new Pawn(Piece.Side.BLACK, new Coordinates(0, 1), this),
                new Pawn(Piece.Side.BLACK, new Coordinates(1, 1), this),
                new Pawn(Piece.Side.BLACK, new Coordinates(2, 1), this),
                new Pawn(Piece.Side.BLACK, new Coordinates(3, 1), this),
                new Pawn(Piece.Side.BLACK, new Coordinates(4, 1), this),
                new Pawn(Piece.Side.BLACK, new Coordinates(5, 1), this),
                new Pawn(Piece.Side.BLACK, new Coordinates(6, 1), this),
                new Pawn(Piece.Side.BLACK, new Coordinates(7, 1), this),
                new Rook(Piece.Side.WHITE, new Coordinates(0, 7), this),
                new Rook(Piece.Side.WHITE, new Coordinates(7, 7), this),
                new Knight(Piece.Side.WHITE, new Coordinates(1, 7), this),
                new Knight(Piece.Side.WHITE, new Coordinates(6, 7), this),
                new Bishop(Piece.Side.WHITE, new Coordinates(2, 7), this),
                new Bishop(Piece.Side.WHITE, new Coordinates(5, 7), this),
                new Queen(Piece.Side.WHITE, new Coordinates(3, 7), this),
                new Pawn(Piece.Side.WHITE, new Coordinates(0, 6), this),
                new Pawn(Piece.Side.WHITE, new Coordinates(1, 6), this),
                new Pawn(Piece.Side.WHITE, new Coordinates(2, 6), this),
                new Pawn(Piece.Side.WHITE, new Coordinates(3, 6), this),
                new Pawn(Piece.Side.WHITE, new Coordinates(4, 6), this),
                new Pawn(Piece.Side.WHITE, new Coordinates(5, 6), this),
                new Pawn(Piece.Side.WHITE, new Coordinates(6, 6), this),
                new Pawn(Piece.Side.WHITE, new Coordinates(7, 6), this)));
        for (Piece piece : pieces) {
            BoardCell targetCell = this.getCell(piece.getPosition().getX(), piece.getPosition().getY());
            targetCell.addPiece(piece);

        }
    }
}





