package main.pieces;

import main.Constants;
import main.model.Coordinate;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by samir on 9/3/16.
 */
public class Bishop extends Piece {
    /**
     * Constructor taking in an x and y coordinate
     */
    public Bishop(String color) {
        setColor(color);
        imagePath = "/" + color + "-bishop.png";
    }

    /**
     * String representation of a Rook
     * @return
     */
    @Override public String toString() {
        return getColor() + "B";
    }

    /**
     * Returns the type of this piece
     */
    public String getType() {
        return Constants.BISHOP;
    }

    /**
     * Return the list of possible legal moves the bishop can make
     * @return
     */
    public ArrayList<Coordinate> possibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<Coordinate>();

        int x = getCoordinate().x;
        int y = getCoordinate().y;

        checkAllDiagonals(moves, x, y);
        Collections.sort(moves);

        return moves;
    }
}
