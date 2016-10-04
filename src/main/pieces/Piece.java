package main.pieces;

import main.model.Board;
import main.model.Coordinate;

import java.util.ArrayList;

/**
 * Created by Samir on 9/1/16.
 *
 * Idea box: Pieces remember how many other pieces it has taken (i.e. level up)
 */
abstract public class Piece {
    private String color; // color of the piece (black vs. white)
    private Coordinate coordinate;
    public String imagePath;

    /**
     * ####################################
     *           Abstract Methods
     * ####################################
     */
    /**
     * Return an arraylist of coordinates of all the possible moves this piece can make
     * @return
     */
    abstract public ArrayList<Coordinate> possibleMoves();

    /**
     * Return the type of the piece
     * @return
     */
    abstract public String getType();

    /**
     * Return the string representation of the piece
     * @return
     */
    @Override
    abstract public String toString();

    /**
     * ####################################
     *         Getter/Setter Methods
     * ####################################
     */
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public Coordinate getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * ####################################
     *            Move Methods
     * ####################################
     */
    /**
     * Checks all the spaces diagonal to the location given by (x,y) and adds them to the moves
     * ArrayList if they are available
     * @param moves
     * @param x
     * @param y
     */
    protected void checkAllDiagonals(ArrayList<Coordinate> moves, int x, int y) {
        addCoordinatesInDirection(moves, x, y, 1, 1);
        addCoordinatesInDirection(moves, x, y, 1, -1);
        addCoordinatesInDirection(moves, x, y, -1, 1);
        addCoordinatesInDirection(moves, x, y, -1, -1);
    }

    /**
     * Checks all the spaces in the same row and column to the location given by (x,y) and adds them
     * to the moves ArrayList if they are available
     * @param moves
     * @param x
     * @param y
     */
    protected void checkAllRowsColumns(ArrayList<Coordinate> moves, int x, int y) {
        addCoordinatesInDirection(moves, x, y, 1, 0);
        addCoordinatesInDirection(moves, x, y, -1, 0);
        addCoordinatesInDirection(moves, x, y, 0, 1);
        addCoordinatesInDirection(moves, x, y, 0, -1);
    }

    /**
     * Check all the spaces in a direction given by xDirection and yDirection
     * @param moves
     * @param x
     * @param y
     * @param xDirection    -1, 0, or +1 to indicate negative, zero, or positive x-direction
     * @param yDirection    -1, 0, or +1 to indicate negative, zero, or positive y-direction
     */
    protected void addCoordinatesInDirection(ArrayList<Coordinate> moves, int x, int y, int xDirection, int yDirection) {
        boolean blocked = false;
        int index = 1;
        while (!blocked) {
            int xIndex = x + (index * xDirection);
            int yIndex = y + (index * yDirection);

            if (isEmptyCoordinate(xIndex, yIndex)) {
                moves.add(new Coordinate(xIndex, yIndex));
                index++;
            }
            else {
                addCoordinateIfAvailable(moves, xIndex, yIndex);
                blocked = true;
            }
        }
    }

    /**
     * Adds the 8 possible positions a knight can move to
     * @param moves
     * @param x
     * @param y
     */
    protected void addKnightCoordinates(ArrayList<Coordinate> moves, int x, int y) {
        addCoordinateIfAvailable(moves, x + 2, y + 1);
        addCoordinateIfAvailable(moves, x + 2, y - 1);
        addCoordinateIfAvailable(moves, x - 2, y + 1);
        addCoordinateIfAvailable(moves, x - 2, y - 1);
        addCoordinateIfAvailable(moves, x + 1, y + 2);
        addCoordinateIfAvailable(moves, x + 1, y - 2);
        addCoordinateIfAvailable(moves, x - 1, y + 2);
        addCoordinateIfAvailable(moves, x - 1, y - 2);
    }



    /**
     * ####################################
     *          Coordinate Methods
     * ####################################
     */
    /**
     * Update the coordinates of the piece
     */
    public void setCoordinate(int x, int y) {
        this.coordinate = new Coordinate(x, y);
    }

    /**
     * Return true/false if the coordinate is unoccupied
     * @param x
     * @param y
     * @return
     */
    protected boolean isEmptyCoordinate(int x, int y) {
        if (Coordinate.validCoordinate(x, y)) {
            Piece piece = Board.getPiece(x, y);
            if (piece == null)
                return true;
        }
        return false;
    }

    /**
     * Checks the space to see if its available or occupied by opposing piece
     * @return A coordinate for the space if it is available
     */
    protected boolean isCoordinateAvailable(int x, int y) {
        if (Coordinate.validCoordinate(x, y)) {
            Piece piece = Board.getPiece(x, y);
            if (piece == null || !color.equals(piece.getColor())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the coordinate indicated by location (x,y) to the moves ArrayList if it is available
     */
    protected boolean addCoordinateIfAvailable(ArrayList<Coordinate> moves, int x, int y) {
        if (isCoordinateAvailable(x, y)) {
            moves.add(new Coordinate(x, y));
            return true;
        }
        return false;
    }

    /**
     * Sets the pieces coordinates to null
     */
    public void removePiece() {
        this.coordinate = null;
    }
}