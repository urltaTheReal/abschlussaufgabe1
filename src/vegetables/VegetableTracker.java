package vegetables;

/**
 * @author urlta this class is responsible for saving and managing different
 *         amounts of Vegetables
 */
public class VegetableTracker {
    private final VegetableType[] vegetables;
    private int[] vegetableAmount;

    /**
     * initializes vegetable array including all vegetables and corresponding amount
     * values
     */
    public VegetableTracker() {
        vegetables = VegetableType.values();
        vegetableAmount = new int[vegetables.length];
    }

    public int[] getVegetableAmount() {
        return vegetableAmount;
    }

    /**
     * replaces the vegetable amount array with the one given
     * 
     * @param vegetableAmount new vegetable amount
     */
    public void setVegetableAmount(int[] vegetableAmount) {
        for (int i = 0; i < vegetableAmount.length; i++) {
            this.vegetableAmount[i] = vegetableAmount[i];
        }
    }

    public VegetableType[] getVegetables() {
        return vegetables;
    }

    public int getLengthOfVegetables() {
        return vegetables.length;
    }

    /**
     * adds the amount values given in the entered array to the current one index by
     * index
     * 
     * @param vegetableAmountToAdd vegetable amount values that will be added to the
     *                             current values
     */
    public void addToVegtableAmount(int[] vegetableAmountToAdd) {
        for (int i = 0; i < vegetableAmountToAdd.length; i++) {
            this.vegetableAmount[i] += vegetableAmountToAdd[i];
        }
    }

    /**
     * adds one vegetable of the chosen type to the saved amount of vegetables
     * 
     * @param adds 1 amount of the vegetable to add
     */
    public void addToVegtableAmount(VegetableType vegetableToAdd) {
        vegetableAmount[getIndexOfVegetable(vegetableToAdd)]++;
    }

    /**
     * adds the chosen amount of the chosen vegetable to the current amount
     * 
     * @param vegetableToAdd chosen vegetable
     * @param amount         chosen amount
     */
    public void addToVegtableAmount(VegetableType vegetableToAdd, int amount) {
        for (int i = 0; i < amount; i++) {
            addToVegtableAmount(vegetableToAdd);
        }
    }

    /**
     * subtracts 1 of the chosen vegetable from the current amount if this method
     * should be used to set a value below 0 an error will be printed, and the
     * amount will be set to 0 again
     * 
     * @param vegetableToSubtract chosen vegetable to subtract
     */
    public void subtractFromVegtableAmount(VegetableType vegetableToSubtract) {
        vegetableAmount[getIndexOfVegetable(vegetableToSubtract)]--;
        if (vegetableAmount[getIndexOfVegetable(vegetableToSubtract)] < 0) {
            System.out.println("Error: Tried to substract vegetables, that dont exist");
            vegetableAmount[getIndexOfVegetable(vegetableToSubtract)] = 0;
        }
    }

    /**
     * subtracts the chosen amount of the chosen vegetable from the current amount
     * if this method should be used to set a value below 0 an error will be
     * printed, and the amount will be set to 0 again
     * 
     * @param vegetableToSubtract
     * @param amount
     */
    public void subtractFromVegtableAmount(VegetableType vegetableToSubtract, int amount) {
        for (int i = 0; i < amount; i++) {
            subtractFromVegtableAmount(vegetableToSubtract);
        }
    }

    /**
     * adds 1 to the amount of every vegetable in the array, if a vegetable exists
     * twice or more in that array, the amount will be raised accordingly
     * 
     * @param vegetablesToAdd array of all vegetables which amounts should be increased
     */
    public void addToVegtableAmount(VegetableType[] vegetablesToAdd) {
        for (VegetableType vegetableToAdd : vegetablesToAdd) {
            addToVegtableAmount(vegetableToAdd);
        }
    }

    /**
     * gives all vegetables the chosen amount as value
     * @param amount new amount of all vegetables
     */
    public void setWholeVegetableAmount(int amount) {
        for (int i = 0; i < vegetableAmount.length; i++) {
            vegetableAmount[i] = amount;
        }
    }

    /**
     * returns the index of chosen vegetable
     * @param vegetable chosen vegetable
     * @return  the index of chosen vegetable
     */
    public int getIndexOfVegetable(VegetableType vegetable) {
        for (int i = 0; i < vegetables.length; i++) {
            if (vegetables[i] == vegetable) {
                return i;
            }
        }
        return -1;
    }

    /**
     * returns the current amount of the chosen vegetable
     * @param vegetable chosen vegetable
     * @return the current amount of the chosen vegetable
     */
    public int getAmountOfVegtable(VegetableType vegetable) {
        return getVegetableAmount()[getIndexOfVegetable(vegetable)];
    }

}
