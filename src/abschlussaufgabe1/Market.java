package abschlussaufgabe1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Vegetables.VegetableTracker;
import Vegetables.VegetableTrackerCommands;
import Vegetables.VegetableType;
import plantSpaces.PlantSpace;

/**
 * @author urlta
 * 
 *         Market class is responsible for managing all tasks and values of the
 *         market such as calculating the price tables or managing the land
 *         which can be bought
 *
 *
 */
public class Market implements VegetableTrackerCommands {
    private CardDeck deck;
    private VegetableTracker soldVegetables = new VegetableTracker();
    private int priceStageCarrotMushroom = 2; // counting starts at 0
    private int priceStageTomatoSalad = 2;
    private int[][] priceTableMushroomCarrot = { { 12, 3 }, { 15, 2 }, { 16, 2 }, { 17, 2 }, { 20, 1 } };
    private int[][] priceTableTomatoSalad = { { 3, 6 }, { 5, 5 }, { 6, 4 }, { 7, 3 }, { 9, 2 } };

    /**
     * When market is initialized, it initializes the card deck, of which land will
     * be bought from
     * 
     * @param random       the random object initialized with seed at the beginning
     *                     of the game
     * @param playerAmount the amount of players needed to determine how many cards
     *                     are needed
     */
    public Market(Random random, int playerAmount) {
        deck = new CardDeck(random, playerAmount);
    }

    /**
     * returns the amount of vegetables which were sold this turn indices are
     * corresponding to VegetableType.values()
     */
    public int[] getVegetableAmount() {
        return soldVegetables.getVegetableAmount();
    }

    /**
     * @return cards left in the deck
     */
    public int getCardsLeftInDeck() {
        return deck.getCardsLeftInDeck();
    }

    /**
     * @return first card in the deck
     */
    public PlantSpace getTopCard() {
        return deck.getTopCard();
    }

    /**
     * deletes first card from the deck
     */
    public void removeTopCardFromDeck() {
        deck.removeTopCardFromDeck();
    }

    /**
     * this method is responsible for successfully executing the show market command
     */
    public void show() {
        ArrayList<String> namesToPrint = new ArrayList<>(Arrays.asList("mushrooms", "carrots", "tomatoes", "salads"));
        ArrayList<Integer> costToPrint = new ArrayList<>();
        costToPrint.add(calculateCostOfVegetable(VegetableType.MUSHROOM));
        costToPrint.add(calculateCostOfVegetable(VegetableType.CARROT));
        costToPrint.add(calculateCostOfVegetable(VegetableType.TOMATO));
        costToPrint.add(calculateCostOfVegetable(VegetableType.SALAD));
        namesToPrint = FormatClass.calculateWordsToPrint(namesToPrint, costToPrint);
        for (String vegtableAndCost : namesToPrint) {
            System.out.println(vegtableAndCost);
        }
    }

    /**
     * This method returns the current worth of the entered vegetable
     * 
     * @param vegetable vegetable of which the price should be calculated
     * @return the current price of the chosen vegetable
     */
    public int calculateCostOfVegetable(VegetableType vegetable) {
        switch (vegetable) {
        case CARROT:
            return priceTableMushroomCarrot[priceStageCarrotMushroom][1];

        case MUSHROOM:
            return priceTableMushroomCarrot[priceStageCarrotMushroom][0];

        case TOMATO:
            return priceTableTomatoSalad[priceStageTomatoSalad][0];

        case SALAD:
            return priceTableTomatoSalad[priceStageTomatoSalad][1];

        default:
            System.out.println("Error: calculateCostOfVegetable was used with unknown vegetable");
            return -1;
        }
    }

    /**
     * calculates the price of a price of land for entered coordinates
     * 
     * @param xCoordinate xCoordinate of place new land will be placed
     * @param yCoordinate yCoordinate of place new land will be placed
     * @return price of Land for chosen parameters
     */
    public int calculateCostOfLand(int xCoordinate, int yCoordinate) {
        return (Math.abs(yCoordinate) + Math.abs(xCoordinate) - 1) * 10;
    }

    /**
     * executes all steps to be executed when a turn ends
     */
    public void endTurn() {
        changePriceTable();
        soldVegetables.setWholeVegetableAmount(0);
    }

    private void changePriceTable() {
        int maxIndex = 4;
        priceStageCarrotMushroom += (soldVegetables.getVegetableAmount()[0] - soldVegetables.getVegetableAmount()[3])
                / 2; // shifts the index variable up for every 2 carrots sold more than mushrooms and
                     // shifts one down for every 2 mushrooms sold more
        if (priceStageCarrotMushroom < 0) {
            priceStageCarrotMushroom = 0;
        } else if (priceStageCarrotMushroom > maxIndex) {
            priceStageCarrotMushroom = maxIndex;
        }

        priceStageTomatoSalad += (soldVegetables.getVegetableAmount()[1] - soldVegetables.getVegetableAmount()[2]) / 2;
        if (priceStageTomatoSalad < 0) {
            priceStageTomatoSalad = 0;
        } else if (priceStageTomatoSalad > maxIndex) {
            priceStageTomatoSalad = maxIndex;
        }
    }

    /**
     * adds one vegetable of the chosen type to the saved amount of vegetables
     */
    public void addVegetable(VegetableType vegetableToAdd) {
        soldVegetables.addToVegtableAmount(vegetableToAdd);
    }

    /**
     * adds the chosen amount values for vegetables for each vegetable to the chosen
     * amount
     * 
     * @param amountToAdd chosen amount of Vegetables to add. indices must be
     *                    corresponding to VegetableType.values()
     */
    public void addVegetable(int[] amountToAdd) {
        soldVegetables.addToVegtableAmount(amountToAdd);
    }

    /**
     * reduces the amount Value for sold vegetables of the chosen vegetableType by 1
     */
    public void subtractVegetable(VegetableType vegetableToSubtract) {
        soldVegetables.subtractFromVegtableAmount(vegetableToSubtract);
    }
}
