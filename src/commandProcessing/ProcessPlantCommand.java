package commandProcessing;

import Vegetables.VegetableType;
import abschlussaufgabe1.Player;
import plantSpaces.PlantSpace;

/**
 * @author urlta
 * 
 * this class implements and executes the plant command
 *
 */
public class ProcessPlantCommand implements Processing {

    private Player player;
    private final String[] command;
    private int xCoordinate;
    private int yCoordinate;
    private VegetableType vegetableToPlant;
    private PlantSpace spaceToPlantOn;
    
    /**
     * @param player  player who entered the command
     * @param command the full line of text entered in the console split after each
     *                space
     */

    public ProcessPlantCommand(Player player, String[] command) {
        this.player = player;
        this.command = command;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * executeCommand can only be successfully executed, if isCommandCorrect returns
     * true for the same input
     *
     */
    public void executeCommand() {
        setValues();
        spaceToPlantOn.plantVegetable(vegetableToPlant);
        spaceToPlantOn.setCurrentAmount(1);
        player.getBarn().subtractVegetable(vegetableToPlant);
    }

    private boolean isCommandRightSize() {
        if (command.length < 4) {
            System.out.println(
                    "Error: not enough arguments were entered, please enter [x-Coordinate] [y-Coordinate] [Vegetable-name] after the plant command");
            return false;
        }
        if (command.length > 4) {
            System.out.println("Error: command too long, please enter only the 3 required arguments after the command and please seperate with only 1 space");
            return false;
        }
        return true;
    }

    private boolean isCommandParseable() {
        try {
            Integer.parseInt(command[1]);
            Integer.parseInt(command[2]);
        } catch (NumberFormatException nfe) {
            System.out.println(
                    "Error: the first 2 arguments after the plant Command have to be integer numbers in the order : [x-Coordinate] [y-Coordinate]");
            return false;
        }
        return true;
    }

    private boolean isCommandSemanticallyCorrect() {
        if (!isCommandRightSize()) {
            return false;
        }
        if (!isCommandParseable()) {
            return false;
        }
        if (!VegetableFinder.existsVegetableWithName(command[3])) {
            System.out.println("Error: cant find a vegetable with coosen name, please choose a viable vegetable");
            return false;
        }
        return true;

    }

    private boolean isCommandLogicallyCorrect(int xCoordinate, int yCoordinate, VegetableType vegetable) {
        if (player.getPlayerBoard().isPositionBarn(xCoordinate, yCoordinate)) {
            System.out.println(
                    "Error: cant plant the vegetable on the barn, please enter the coordinates of a viable space to plant");
            return false;
        }
        if (!player.getPlayerBoard().isElementOnBoardOnPosition(xCoordinate, yCoordinate)) {
            System.out.println(
                    "Error: cant find a space to plant the vegetable on choosen coordinates, please enter the coordinates of a viable space to plant");
            return false;
        }

        setValues();
        
        if(player.getBarn().getAmountOfVegetable(vegetable) == 0) {
            System.out.println("Error: You dont have enough vegetables to perform this action, please buy some from the store");
            return false;
        }

        if (spaceToPlantOn.getCurrentAmount() != 0) {
            System.out.println("Error: The choosen tile has already vegetables on it please choose an empty tile");
            return false;
        }

        if (!isVegetableAllowedOnSpace()) {
            System.out.println("Error: The choosen Vegetable can´t be planted on the choosen ground");
            return false;
        }
        return true;

    }

    private boolean isVegetableAllowedOnSpace() {
        for (VegetableType currentVegetable : spaceToPlantOn.getAllowedVegetables()) {
            if (vegetableToPlant == currentVegetable) {
                return true;
            }
        }
        return false;
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
        setPosition();
        if (!isCommandLogicallyCorrect(xCoordinate, yCoordinate, VegetableFinder.findVegetableByName(command[3]))) {
            return false;
        }
        return true;
    }

    /**
     * setValues can only be successfully executed, if isCommandCorrect returns true
     * for the same command
     */
    private void setValues() {
        setPosition();
        vegetableToPlant = VegetableFinder.findVegetableByName(command[3]);
        spaceToPlantOn = player.getPlayerBoard().getTileOnPosition(xCoordinate, yCoordinate);
    }

    /**
     * setValues can only be successfully executed, if isCommandSemanticallyCorrect
     * returns true for the same command
     */
    private void setPosition() {
        xCoordinate = Integer.parseInt(command[1]);
        yCoordinate = Integer.parseInt(command[2]);
    }

}
