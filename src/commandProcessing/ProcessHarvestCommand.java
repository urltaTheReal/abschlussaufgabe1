package commandProcessing;

import Vegetables.VegetableType;
import abschlussaufgabe1.Player;
import plantSpaces.PlantSpace;


/**
 * @author urlta
 * 
 * this class implements the harvest command in all variations
 *
 */

public class ProcessHarvestCommand implements Processing {
    private Player player;
    private int xCoordinate;
    private int yCoordinate;
    private int amount;
    private final String[] command;

    /**
     * @param player  player who entered the command
     * @param command the full line of text entered in the console split after each
     *                space
     */
    public ProcessHarvestCommand(Player player, String[] command) {
        this.player = player;
        this.command = command;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * executeCommand can only be successfully executed, if isCommandCorrect returns
     * true for the same input
     *
     */
    public void executeCommand() {
        setValues();
        VegetableType harvestedVegetable = returnVegetableTypeOnTile(xCoordinate, yCoordinate);
        harvest(xCoordinate, yCoordinate, amount);
        if (amount == 1) {
            System.out.println("You have harvested 1 " + harvestedVegetable.getName());

        } else {
            System.out.println("You have harvested " + amount + " " + harvestedVegetable.getNamePlural());
        }

    }

    private boolean isParseingEntrysPossible() {
        try {
            Integer.parseInt(command[1]);
            Integer.parseInt(command[2]);
            Integer.parseInt(command[3]);
        } catch (NumberFormatException nfe) {
            System.out.println(
                    "Error: the 3 arguments after the harvest Command have to be integer numbers in the order : [x-Coordinate] [y-Coordinate] [Amount]");
            return false;
        }
        return true;
    }

    /**
     * setValues can only be successfully executed, if isCommandSemanticallyCorrect
     * returns true for the same command
     */
    private void setValues() {
        xCoordinate = Integer.parseInt(command[1]);
        yCoordinate = Integer.parseInt(command[2]);
        amount = Integer.parseInt(command[3]);
    }

    private boolean isCommandSizeCorrect() {
        if (command.length > 4) {
            System.out.println(
                    "Error: too many arguments were entered, please enter only the 3 required and please seperate with only 1 space");
            return false;
        }
        if (command.length < 4) {
            System.out.println(
                    "Error: please use after the harvest command 3 arguments in the order : [x-Coordinate] [y-Coordinate] [Amount]");
            return false;
        }
        return true;
    }

    private boolean areCoordinatesHarvestable(int xCoordinate, int yCoordinate, int amount) {
        if (player.getPlayerBoard().isPositionBarn(xCoordinate, yCoordinate)) {
            System.out.println("Error: you cant harvest the Barn, please choose a viabhle tile");
            return false;
        }
        if (!player.getPlayerBoard().isElementOnBoardOnPosition(xCoordinate, yCoordinate)) {
            System.out.println("Error: nothing was found on given Coordinates");
            return false;
        }
        if (player.getPlayerBoard().getTileOnPosition(xCoordinate, yCoordinate).getCurrentAmount() < amount) {
            System.out.println(
                    "Error: the amount you tried to harvest exeeds the available amount, poease choose a smaller amount");
            return false;
        }
        return true;
    }

    private boolean isCommandSemanticallyCorrect() {
        if (!isCommandSizeCorrect()) {
            return false;
        }
        if (!isParseingEntrysPossible()) {
            return false;
        }
        return true;
    }

    private boolean isCommandLogicallyCorrect(int xCoordinate, int yCoordinate, int amount) {
        if (!areCoordinatesHarvestable(xCoordinate, yCoordinate, amount)) {
            return false;
        }
        if (amount < 1) {
            System.out.println("Error: you have to harvest at least 1 vegetable");
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
        if (!isCommandSemanticallyCorrect()) {
            return false;
        }
        setValues();
        if (!isCommandLogicallyCorrect(xCoordinate, yCoordinate, amount)) {
            return false;
        }
        return true;
    }

    private VegetableType returnVegetableTypeOnTile(int xCoordinate, int yCoordinate) {
        return player.getPlayerBoard().getTileOnPosition(xCoordinate, yCoordinate).getPlantedVegetable();
    }

    private void harvest(int xCoordinate, int yCoordinate, int amount) {
        PlantSpace spaceToHarvest = player.getPlayerBoard().getTileOnPosition(xCoordinate, yCoordinate);
        VegetableType harvestedVegetable = returnVegetableTypeOnTile(xCoordinate, yCoordinate);
        spaceToHarvest.setCurrentAmount(spaceToHarvest.getCurrentAmount() - amount);
        player.getBarn().addVegetable(harvestedVegetable, amount);
        if (spaceToHarvest.getCurrentAmount() == 0) {
            spaceToHarvest.setGrowCountdown(-1);
            spaceToHarvest.setPlantedVegetable(null);
        } else {
            spaceToHarvest.setGrowCountdown(spaceToHarvest.getPlantedVegetable().getTimeToGrow());
        }

    }

}
