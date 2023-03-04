package board;

import java.util.ArrayList;
import java.util.Collections;

import Barn.Barn;
import plantSpaces.*;

/**
 * @author urlta
 * 
 *         Board class manages everything that has to do with the players board
 *
 */
public class Board {
    private ArrayList<PlantSpace> board = new ArrayList<>();
    private PlantSpace startingGarden = new Garden();
    private PlantSpace startingGarden2 = new Garden();
    private PlantSpace startingField = new Field();
    private int plantsGrownLastTurn;

    /**
     * sets the 3 starting tiles for the board at the beginning of the game
     */
    public Board() {
        startingField.setPosition(0, 1);
        startingGarden.setPosition(-1, 0);
        startingGarden2.setPosition(1, 0);

        board.add(startingField);
        board.add(startingGarden);
        board.add(startingGarden2);
    }

    /**
     * prints the visualization of the board with the chosen barn
     * 
     * @param barn chosen barn to print
     */
    public void showBoard(Barn barn) {
        PrintBoard printer = new PrintBoard(board);
        printer.printBoard(barn);
    }

    /**
     * calculates the total plants grown at the end of the turn
     */
    public void endRound() {
        plantsGrownLastTurn = 0;
        for (int i = 0; i < board.size(); i++) {
            plantsGrownLastTurn += board.get(i).endRound();
        }
    }

    /**
     * prints out the required starting message if vegetables grew last turn
     */
    public void startRound() {
        if (plantsGrownLastTurn == 1) {
            System.out.println(plantsGrownLastTurn + " vegetable has grown since your last turn.");
        } else if (plantsGrownLastTurn > 0) {
            System.out.println(plantsGrownLastTurn + " vegetables has grown since your last turn.");
        }
    }

    /**
     * compares the axis value of List element with that of the next Element
     * 
     * @param axis         axis can be x or y
     * @param relation     can only be either '<', '>', or '='
     * @param currentIndex current index of element on board
     * 
     * @return if the axis value of the current element has the chosen relation to
     *         the axis value of the next element
     */
    private boolean hasCoordinateRelationToNextIndex(char axis, char relation, int currentIndex) {
        switch (relation) {
        case '<':
            return board.get(currentIndex).getPosition(axis) < board.get(currentIndex + 1).getPosition(axis);
        case '>':
            return board.get(currentIndex).getPosition(axis) > board.get(currentIndex + 1).getPosition(axis);
        case '=':
            return board.get(currentIndex).getPosition(axis) == board.get(currentIndex + 1).getPosition(axis);
        default:
            System.out.println("Error: unsupported relation was entered");
            return false;
        }
    }

    /**
     * Adds the new Element into the board, so that the y-Axis value of the elements
     * with higher indices are equal or lower. Also sorts, that elements with the
     * same y-Axis value but higher x-Axis values have higher indices then the new
     * Element
     * 
     * @param newTile must be a Plant space with initialized Coordinates otherwise
     *                the method fails
     */
    public void addElementToBoard(PlantSpace newTile) {
        int currentIndex = 0;
        boolean isLastElement = false;
        board.add(0, newTile);

        while (!isLastElement && hasCoordinateRelationToNextIndex('y', '<', currentIndex)) {

            Collections.swap(board, currentIndex, currentIndex + 1);
            currentIndex++;
            if (currentIndex == board.size() - 1) { // stops Loop if element is now at the last index
                isLastElement = true;
            }
        }

        while (!isLastElement && hasCoordinateRelationToNextIndex('y', '=', currentIndex)
                && hasCoordinateRelationToNextIndex('x', '>', currentIndex)) {
            Collections.swap(board, currentIndex, currentIndex + 1);
            currentIndex++;
            if (currentIndex == board.size() - 1) { // stops Loop if element is now at the last index
                isLastElement = true;
            }
        }

    }

    private boolean isPosition1NextToOrAbovePosition2(int xCoordinate1, int yCoordinate1, int xCoordinate2,
            int yCoordinate2) {
        return (Math.abs(xCoordinate1 - xCoordinate2) == 1 && yCoordinate1 == yCoordinate2)
                || (yCoordinate1 - yCoordinate2 == 1 && xCoordinate1 == xCoordinate2);
    }

    /**
     * checks if the position of two points in a 2 dimensional system are equal
     * 
     * @param xCoordinate1 xCoordinate of 1st position
     * @param yCoordinate1 yCoordinate of 1st position
     * @param xCoordinate2 xCoordinate of 2nd position
     * @param yCoordinate2 yCoordinate of 2nd position
     * @return true if points are on the same position
     */
    protected boolean positionIsEqual(int xCoordinate1, int yCoordinate1, int xCoordinate2, int yCoordinate2) {
        return xCoordinate1 == xCoordinate2 && yCoordinate1 == yCoordinate2;
    }

    /**
     * checks if something is on board of witch the entered coordinates are above or
     * next to
     * 
     * @param xCoordinate entered x coordinate
     * @param yCoorinate  entered y coordinate
     * @return true if something is on board of witch the entered coordinates are
     *         above or next to
     */
    public boolean isNextToOrAboveElementOnBoard(int xCoordinate, int yCoorinate) {
        for (int i = 0; i < board.size(); i++) {
            if (isPosition1NextToOrAbovePosition2(xCoordinate, yCoorinate, board.get(i).getPosition('x'),
                    board.get(i).getPosition('y'))) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if something is on board of witch the entered coordinates equal to
     * 
     * @param xCoordinate entered x coordinate
     * @param yCoorinate  entered y coordinate
     * @return true if something is on board of witch the entered coordinates equal
     *         to
     */
    public boolean isElementOnBoardOnPosition(int xCoordinate, int yCoordinate) {
        if (isPositionBarn(xCoordinate, yCoordinate)) { // special Case Barn is at 0,0 nothing can be placed there
            return true;
        }
        for (int i = 0; i < board.size(); i++) {
            if (positionIsEqual(xCoordinate, yCoordinate, board.get(i).getPosition('x'),
                    board.get(i).getPosition('y'))) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns PlantSpace on chosen position only works properly if
     * isElementOnBoardOnPosition returned true for the same coordinates and is
     * Position barn has to return false for the same coordinates
     * 
     * @param xCoordinate of PlantSpace to be returned
     * @param yCoordinate of PlantSpace to be returned
     * @return plant space on coordinate (or first plant space if called on a non
     *         exsisting tile)
     */
    public PlantSpace getTileOnPosition(int xCoordinate, int yCoordinate) {
        for (int i = 0; i < board.size(); i++) {
            if (positionIsEqual(xCoordinate, yCoordinate, board.get(i).getPosition('x'),
                    board.get(i).getPosition('y'))) {
                return board.get(i);
            }
        }
        System.out.println(
                "Error: tried to getTileOnPosition without checking that a tile on position exists(0,0 is not a PlantingSpace)");
        return board.get(0);

    }

    /**
     * returns if the entered position is the position of the barn
     * 
     * @param xCoordinate entered x coordinate
     * @param yCoordinate entered y coordinate
     * @return true f the entered position is the position of the barn(0, 0)
     */
    public boolean isPositionBarn(int xCoordinate, int yCoordinate) {
        return xCoordinate == 0 && yCoordinate == 0;
    }

}
