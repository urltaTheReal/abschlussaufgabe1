package barn;

import Vegetables.VegetableTracker;
import Vegetables.VegetableTrackerCommands;
import Vegetables.VegetableType;

/**
 * @author urlta
 * 
 *         manages vegetables and gold for each player
 *
 */
public class Barn implements VegetableTrackerCommands {
    private VegetableTracker storedVegetables = new VegetableTracker();
    private int spoilCountdown = 6;
    private int goldAmount;
    private boolean wasVegetableSpoiled = false;

    /**
     * Initialization of barn, at the beginning of the game with 1 of each vegetable
     * 
     * @param startingGold gold saved in barn at the beginning of the day
     */
    public Barn(int startingGold) {
        storedVegetables.setWholeVegetableAmount(1);
        // sets starting amount of all vegetables to 1
        goldAmount = startingGold;
    }

    /**
     * empty constructor for children classes
     */
    protected Barn() {

    }

    /**
     * returns the amount of each vegetable indices corresponding to vegetables in
     * VegetableType.values()
     */
    public int[] getVegetableAmount() {
        return storedVegetables.getVegetableAmount();
    }

    /**
     * @return round until the vegetables in the barn will spoil (-1 if no countdown
     *         is running)
     */
    public int getSpoilCountdown() {
        return spoilCountdown;
    }

    /**
     * @return returns gold of player saved in barn
     */
    public int getGoldAmount() {
        return goldAmount;
    }

    /**
     * sets gold amount to new gold amount
     * 
     * @param goldAmount new gold amount
     */
    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    /**
     * @param vegetable vegetable of which the amount in the barn is returned
     * @return amount of vegetables of chosen type in barn
     */
    public int getAmountOfVegetable(VegetableType vegetable) {
        return storedVegetables.getAmountOfVegtable(vegetable);
    }

    /**
     * @return if vegetable was spoiled at the end of last round
     */
    public boolean isWasVegetableSpoiled() {
        return wasVegetableSpoiled;
    }

    public void show() {
        PrintBarn printer = new PrintBarn(storedVegetables, goldAmount, spoilCountdown);
        printer.show();
    }

    /**
     * Decreases spoilCountdown Deletes (spoils) every Vegetable in the barn, if
     * spoilCountdown falls to 0 if everything is spoiled, countdown will be set to
     * -1
     */
    public void endRound() {
        if (spoilCountdown > 0) {
            spoilCountdown--;
        }
        if (spoilCountdown == 0) {
            storedVegetables.setWholeVegetableAmount(0);

            spoilCountdown = -1;
            wasVegetableSpoiled = true;
            // Sets amount of Vegetables as 0 for every vegetable(spoils vegetables)
        }

    }

    /**
     * performs calculations and prints text which have to be made at the beginning
     * of a turn
     */
    public void startRound() {
        if (wasVegetableSpoiled) {
            System.out.println("The vegetables in your barn are spoiled.");
            wasVegetableSpoiled = false;
        }
    }

    /**
     * adds one vegetable of the chosen type to the saved amount of vegetables
     */
    public void addVegetable(VegetableType vegetable) {
        storedVegetables.addToVegtableAmount(vegetable);
        if (spoilCountdown == -1) {
            spoilCountdown = 6;
        }
    }

    /**
     *
     * adds amount vegetables of the chosen type to the saved amount of vegetables
     * 
     * @param vegetable vegetable to be added
     * @param amount    amount of vegetable to be added
     */
    public void addVegetable(VegetableType vegetable, int amount) {
        for (int i = 0; i < amount; i++) {
            addVegetable(vegetable);
        }
    }

    /**
     * reduces the amount Value for sold vegetables of the chosen vegetableType by 1
     */
    public void subtractVegetable(VegetableType vegetable) {
        storedVegetables.subtractFromVegtableAmount(vegetable);
        setBarnEmptyIfEmpty();

    }

    /**
     * subtracts amount vegetables of the chosen type to the saved amount of
     * vegetables
     * 
     * @param vegetable vegetable to be subtracted
     * @param amount    amount of vegetable to be subtracted
     */
    public void subtractVegetable(VegetableType vegetable, int amount) {
        storedVegetables.subtractFromVegtableAmount(vegetable, amount);
        setBarnEmptyIfEmpty();
    }

    private boolean isBarnEmpty() {
        for (int i = 0; i < storedVegetables.getVegetableAmount().length; i++) {
            if (storedVegetables.getVegetableAmount()[i] != 0) {
                return false;
            }
        }
        return true;
    }

    private void setBarnEmptyIfEmpty() {
        if (isBarnEmpty()) {
            spoilCountdown = -1;
        }
    }
}
