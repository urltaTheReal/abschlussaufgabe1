package barn;

import java.util.ArrayList;
import java.util.Collections;

import Vegetables.VegetableTracker;
import Vegetables.VegetableType;
import abschlussaufgabe1.FormatClass;

/**
 * @author urlta
 * 
 *         this class is responsible for calculating how, and then printing the
 *         text to visualize the barn
 *
 */
public class PrintBarn extends Barn {
    private VegetableTracker storedVegetables;
    private int goldAmount;
    private int spoilCountdown;
    private ArrayList<String> namesToPrint = new ArrayList<>();
    private ArrayList<Integer> amountsToPrint = new ArrayList<>();
    ArrayList<String> namesAndValuesFormatted = new ArrayList<>();
    private int sumOfVegetables = 0;
    private int longestWordLength;

    /**
     * This constructor sets ale values needed for the code to work
     * 
     * @param storedVegetables the Vegetable tracker keeping track of the vegetables
     *                         to be printed
     * @param goldAmount       the gold amount to be printed
     * @param spoilCountdown   the spoil count down (to be printed if not -1)
     */
    protected PrintBarn(VegetableTracker storedVegetables, int goldAmount, int spoilCountdown) {
        this.storedVegetables = storedVegetables;
        this.goldAmount = goldAmount;
        this.spoilCountdown = spoilCountdown;
    }

    private ArrayList<Integer> calculateIndexOrderForPrinting(VegetableType[] vegetables, int[] vegetableAmount) {
        ArrayList<Integer> indicesOrderedForPrinting = calculateOrderOfNames(vegetables);
        return sortByAmount(indicesOrderedForPrinting, vegetableAmount);
    }

    private ArrayList<Integer> sortByAmount(ArrayList<Integer> listToOrder, int[] vegetableAmount) {
        for (int i = 1; i < listToOrder.size(); i++) {
            int indexToMove = listToOrder.size() - 1 - i;
            listToOrder = shiftDown(listToOrder, indexToMove, vegetableAmount);
        }
        return listToOrder;
    }

    private ArrayList<Integer> shiftDown(ArrayList<Integer> listToShift, int indexToMove, int[] vegetableAmount) {
        boolean movingFinished = false;
        while (!movingFinished) {
            if (indexToMove == listToShift.size() - 1) {
                movingFinished = true;
            } else if (isAmountOfFirstIndexHigherThanSecond(listToShift.get(indexToMove),
                    listToShift.get(indexToMove + 1), vegetableAmount)) {
                Collections.swap(listToShift, indexToMove, indexToMove + 1);
                indexToMove++;
            } else {
                movingFinished = true;
            }
        }
        return listToShift;
    }

    private boolean isAmountOfFirstIndexHigherThanSecond(int index1, int index2, int[] vegetableAmount) {
        return vegetableAmount[index1] > vegetableAmount[index2];
    }

    private ArrayList<Integer> calculateOrderOfNames(VegetableType[] vegetables) {
        boolean[] wasUsed = new boolean[vegetables.length];
        for (int i = 0; i < vegetables.length; i++) {
            wasUsed[i] = false;
        }

        ArrayList<Integer> indicesSortetByNames = new ArrayList<>();
        for (int i = 0; i < vegetables.length; i++) {
            indicesSortetByNames.add(calculateIndexOfEarliestLetter(vegetables, wasUsed));
            wasUsed[indicesSortetByNames.get(i)] = true;
        }
        return indicesSortetByNames;
    }

    private int calculateIndexOfEarliestLetter(VegetableType[] vegetables, boolean[] wasUsed) {
        int index = -1;
        for (int i = 0; i < vegetables.length; i++) {
            if (index == -1 && wasUsed[i] != true) {
                index = i;
            } else if (wasUsed[i] != true
                    && getFirstCharOfName(vegetables[i]) < getFirstCharOfName(vegetables[index])) {
                index = i;
            }
        }
        return index;
    }

    private char getFirstCharOfName(VegetableType vegetable) {
        return vegetable.getName().charAt(0);
    }

    private void filterVegetablesAndAddToList(ArrayList<Integer> vegetableIndicesOrdered) {
        for (int i = 0; i < getVegetableAmount().length; i++) {
            int indexToAdd = vegetableIndicesOrdered.get(i);
            if (getVegetableAmount()[indexToAdd] != 0) {
                addNameAndAmountToPrintList(indexToAdd);
            }
        }
    }

    private void addNameAndAmountToPrintList(int indexToAdd) {
        namesToPrint.add(getVegetables()[indexToAdd].getNamePlural());
        amountsToPrint.add(getVegetableAmount()[indexToAdd]);
        sumOfVegetables += getVegetableAmount()[indexToAdd];
    }

    /**
     * executes the show command and prints the visualization of the barn
     */
    @Override
    public void show() {
        ArrayList<Integer> vegetableIndicesInOrder = calculateIndexOrderForPrinting(getVegetables(),
                getVegetableAmount());
        printBarnText();
        filterVegetablesAndAddToList(vegetableIndicesInOrder);
        // the code above adds the vegetables with amount > 0 to the names and values to
        // be printed
        if (sumOfVegetables != 0) {
            namesToPrint.add("Sum");
            amountsToPrint.add(sumOfVegetables);
        }
        // adds the sum of the vegetables, if any exist
        namesToPrint.add("Gold");
        amountsToPrint.add(goldAmount);

        longestWordLength = FormatClass.calculatelengthOfLongestWordWithNumber(namesToPrint, amountsToPrint) + 2;
        // +2, because the method does calculates the longest word without ": " (witch
        // has length of 2)
        namesAndValuesFormatted = FormatClass.calculateWordsToPrint(namesToPrint, amountsToPrint);
        // adds
        printWholeText();
    }

    private void printWholeText() {
        for (int i = 0; i < namesAndValuesFormatted.size(); i++) {
            if (sumOfVegetables != 0 && i == namesAndValuesFormatted.size() - 2) {
                System.out.println(seperatorAfterVegtables(longestWordLength));
                System.out.println(namesAndValuesFormatted.get(i));
                // adds separator before the last 2 arguments(before sum and gold)
            } else if (sumOfVegetables != 0 && i == namesAndValuesFormatted.size() - 1) {
                System.out.println();
                System.out.println(namesAndValuesFormatted.get(i));
                // adds a empty line before printing the gold with value
            } else {
                System.out.println(namesAndValuesFormatted.get(i));
            }
        }
    }

    private void printBarnText() {
        if (spoilCountdown > 1) {
            System.out.println("Barn (spoils in " + spoilCountdown + " turns)");
        } else if (spoilCountdown == 1) {
            System.out.println("Barn (spoils in " + spoilCountdown + " turn)");
        } else {
            System.out.println("Barn");
        }
    }

    private String seperatorAfterVegtables(int lengthOfLongestWord) {
        String seperator = "";
        for (int i = 0; i < lengthOfLongestWord; i++) {
            seperator += "-";
        }
        return seperator;
    }

    /**
     * returns vegetableAmount from this class instead of barn
     */
    @Override
    public int[] getVegetableAmount() {
        return storedVegetables.getVegetableAmount();
    }

    /**
     * @return returns the vegetables (in current version the same as
     *         VegetableType.values())
     */
    public VegetableType[] getVegetables() {
        return storedVegetables.getVegetables();
    }

}
