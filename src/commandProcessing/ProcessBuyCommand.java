package commandProcessing;

import abschlussaufgabe1.Market;
import abschlussaufgabe1.Player;

/**
 * @author urlta
 * 
 * this class implements the buy command in all variations
 *
 */

public class ProcessBuyCommand implements Processing {
    private Player player;
    private Market market;
    private final String[] command;

    /**
     * @param player  player who entered the command
     * @param market  current state of the market
     * @param command the full line of text entered in the console split after each
     *                space
     */
    public ProcessBuyCommand(Player player, Market market, String[] command) {
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

        if (command[1].equals("land")) {
            ProcessBuyLandCommand buyLand = new ProcessBuyLandCommand(player, market, command);
            buyLand.executeCommand();
            this.player = buyLand.getPlayer();
            this.market = buyLand.getMarket();
        } else {
            ProcessBuyVegetableCommand buyVegetable = new ProcessBuyVegetableCommand(player, market, command);
            buyVegetable.executeCommand();
            this.player = buyVegetable.getPlayer();
            this.market = buyVegetable.getMarket();
        }

    }

    /**
     * isCommandCorrect checks the entered command for semantical and logical
     * Correctness. returns true if the command was entered correctly and works
     * logically
     * 
     * @return returns true if execute command can be executed without error
     */
    public boolean isCommandCorrect() {
        if (command.length < 3) {
            System.out.println(
                    "Error: missing argument after the buy command. Please choose between: land [x-coordinate] [y-coordinate] or vegetable [vegetable name]");
            return false;
        }
        if (!command[1].equals("land") && !command[1].equals("vegetable")) {
            System.out.println(
                    "Error: you entered a unknown parameter after buy. Please choose again between: land [x-coordinate] [y-coordinate] or vegetable [vegetable name] ");
            return false;
        } else if (command[1].equals("land")) {
            ProcessBuyLandCommand buyLand = new ProcessBuyLandCommand(player, market, command);
            return buyLand.isCommandCorrect();
        } else {
            ProcessBuyVegetableCommand buyVegetable = new ProcessBuyVegetableCommand(player, market, command);
            return buyVegetable.isCommandCorrect();
        }
    }

}
