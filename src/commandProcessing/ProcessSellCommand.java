package commandProcessing;

import Vegetables.VegetableTracker;
import abschlussaufgabe1.Market;
import abschlussaufgabe1.Player;

/**
 * @author urlta
 * 
 * this class implements the sell command in all variations
 *
 */
public class ProcessSellCommand implements Processing {
    VegetableTracker vegetablesSold = new VegetableTracker();
    private int totalSold = 0;
    private int goldEarned = 0;
    private Player player;
    private Market market;
    private final String[] command;

    /**
     * @param player  player who entered the command
     * @param market  current state of the market
     * @param command the full line of text entered in the console split after each
     *                space
     */
    public ProcessSellCommand(Player player, Market market, String[] command) {
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
        calculateWhatTosell();
        goldEarned = calculateEarnedMoney();
        totalSold = calculateTotalSold();
        sellVegetables();
        printConfirmationMessage();
    }

    private void calculateWhatTosell() {
        if (command.length > 1 && command[1].equals("all")) {
            selectAllToSell();
        } else {
            readVegetablesToSell();
        }
    }

    private void sellVegetables() {
        removeSoldVegetablesFromBarn(vegetablesSold.getVegetableAmount());
        market.addVegetable(vegetablesSold.getVegetableAmount());
        player.getBarn().setGoldAmount(player.getBarn().getGoldAmount() + goldEarned);
    }

    private boolean isSpecialCase(String[] command) {
        if (command.length == 1) {
            return true;
        }
        if (command[1].equals("all") && command.length == 2) {
            return true;
        }
        return false;
    }

    private boolean doAllVegetablesExsist() {
        for (int i = 1; i < command.length; i++) {
            if (!VegetableFinder.existsVegetableWithName(command[i])) {
                System.out.println("Error: unable to find requested vegetable, your argument no." + i + " was faulty");
                return false;
            }
        }
        return true;
    }

    private int[] calculateVegetablesToSell() {
        int[] vegetablesToSell = new int[vegetablesSold.getLengthOfVegetables()];
        for (int i = 1; i < command.length; i++) {
            int index = vegetablesSold.getIndexOfVegetable(VegetableFinder.findVegetableByName(command[i]));
            vegetablesToSell[index]++;
        }
        return vegetablesToSell;
    }

    private boolean doesPlayerHaveEnougthVegetables() {
        for (int i = 0; i < vegetablesSold.getLengthOfVegetables(); i++) {
            if (vegetablesSold.getVegetableAmount()[i] > player.getBarn().getVegetableAmount()[i]) {
                System.out.println(
                        "Error: don´t bring shame on the queens name. you tried to sell something you don´t own");
                return false;
            }
        }
        return true;
    }

    private boolean isCommandLogicallyCorrect() {
        if (isSpecialCase(command)) {
            return true;
        }
        if (!doAllVegetablesExsist()) {
            return false;
        }
        readVegetablesToSell();

        if (!doesPlayerHaveEnougthVegetables()) {
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

        if (!isCommandLogicallyCorrect()) {
            return false;
        }
        return true;
    }

    private void selectAllToSell() {
        vegetablesSold.setVegetableAmount(player.getBarn().getVegetableAmount());
    }

    private int calculateEarnedMoney() {
        int totalEarnings = 0;

        for (int i = 0; i < vegetablesSold.getLengthOfVegetables(); i++) {
            int earningsOfVegetableType = market.calculateCostOfVegetable(vegetablesSold.getVegetables()[i]);
            earningsOfVegetableType = earningsOfVegetableType * vegetablesSold.getVegetableAmount()[i];
            totalEarnings += earningsOfVegetableType;
        }
        return totalEarnings;

    }

    private int calculateTotalSold() {
        int amount = 0;
        for (int i = 0; i < vegetablesSold.getLengthOfVegetables(); i++) {
            amount += vegetablesSold.getVegetableAmount()[i];
        }
        return amount;
    }

    private void removeSoldVegetablesFromBarn(int[] soldAmount) {
        for (int i = 0; i < soldAmount.length; i++) {
            player.getBarn().subtractVegetable(vegetablesSold.getVegetables()[i], soldAmount[i]);
        }
    }

    private void printConfirmationMessage() {
        if (totalSold == 1) {
            System.out.println("You have sold 1 vegetable for " + goldEarned + " gold.");
        } else {
            System.out.println("You have sold " + totalSold + " vegetables for " + goldEarned + " gold.");
        }
    }

    /**
     * readVegetablesToSel can only be successfully executed, if
     * isCommandSemanticallyCorrect and doAllVegetablesExsist() return true for the
     * same input
     */
    private void readVegetablesToSell() {
        vegetablesSold.setVegetableAmount(calculateVegetablesToSell());
    }

}
