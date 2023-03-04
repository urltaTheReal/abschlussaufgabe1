package board;

import java.util.ArrayList;

import Barn.Barn;
import plantSpaces.*;

/**
 * @author urlta
 * 
 *         Print board is responsible for the visualization and printing of the
 *         board
 *
 */
public class PrintBoard extends Board {
    private ArrayList<PlantSpace> board = new ArrayList<>();
    private final int lowestXCoordinate;
    private final int highestXCoordinate;
    private final int lowestYCoordinate = 0; // building below a y of 0 is impossible
    private final int highestYCoordinate;
    private String[] wholeYLine = { "", "", "" };
    private int currentXCoordinate;
    private int currentYCoordinate;
    private int currentCardIndex = 0;

    /**
     * to print the board we have to know how big the field is. to know that we have
     * to know the largest and smallest tile on each axis. this will be calculated
     * on initialization
     * 
     * @param board
     */
    protected PrintBoard(ArrayList<PlantSpace> board) {
        this.board = board;
        lowestXCoordinate = findLowestAxisValue('x');
        highestXCoordinate = findHighestAxisValue('x');
        highestYCoordinate = findHighestAxisValue('y');
        currentXCoordinate = lowestXCoordinate;
        currentYCoordinate = highestYCoordinate;
    }

    /**
     * prints the board
     * 
     * @param barn barn to be printed
     */
    public void printBoard(Barn barn) {

        while (currentYCoordinate != lowestYCoordinate - 1) {
            addCurrentCoordinateToLine(barn);

            if (isCursorAtTheEndOfLine()) {
                printAndResetLine();
            }

            jumpToNextCoordinate();

        }
    }

    private boolean isCurrentPositionACard() {
        return currentCardIndex != board.size() && positionIsEqual(board.get(currentCardIndex).getPosition('x'),
                board.get(currentCardIndex).getPosition('y'), currentXCoordinate, currentYCoordinate);
    }

    private boolean isCursorAtTheEndOfLine() {
        return currentXCoordinate == highestXCoordinate;
    }

    private void jumpToNextCoordinate() {
        if (isCursorAtTheEndOfLine()) {
            jumpToNextLine();
        } else {
            currentXCoordinate++;
        }
    }

    private void jumpToNextLine() {
        currentXCoordinate = lowestXCoordinate;
        currentYCoordinate--;
    }

    private void printAndResetLine() {
        for (int i = 0; i < wholeYLine.length; i++) {
            System.out.println(wholeYLine[i]);
            wholeYLine[i] = "";
        }
    }

    private void addCurrentCoordinateToLine(Barn barn) {
        if (isCurrentPositionACard()) {
            wholeYLine = addCard(board.get(currentCardIndex), wholeYLine, currentCardIndex); // if we have to
                                                                                             // add a card, we
                                                                                             // add a card
            currentCardIndex++;
        } else if (currentXCoordinate == 0 && currentYCoordinate == 0) {
            wholeYLine = addBarn(wholeYLine, barn); // on 0,0 the Barn will be added
        } else if (shortEmptynessNeeded()) {
            wholeYLine = addShortEmptyCoordinate(wholeYLine); // adds 5 spaces if conditions are met(see
                                                              // shortEmptynessNeeded)
        } else {
            wholeYLine = addEmptyCoordinate(wholeYLine); // if neither is the case, empty Space will be added
        }
    }

    /**
     * returns the count-down value which can be displayed as wanted
     * 
     * @param countdownValue value to be printed. must be a number from 1-9 or -1
     * @return the countdownValue as a char, or in case of countdown Value being -1
     *         returns a * char
     */
    private char countdownToPrint(int countdownValue) {
        if (countdownValue == -1) {
            return '*';
        } else {
            return (char) (countdownValue + '0');
        }
    }

    private String addTopLineOfCard(PlantSpace currentCard) {
        String separator = separatorIfTileToTheLeft();

        switch (currentCard.getShortName().length()) {
        case 1:

            return separator + " " + currentCard.getShortName() + " " + countdownToPrint(currentCard.getGrowCountdown())
                    + " |";

        case 2:

            return separator + " " + currentCard.getShortName() + " " + countdownToPrint(currentCard.getGrowCountdown())
                    + "|";

        case 3:
            return separator + currentCard.getShortName() + " " + countdownToPrint(currentCard.getGrowCountdown())
                    + "|";

        default:
            return "Error: Short Name Of card " + currentCard.getLongName() + " was too long:"
                    + currentCard.getShortName();
        }
    }

    private String addMidLineOfCard(PlantSpace currentCard) {
        String separator = separatorIfTileToTheLeft();
        if (currentCard.getPlantedVegetable() != null) {
            return separator + "  " + Character.toUpperCase((currentCard.getPlantedVegetable().getName().charAt(0)))
                    + "  |";

        } else {
            return separator + "     |";
        }
    }

    private String addBotLineOfCard(PlantSpace currentCard) {
        String separator = separatorIfTileToTheLeft();
        return separator + " " + currentCard.getCurrentAmount() + "/" + currentCard.getCapacity() + " |";
    }

    private String[] addCard(PlantSpace currentCard, String[] currentYLine, int index) {

        currentYLine[0] += addTopLineOfCard(currentCard);
        currentYLine[1] += addMidLineOfCard(currentCard);
        currentYLine[2] += addBotLineOfCard(currentCard);
        return currentYLine;
    }

    private String[] addBarn(String[] yLine, Barn barn) {

        yLine[0] += "     ";
        if (barn.getSpoilCountdown() != -1) {
            yLine[1] += " B " + barn.getSpoilCountdown() + " ";
        } else {
            yLine[1] += " B " + '*' + " ";
        }
        yLine[2] += "     ";
        return yLine;
    }

    private String[] addEmptyCoordinate(String[] yLine) {
        for (int i = 0; i < yLine.length; i++) {
            yLine[i] += "      ";
        }
        return yLine;
    }

    private String[] addShortEmptyCoordinate(String[] yLine) {
        for (int i = 0; i < yLine.length; i++) {
            yLine[i] += "     ";
        }
        return yLine;
    }

    /**
     * Checks if a shorter version of nothing(5 spaces instead of 6) are needed.
     * this is the case right after a placed tile, if another will be placed later
     * on the same height (otherwise the separators don`t line up anymore)
     * 
     * @param xCoordinate current xCoordinate
     * @param yCoordinate current yCoordinate
     * @param index       current index of next tile to print on console
     * @return if a only 5 spaces are needed this Coordinate
     */
    private boolean shortEmptynessNeeded() {
        if (currentCardIndex == 0) {
            return false;
        }
        return (board.get(currentCardIndex).getPosition('y') == board.get(currentCardIndex - 1).getPosition('y'))
                && board.get(currentCardIndex - 1).getPosition('x') == currentXCoordinate - 1;
        // checks if one Card is to come fot the current y value, if yes, it checks if
        // the current position is exactly 1 space after the last placed card. If both
        // apply, true will bee returned
    }

    /**
     * finds lowest point with a card on the x/y- axis
     * 
     * @param axis decides if the x or y value is searched for. x is standard
     *             changes value for y. z axis is not supported
     * @return lowest point with a card on desired axis
     */
    private int findLowestAxisValue(char axis) {
        int lowestAxisValue = 0;
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i).getPosition(axis) < lowestAxisValue) {
                lowestAxisValue = board.get(i).getPosition(axis);
            }
        }
        return lowestAxisValue;
    }

    /**
     * finds highest point with a card on the x/y- axis
     * 
     * @param axis decides if the x or y value is searched for. x is standard
     *             changes value for y. z axis is not supported
     * @return highest point with a card on desired axis
     */
    private int findHighestAxisValue(char axis) {
        int highestAxisValue = 0;
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i).getPosition(axis) > highestAxisValue) {
                highestAxisValue = board.get(i).getPosition(axis);
            }
        }
        return highestAxisValue;
    }

    private boolean isTileToTheLeft() {
        if (currentCardIndex == 0) {// if card is first card, no card could have come before
            return false;
        }

        return board.get(currentCardIndex).getPosition('x') - board.get(currentCardIndex - 1).getPosition('x') == 1;
    }

    private String separatorIfTileToTheLeft() {
        String separator = "";
        if (!isTileToTheLeft()) {
            separator = "|";
        }
        return separator;
    }

}
