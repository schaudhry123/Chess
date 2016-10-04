package main.pieces;

import main.model.Coordinate;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Samir on 9/3/16.
 */
public class Queen extends Piece {
    /**
     * Constructor taking in the queen's color
     */
    public Queen(String color) {
        setColor(color);
        imagePath = "/" + color + "-queen.png";
    }

    /**
     * Returns the type of this piece
     */
    public String getType() {
        return "Queen";
    }

    /**
     * String representation of a Queen
     * @return
     */
    @Override
    public String toString() {
        return getColor() + "Q";
    }

    /**
     * Return an ArrayList of Coordinates of possible moves the queen can make
     * @return
     */
    public ArrayList<Coordinate> possibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<Coordinate>();

        int x = getCoordinate().x;
        int y = getCoordinate().y;

        // Queen can move both diagonally and in a straight line
        checkAllDiagonals(moves, x, y);
        checkAllRowsColumns(moves, x, y);

        Collections.sort(moves);

        return moves;
    }
}