package commandProcessing;

import abschlussaufgabe1.Market;
import abschlussaufgabe1.Player;
import plantSpaces.PlantSpace;

/**
 * @author urlta
 * 
 * this class implements the buy land command
 *
 */
public class ProcessBuyLandCommand implements Processing {
    private Player player;
    private Market market;
    private final String[] command;
    private int xCoordinate;
    private int yCoordinate;
    private int cost;

    public Player getPlayer() {
        return player;
    }

    public Market getMarket() {
        return market;
    }

    /**
     * @param player  player who entered the command
     * @param market  current state of the market
     * @param command the full line of text entered in the console split after each
     *                space
     */
    public ProcessBuyLandCommand(Player player, Market market, String[] command) {
        this.player = player;
        this.market = market;
        this.command = command;
    }

    private boolean isCommandRightLength() {
        if (command.length < 4) {
            System.out.println(
                    "Error: missing argument after the buy command. Please choose between: land [x-coordinate] [y-coordinate] or vegetable [vegetable name]");
            return false;
        }
        if (command.length > 4) {
            System.out.println(
                    "Error: too many arguments after buy land command. please onle enter coordinates as arguments and seperate with only 1 space");
            return false;
        }
        return true;
    }

    private boolean isCommandParseable() {
        try {
            Integer.parseInt(command[2]);
            Integer.parseInt(command[3]);
        } catch (NumberFormatException nfe) {
            System.out.println(
                    "Error: one of the Coordinates couldnt be read as a Number, please use a number that can be saved as an int value");
            return false;
        }
        return true;
    }

    private boolean isCommandSemanticallyCorrect() {
        if (!isCommandRightLength()) {
            return false;
        }
        if (!isCommandParseable()) {
            return false;
        }
        return true;
    }

    /**
     * isCommandCorrect checks the entered command for semantical and logical
     * Correctness. returns true if the command was entered correctly and works
     * logically
     * 
     * @return returns true if execute command can be executed without error
     */

    public boolean isCommandCorrect() {
        if (isCommandSemanticallyCorrect()) {
            setValues();
            if (isCommandLogicallyCorrect(xCoordinate, yCoordinate, cost)) {
                return true;
            }
        }

        return false;
    }

    private boolean isCommandLogicallyCorrect(int xCoordinate, int yCoordinate, int cost) {
        if (market.getCardsLeftInDeck() == 0) {
            System.out.println("Error: no cards left to buy");
            return false;
        }

        if (player.getPlayerBoard().isElementOnBoardOnPosition(xCoordinate, yCoordinate)) {
            System.out.println("Error: It is impossible to place a tile on the same position as an exsisting tile");
            return false;
        }

        if (!player.getPlayerBoard().isNextToOrAboveElementOnBoard(xCoordinate, yCoordinate)) {
            System.out.println("Error: the position must be next to or above to one of the alredy set tiles");
            return false;
        }
        if (player.getBarn().getGoldAmount() < cost) {
            System.out.println("Error: Player has not enough money for this Transaction");
            return false;
        }
        return true;
    }

    /**
     * setValues can only be successfully executed, if isCommandSemanticallyCorrect
     * returns true for the same command
     */
    private void setValues() {
        xCoordinate = Integer.parseInt(command[2]);
        yCoordinate = Integer.parseInt(command[3]);
        cost = market.calculateCostOfLand(xCoordinate, yCoordinate);
    }

    /**
     * Does all steps to buy a Land: removes money form barn assigns coordinates
     * places Tile on board.
     * 
     * executeCommand can only be successfully executed, if isCommandCorrect returns
     * true for the same command
     *
     */
    public void executeCommand() {
        setValues();

        player.getBarn().setGoldAmount(player.getBarn().getGoldAmount() - cost);
        System.out.println("You have bought a " + market.getTopCard().getLongName() + " for " + cost + " gold.");
        PlantSpace newCard = market.getTopCard();
        newCard.setPosition(xCoordinate, yCoordinate);
        player.getPlayerBoard().addElementToBoard(newCard);
        market.removeTopCardFromDeck();
    }
}
