package game.actor.pieces;


import game.board.Board;
import game.board.BoardCell;
import jdk.swing.interop.SwingInterOpUtils;
import utilities.Coordinates;

import javax.swing.*;


import java.sql.SQLOutput;
import java.util.ArrayList;

public abstract class Piece {

    private final Side side;
    private final String name;
    private Coordinates position;
    private int numberOfPlay = 0;
    private final Board board;
    private boolean active = true;

    public static enum Side {
        BLACK, WHITE;
    }

    public void kill() {
        this.deactivate();
        this.removeFromCell();
        this.getBoard().addToScoreBoard(this);
    }

    public boolean isActive() {
        return this.active;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public Piece(Side side, String name, Coordinates position, Board board) {
        this.side = side;
        this.name = name;
        this.position = position;
        this.board = board;
    }

    public abstract boolean isKing();

    public void hasMoved() {
        this.numberOfPlay += 1;
    }

    public int getNumberOfPlay() {
        return numberOfPlay;
    }

    protected Board getBoard() {
        return this.board;
    }

    public void changePosition(Coordinates newCoords) {
        this.position = newCoords;
    }

    public boolean canMove() {
        ArrayList<Coordinates> movement = this.getMovement(false);
        movement.removeIf(a -> !checkIfMoveSafe(a));
        return !movement.isEmpty();
    }

    public boolean checkIfMoveSafe(Coordinates targetPos) {
        Coordinates cachedPos = this.getPosition();


        BoardCell targetCell = this.getBoard().getCellByCoords(targetPos);
        Piece targetPiece = targetCell.getPiece();


        boolean occupancyTargetCell = targetCell.isOccupied();
        if (occupancyTargetCell) {
            targetPiece.deactivate();
        }
        this.removeFromCell();
        this.position = targetPos;
        this.addToCell();

        //check if king is safe
        boolean isKingSafe = this.getBoard().getKing(this.getSide()).isSafe();

        this.removeFromCell();
        if (occupancyTargetCell) {
            targetCell.addPiece(targetPiece);
            targetPiece.activate();

        }
        this.position = cachedPos;

        this.addToCell();
        return isKingSafe;
    }

    public void addToCell() {
        this.getBoard().getCellByCoords(this.getPosition()).addPiece(this);
    }

    public void removeFromCell() {
        this.board.getCellByCoords(this.getPosition()).removePiece();
    }

    public ImageIcon getSprite() {
        return new ImageIcon("res/" + this.name + ((Side.BLACK == this.side) ? "Black" : "White") + ".png");
    }

    public ImageIcon getDeadSprite() {
        return new ImageIcon("res/deadPiece/" + this.name + ((Side.BLACK == this.side) ? "Black" : "White") + ".png");
    }

    /**
     * get position of current piece
     *
     * @return coordinates
     */
    public Coordinates getPosition() {
        return this.position;
    }

    public String getName() {
        return name;
    }

    public void move(BoardCell targetCell) {
        ArrayList<Coordinates> movement = this.getMovement(false);
        movement.removeIf(a -> !checkIfMoveSafe(a));

        if (!movement.isEmpty() && Coordinates.contains(movement, targetCell.getCoords())) {
            if (targetCell.isOccupied() && targetCell.getPiece().getSide() != this.getSide()) {
                targetCell.getPiece().kill();
            }
            this.board.desactiveCells(movement);
            this.removeFromCell();
            targetCell.addPiece(this);
            this.hasMoved();

            if ((this.getName().equals("pawn") && this.getSide() == Side.BLACK && this.getPosition().getY() == (Board.NUMBEROFROW - 1)) || (this.getName().equals("pawn") && this.getSide() == Side.WHITE && this.getPosition().getY() == 0)) {
                this.getBoard().swap(this);
            }


            board.nextRound();

        }
        this.board.desactiveCells(movement);
        board.selectionPhase();
    }

    public void select() {
        ArrayList<Coordinates> movement = this.getMovement(false);
        movement.removeIf(a -> !checkIfMoveSafe(a));

        if (!movement.isEmpty()) {
            this.board.activateCells(movement);
            this.board.movementPhase();
        }
    }

    public Side getSide() {
        return side;
    }

    public abstract ArrayList<Coordinates> getMovement(boolean attackRange);


    // movement handling
    protected boolean validateAndAdd(ArrayList<Coordinates> movement, Coordinates possibility) {
        if (this.getBoard().isOnBoard(possibility) && !this.getBoard().getCellByCoords(possibility).isOccupied()) {
            movement.add(possibility);
        } else {
            if (this.getBoard().isOnBoard(possibility) && this.getBoard().getCellByCoords(possibility).isOccupiedByEnemy(this)) {
                movement.add(possibility);
            }
            return true;
        }
        return false;
    }


    protected ArrayList<Coordinates> verticalAxesMovement() {


        ArrayList<Coordinates> movement = new ArrayList<>();
        for (int posY = 1; posY <= 7; posY++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.decrementY(posY);
            if (validateAndAdd(movement, possibility)) {
                break;
            }

        }
        for (int negY = 1; negY <= 7; negY++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.incrementY(negY);
            if (validateAndAdd(movement, possibility)) {
                break;
            }
        }
        for (int posX = 1; posX <= 7; posX++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.decrementX(posX);
            if (validateAndAdd(movement, possibility)) {
                break;
            }
        }
        for (int negX = 1; negX <= 7; negX++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.incrementX(negX);
            if (validateAndAdd(movement, possibility)) {
                break;
            }
        }

        return movement;
    }

    protected ArrayList<Coordinates> diagonalAxesMovement() {

        ArrayList<Coordinates> movement = new ArrayList<>();

        for (int a = 1; a <= 7; a++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.increment(new Coordinates(-a, -a));
            if (validateAndAdd(movement, possibility)) {
                break;
            }
        }

        for (int b = 1; b <= 7; b++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.increment(new Coordinates(b, b));
            if (validateAndAdd(movement, possibility)) {
                break;
            }
        }

        for (int c = 1; c <= 7; c++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.increment(new Coordinates(-c, c));
            if (validateAndAdd(movement, possibility)) {
                break;
            }
        }

        for (int d = 1; d <= 7; d++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.increment(new Coordinates(d, -d));
            if (validateAndAdd(movement, possibility)) {
                break;
            }
        }
        return movement;
    }


}
