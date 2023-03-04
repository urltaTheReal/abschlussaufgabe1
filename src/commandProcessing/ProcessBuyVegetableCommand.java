package commandProcessing;

import Vegetables.VegetableType;
import abschlussaufgabe1.Market;
import abschlussaufgabe1.Player;

/**
 * @author urlta
 * 
 * this class implements the buy vegetable command
 *
 */
public class ProcessBuyVegetableCommand implements Processing {
    private Player player;
    private Market market;
    private VegetableType vegetable;
    private final String[] command;

    
    /**
     * @param player  player who entered the command
     * @param market  current state of the market
     * @param command the full line of text entered in the console split after each
     *                space
     */
    public ProcessBuyVegetableCommand(Player player, Market market, String[] command) {
        this.player = player;
        this.market = market;
        this.command = command;
    }

    public Player getPlayer() {
        return player;
    }

    public Market getMarket() {
        return market;
    }

    /**
     * executeCommand can only be successfully executed, if isCommandCorrect returns
     * true for the same input
     *
     */
    public void executeCommand() {
        setVegetable();
        player.getBarn().setGoldAmount(player.getBarn().getGoldAmount() - market.calculateCostOfVegetable(vegetable));
        player.getBarn().addVegetable(vegetable);
        System.out.println(
                "You have bought a " + command[2] + " for " + market.calculateCostOfVegetable(vegetable) + " gold.");
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
        setVegetable();
        if (!isCommandLogicallyCorrect(vegetable)) {
            return false;
        }

        return true;

    }

    private boolean isCommandLogicallyCorrect(VegetableType vegetable) {

        if (market.calculateCostOfVegetable(vegetable) > player.getBarn().getGoldAmount()) {
            System.out.println("Error: You have not enough money to perform that action");
            return false;
        }
        return true;
    }

    private boolean isCommandSemanticallyCorrect() {
        if (command.length > 3) {
            System.out.println(
                    "Error: too many arguments after buy vegetable command. you can only buy 1 vegetable and please seperate with only 1 space");
            return false;
        }
        if (command.length < 3) {
            System.out.println("Error: not enougth arguments after buy vegetable command. Please seperate arguments with spaces");
            return false;
        }
        if (!VegetableFinder.existsVegetableWithName(command[2])) {
            System.out.println(
                    "Error: The requested vegetable does not exsist, please type in one of the allowed vegetables");
            return false;
        }
        return true;
    }
    
    
    /**
     * setVegetable can only be successfully executed, if isCommandSemanticallyCorrect returns
     * true for the same command
     */
    private void setVegetable() {
        vegetable = VegetableFinder.findVegetableByName(command[2]);
    }

}
