package main.model;

import main.pieces.Piece;

/**
 * Created by samir on 9/20/16.
 */
public class Move {
    public Piece piece;
    public Piece existing;
    public Coordinate start;
    public Coordinate destination;

    public Move(Piece piece, Piece existing, Coordinate start, Coordinate destination) {
        this.piece = piece;
        this.existing = existing;
        this.start = start;
        this.destination = destination;
    }
}
