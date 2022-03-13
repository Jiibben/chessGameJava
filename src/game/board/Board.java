package game.board;

import game.actor.pieces.*;
import utilities.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    public static int NUMBEROFROW = 8;
    public static int NUMBEROFCOL = 8;
    private final ArrayList<ArrayList<BoardCell>> cells = new ArrayList<>(NUMBEROFROW);


    public Board() {
        this.setLayout(new GridLayout(NUMBEROFROW, NUMBEROFCOL));
        for (int y = 0; y < NUMBEROFROW; y++) {
            ArrayList<BoardCell> line = new ArrayList<>();
            for (int x = 0; x < NUMBEROFCOL; x++) {
                BoardCell cell = new BoardCell(new Coordinates(x, y));
                line.add(cell);
                this.add(cell);
            }
            this.cells.add(line);

        }
        this.setVisible(true);
        this.placePieces();
    }

    private BoardCell getCell(int x, int y) {
        return this.cells.get(y).get(x);
    }




    //todo better the pieces placement
    private void placePieces() {
        for (int y = 0; y < NUMBEROFROW; y++) {
            for (int x = 0; x < NUMBEROFCOL; x++) {

                //pawn placement
                if (y == 1) {
                    Piece blackPawn = new Pawn(Piece.Side.BLACK);
                    this.getCell(x, y).addPiece(blackPawn);
                }
                else if (y == 6){
                    Piece whitePawn = new Pawn(Piece.Side.WHITE);
                    this.getCell(x, y).addPiece(whitePawn);

                }

                //Rook placement

                if (y == 0 && x == 0 || y == 0 && x == 7){
                    Piece blackRook = new Rook(Piece.Side.BLACK);
                    this.getCell(x,y).addPiece(blackRook);
                }
                else if (y == 7 && x == 0 || y == 7 && x == 7){
                    Piece whiteRook = new Rook(Piece.Side.WHITE);
                    this.getCell(x,y).addPiece(whiteRook);
                }

                //knight placement
                if (y == 0 && x == 1 || y == 0 && x == 6){
                    Piece blackKnight = new Knight(Piece.Side.BLACK);
                    this.getCell(x,y).addPiece(blackKnight);
                }
                else if (y == 7 && x == 1 || y == 7 && x == 6){
                    Piece whiteKnight = new Knight(Piece.Side.WHITE);
                    this.getCell(x,y).addPiece(whiteKnight);
                }

                //bishop placement
                if (y == 0 && x == 2 || y == 0 && x == 5){
                    Piece blackBishop = new Bishop(Piece.Side.BLACK);
                    this.getCell(x,y).addPiece(blackBishop);
                }
                else if (y == 7 && x == 2 || y == 7 && x == 5){
                    Piece whiteBishop = new Bishop(Piece.Side.WHITE);
                    this.getCell(x,y).addPiece(whiteBishop);
                }

                //king placement
                if (y == 0 && x == 3){
                    Piece blackKing = new King(Piece.Side.BLACK);
                    this.getCell(x,y).addPiece(blackKing);
                }
                else if (y == 7 && x == 3){
                    Piece whiteKing = new King(Piece.Side.WHITE);
                    this.getCell(x,y).addPiece(whiteKing);
                }
                //queen placement
                if (y == 0 && x == 4){
                    Piece blackQueen = new Queen(Piece.Side.BLACK);
                    this.getCell(x,y).addPiece(blackQueen);
                }
                else if (y == 7 && x == 4){
                    Piece whiteQueen= new Queen(Piece.Side.WHITE);
                    this.getCell(x,y).addPiece(whiteQueen);
                }


            }
        }

    }

}
