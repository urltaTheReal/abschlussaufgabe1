package vegetables;

/**
 * @author urlta
 * 
 * commands that should be expected to exist if a vegetable tracker is implemented in a class
 *
 */
public interface VegetableTrackerCommands {

    
    /**
     * adds 1 of the chosen vegetable to the whole amount
     * @param vegetableToAdd chosen vegetable
     */
    public void addVegetable(VegetableType vegetableToAdd);
    
    /**
     * subtracts 1 of the chosen vegetable from the whole amount
     * @param vegetableToAdd chosen vegetable
     */
    public void subtractVegetable(VegetableType vegetableToSubtract);
    
    
    /**
     * returns the full array of vegetable amounts
     * @return  the full array of vegetable amounts
     */
    public int[] getVegetableAmount();
    
}
