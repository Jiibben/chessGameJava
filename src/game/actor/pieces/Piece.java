package game.actor.pieces;


import game.board.Board;
import game.board.BoardCell;
import utilities.Coordinates;

import javax.swing.*;
import java.util.ArrayList;

public abstract class Piece {
    private final Side side;
    private final String name;
    private Coordinates position;

    private int numberOfPlay = 0;
    private final Board board;

    public static enum Side {
        BLACK, WHITE;
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


    public void removeFromCell() {
        this.board.getCellByCoords(this.position).removePiece();
    }

    public ImageIcon getSprite() {
        return new ImageIcon("res/" + this.name + ((Side.BLACK == this.side) ? "Black" : "White") + ".png");
    }

    public Coordinates getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public void move(BoardCell targetCell) {
        ArrayList<Coordinates> movement = this.getMovement(false);
        if (!movement.isEmpty() && Coordinates.contains(movement, targetCell.getCoords())) {
            this.board.desactiveCells(movement);
            this.removeFromCell();
            targetCell.addPiece(this);
            this.hasMoved();
            board.selectionPhase();
        }
        this.board.desactiveCells(movement);
        board.selectionPhase();
    }

    public void select() {
        ArrayList<Coordinates> movement = this.getMovement(false);
        if (!movement.isEmpty()) {
            this.board.activateCells(this.getMovement(false));
            this.board.movementPhase();

        }
    }

    public Side getSide() {
        return side;
    }

    public abstract ArrayList<Coordinates> getMovement(boolean attackRange);


    protected ArrayList<Coordinates> verticalAxesMovement() {
        ArrayList<Coordinates> movement = new ArrayList<>();
        for (int posY = 1; posY <= 7; posY++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.decrementY(posY);
            if (this.getBoard().isOnBoard(possibility) && !this.getBoard().getCellByCoords(possibility).isOccupied()) {
                movement.add(possibility);
            } else {
                if (this.getBoard().isOnBoard(possibility) && this.getBoard().getCellByCoords(possibility).isOccupiedByEnemy(this)) {
                    movement.add(possibility);
                }
                break;
            }
        }
        for (int negY = 1; negY <= 7; negY++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.incrementY(negY);
            if (this.getBoard().isOnBoard(possibility) && !this.getBoard().getCellByCoords(possibility).isOccupied()) {
                movement.add(possibility);
            } else {
                if (this.getBoard().isOnBoard(possibility) && this.getBoard().getCellByCoords(possibility).isOccupiedByEnemy(this)) {
                    movement.add(possibility);
                }
                break;
            }
        }
        for (int posX = 1; posX <= 7; posX++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.decrementX(posX);
            if (this.getBoard().isOnBoard(possibility) && !this.getBoard().getCellByCoords(possibility).isOccupied()) {
                movement.add(possibility);
            } else {
                if (this.getBoard().isOnBoard(possibility) && this.getBoard().getCellByCoords(possibility).isOccupiedByEnemy(this)) {
                    movement.add(possibility);
                }
                break;
            }
        }
        for (int negX = 1; negX <= 7; negX++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.incrementX(negX);
            if (this.getBoard().isOnBoard(possibility) && !this.getBoard().getCellByCoords(possibility).isOccupied()) {
                movement.add(possibility);
            } else {
                if (this.getBoard().isOnBoard(possibility) && this.getBoard().getCellByCoords(possibility).isOccupiedByEnemy(this)) {
                    movement.add(possibility);
                }
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
            if (this.getBoard().isOnBoard(possibility) && !this.getBoard().getCellByCoords(possibility).isOccupied()) {
                movement.add(possibility);
            } else {
                if (this.getBoard().isOnBoard(possibility) && this.getBoard().getCellByCoords(possibility).isOccupiedByEnemy(this)) {
                    movement.add(possibility);
                }
                break;
            }
        }

        for (int b = 1; b <= 7; b++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.increment(new Coordinates(b, b));
            if (this.getBoard().isOnBoard(possibility) && !this.getBoard().getCellByCoords(possibility).isOccupied()) {
                movement.add(possibility);
            } else {
                if (this.getBoard().isOnBoard(possibility) && this.getBoard().getCellByCoords(possibility).isOccupiedByEnemy(this)) {
                    movement.add(possibility);
                }
                break;
            }
        }

        for (int c = 1; c <= 7; c++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.increment(new Coordinates(-c, c));
            if (this.getBoard().isOnBoard(possibility) && !this.getBoard().getCellByCoords(possibility).isOccupied()) {
                movement.add(possibility);
            } else {
                if (this.getBoard().isOnBoard(possibility) && this.getBoard().getCellByCoords(possibility).isOccupiedByEnemy(this)) {
                    movement.add(possibility);
                }
                break;
            }
        }

        for (int d = 1; d <= 7; d++) {
            Coordinates possibility = new Coordinates(this.getPosition());
            possibility.increment(new Coordinates(d, -d));
            if (this.getBoard().isOnBoard(possibility) && !this.getBoard().getCellByCoords(possibility).isOccupied()) {
                movement.add(possibility);
            } else {
                if (this.getBoard().isOnBoard(possibility) && this.getBoard().getCellByCoords(possibility).isOccupiedByEnemy(this)) {
                    movement.add(possibility);
                }
                break;
            }
        }

        return movement;

    }


}
